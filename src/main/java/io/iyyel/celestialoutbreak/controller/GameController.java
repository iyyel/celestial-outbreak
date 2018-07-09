package io.iyyel.celestialoutbreak.controller;

import io.iyyel.celestialoutbreak.data.dao.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.graphics.ScreenRenderer;
import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.menu.game.FinishedLevelMenu;
import io.iyyel.celestialoutbreak.menu.game.NewLevelMenu;
import io.iyyel.celestialoutbreak.menu.game.NewPlayerMenu;
import io.iyyel.celestialoutbreak.menu.game.WelcomeMenu;
import io.iyyel.celestialoutbreak.menu.main_menu.*;
import io.iyyel.celestialoutbreak.menu.player.PlayerSelectMenu;
import io.iyyel.celestialoutbreak.menu.settings.ConfigurationMenu;
import io.iyyel.celestialoutbreak.menu.settings.PlayerSettingsMenu;
import io.iyyel.celestialoutbreak.menu.settings.SettingsMenu;
import io.iyyel.celestialoutbreak.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.net.URL;
import java.util.Map;

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
     * Menu colors.
     */
    private Color menuFontColor;
    private Color menuBtnColor;
    private Color menuSelectedBtnColor;

    /*
     * Objects used for menu's.
     */
    private final WelcomeMenu welcomeMenu;
    private final NewPlayerMenu newPlayerMenu;
    private final MainMenu mainMenu;
    private final PauseMenu pauseMenu;
    private final ScoresMenu scoresMenu;
    private final SettingsMenu settingsMenu;
    private final PlayerSettingsMenu playerSettingsMenu;
    private final PlayerSelectMenu playerSelectMenu;
    private final ConfigurationMenu configurationMenu;
    private final ControlsMenu controlMenu;
    private final AboutMenu aboutMenu;
    private final ExitMenu exitMenu;
    private final NewLevelMenu newLevelMenu;
    private final FinishedLevelMenu finishedLevelMenu;
    private Color menuColor;

    public enum State {
        WELCOME_MENU, NEW_PLAYER_MENU,
        MAIN_MENU, PLAY, SCORES_MENU, CONTROLS_MENU, SETTINGS_MENU,
        PLAYER_SETTINGS_MENU, CUSTOM_SETTINGS_MENU,
        PLAYER_SELECT_MENU, PLAYER_NEW_SETTINGS, PLAYER_UPDATE_SETTINGS, PLAYER_REMOVE_SETTINGS,
        ABOUT_MENU, EXIT_MENU, PAUSE_SCREEN, NEW_LEVEL, FINISHED_LEVEL
    }

    /*
     * Current state of the gameController.
     * Starts with showing the menu state.
     */
    private State state = State.MAIN_MENU;

    /*
     * Constructor of GameController class.
     * Initialize above objects here. Start gameController loop, etc.
     */
    public GameController() {
        fileHandler.writeLog(textHandler.START_NEW_APP_INSTANCE);

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

        /* Set dimensions of the JFrame and Canvas */
        Dimension size = new Dimension(SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        /* Create JFrame */
        gameFrame = new JFrame();
        gameFrame.setSize(size);

        /* Game Icon */
        URL url = ClassLoader.getSystemResource("icon/app_icon_small.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        gameFrame.setIconImage(img);

        /* Initialize levelHandler */
        levelHandler = new LevelHandler(0, this, inputHandler, soundHandler, fileHandler);

        /* Create screenRenderer renderer */
        screenRenderer = new ScreenRenderer(SCREEN_WIDTH, SCREEN_HEIGHT, pixels);

        /* Instantiate main menu colors */
        try {
            initMenuColors();
        } catch (Exception e) {
            fileHandler.writeLog(textHandler.errorParsingPropertiesMsg(textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH, e.getMessage()));
            stop();
        }

        /* Create menu objects */
        newPlayerMenu = new NewPlayerMenu(this, inputHandler, menuFontColor);
        welcomeMenu = new WelcomeMenu(this, inputHandler, menuFontColor);
        mainMenu = new MainMenu(this, inputHandler, soundHandler, menuFontColor, menuBtnColor, menuSelectedBtnColor);
        pauseMenu = new PauseMenu(this, inputHandler, menuFontColor);
        scoresMenu = new ScoresMenu(this, inputHandler, menuFontColor);
        settingsMenu = new SettingsMenu(this, inputHandler, soundHandler, menuFontColor, menuBtnColor, menuSelectedBtnColor);
        playerSettingsMenu = new PlayerSettingsMenu(this, inputHandler, soundHandler, menuFontColor, menuBtnColor, menuSelectedBtnColor);
        playerSelectMenu = new PlayerSelectMenu(this, inputHandler, soundHandler, menuFontColor, menuBtnColor, menuSelectedBtnColor);
        configurationMenu = new ConfigurationMenu(this, inputHandler, menuFontColor);
        controlMenu = new ControlsMenu(this, inputHandler, menuFontColor);
        aboutMenu = new AboutMenu(this, inputHandler, menuFontColor);
        exitMenu = new ExitMenu(this, inputHandler, menuFontColor);
        newLevelMenu = new NewLevelMenu(this, inputHandler, menuFontColor);
        finishedLevelMenu = new FinishedLevelMenu(this, inputHandler, menuFontColor);
        menuColor = utils.generatePastelColor(0.9F, 9000F);

        /* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        addKeyListener(inputHandler);

        /* Initialize the JFrame and start the gameController loop */
        initFrame();
        fileHandler.writeLog(textHandler.SUCCESS_NEW_APP_INSTANCE);

        /* Show new player menu if necessary. */
        showNewPlayerMenu();
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
                /* Is this correctly calculated? (ram) */
                if (utils.isVerboseEnabled()) {
                    double allocatedRam = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024.0 * 1024.0);
                    gameFrame.setTitle(textHandler.GAME_TITLE + " | " + textHandler.vPerformanceMsg(frames, updates, allocatedRam));
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

        soundHandler.playStateMusic(state, true);

        /* Let the current gameController state decide what to update exactly. */
        switch (state) {
            case WELCOME_MENU:
                switchMenuColor();
                welcomeMenu.update();
                break;
            case NEW_PLAYER_MENU:
                switchMenuColor();
                newPlayerMenu.update();
                break;
            case MAIN_MENU:
                switchMenuColor();
                mainMenu.update();
                break;
            case PLAY:
                levelHandler.update();
                if (inputHandler.isPausePressed())
                    switchState(State.PAUSE_SCREEN);
                break;
            case SCORES_MENU:
                scoresMenu.update();
                break;
            case CONTROLS_MENU:
                controlMenu.update();
                break;
            case SETTINGS_MENU:
                settingsMenu.update();
                break;
            case PLAYER_SETTINGS_MENU:
                playerSettingsMenu.update();
                break;
            case PLAYER_SELECT_MENU:
                playerSelectMenu.update();
                break;
            case CUSTOM_SETTINGS_MENU:
                configurationMenu.update();
                break;
            case ABOUT_MENU:
                aboutMenu.update();
                break;
            case EXIT_MENU:
                exitMenu.update();
                break;
            case PAUSE_SCREEN:
                pauseMenu.update();
                break;
            case NEW_LEVEL:
                newLevelMenu.update();
                newLevelMenu.updateActiveLevel(levelHandler.getActiveLevel());
                break;
            case FINISHED_LEVEL:
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
        switch (state) {
            case WELCOME_MENU:
            case NEW_PLAYER_MENU:
            case MAIN_MENU:
            case SCORES_MENU:
            case CONTROLS_MENU:
            case SETTINGS_MENU:
            case PLAYER_SETTINGS_MENU:
            case PLAYER_SELECT_MENU:
            case CUSTOM_SETTINGS_MENU:
            case ABOUT_MENU:
            case EXIT_MENU:
                screenRenderer.render(menuColor);
                break;
            case PLAY:
            case PAUSE_SCREEN:
            case NEW_LEVEL:
                screenRenderer.render(levelHandler.getActiveLevel().getColor());
                break;
            case FINISHED_LEVEL:
                screenRenderer.render(levelHandler.getPrevLevel().getColor());
                break;
            default:
                break;
        }

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
        switch (state) {
            case WELCOME_MENU:
                welcomeMenu.render(g);
                break;
            case NEW_PLAYER_MENU:
                newPlayerMenu.render(g);
                break;
            case MAIN_MENU:
                mainMenu.render(g);
                break;
            case PLAY:
                levelHandler.render(g);
                break;
            case SCORES_MENU:
                scoresMenu.render(g);
                break;
            case CONTROLS_MENU:
                controlMenu.render(g);
                break;
            case SETTINGS_MENU:
                settingsMenu.render(g);
                break;
            case PLAYER_SETTINGS_MENU:
                playerSettingsMenu.render(g);
                break;
            case PLAYER_SELECT_MENU:
                playerSelectMenu.render(g);
                break;
            case CUSTOM_SETTINGS_MENU:
                configurationMenu.render(g);
                break;
            case ABOUT_MENU:
                aboutMenu.render(g);
                break;
            case EXIT_MENU:
                exitMenu.render(g);
                break;
            case PAUSE_SCREEN:
                pauseMenu.render(g);
                break;
            case NEW_LEVEL:
                newLevelMenu.render(g);
                break;
            case FINISHED_LEVEL:
                finishedLevelMenu.render(g);
                break;
            default:
                break;
        }

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
        if (isRunning)
            isRunning = false;
        System.exit(0);
    }

    /*
     * Initialize settings for the JFrame.
     */
    private void initFrame() {
        gameFrame.setTitle(textHandler.GAME_TITLE + " " + textHandler.GAME_VERSION);
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

    /*
     * Read settings for the menuColors at startup.
     */
    private void initMenuColors() {
        Map<String, String> map = fileHandler.readPropertiesFromFile(textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH);
        this.menuFontColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_FONT_COLOR_HEX)));
        this.menuBtnColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_BTN_COLOR_HEX)));
        this.menuSelectedBtnColor = new Color(Integer.decode(map.get(textHandler.PROP_SELECTED_BTN_COLOR_HEX)));
    }

    private void showNewPlayerMenu() {
        if (playerDAO.getPlayerList().isEmpty())
            switchState(State.NEW_PLAYER_MENU);
    }

}