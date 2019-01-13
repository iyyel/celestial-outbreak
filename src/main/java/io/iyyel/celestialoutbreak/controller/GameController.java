package io.iyyel.celestialoutbreak.controller;

import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.graphics.ScreenRenderer;
import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.menu.WelcomeMenu;
import io.iyyel.celestialoutbreak.menu.main_menu.*;
import io.iyyel.celestialoutbreak.menu.options_menu.ConfigOptionsMenu;
import io.iyyel.celestialoutbreak.menu.options_menu.GameOptionsMenu;
import io.iyyel.celestialoutbreak.menu.options_menu.PlayerOptionsMenu;
import io.iyyel.celestialoutbreak.menu.play.PauseMenu;
import io.iyyel.celestialoutbreak.menu.play.PostLevelMenu;
import io.iyyel.celestialoutbreak.menu.play.PreLevelMenu;
import io.iyyel.celestialoutbreak.menu.play.SelectLevelMenu;
import io.iyyel.celestialoutbreak.menu.player_options.PlayerCreateMenu;
import io.iyyel.celestialoutbreak.menu.player_options.PlayerDeleteMenu;
import io.iyyel.celestialoutbreak.menu.player_options.PlayerSelectMenu;
import io.iyyel.celestialoutbreak.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.net.URL;

/*
 * This is the GameController class.
 * This is the canvas upon where the game is drawn and controlled.
 */
public class GameController extends Canvas implements Runnable {

    /*
     * SCREEN_WIDTH and SCREEN_HEIGHT will be multiplied by
     * SCREEN_SCALE to make the width and height of the screenRenderer.
     * Update rate is the targeted update rate.
     */
    private final int SCREEN_WIDTH = 640;
    private final int SCREEN_HEIGHT = 360;
    private final int SCREEN_SCALE = 2;
    private final int SCREEN_UPDATE_RATE = 60;

    /*
     * Timers to switch the mainMenu background color in a slower interval than 60 times a second.
     */
    private final int INITIAL_MENU_COLOR_TIMER_VALUE = SCREEN_UPDATE_RATE;
    private int menuColorTimer = INITIAL_MENU_COLOR_TIMER_VALUE;

    /*
     * gameThread is the main thread of the application.
     * isRunning determines whether the gameController loop is running or not.
     */
    private Thread gameThread;
    private boolean isRunning = false;

    /*
     * BufferedImage for the actual rendering of the screenRenderer.
     * pixels[] is the extracted pixels from the image.
     */
    private final BufferedImage image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /*
     * JFrame and screenRenderer renderer objects.
     */
    private final JFrame gameFrame;
    private final ScreenRenderer screenRenderer;

    /*
     * Singleton objects.
     */
    private final TextHandler textHandler = TextHandler.getInstance();
    private final Utils utils = Utils.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    /*
     * Objects used for menu's.
     */
    private final WelcomeMenu welcomeMenu;
    private final MainMenu mainMenu;
    private final SelectLevelMenu selectLevelMenu;
    private final PreLevelMenu preLevelMenu;
    private final PostLevelMenu postLevelMenu;
    private final PauseMenu pauseMenu;
    private final ScoresMenu scoresMenu;
    private final ControlsMenu controlsMenu;
    private final OptionsMenu optionsMenu;
    private final PlayerOptionsMenu playerOptionsMenu;
    private final PlayerSelectMenu playerSelectMenu;
    private final PlayerCreateMenu playerCreateMenu;
    private final PlayerDeleteMenu playerDeleteMenu;
    private final GameOptionsMenu gameOptionsMenu;
    private final ConfigOptionsMenu configOptionsMenu;
    private final AboutMenu aboutMenu;
    private final ExitMenu exitMenu;

    private Color menuColor;

    /*
     * NONE,
     * WELCOME,
     * MAIN,
     *   PLAY,
     *       SELECT_LEVEL,
     *       PRE_LEVEL,
     *       POST_LEVEL,
     *       PAUSE,
     *   SCORES,
     *   CONTROLS,
     *   OPTIONS,
     *       PLAYER_OPTIONS,
     *           PLAYER_SELECT,
     *           PLAYER_CREATE,
     *           PLAYER_DELETE,
     *       CONFIG_OPTIONS,
     *   ABOUT,
     *   EXIT,
     *   ERROR
     */
    public enum State {
        NONE,
        WELCOME,
        MAIN,
        PLAY,
        SELECT_LEVEL,
        PRE_LEVEL,
        POST_LEVEL,
        PAUSE,
        SCORES,
        CONTROLS,
        OPTIONS,
        PLAYER_OPTIONS,
        PLAYER_SELECT,
        PLAYER_CREATE,
        PLAYER_DELETE,
        GAME_OPTIONS,
        CONFIG_OPTIONS,
        ABOUT,
        EXIT,
        ERROR
    }

    /*
     * Current state of the gameController.
     * Starts with showing the main menu state.
     */
    private State state = State.MAIN;
    private State prevState = State.NONE;

    /*
     * Constructor of GameController class.
     * Initialize above objects here. Start gameController loop, etc.
     */
    public GameController() {
        /* UI Look and Feel */
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        });

        /* Load players */
        try {
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            // Handle this.
            e.printStackTrace();
        }

        /* Create JFrame */
        gameFrame = new JFrame();

        /* Set dimensions of the JFrame and Canvas */
        Dimension size = new Dimension(SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE);

        /* Initialize the canvas. */
        initCanvas(size);

        /* Initialize game icon. */
        initGameIcon();

        /* Initialize levelHandler */
        levelHandler.initLevels(this);

        /* Create screenRenderer renderer */
        screenRenderer = new ScreenRenderer(SCREEN_WIDTH, SCREEN_HEIGHT, pixels);

        /* Create menu objects */
        welcomeMenu = new WelcomeMenu(this);
        mainMenu = new MainMenu(this);
        selectLevelMenu = new SelectLevelMenu(this);
        preLevelMenu = new PreLevelMenu(this);
        postLevelMenu = new PostLevelMenu(this);
        pauseMenu = new PauseMenu(this);
        scoresMenu = new ScoresMenu(this);
        controlsMenu = new ControlsMenu(this);
        optionsMenu = new OptionsMenu(this);
        playerOptionsMenu = new PlayerOptionsMenu(this);
        playerSelectMenu = new PlayerSelectMenu(this);
        playerCreateMenu = new PlayerCreateMenu(this);
        playerDeleteMenu = new PlayerDeleteMenu(this);
        gameOptionsMenu = new GameOptionsMenu(this);
        configOptionsMenu = new ConfigOptionsMenu(this);
        aboutMenu = new AboutMenu(this);
        exitMenu = new ExitMenu(this);
        menuColor = utils.generatePastelColor(0.9F, 9000F);

        /* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        addKeyListener(inputHandler);

        /* If no players exist, assume its first run */
        if (playerDAO.getPlayerList().isEmpty()) {
            state = State.WELCOME;
        }

        /* Initialize the JFrame and start the gameController loop */
        initFrame();

        /* Log that the game has been initialized */
        fileHandler.writeLog(textHandler.GAME_INIT_FINISHED);
    }

    /*
     * GameController loop with frames and updates counter.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / SCREEN_UPDATE_RATE;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
                if (optionsHandler.isFpsLockEnabled()) {
                    render();
                    frames++;
                }
            }
            if (!optionsHandler.isFpsLockEnabled()) {
                render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                if (optionsHandler.isVerboseLogEnabled()) {
                    /* Is this correctly calculated? (ram) */
                    double allocatedRam = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024.0 * 1024.0);
                    gameFrame.setTitle(textHandler.GAME_TITLE + " | Version: " + textHandler.GAME_VERSION +
                            " - " + textHandler.vPerformanceMsg(frames, updates, allocatedRam));
                    fileHandler.writeLog(textHandler.vPerformanceMsg(frames, updates, allocatedRam));
                } else {
                    gameFrame.setTitle(textHandler.GAME_TITLE);
                }
                updates = 0;
                frames = 0;
            }
        }
    }

    /*
     * Update the state of the gameController entities as well as the different menus.
     */
    private void update() {
        /* Update the currently pressed keys. */
        inputHandler.update();

        /* Play sound based on current state if sound is enabled */
        if (optionsHandler.isSoundEnabled()) {
            soundHandler.playStateSound(state, prevState, true, false);
        } else {
            /* If not, stop all sound immediately */
            soundHandler.stopAllSound();
        }

        /* Let the current gameController state decide what to update exactly. */
        switch (state) {
            case WELCOME:
                switchMenuColor();
                welcomeMenu.update();
                break;
            case MAIN:
                switchMenuColor();
                mainMenu.update();
                break;
            case SELECT_LEVEL:
                selectLevelMenu.update();
                break;
            case PRE_LEVEL:
                preLevelMenu.update();
                break;
            case POST_LEVEL:
                postLevelMenu.update();
                break;
            case PAUSE:
                pauseMenu.update();
                break;
            case PLAY:
                levelHandler.update();
                pauseCheck();
                break;
            case SCORES:
                scoresMenu.update();
                break;
            case CONTROLS:
                controlsMenu.update();
                break;
            case OPTIONS:
                optionsMenu.update();
                break;
            case PLAYER_OPTIONS:
                playerOptionsMenu.update();
                break;
            case PLAYER_SELECT:
                playerSelectMenu.update();
                break;
            case PLAYER_CREATE:
                switchMenuColor();
                playerCreateMenu.update();
                break;
            case PLAYER_DELETE:
                playerDeleteMenu.update();
                break;
            case GAME_OPTIONS:
                gameOptionsMenu.update();
                break;
            case CONFIG_OPTIONS:
                configOptionsMenu.update();
                break;
            case ABOUT:
                aboutMenu.update();
                break;
            case EXIT:
                exitMenu.update();
                break;
            default:
                break;
        }
    }

    /*
     * Render gameController entities as well as the menus and everything else.
     */
    private void render() {
        /* Get BufferStrategy object handle. */
        BufferStrategy bs = getBufferStrategy();

        /* Create the buffer strategy if it does not already exist. */
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        /* Clear the screenRenderer, i.e. make it go all black. */
        screenRenderer.clear();

        /* Let the current gameController state decide whether to render a level's color or the menu's color. */
        renderBgColor();

        /* Get the graphics2D object from the buffer strategy. */
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        /* Enable some sweet antialiasing to make the graphics look smoother. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        /* Draw the buffered image to the screen. */
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        /* Make the current state of the gameController decide what to render. */
        renderMenu(g);

        /* Dispose the graphics and show the buffer to the screenRenderer. */
        g.dispose();
        bs.show();
    }

    private void renderBgColor() {
        switch (state) {
            case WELCOME:
            case MAIN:
            case SELECT_LEVEL:
            case SCORES:
            case CONTROLS:
            case OPTIONS:
            case PLAYER_OPTIONS:
            case PLAYER_SELECT:
            case PLAYER_CREATE:
            case PLAYER_DELETE:
            case GAME_OPTIONS:
            case CONFIG_OPTIONS:
            case ABOUT:
            case EXIT:
                screenRenderer.render(menuColor);
                break;
            case PLAY:
            case PAUSE:
            case PRE_LEVEL:
            case POST_LEVEL:
                screenRenderer.render(levelHandler.getActiveLevel().getColor());
                break;
            default:
                break;
        }
    }

    private void renderMenu(Graphics2D g) {
        switch (state) {
            case WELCOME:
                welcomeMenu.render(g);
                break;
            case MAIN:
                mainMenu.render(g);
                break;
            case SELECT_LEVEL:
                selectLevelMenu.render(g);
                break;
            case PRE_LEVEL:
                preLevelMenu.render(g);
                break;
            case POST_LEVEL:
                postLevelMenu.render(g);
                break;
            case PAUSE:
                pauseMenu.render(g);
                break;
            case PLAY:
                levelHandler.render(g);
                break;
            case SCORES:
                scoresMenu.render(g);
                break;
            case CONTROLS:
                controlsMenu.render(g);
                break;
            case OPTIONS:
                optionsMenu.render(g);
                break;
            case PLAYER_OPTIONS:
                playerOptionsMenu.render(g);
                break;
            case PLAYER_SELECT:
                playerSelectMenu.render(g);
                break;
            case PLAYER_CREATE:
                playerCreateMenu.render(g);
                break;
            case PLAYER_DELETE:
                playerDeleteMenu.render(g);
                break;
            case GAME_OPTIONS:
                gameOptionsMenu.render(g);
                break;
            case CONFIG_OPTIONS:
                configOptionsMenu.render(g);
                break;
            case ABOUT:
                aboutMenu.render(g);
                break;
            case EXIT:
                exitMenu.render(g);
                break;
            default:
                break;
        }
    }

    /*
     * Initialize options for the JFrame.
     */
    private void initFrame() {
        gameFrame.setTitle(textHandler.GAME_TITLE);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(this);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.requestFocus();
    }

    private void initCanvas(Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        if (gameFrame != null) {
            gameFrame.setSize(size);
        }
    }

    private void initGameIcon() {
        /* Game Icon */
        URL url = ClassLoader.getSystemResource("icon/app_icon_small.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        gameFrame.setIconImage(img);
    }

    /*
     * Continuously switch the MENU_CLIP background with a nice pastel color.
     */
    private void switchMenuColor() {
        if (menuColorTimer == 0) {
            menuColor = utils.generatePastelColor(0.8F, 9000F);
            menuColorTimer = INITIAL_MENU_COLOR_TIMER_VALUE;
        } else {
            menuColorTimer--;
        }
    }

    private void pauseCheck() {
        pauseMenu.decInputTimer();
        if (inputHandler.isPausePressed() && pauseMenu.isInputAvailable()) {
            pauseMenu.resetInputTimer();
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_MENU_BTN_USE).play(false);
            levelHandler.getActiveLevel().pauseSound();
            utils.pauseTimer();
            switchState(State.PAUSE);
        }
    }

    /*
     * Start method for the gameController thread/loop.
     */
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this, "GameController");
            gameThread.start();
        }
    }

    /*
     * Stop method for the gameController thread/loop. Will exit the application.
     */
    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            fileHandler.writeLog(textHandler.GAME_TITLE + " shutting down.");
            System.exit(0);
        }
    }

    /*
     * Change the current state of the gameController.
     */
    public void switchState(State state) {
        if (state == null || state == State.NONE) {
            return;
        }
        prevState = this.state;
        this.state = state;
    }

    /*
     * Getters and setters
     */
    public State getPrevState() {
        return prevState;
    }

    public State getState() {
        return state;
    }

}