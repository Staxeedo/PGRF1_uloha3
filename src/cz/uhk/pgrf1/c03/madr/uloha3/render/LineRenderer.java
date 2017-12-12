package cz.uhk.pgrf1.c03.madr.uloha3.render;

import java.awt.image.BufferedImage;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Point;

/**
 * trida pro vykreslovani primky
 */

public class LineRenderer extends Renderer {

	public LineRenderer(BufferedImage img) {
		super(img);
	}

	public void draw(Line line, int color) {

		Point first = line.getFirst();
		Point last = line.getLast();
		double k, dx, dy, x, y;
		// usecka ma 2 stejne body
		if (first.getX() == last.getX() && first.getY() == last.getY()) {
			img.setRGB((int) first.getX(), (int) last.getY(), color);
		} else {
			// podle dx a dy poznam kde vykresluju a spocitam k 
			dy = (last.getY() - first.getY());
			dx = (last.getX() - first.getX());
			k = dy / dx;

			// ridime se podle x
			if (Math.abs(dx) >= Math.abs(dy)) {
				// x2<x1
				if (dx < 0) {

					first = line.getLast();
					last = line.getFirst();
				}
				y = first.getY();
				for (double i = first.getX(); i <= last.getX(); i++) {
					img.setRGB((int) i, (int) y, color);
					y = y + k;
				}
			} else {
				// ridime se podle y
				// y2<y1
				if (dy < 0) {
					first = line.getLast();
					last = line.getFirst();

				}

				x = first.getX();
				for (double i = first.getY(); i <= last.getY(); i++) {

					img.setRGB((int) x, (int) i, color);
					x = x + 1 / k;
				}

			}

		}
	}

}
