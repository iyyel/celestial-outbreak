package io.iyyel.celestialoutbreak.controller;

import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.graphics.ScreenRenderer;
import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.menu.WelcomeMenu;
import io.iyyel.celestialoutbreak.menu.main_menu.*;
import io.iyyel.celestialoutbreak.menu.options_menu.ConfigOptionsMenu;
import io.iyyel.celestialoutbreak.menu.options_menu.PlayerOptionsMenu;
import io.iyyel.celestialoutbreak.menu.play.FinishedLevelMenu;
import io.iyyel.celestialoutbreak.menu.play.NewLevelMenu;
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
 * This is the canvas upon where the game is drawn and played.
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
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    /*
     * LevelHandler object.
     */
    private final LevelHandler levelHandler;

    /*
     * Objects used for menu's.
     */
    private final WelcomeMenu welcomeMenu;
    private final MainMenu mainMenu;
    private final PauseMenu pauseMenu;
    private final ScoresMenu scoresMenu;
    private final OptionsMenu settingsMenu;
    private final PlayerOptionsMenu playerOptionsMenu;
    private final PlayerSelectMenu playerSelectMenu;
    private final PlayerCreateMenu playerNewMenu;
    private final PlayerDeleteMenu playerDeleteMenu;
    private final ConfigOptionsMenu configurationMenu;
    private final ControlsMenu controlMenu;
    private final AboutMenu aboutMenu;
    private final ExitMenu exitMenu;
    private final NewLevelMenu newLevelMenu;
    private final FinishedLevelMenu finishedLevelMenu;
    private Color menuColor;

    /*
     * NONE,
     * WELCOME_MENU,
     * MAIN_MENU,
     *   PLAY_SCREEN,
     *       PAUSE_SCREEN,
     *       NEW_LEVEL_SCREEN,
     *       FINISHED_LEVEL_SCREEN,
     *   SCORES_SCREEN,
     *   CONTROLS_SCREEN,
     *   OPTIONS_MENU,
     *       PLAYER_OPTIONS_MENU,
     *           PLAYER_SELECT_SCREEN,
     *           PLAYER_CREATE_SCREEN,
     *           PLAYER_DELETE_SCREEN,
     *       CONFIG_OPTIONS_SCREEN,
     *   ABOUT_SCREEN,
     *   EXIT_SCREEN,
     *   ERROR_SCREEN
     */
    public enum State {
        NONE,
        WELCOME_MENU,
        MAIN_MENU,
        PLAY_SCREEN,
        PAUSE_SCREEN,
        NEW_LEVEL_SCREEN,
        FINISHED_LEVEL_SCREEN,
        SCORES_SCREEN,
        CONTROLS_SCREEN,
        OPTIONS_MENU,
        PLAYER_OPTIONS_MENU,
        PLAYER_SELECT_SCREEN,
        PLAYER_CREATE_SCREEN,
        PLAYER_DELETE_SCREEN,
        CONFIG_OPTIONS_SCREEN,
        ABOUT_SCREEN,
        EXIT_SCREEN,
        ERROR_SCREEN
    }

    /*
     * Current state of the gameController.
     * Starts with showing the main menu state.
     */
    private State state = State.MAIN_MENU;
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
        levelHandler = new LevelHandler(0, this);

        /* Create screenRenderer renderer */
        screenRenderer = new ScreenRenderer(SCREEN_WIDTH, SCREEN_HEIGHT, pixels);

        /* Create menu objects */
        playerNewMenu = new PlayerCreateMenu(this);
        welcomeMenu = new WelcomeMenu(this);
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this);
        scoresMenu = new ScoresMenu(this);
        settingsMenu = new OptionsMenu(this);
        playerOptionsMenu = new PlayerOptionsMenu(this);
        playerSelectMenu = new PlayerSelectMenu(this);
        playerDeleteMenu = new PlayerDeleteMenu(this);
        configurationMenu = new ConfigOptionsMenu(this);
        controlMenu = new ControlsMenu(this);
        aboutMenu = new AboutMenu(this);
        exitMenu = new ExitMenu(this);
        newLevelMenu = new NewLevelMenu(this);
        finishedLevelMenu = new FinishedLevelMenu(this);
        menuColor = utils.generatePastelColor(0.9F, 9000F);

        /* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        addKeyListener(inputHandler);

        /* If no players exist, assume its first run */
        if (playerDAO.getPlayerList().isEmpty()) {
            state = State.WELCOME_MENU;
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
                if (utils.isFpsLockEnabled()) {
                    render();
                    frames++;
                }
            }
            if (!utils.isFpsLockEnabled()) {
                render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                if (utils.isVerboseLogEnabled()) {
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

        /* Play music based on current state */
        soundHandler.playStateMusic(state, prevState, true);

        /* Let the current gameController state decide what to update exactly. */
        switch (state) {
            case WELCOME_MENU:
                switchMenuColor();
                welcomeMenu.update();
                break;
            case PLAYER_CREATE_SCREEN:
                switchMenuColor();
                playerNewMenu.update();
                break;
            case MAIN_MENU:
                switchMenuColor();
                mainMenu.update();
                break;
            case PLAY_SCREEN:
                levelHandler.update();
                pauseMenu.decInputTimer();
                if (inputHandler.isPausePressed() && pauseMenu.isInputAvailable()) {
                    pauseMenu.resetInputTimer();
                    switchState(State.PAUSE_SCREEN);
                }
                break;
            case SCORES_SCREEN:
                scoresMenu.update();
                break;
            case CONTROLS_SCREEN:
                controlMenu.update();
                break;
            case OPTIONS_MENU:
                settingsMenu.update();
                break;
            case PLAYER_OPTIONS_MENU:
                playerOptionsMenu.update();
                break;
            case PLAYER_SELECT_SCREEN:
                playerSelectMenu.update();
                break;
            case PLAYER_DELETE_SCREEN:
                playerDeleteMenu.update();
                break;
            case CONFIG_OPTIONS_SCREEN:
                configurationMenu.update();
                break;
            case ABOUT_SCREEN:
                aboutMenu.update();
                break;
            case EXIT_SCREEN:
                exitMenu.update();
                break;
            case PAUSE_SCREEN:
                pauseMenu.update();
                break;
            case NEW_LEVEL_SCREEN:
                newLevelMenu.update();
                newLevelMenu.updateActiveLevel(levelHandler.getActiveLevel());
                break;
            case FINISHED_LEVEL_SCREEN:
                finishedLevelMenu.update();
                finishedLevelMenu.updateLevelNames(levelHandler.getPrevLevel().getName(), levelHandler.getActiveLevel().getName());
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
        if (utils.isAntiAliasingEnabled()) {
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

    private void renderBgColor() {
        switch (state) {
            case WELCOME_MENU:
            case PLAYER_CREATE_SCREEN:
            case MAIN_MENU:
            case SCORES_SCREEN:
            case CONTROLS_SCREEN:
            case OPTIONS_MENU:
            case PLAYER_OPTIONS_MENU:
            case PLAYER_SELECT_SCREEN:
            case PLAYER_DELETE_SCREEN:
            case CONFIG_OPTIONS_SCREEN:
            case ABOUT_SCREEN:
            case EXIT_SCREEN:
                screenRenderer.render(menuColor);
                break;
            case PLAY_SCREEN:
            case PAUSE_SCREEN:
            case NEW_LEVEL_SCREEN:
                screenRenderer.render(levelHandler.getActiveLevel().getColor());
                break;
            case FINISHED_LEVEL_SCREEN:
                screenRenderer.render(levelHandler.getPrevLevel().getColor());
                break;
            default:
                break;
        }
    }

    private void renderMenu(Graphics2D g) {
        switch (state) {
            case WELCOME_MENU:
                welcomeMenu.render(g);
                break;
            case PLAYER_CREATE_SCREEN:
                playerNewMenu.render(g);
                break;
            case MAIN_MENU:
                mainMenu.render(g);
                break;
            case PLAY_SCREEN:
                levelHandler.render(g);
                break;
            case SCORES_SCREEN:
                scoresMenu.render(g);
                break;
            case CONTROLS_SCREEN:
                controlMenu.render(g);
                break;
            case OPTIONS_MENU:
                settingsMenu.render(g);
                break;
            case PLAYER_OPTIONS_MENU:
                playerOptionsMenu.render(g);
                break;
            case PLAYER_SELECT_SCREEN:
                playerSelectMenu.render(g);
                break;
            case PLAYER_DELETE_SCREEN:
                playerDeleteMenu.render(g);
                break;
            case CONFIG_OPTIONS_SCREEN:
                configurationMenu.render(g);
                break;
            case ABOUT_SCREEN:
                aboutMenu.render(g);
                break;
            case EXIT_SCREEN:
                exitMenu.render(g);
                break;
            case PAUSE_SCREEN:
                pauseMenu.render(g);
                break;
            case NEW_LEVEL_SCREEN:
                newLevelMenu.render(g);
                break;
            case FINISHED_LEVEL_SCREEN:
                finishedLevelMenu.render(g);
                break;
            default:
                break;
        }
    }

    /*
     * Getters and setters
     */
    public State getPrevState() {
        return prevState;
    }

    private void initGameIcon() {
        /* Game Icon */
        URL url = ClassLoader.getSystemResource("icon/app_icon_small.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        gameFrame.setIconImage(img);
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

}