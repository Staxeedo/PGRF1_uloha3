package cz.uhk.pgrf1.c03.madr.uloha3.render;

import java.awt.image.BufferedImage;
/**
 * Interface pro ostatni Renderery
 */
public class Renderer {
	protected BufferedImage img;

	Renderer(BufferedImage img) {
		this.img = img;
	}
}
