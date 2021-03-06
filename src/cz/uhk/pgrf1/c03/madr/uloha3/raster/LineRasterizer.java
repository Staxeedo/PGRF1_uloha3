package cz.uhk.pgrf1.c03.madr.uloha3.raster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import transforms.Vec3D;
/**
 * Trida pro vykresleni usecky
 * @author stand
 *
 */
public class LineRasterizer extends Rasterizer {
	public LineRasterizer(BufferedImage img) {
		super(img);

	}

	public void draw(Vec3D a, Vec3D b, Color color) {
		Graphics g = img.getGraphics();
		g.setColor(color);
		int x1 = (int) a.getX();
		int x2 = (int) b.getX();
		int y1 = (int) a.getY();
		int y2 = (int) b.getY();
         
		g.drawLine(x1, y1, x2, y2);

	}

}
