package io.inabsentia.celestialoutbreak.menu.player;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.data.dao.IPlayerDAO;
import io.inabsentia.celestialoutbreak.data.dao.PlayerDAO;
import io.inabsentia.celestialoutbreak.data.dto.PlayerDTO;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class PlayerSelectMenu extends Menu {

    private Rectangle[] playerRects;
    private final Font btnFont;

    private Color rectColor, selectedColor;

    private String[] options = {"SELECT", "NEW", "UPDATE", "REMOVE"};
    private Color[] rectColors;

    private final SoundHandler soundHandler;
    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    private int selected = 0;
    private int inputTimer = 18;
    private boolean firstUpdate = true;
    private int playerAmount = 0;

    private PlayerDTO playerDTO;

    public PlayerSelectMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                            Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        playerRects = new Rectangle[0];

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        /*
         * Do this ONCE everytime the user is on this screen.
         */
        if (firstUpdate) {
            firstUpdate = false;
            updatePlayerData();
        }

        if (inputTimer > 0) {
            inputTimer--;
        }

        if (inputHandler.isCancelPressed() && inputTimer == 0) {
            gameController.switchState(GameController.State.PLAYER_SETTINGS_MENU);
            firstUpdate = true;
            inputTimer = 10;
        }

    }

    @Override
    public void render(Graphics2D g) {
        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle("Select Player", g);

        /* Render buttons  */
        g.setFont(btnFont);
        g.setColor(fontColor);

        for (int i = 0; i < playerAmount; i++) {
            g.drawString(playerDTO.getPlayers().get(i), playerRects[i].x + 5, playerRects[i].y + 32);
            g.setColor(rectColors[i]);
            g.draw(playerRects[i]);
        }

        drawInformationPanel(g);
    }

    private void updatePlayerData() {
        try {
            playerDTO = playerDAO.getPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        playerAmount = playerDTO.getPlayers().size();

        // Update rectangles
        playerRects = new Rectangle[playerAmount];
        rectColors = new Color[playerAmount];

        int initialX = 150;
        int initialY = 240;
        int x = initialX;
        int y = initialY;
        int xInc = 200;
        int yInc = 80;

        for (int i = 0; i < playerAmount; i++) {
            rectColors[i] = Color.WHITE;
            if (i % 5 == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            playerRects[i] = new Rectangle(x, y, 150, 50);
            y += yInc;
        }
    }

}