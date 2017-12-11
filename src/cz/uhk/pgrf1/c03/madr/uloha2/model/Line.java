package cz.uhk.pgrf1.c03.madr.uloha2.model;

/**
 * trida pro uchovavani informaci o primce
 * 
 * 
 */

public class Line {
	private Point first;
	private Point last;

	public Line() {
	}

	public Line(Point f, Point l) {
		first = f;
		last = l;
	}

	public Point getFirst() {
		return this.first;

	}

	public Point getLast() {
		return this.last;

	}

	public void setFirst(Point p) {
		this.first = p;
	}

	public void setLast(Point p) {
		this.last = p;
	}

	public Boolean isHorizontal() {
		// usecka je vodorovna
		return (int) first.getY() == (int) last.getY();

	}

	public Line getOrientedLine() {
		// spravne orientovana primka
		if (first.getY() > last.getY()) {

			return new Line(last, first);

		}

		return this;

	}

	public Boolean isIntersection(int y) {
		if(y>=first.getY()&&y<last.getY())
			return true;
		return false;

	}


	public Integer intersection(int y) {
		// dopocitani x pruseciku
		double k = (last.getX()-first.getX()) / (last.getY()-first.getY());
		double q = first.getX() - (k * first.getY());
		int x = (int) (k*y+q) ;
		return x;
	}

	public Boolean isInside(Point v) {
		
		int x = (int)v.getX();
		int y = (int)v.getY();
		int aX = (int)first.getX();
		int aY = (int)first.getY();
		int bX= (int)last.getX();
		int bY = (int)last.getY();
		
		
		int side = ((bX-aX)*(y-aY)-(bY-aY)*(x-aX));
		// 3 stavy : 0 na primce, <0 na jedne strane primky, >0 na druhe strane primky
		if(side<0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public Point intersection(Point v1, Point v2) {
		// metoda pro vypocet pruseciku pomoci primky a dvou bodu
		double x0,y0;
		double x1 = first.getX();
		double y1 = first.getY();
		double x2 = last.getX();
		double y2= last.getY();
		double x3 = v2.getX();
		double y3 = v2.getY();
		double x4 = v1.getX();
		double y4 = v1.getY();
		
		
		
		x0=((x1*y2-x2*y1)*(x3-x4)-(x3*y4-x4*y3)*(x1-x2))
				/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		y0=((x1*y2-x2*y1)*(y3-y4)-(x3*y4-x4*y3)*(y1-y2))
			/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		return new Point(x0,y0);
	}

}
