package io.inabsentia.celestialoutbreak.graphics;

import java.awt.*;

public class Screen {

    private final int WIDTH;
    private final int HEIGHT;

    public int[] pixels;

    private final Color color;

    public Screen(int width, int height, int[] pixels, Color color) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.pixels = pixels;
        this.color = color;
    }

    public void render() {
        for (int y = 0; y < HEIGHT; y++) {
            if (y < 0 || y >= HEIGHT) break;
            for (int x = 0; x < WIDTH; x++) {
                if (x < 0 || x >= WIDTH) break;
                pixels[x + y * WIDTH] = color.darker().getRGB();
            }
        }
    }

    public void render(Color color) {
        for (int y = 0; y < HEIGHT; y++) {
            if (y < 0 || y >= HEIGHT) break;
            for (int x = 0; x < WIDTH; x++) {
                if (x < 0 || x >= WIDTH) break;
                pixels[x + y * WIDTH] = color.getRGB();
            }
        }
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

}