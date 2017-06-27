package io.inabsentia.celestialoutbreak.controller;

import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.graphics.Screen;
import io.inabsentia.celestialoutbreak.handler.*;
import io.inabsentia.celestialoutbreak.menu.*;
import io.inabsentia.celestialoutbreak.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    /*
     * WIDTH and HEIGHT will be multiplied by SCALE to make the width and height of the screen.
     * Update rate is the targeted update rate.
     */
    private final int WIDTH = 640;
    private final int HEIGHT = 360;
    private final int SCALE = 2;
    private final int UPDATE_RATE = 60;

    /*
     * Timers to switch the mainMenu background color in a slower interval than 60 times a second.
     */
    private final int initialMenuColorTimer = UPDATE_RATE * 7;
    private int menuColorTimer = initialMenuColorTimer;

    /*
     * gameThread is the io.inabsentia.celestialoutbreak.main thread.
     * isRunning determines whether the game loop is running or not.
     */
    private Thread gameThread;
    private boolean isRunning = false;

    /*
     * BufferedImage for the actual rendering of the screen.
     * pixels[] is the extracted pixels from the image.
     */
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /*
     * JFrame and screen renderer objects.
     */
    private final JFrame gameFrame;
    private final Screen screen;

    /*
     * Singleton objects.
     */
    private final Utils utils = Utils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    /*
     * LevelHandler object.
     */
    private final LevelHandler levelHandler;

    /*
     * Objects used for io.inabsentia.celestialoutbreak.menu's.
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
     * Starts with showing the io.inabsentia.celestialoutbreak.menu.
     */
    private State state = State.MENU;

    /*
     * Constructor of Game class.
     * Initialize above objects here. Start game loop.
     */
    public Game() {
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
        levelHandler = new LevelHandler(this, inputHandler, soundHandler, fileHandler);

		/* Create screen renderer */
        screen = new Screen(WIDTH, HEIGHT, pixels);

		/* Create io.inabsentia.celestialoutbreak.menu objects */
        mainMenu = new MainMenu(this, inputHandler, Color.WHITE, Color.WHITE, Color.BLACK);
        pauseMenu = new PauseMenu(this, inputHandler);
        scoresMenu = new ScoresMenu(this, inputHandler);
        settingsMenu = new SettingsMenu(this, inputHandler);
        aboutMenu = new AboutMenu(this, inputHandler);
        exitMenu = new ExitMenu(this, inputHandler);
        newLevelMenu = new NewLevelMenu(this, inputHandler);
        finishedLevelMenu = new FinishedLevelMenu(this, inputHandler);
        menuColor = utils.generatePastelColor(0.9F, 9000F);

		/* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        addKeyListener(inputHandler);

		/* Initialize the JFrame and start the game loop */
        initFrame();
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
                double allocatedRam = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576.0;
                if (utils.isVerboseEnabled()) {
                    gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION + " | " + textHandler.performanceMsg(frames, updates, allocatedRam));
                    fileHandler.writeLogMessage(textHandler.performanceMsg(frames, updates, allocatedRam));
                } else {
                    gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION);
                }
                updates = 0;
                frames = 0;
            }
        }
        /* If we get out of the game loop for some reason, stop execution immediately. */
        stop();
    }

    /*
     * Update the state of the game entities as well as the different menus.
     */
    private void update() {
        /* Update the currently pressed keys. */
        inputHandler.update();

        /* Let the current game state decide what to update exactly. */
        switch (state) {
            case MENU:
                mainMenu.update();
                break;
            case PLAY:
                levelHandler.update(state);
                if (inputHandler.isPausePressed()) switchPlayPauseState();
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
                if (inputHandler.isPausePressed()) switchPlayPauseState();
                break;
            case NEW_LEVEL:
                newLevelMenu.update();
                newLevelMenu.updateActiveLevelDesc(levelHandler.getActiveLevel().getLevelDesc());
                break;
            case FINISHED_LEVEL:
                finishedLevelMenu.setLevelNames(levelHandler.getPrevLevel().getLevelName(), levelHandler.getActiveLevel().getLevelName());
                finishedLevelMenu.update();
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

        /* Clear the screen, i.e. make it go all black. */
        screen.clear();

        /* Let the current game state decide whether to render a level's color or the menu's color. */
        switch (state) {
            case MENU:
                switchMenuColor();
                screen.render(menuColor);
                break;
            case SCORES:
            case SETTINGS:
            case ABOUT:
            case EXIT:
                screen.render(menuColor);
                break;
            case PLAY:
            case PAUSE:
            case NEW_LEVEL:
                screen.render(levelHandler.getActiveLevel().getLevelColor());
                break;
            case FINISHED_LEVEL:
                screen.render(levelHandler.getPrevLevel().getLevelColor());
                break;
            default:
                break;
        }

        /* Get the graphics2D object from the buffer strategy. */
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        /* Enable some sweet antialiasing to make the graphics look smoother. */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        /* Draw the buffered image to the screen. */
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        /* Make the current state of the game decide what to render. */
        switch (state) {
            case MENU:
                mainMenu.render(g);
                soundHandler.playStateMusic(state, true);
                break;
            case PLAY:
                levelHandler.render(g);
                soundHandler.playStateMusic(state, true);
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
                soundHandler.playStateMusic(state, true);
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

        /* Dispose the graphics and show the buffer to the screen. */
        g.dispose();
        bs.show();
    }

    /*
     * Start method for the game thread/loop.
     */
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this, "Display");
            gameThread.start();
        }
    }

    /*
     * Stop method for the game thread/loop. Will exit the application.
     */
    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
     * Continuously switch the menu background with a nice pastel color.
     */
    private void switchMenuColor() {
        if (menuColorTimer == initialMenuColorTimer) {
            menuColor = utils.generatePastelColor(0.8F, 9000F);
            menuColorTimer--;
        } else {
            menuColorTimer--;
            if (menuColorTimer == 0) menuColorTimer = initialMenuColorTimer;
        }
    }

    /*
     * Convenience method to switch between play and paused game state.
     */
    private void switchPlayPauseState() {
        if (state == State.PLAY)
            switchState(State.PAUSE);
        else if (state == State.PAUSE)
            switchState(State.PLAY);
        utils.sleep(100);
    }

}