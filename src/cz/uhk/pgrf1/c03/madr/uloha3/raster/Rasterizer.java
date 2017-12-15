package cz.uhk.pgrf1.c03.madr.uloha3.raster;

import java.awt.image.BufferedImage;

public abstract class Rasterizer 
{
	protected BufferedImage img;  
	
	public Rasterizer(BufferedImage img)
	{
		this.img = img;
	}
}