package io.iyyel.celestialoutbreak.controller;

import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.graphics.ScreenRenderer;
import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.WelcomeScreen;
import io.iyyel.celestialoutbreak.ui.screen.main.*;
import io.iyyel.celestialoutbreak.ui.screen.options.ConfigOptionsScreen;
import io.iyyel.celestialoutbreak.ui.screen.options.GameOptions;
import io.iyyel.celestialoutbreak.ui.screen.options.GraphicsOptions;
import io.iyyel.celestialoutbreak.ui.screen.options.PlayerOptionsScreen;
import io.iyyel.celestialoutbreak.ui.screen.play.*;
import io.iyyel.celestialoutbreak.ui.screen.player_options.PlayerCreateScreen;
import io.iyyel.celestialoutbreak.ui.screen.player_options.PlayerDeleteScreen;
import io.iyyel.celestialoutbreak.ui.screen.player_options.PlayerSelectScreen;
import io.iyyel.celestialoutbreak.util.Util;

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
public final class GameController extends Canvas implements Runnable {

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
     * Timers to switch the mainScreen background color in a slower interval than 60 times a second.
     */
    private final int INITIAL_MAIN_MENU_SCREEN_COLOR_TIMER_VALUE = SCREEN_UPDATE_RATE;
    private int mainMenuScreenColorTimer = INITIAL_MAIN_MENU_SCREEN_COLOR_TIMER_VALUE;

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
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /*
     * JFrame and screenRenderer renderer objects.
     */
    private final JFrame gameFrame;
    private final ScreenRenderer screenRenderer;

    /*
     * Singleton objects.
     */
    private final TextHandler textHandler = TextHandler.getInstance();
    private final Util util = Util.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    /*
     * Objects used for menu's.
     */
    private final WelcomeScreen welcomeScreen;
    private final MainScreen mainScreen;
    private final PlayScreen playScreen;
    private final SelectLevelScreen selectLevelScreen;
    private final PreLevelScreen preLevelScreen;
    private final PostLevelScreen postLevelScreen;
    private final PauseScreen pauseScreen;
    private final HighScoresScreen highScoresScreen;
    private final ControlsScreen controlsScreen;
    private final OptionsScreen optionsScreen;
    private final PlayerOptionsScreen playerOptionsScreen;
    private final PlayerSelectScreen playerSelectScreen;
    private final PlayerCreateScreen playerCreateScreen;
    private final PlayerDeleteScreen playerDeleteScreen;
    private final GameOptions gameOptions;
    private final GraphicsOptions graphicsOptions;
    private final ConfigOptionsScreen configOptionsScreen;
    private final AboutScreen aboutScreen;
    private final ExitScreen exitScreen;

    private Color mainMenuScreenColor;

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
        GRAPHICS_OPTIONS,
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

        /* Create JFrame */
        gameFrame = new JFrame();

        /* Set dimensions of the JFrame and Canvas */
        Dimension size = new Dimension(SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE);

        /* Initialize the canvas. */
        initCanvas(size);

        /* Initialize game icon. */
        initGameIcon();

        /* Load players */
        try {
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            // TODO: Handle this.
            e.printStackTrace();
        }

        /* Initialize levelHandler */
        levelHandler.initPreLevels(this);

        /* Create screenRenderer renderer */
        screenRenderer = new ScreenRenderer(SCREEN_WIDTH, SCREEN_HEIGHT, pixels);

        /* Create menu objects */
        welcomeScreen = new WelcomeScreen(AbstractNavigationScreen.NavStyle.VERTICAL, 2, this);
        mainScreen = new MainScreen(AbstractNavigationScreen.NavStyle.VERTICAL, 6, this);
        playScreen = new PlayScreen(this);
        selectLevelScreen = new SelectLevelScreen(this);
        preLevelScreen = new PreLevelScreen(this);
        postLevelScreen = new PostLevelScreen(this);
        pauseScreen = new PauseScreen(this);
        highScoresScreen = new HighScoresScreen(this);
        controlsScreen = new ControlsScreen(this);
        optionsScreen = new OptionsScreen(AbstractNavigationScreen.NavStyle.VERTICAL, 4, this);
        playerOptionsScreen = new PlayerOptionsScreen(AbstractNavigationScreen.NavStyle.VERTICAL, 3, this);
        playerSelectScreen = new PlayerSelectScreen(AbstractNavigationScreen.NavStyle.VERTICAL_HORIZONTAL,
                playerDAO.getPlayers().size(), 5, this);
        playerCreateScreen = new PlayerCreateScreen(this);
        playerDeleteScreen = new PlayerDeleteScreen(AbstractNavigationScreen.NavStyle.VERTICAL_HORIZONTAL,
                playerDAO.getPlayers().size(), 5, this);
        gameOptions = new GameOptions(AbstractNavigationScreen.NavStyle.VERTICAL, 2, this);
        graphicsOptions = new GraphicsOptions(AbstractNavigationScreen.NavStyle.VERTICAL, 2, this);
        configOptionsScreen = new ConfigOptionsScreen(this);
        aboutScreen = new AboutScreen(this);
        exitScreen = new ExitScreen(this);
        mainMenuScreenColor = util.generatePastelColor(0.9F, 9000F);

        /* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        addKeyListener(inputHandler);

        /* If no players exist, assume its first run */
        if (playerDAO.getPlayers().isEmpty()) {
            state = State.WELCOME;
        }

        /* Initialize the JFrame and start the gameController loop */
        initFrame();

        /* Log that the game has been initialized */
        logHandler.log(textHandler.GAME_INIT_FINISHED, "GameController", LogHandler.LogLevel.INFO, false);
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
                    gameFrame.setTitle(textHandler.GAME_TITLE + " | " + textHandler.GAME_VERSION +
                            " - " + textHandler.vPerformanceMsg(frames, updates));
                    logHandler.log(textHandler.vPerformanceMsg(frames, updates), "run", LogHandler.LogLevel.INFO, true);
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
        } else { /* If not, stop all sound immediately */
            soundHandler.stopAllSound();
        }

        /* Let the current gameController state decide what to update exactly. */
        switch (state) {
            case WELCOME:
                switchScreenColor();
                welcomeScreen.update();
                break;
            case MAIN:
                switchScreenColor();
                mainScreen.update();
                break;
            case SELECT_LEVEL:
                selectLevelScreen.update();
                break;
            case PRE_LEVEL:
                preLevelScreen.update();
                break;
            case POST_LEVEL:
                postLevelScreen.update();
                break;
            case PAUSE:
                pauseScreen.update();
                break;
            case PLAY:
                playScreen.update();
                break;
            case SCORES:
                highScoresScreen.update();
                break;
            case CONTROLS:
                controlsScreen.update();
                break;
            case OPTIONS:
                optionsScreen.update();
                break;
            case PLAYER_OPTIONS:
                playerOptionsScreen.update();
                break;
            case PLAYER_SELECT:
                playerSelectScreen.update();
                break;
            case PLAYER_CREATE:
                playerCreateScreen.update();
                break;
            case PLAYER_DELETE:
                playerDeleteScreen.update();
                break;
            case GAME_OPTIONS:
                gameOptions.update();
                break;
            case GRAPHICS_OPTIONS:
                graphicsOptions.update();
                break;
            case CONFIG_OPTIONS:
                configOptionsScreen.update();
                break;
            case ABOUT:
                aboutScreen.update();
                break;
            case EXIT:
                exitScreen.update();
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
        renderBackgroundColor();

        /* Get the graphics2D object from the buffer strategy. */
        Graphics2D g = null;

        try {
            g = (Graphics2D) bs.getDrawGraphics();
        } catch (IllegalStateException e) {
            logHandler.log("You dragged the screen to another monitor, didn't you? This doesn't work. :(", "render", LogHandler.LogLevel.ERROR, false);
            e.printStackTrace();
            stop();
        }

        if (g == null) {
            // log incident here
            stop();
        }

        /* Enable some sweet antialiasing to make the graphics look smoother. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        /* Draw the buffered image to the screen. */
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        /* Make the current state of the gameController decide what to render. */
        renderScreen(g);

        /* Dispose the graphics and show the buffer to the screenRenderer. */
        g.dispose();
        bs.show();
    }

    private void renderBackgroundColor() {
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
            case GRAPHICS_OPTIONS:
            case CONFIG_OPTIONS:
            case ABOUT:
            case EXIT:
                screenRenderer.render(mainMenuScreenColor);
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

    private void renderScreen(Graphics2D g) {
        switch (state) {
            case WELCOME:
                welcomeScreen.render(g);
                break;
            case MAIN:
                mainScreen.render(g);
                break;
            case SELECT_LEVEL:
                selectLevelScreen.render(g);
                break;
            case PRE_LEVEL:
                preLevelScreen.render(g);
                break;
            case POST_LEVEL:
                postLevelScreen.render(g);
                break;
            case PAUSE:
                pauseScreen.render(g);
                break;
            case PLAY:
                playScreen.render(g);
                break;
            case SCORES:
                highScoresScreen.render(g);
                break;
            case CONTROLS:
                controlsScreen.render(g);
                break;
            case OPTIONS:
                optionsScreen.render(g);
                break;
            case PLAYER_OPTIONS:
                playerOptionsScreen.render(g);
                break;
            case PLAYER_SELECT:
                playerSelectScreen.render(g);
                break;
            case PLAYER_CREATE:
                playerCreateScreen.render(g);
                break;
            case PLAYER_DELETE:
                playerDeleteScreen.render(g);
                break;
            case GAME_OPTIONS:
                gameOptions.render(g);
                break;
            case GRAPHICS_OPTIONS:
                graphicsOptions.render(g);
                break;
            case CONFIG_OPTIONS:
                configOptionsScreen.render(g);
                break;
            case ABOUT:
                aboutScreen.render(g);
                break;
            case EXIT:
                exitScreen.render(g);
                break;
            default:
                break;
        }
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
     * Continuously switch the screen background with a nice pastel color.
     */
    private void switchScreenColor() {
        if (mainMenuScreenColorTimer == 0) {
            mainMenuScreenColor = util.generatePastelColor(0.8F, 9000F);
            mainMenuScreenColorTimer = INITIAL_MAIN_MENU_SCREEN_COLOR_TIMER_VALUE;
        } else {
            mainMenuScreenColorTimer--;
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
        }
        logHandler.log(textHandler.GAME_TITLE + " shutting down.", "stop", LogHandler.LogLevel.INFO, false);
        System.exit(0);
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