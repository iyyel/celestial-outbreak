package io.inabsentia.celestialoutbreak.controller;

import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.graphics.ScreenRenderer;
import io.inabsentia.celestialoutbreak.handler.*;
import io.inabsentia.celestialoutbreak.menu.*;
import io.inabsentia.celestialoutbreak.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Map;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    /*
     * WIDTH and HEIGHT will be multiplied by SCALE to make the width and height of the screenRenderer.
     * Update rate is the targeted update rate.
     */
    private final int WIDTH = 640;
    private final int HEIGHT = 360;
    private final int SCALE = 2;
    private final int UPDATE_RATE = 60;

    /*
     * Timers to switch the mainMenu background color in a slower interval than 60 times a second.
     */
    private final int INITIAL_MENU_COLOR_TIMER_VALUE = UPDATE_RATE * 7;
    private int menuColorTimer = INITIAL_MENU_COLOR_TIMER_VALUE;

    /*
     * gameThread is the io.inabsentia.celestialoutbreak.main thread.
     * isRunning determines whether the game loop is running or not.
     */
    private Thread gameThread;
    private boolean isRunning = false;

    /*
     * BufferedImage for the actual rendering of the screenRenderer.
     * pixels[] is the extracted pixels from the image.
     */
    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
    private final GameUtils gameUtils = GameUtils.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

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
     * Objects used for io.inabsentia.celestialoutbreak.MENU_CLIP's.
     */
    private final MainMenu mainMenu;
    private final PauseMenu pauseMenu;
    private final ScoresMenu scoresMenu;
    private final SettingsMenu settingsMenu;
    private final AboutMenu aboutMenu;
    private final ExitMenu exitMenu;
    private final NewLevelMenu newLevelMenu;
    private final FinishedLevelMenu finishedLevelMenu;
    private Color menuColor;

    /*
     * Current state of the game.
     * Starts with showing the io.inabsentia.celestialoutbreak.MENU_CLIP.
     */
    private State state = State.MENU;

    /*
     * Constructor of Game class.
     * Initialize above objects here. Start game loop.
     */
    public Game() {
        fileHandler.writeLogMsg(textHandler.NEW_APP_INSTANCE);

        /* Set dimensions of the JFrame and Canvas */
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

		/* Create JFrame */
        gameFrame = new JFrame();
        gameFrame.setSize(size);

        /* Initialize levelHandler */
        levelHandler = new LevelHandler(0, this, inputHandler, soundHandler, fileHandler);

		/* Create screenRenderer renderer */
        screenRenderer = new ScreenRenderer(WIDTH, HEIGHT, pixels);

        /* Instantiate MENU_CLIP colors */
        try {
            initMenuColors();
        } catch (Exception e) {
            fileHandler.writeLogMsg(textHandler.errParsingProperties(textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH, e.getMessage()));
            stop();
        }

        /* Create io.inabsentia.celestialoutbreak.MENU_CLIP objects */
        mainMenu = new MainMenu(this, inputHandler, soundHandler, menuFontColor, menuBtnColor, menuSelectedBtnColor);
        pauseMenu = new PauseMenu(this, inputHandler, menuFontColor);
        scoresMenu = new ScoresMenu(this, inputHandler, menuFontColor);
        settingsMenu = new SettingsMenu(this, inputHandler, menuFontColor);
        aboutMenu = new AboutMenu(this, inputHandler, menuFontColor);
        exitMenu = new ExitMenu(this, inputHandler, menuFontColor);
        newLevelMenu = new NewLevelMenu(this, inputHandler, menuFontColor);
        finishedLevelMenu = new FinishedLevelMenu(this, inputHandler, menuFontColor);
        menuColor = gameUtils.generatePastelColor(0.9F, 9000F);

		/* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        addKeyListener(inputHandler);

		/* Initialize the JFrame and start the game loop */
        initFrame();
        fileHandler.writeLogMsg(textHandler.NEW_APP_INSTANCE_SUCCESS);
    }

    /*
     * Game loop with frames and updates counter.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / UPDATE_RATE;
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
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                /* Is this correctly calculated? */
                double allocatedRam = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024.0 * 1024.0);
                if (gameUtils.isVerboseEnabled()) {
                    gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION + " | " + textHandler.performanceMsg(frames, updates, allocatedRam));
                    fileHandler.writeLogMsg(textHandler.performanceMsg(frames, updates, allocatedRam));
                } else {
                    gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION);
                }
                updates = 0;
                frames = 0;
            }
        }
    }

    /*
     * Update the state of the game entities as well as the different menus.
     */
    private void update() {
        /* Update the currently pressed keys. */
        inputHandler.update();

        soundHandler.playStateMusic(state, true);

        /* Let the current game state decide what to update exactly. */
        switch (state) {
            case MENU:
                mainMenu.update();
                break;
            case PLAY:
                levelHandler.update();
                if (inputHandler.isPausePressed()) switchState(State.PAUSE);
                break;
            case SCORES:
                scoresMenu.update();
                break;
            case SETTINGS:
                settingsMenu.update();
                break;
            case ABOUT:
                aboutMenu.update();
                break;
            case EXIT:
                exitMenu.update();
                break;
            case PAUSE:
                pauseMenu.update();
                break;
            case NEW_LEVEL:
                newLevelMenu.update();
                newLevelMenu.updateActiveLevelDesc(levelHandler.getActiveLevel().getLevelDesc());
                break;
            case FINISHED_LEVEL:
                finishedLevelMenu.update();
                finishedLevelMenu.updateLevelNames(levelHandler.getPrevLevel().getLevelName(), levelHandler.getActiveLevel().getLevelName());
                break;
            default:
                break;
        }
    }

    /*
     * Render game entities as well as the menus and everything else.
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

        /* Let the current game state decide whether to render a level's color or the MENU_CLIP's color. */
        switch (state) {
            case MENU:
                switchMenuColor();
                screenRenderer.render(menuColor);
                break;
            case SCORES:
            case SETTINGS:
            case ABOUT:
            case EXIT:
                screenRenderer.render(menuColor);
                break;
            case PLAY:
            case PAUSE:
            case NEW_LEVEL:
                screenRenderer.render(levelHandler.getActiveLevel().getLevelColor());
                break;
            case FINISHED_LEVEL:
                screenRenderer.render(levelHandler.getPrevLevel().getLevelColor());
                break;
            default:
                break;
        }

        /* Get the graphics2D object from the buffer strategy. */
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        /* Enable some sweet antialiasing to make the graphics look smoother. */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        /* Draw the buffered image to the screenRenderer. */
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        /* Make the current state of the game decide what to render. */
        switch (state) {
            case MENU:
                mainMenu.render(g);
                break;
            case PLAY:
                levelHandler.render(g);
                break;
            case SCORES:
                scoresMenu.render(g);
                break;
            case SETTINGS:
                settingsMenu.render(g);
                break;
            case ABOUT:
                aboutMenu.render(g);
                break;
            case EXIT:
                exitMenu.render(g);
                break;
            case PAUSE:
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
     * Start method for the game thread/loop.
     */
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this, "Game");
            gameThread.start();
        }
    }

    /*
     * Stop method for the game thread/loop. Will exit the application.
     */
    public synchronized void stop() {
        if (isRunning) isRunning = false;
        System.exit(0);
    }

    /*
     * Initialize settings for the JFrame.
     */
    private void initFrame() {
        gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(this);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.requestFocus();
    }

    /*
     * Change the current state of the game.
     */
    public void switchState(State state) {
        this.state = state;
    }

    /*
     * Continuously switch the MENU_CLIP background with a nice pastel color.
     */
    private void switchMenuColor() {
        if (menuColorTimer == 0) {
            menuColor = gameUtils.generatePastelColor(0.8F, 9000F);
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
        this.menuFontColor = new Color(Integer.decode(map.get("MENU_FONT_COLOR")));
        this.menuBtnColor = new Color(Integer.decode(map.get("MENU_BTN_COLOR")));
        this.menuSelectedBtnColor = new Color(Integer.decode(map.get("MENU_SELECTED_BTN_COLOR")));
    }

}