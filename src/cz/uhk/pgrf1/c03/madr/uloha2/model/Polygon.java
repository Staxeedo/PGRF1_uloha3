
package cz.uhk.pgrf1.c03.madr.uloha2.model;

import java.util.ArrayList;

/**
 * trida pro uchovavani informaci o polygonu
 */
public class Polygon {

	private ArrayList<Point> points = new ArrayList<>();

	public Polygon() {

	}
	public Polygon(Point one, Point two, Point three)
	{
		points.add(one);
		points.add(two);
		points.add(three);
	}

	public Polygon(Polygon clipedPoly) {
		for(int i = 0; i<clipedPoly.getSize();i++)
		{
			this.points.add(clipedPoly.getPoint(i));
		}
	}
	public void add(Point p) {
		points.add(p);
	}

	public Point getPoint(int index) {
		if (index < 0 || index > points.size()) {
			throw new IndexOutOfBoundsException();
		}
		return points.get(index);

	}

	public int getSize() {
		return points.size();
	}

	public Point getLast() {
		return points.get(getSize() - 1);

	}
	public void clearPolygon()
	{
		this.points.clear();
	}

}
