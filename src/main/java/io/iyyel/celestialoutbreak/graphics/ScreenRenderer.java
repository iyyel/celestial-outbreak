package io.iyyel.celestialoutbreak.graphics;

import java.awt.*;
import java.util.Arrays;

public final class ScreenRenderer {

    private final int WIDTH;
    private final int HEIGHT;

    private final int[] pixels;

    public ScreenRenderer(int width, int height, int[] pixels) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.pixels = pixels;
    }

    public void render(Color color) {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++)
                pixels[x + y * WIDTH] = color.getRGB();
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

}