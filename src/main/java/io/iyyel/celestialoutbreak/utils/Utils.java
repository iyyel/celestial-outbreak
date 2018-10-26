package io.iyyel.celestialoutbreak.utils;

import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public final class Utils {

    private static Utils instance;

    private final TextHandler textHandler = TextHandler.getInstance();

    private final Random random = new Random();

    private Utils() {

    }

    static {
        try {
            instance = new Utils();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Utils getInstance() {
        return instance;
    }

    public Color generatePastelColor(final float luminance, final float sat) {
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(2000) + 1000) / sat;
        final Color color = Color.getHSBColor(hue, saturation, luminance);
        return color;
    }

    public Font getGameFont() {
        Font gameFont = null;
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.GAME_FONT_FILE_CLIENT_PATH));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.GAME_FONT_FILE_CLIENT_PATH)));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return gameFont;
    }

    public Font getPanelFont() {
        Font panelFont = null;
        try {
            panelFont = Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.PANEL_FONT_FILE_CLIENT_PATH));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.PANEL_FONT_FILE_CLIENT_PATH)));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return panelFont;
    }

    public void createDemoPlayers(IPlayerDAO playerDAO) {
        try {
            playerDAO.addPlayer("Fluffy");
            playerDAO.selectPlayer("Fluffy");
            playerDAO.addPlayer("Rocket");
            playerDAO.addPlayer("Jackson");
            playerDAO.addPlayer("Sally");
            playerDAO.addPlayer("Emil");
            playerDAO.addPlayer("Dimitri");
            playerDAO.addPlayer("Jake");
            playerDAO.addPlayer("Veronica");
            playerDAO.addPlayer("Mike");
            playerDAO.addPlayer("Clarke");
            playerDAO.addPlayer("Mustafa");
            playerDAO.addPlayer("WoW");
            playerDAO.addPlayer("Iyyel");
            playerDAO.addPlayer("Musti");
            playerDAO.addPlayer("Hassan");
            playerDAO.addPlayer("Uweuweu");
            playerDAO.addPlayer("Cododa");
            playerDAO.addPlayer("Troels");
            playerDAO.addPlayer("Behnia");
            playerDAO.addPlayer("Thomas");
            playerDAO.addPlayer("Daniel");
            playerDAO.addPlayer("Kevin");
            playerDAO.addPlayer("Alice");
            playerDAO.addPlayer("Ryan");
            playerDAO.addPlayer("Rackman");
            playerDAO.savePlayerDTO();
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }
    }

}