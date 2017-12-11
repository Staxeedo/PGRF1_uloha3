package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;

import cz.uhk.pgrf1.c03.madr.uloha2.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Polygon;
/**
 * trida pro vykreslovani polygonu
 */
public class PolygonRenderer extends Renderer {
	
	LineRenderer lren=new LineRenderer(img);
	
	 public PolygonRenderer(BufferedImage img) {
		super(img);
		
		
	}
	 
	 
	public void draw(Polygon pol, int color)
	{
		Line lin= new Line();
		Point p;
		Point p2;
		
		for(int i = 0 ; i<pol.getSize();i++)
		{
			p=pol.getPoint(i);
			// vykresleni cary mezi poslednim a prvnim bodem
			if(i==pol.getSize()-1)
			{
				p2=pol.getPoint(0);
			}
			else
			{
				p2=pol.getPoint((i+1)%pol.getSize());
			}
			
			lin.setFirst(p);
			lin.setLast(p2);
			lren.draw(lin,color);
			
		}
	

		
		
	}
	 

}

