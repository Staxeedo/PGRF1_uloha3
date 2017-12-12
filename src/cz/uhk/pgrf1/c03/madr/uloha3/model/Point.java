package cz.uhk.pgrf1.c03.madr.uloha3.model;

/**
 * trida pro uchovavani informaci o jednotlivem bode
 * 
 * 
 */
public class Point {

	private double x;
	private double y;

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}