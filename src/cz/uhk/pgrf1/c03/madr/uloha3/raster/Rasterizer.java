package cz.uhk.pgrf1.c03.madr.uloha3.raster;

import java.awt.image.BufferedImage;
/**
 * Abstraktni trida pro Rasterizery
 * @author stand
 *
 */
public abstract class Rasterizer 
{
	protected BufferedImage img;  
	
	public Rasterizer(BufferedImage img)
	{
		this.img = img;
	}
}