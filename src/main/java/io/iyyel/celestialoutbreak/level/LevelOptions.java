package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import io.iyyel.celestialoutbreak.ui.entity.AbstractEntity;
import io.iyyel.celestialoutbreak.ui.interfaces.IEntityRenderable.Shape;
import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.ui.entity.effects.Effect;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.awt.*;
import java.util.Map;

public final class LevelOptions {

    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();

    /*
     * Level options.
     */
    private String levelName;
    private String levelDesc;
    private int levelPlayerLife;
    private String levelSoundFileName;
    private Color levelColor;

    /*
     * Power up options.
     */
    private Dimension powerUpDim;
    private Shape powerUpShape;
    private int powerUpSpeed;
    private int powerUpChance;

    /*
     * Power up effects.
     */
    private Effect[] effects;

    /*
     * Paddle options.
     */
    private Point paddlePos;
    private Dimension paddleDim;
    private Shape paddleShape;
    private int paddleSpeed;
    private Color paddleColor;

    /*
     * Ball options.
     */
    private Point ballPos;
    private Dimension ballDim;
    private Shape ballShape;
    private int ballSpeed;
    private Color ballColor;

    /*
     * BlockField options.
     */
    private Point blockPosStart;
    private Point blockPosSpacing;
    private int blockAmount;
    private int blockHitPoints;
    private Dimension blockDim;
    private Shape blockShape;
    private float blockLum;
    private float blockSat;

    /*
     * GamePanel options.
     */
    private Color gamePanelTitleColor;
    private Color gamePanelValueColor;

    /*
     * Required objects.
     */
    private final GameController gameController;

    /*
     * Constructor.
     */
    public LevelOptions(String fileName, GameController gameController) {
        this.gameController = gameController;

        try {
            parseLevelOptions(fileName);
        } catch (Exception e) {
            /*
             * If the level options aren't able to be parsed correctly, any exception that will be
             * thrown will be printed to the console as well as the current log file, and then the
             * gameController will be stopped, since invalid level options has been found which might cause
             * the application to be in an inconsistent state.
             */
            logHandler.log(textHandler.errorParsingPropertiesMsg(fileName, ExceptionUtils.getStackTrace(e)), "LevelOptions", LogHandler.LogLevel.ERROR, false);
            gameController.stop();
        }
    }

    private void parseLevelOptions(String fileName) {
        Map<String, String> props = fileHandler.readPropertiesFromFile(fileName);

        /* Level options */
        levelName = props.get(textHandler.PROP_KEY_LEVEL_NAME);
        levelDesc = props.get(textHandler.PROP_KEY_LEVEL_DESC);
        levelPlayerLife = Integer.parseInt(props.get(textHandler.PROP_KEY_LEVEL_PLAYER_LIFE));
        levelSoundFileName = props.get(textHandler.PROP_KEY_LEVEL_SOUND_FILE_NAME);
        int levelColorValue = Integer.decode(props.get(textHandler.PROP_KEY_LEVEL_COLOR));
        levelColor = new Color(levelColorValue);

        /* Power up options */
        int powerUpWidth = Integer.parseInt(props.get(textHandler.PROP_KEY_POWERUP_WIDTH));
        int powerUpHeight = Integer.parseInt(props.get(textHandler.PROP_KEY_POWERUP_HEIGHT));
        powerUpDim = new Dimension(powerUpWidth, powerUpHeight);
        powerUpSpeed = Integer.parseInt(props.get(textHandler.PROP_KEY_POWERUP_SPEED));
        powerUpChance = Integer.parseInt(props.get(textHandler.PROP_KEY_POWERUP_CHANCE));
        String powerUpShapeStr = props.get(textHandler.PROP_KEY_POWERUP_SHAPE);
        powerUpShape = AbstractEntity.Shape.valueOf(powerUpShapeStr);

        int powerUpAmount = Integer.parseInt(props.get(textHandler.PROP_KEY_POWERUP_AMOUNT));
        effects = new Effect[powerUpAmount];

        for (int i = 0; i < powerUpAmount; i++) {
            String pKeyType = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_TYPE, i);
            String effectType = fileHandler.readPropertyFromFile(pKeyType, fileName);

            String pKeyDuration = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_DURATION, i);
            int effectDuration = Integer.parseInt(fileHandler.readPropertyFromFile(pKeyDuration, fileName));

            String pKeyWidth = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_WIDTH, i);
            int effectWidth = Integer.parseInt(fileHandler.readPropertyFromFile(pKeyWidth, fileName));

            String pKeyHeight = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_HEIGHT, i);
            int effectHeight = Integer.parseInt(fileHandler.readPropertyFromFile(pKeyHeight, fileName));
            Dimension effectDim = new Dimension(effectWidth, effectHeight);

            String pKeyShape = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_SHAPE, i);
            String effectShapeStr = fileHandler.readPropertyFromFile(pKeyShape, fileName);
            Shape effectShape = Shape.valueOf(effectShapeStr);

            String pKeyColor = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_COLOR, i);
            Color effectColor = new Color(Integer.decode(fileHandler.readPropertyFromFile(pKeyColor, fileName)));

            String pKeySpeed = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_EFFECT_SPEED, i);
            int effectSpeed = Integer.parseInt(fileHandler.readPropertyFromFile(pKeySpeed, fileName));

            String pEffectSpawnSound = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_SPAWN_SOUND_FILE_NAME, i);
            String effectSpawnSound = fileHandler.readPropertyFromFile(pEffectSpawnSound, fileName);
            soundHandler.addSoundClip(effectSpawnSound, textHandler.getClientSoundFilePath(effectSpawnSound));

            String pEffectCollideSound = textHandler.powerUpPropNumbered(textHandler.PROP_KEY_POWERUP_COLLIDE_SOUND_FILE_NAME, i);
            String effectCollideSound = fileHandler.readPropertyFromFile(pEffectCollideSound, fileName);
            soundHandler.addSoundClip(effectCollideSound, textHandler.getClientSoundFilePath(effectCollideSound));

            if (effectType.equals("Paddle")) {
                effects[i] = new PaddleEffect(effectDuration, effectDim, effectShape,
                        effectColor, effectSpeed, effectSpawnSound, effectCollideSound);
            } else {
                effects[i] = new BallEffect(effectDuration, effectDim, effectShape,
                        effectColor, effectSpeed, effectSpawnSound, effectCollideSound);
            }
        }

        /* Paddle options */
        int paddlePosXOffset = Integer.parseInt(props.get(textHandler.PROP_KEY_PADDLE_POS_X_OFFSET));
        int paddlePosYOffset = Integer.parseInt(props.get(textHandler.PROP_KEY_PADDLE_POS_Y_OFFSET));
        paddlePos = new Point((gameController.getWidth() / 2) - paddlePosXOffset, gameController.getHeight() - paddlePosYOffset);
        int paddleWidth = Integer.parseInt(props.get(textHandler.PROP_KEY_PADDLE_WIDTH));
        int paddleHeight = Integer.parseInt(props.get(textHandler.PROP_KEY_PADDLE_HEIGHT));
        paddleDim = new Dimension(paddleWidth, paddleHeight);
        String paddleShapeStr = props.get(textHandler.PROP_KEY_PADDLE_SHAPE);
        paddleShape = Shape.valueOf(paddleShapeStr);
        paddleSpeed = Integer.parseInt(props.get(textHandler.PROP_KEY_PADDLE_SPEED));
        int paddleCol = Integer.decode(props.get(textHandler.PROP_KEY_PADDLE_COLOR));
        paddleColor = new Color(paddleCol);

        /* Ball options */
        int ballWidth = Integer.parseInt(props.get(textHandler.PROP_KEY_BALL_WIDTH));
        int ballHeight = Integer.parseInt(props.get(textHandler.PROP_KEY_BALL_HEIGHT));
        ballDim = new Dimension(ballWidth, ballHeight);
        String ballShapeStr = props.get(textHandler.PROP_KEY_BALL_SHAPE);
        ballShape = Shape.valueOf(ballShapeStr);
        ballPos = new Point(paddlePos.x + (paddleWidth / 2) - (ballWidth / 2), paddlePos.y - (ballHeight));
        ballSpeed = Integer.parseInt(props.get(textHandler.PROP_KEY_BALL_SPEED));
        int ballColorHex = Integer.decode(props.get(textHandler.PROP_KEY_BALL_COLOR));
        ballColor = new Color(ballColorHex);

        /* Block options */
        int blockPosXStart = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_POS_X_START));
        int blockPosYStart = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_POS_Y_START));
        blockPosStart = new Point(blockPosXStart, blockPosYStart);
        int blockPosXSpacing = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_POS_X_SPACING));
        int blockPosYSpacing = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_POS_Y_SPACING));
        blockPosSpacing = new Point(blockPosXSpacing, blockPosYSpacing);
        blockAmount = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_AMOUNT));
        blockHitPoints = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_HEALTH));
        int blockWidth = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_WIDTH));
        int blockHeight = Integer.parseInt(props.get(textHandler.PROP_KEY_BLOCK_HEIGHT));
        String blockShapeStr = props.get(textHandler.PROP_KEY_BLOCK_SHAPE);
        blockShape = Shape.valueOf(blockShapeStr);
        blockDim = new Dimension(blockWidth, blockHeight);
        blockLum = Float.parseFloat(props.get(textHandler.PROP_KEY_BLOCK_LUMINANCE));
        blockSat = Float.parseFloat(props.get(textHandler.PROP_KEY_BLOCK_SATURATION));

        /* GamePanel options */
        int gamePanelTitleColorInt = Integer.decode(props.get(textHandler.PROP_KEY_GAME_PANEL_TITLE_COLOR));
        int gamePanelValueColorInt = Integer.decode(props.get(textHandler.PROP_KEY_GAME_PANEL_VALUE_COLOR));
        gamePanelTitleColor = new Color(gamePanelTitleColorInt);
        gamePanelValueColor = new Color(gamePanelValueColorInt);
    }

    public String getLevelName() {
        return levelName;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public int getLevelPlayerLife() {
        return levelPlayerLife;
    }

    public String getLevelSoundFileName() {
        return levelSoundFileName;
    }

    public Color getLevelColor() {
        return levelColor;
    }

    public Dimension getPowerUpDim() {
        return powerUpDim;
    }

    public int getPowerUpSpeed() {
        return powerUpSpeed;
    }

    public int getPowerUpChance() {
        return powerUpChance;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public Point getPaddlePos() {
        return paddlePos;
    }

    public Dimension getPaddleDim() {
        return paddleDim;
    }

    public Shape getPaddleShape() {
        return paddleShape;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public Color getPaddleColor() {
        return paddleColor;
    }

    public Point getBallPos() {
        return ballPos;
    }

    public Dimension getBallDim() {
        return ballDim;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public Point getBlockPosStart() {
        return blockPosStart;
    }

    public Point getBlockPosSpacing() {
        return blockPosSpacing;
    }

    public int getBlockAmount() {
        return blockAmount;
    }

    public int getBlockHitPoints() {
        return blockHitPoints;
    }

    public Dimension getBlockDim() {
        return blockDim;
    }

    public float getBlockLum() {
        return blockLum;
    }

    public float getBlockSat() {
        return blockSat;
    }

    public Color getGamePanelTitleColor() {
        return gamePanelTitleColor;
    }

    public Color getGamePanelValueColor() {
        return gamePanelValueColor;
    }

    public Shape getPowerUpShape() {
        return powerUpShape;
    }

    public Shape getBallShape() {
        return ballShape;
    }

    public Shape getBlockShape() {
        return blockShape;
    }

}