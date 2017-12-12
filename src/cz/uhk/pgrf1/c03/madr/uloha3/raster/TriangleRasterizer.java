package cz.uhk.pgrf1.c03.madr.uloha3.raster;



import java.awt.image.BufferedImage;

import transforms.Vec3D;

public class TriangleRasterizer {
	BufferedImage img;
	public TriangleRasterizer(BufferedImage img) {
		this.img=img;
	}
	public void draw(Vec3D v1, Vec3D v2, Vec3D v3) {
		
		img.getGraphics().drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v3.getY());
		//...
	}

}
