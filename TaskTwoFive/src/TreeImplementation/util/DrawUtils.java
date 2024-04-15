package TreeImplementation.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class DrawUtils {
    public static void drawStringInCenter(Graphics gr, Font font, String s, int x, int y, int width, int height) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (width / 2) - (rWidth / 2) - rX;
        int b = (height / 2) - (rHeight / 2) - rY;

        gr.setFont(font);
        gr.drawString(s, x + a, y + b);
    }
}
