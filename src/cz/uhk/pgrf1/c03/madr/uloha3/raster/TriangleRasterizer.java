package cz.uhk.pgrf1.c03.madr.uloha3.raster;



import java.awt.Color;
import java.awt.image.BufferedImage;

import transforms.Vec3D;
/**
 * Trida pro vykresleni trojuhelnika
 * @author stand
 *
 */
public class TriangleRasterizer extends Rasterizer {
	

	public TriangleRasterizer(BufferedImage img) {
		super(img);
		
	}

	public void draw(Vec3D v1, Vec3D v2, Vec3D v3,Color color) {
		
		img.getGraphics().setColor(color);
		img.getGraphics().drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
		img.getGraphics().drawLine((int)v2.getX(), (int)v2.getY(), (int)v3.getX(), (int)v3.getY());
		img.getGraphics().drawLine((int)v3.getX(), (int)v3.getY(), (int)v1.getX(), (int)v1.getY());
		
	}

}
