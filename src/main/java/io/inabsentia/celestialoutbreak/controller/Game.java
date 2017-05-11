package io.inabsentia.celestialoutbreak.controller;

import io.inabsentia.celestialoutbreak.entity.Ball;
import io.inabsentia.celestialoutbreak.entity.BlockList;
import io.inabsentia.celestialoutbreak.entity.Paddle;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.graphics.Screen;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.menu.BottomPanelMenu;
import io.inabsentia.celestialoutbreak.menu.MainMenu;
import io.inabsentia.celestialoutbreak.menu.PauseMenu;
import io.inabsentia.celestialoutbreak.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    /**
     * WIDTH and HEIGHT will be multiplied by SCALE
     * to make the width and height of the screen.
     * Update rate is the targeted update rate.
     */
    private final int WIDTH = 640;
    private final int HEIGHT = 360;
    private final int SCALE = 2;
    private final int UPDATE_RATE = 60;

    /**
     * Timers to switch the mainMenu background color
     * in a slower interval than 60 times a second.
     */
    private final int initialMenuColorTimer = UPDATE_RATE * 4;
    private int menuColorTimer = initialMenuColorTimer;

    /**
     * gameThread is the main thread.
     * isRunning determines whether the game loop is
     * running or not.
     */
    private Thread gameThread;
    private boolean isRunning = false;

    /**
     * BufferedImage for the actual rendering of the screen.
     * pixels[] is the extracted pixels from the image.
     */
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /**
     * JFrame and screen renderer objects.
     */
    private final JFrame gameFrame;
    private final Screen screen;

    /**
     * Singleton objects.
     */
    private final Utils utils;
    private final TextHandler textHandler;
    private final InputHandler inputHandler;
    private final SoundHandler soundHandler;

    /**
     * Objects used for io.inabsentia.celestialoutbreak.menu's.
     */
    private final MainMenu mainMenu;
    private final PauseMenu pauseMenu;
    private final BottomPanelMenu bottomPanelMenu;
    private Color menuColor;

    /**
     * Game play entities.
     */
    private final Paddle paddle;
    private final Ball ball;
    private final BlockList blockList;

    /**
     * Current state of the game.
     * Start with showing the io.inabsentia.celestialoutbreak.menu.
     */
    private State state = State.MENU;

    /**
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

		/* Get singleton instances */
        utils = Utils.getInstance();
        textHandler = TextHandler.getInstance();
        inputHandler = InputHandler.getInstance();
        soundHandler = SoundHandler.getInstance();

		/* Create screen renderer */
        screen = new Screen(WIDTH, HEIGHT, pixels, utils.generatePastelColor(0.5F, 2000F));

		/* Create io.inabsentia.celestialoutbreak.menu objects */
        mainMenu = new MainMenu(this, inputHandler, Color.WHITE, Color.WHITE, Color.BLACK);
        pauseMenu = new PauseMenu(this, inputHandler, Color.WHITE);
        bottomPanelMenu = new BottomPanelMenu(this, inputHandler, Color.WHITE);
        menuColor = utils.generatePastelColor(0.9F, 9000F);

		/* Create game objects */
        paddle = new Paddle(new Point((getWidth() / 2) - 25, getHeight() - 50), 60, 10, 7, Color.WHITE, this);
        ball = new Ball(new Point((WIDTH * SCALE) / 2, (HEIGHT * SCALE) / 2), 15, 15, 5, Color.WHITE, this, soundHandler);
        blockList = new BlockList(133, new Point(30, 30), 50, 15, 15, this);

		/* Add input handlers */
        gameFrame.addKeyListener(inputHandler);
        this.addKeyListener(inputHandler);

		/* Initialize the JFrame and start the game loop */
        initFrame();
        start();
    }

    /**
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
                gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION + " | UPS: " + updates + " FPS: " + frames);
                // DEBUGGING/LOGGING
                System.out.println("UPS: " + updates + " FPS: " + frames);
                updates = 0;
                frames = 0;
            }
        }
        /**
         * If we get out of the game loop for some reason,
         * stop execution immediately.
         */
        stop();
    }

    /**
     * Update the state of the game entities
     * as well as the different menus.
     */
    private void update() {
        if (inputHandler.pause) switchPlayPauseState();

		/* Figure this out. It should not be here, and it is not quite a io.inabsentia.celestialoutbreak.menu. */
        bottomPanelMenu.updateBlockAmount(blockList.getBlocksLeft());

        inputHandler.update();
        if (state == State.PLAY) {
            paddle.update(inputHandler.left, inputHandler.right);
            ball.update(paddle, blockList);

        } else if (state == State.PAUSE) {

        } else if (state == State.MENU) {
            mainMenu.update();

        }
    }

    /**
     * Render game entities as well as
     * the menus and everything else.
     */
    private void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        if (state == State.PLAY || state == State.PAUSE) {
            screen.render();

        } else if (state == State.MENU) {
            switchMenuColor();
            screen.render(menuColor);

        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        if (state == State.PLAY) {
            paddle.render(g);
            ball.render(g);
            blockList.render(g);
            bottomPanelMenu.render(g);
            soundHandler.playStateMusic(state, true);
        } else if (state == State.PAUSE) {
            pauseMenu.render(g);
            soundHandler.playStateMusic(state, true);
        } else if (state == State.MENU) {
            mainMenu.render(g);
            soundHandler.playStateMusic(state, true);
        }

        g.dispose();
        bs.show();
    }

    private synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this, "Display");
            gameThread.start();
        }
    }

    private synchronized void stop() {
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

    private void initFrame() {
        gameFrame.setTitle(textHandler.TITLE + " " + textHandler.VERSION);
        gameFrame.setResizable(false);
        gameFrame.add(this);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.requestFocus();
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void switchMenuColor() {
        if (menuColorTimer == initialMenuColorTimer) {
            menuColor = utils.generatePastelColor(0.8F, 9000F);
            menuColorTimer--;
        } else {
            menuColorTimer--;
            if (menuColorTimer == 0) {
                menuColorTimer = initialMenuColorTimer;
            }
        }
    }

    public void switchPlayPauseState() {
        if (state == State.PLAY) {
            state = State.PAUSE;
        } else if (state == State.PAUSE) {
            state = State.PLAY;
        }
        utils.sleep(100);
    }

}