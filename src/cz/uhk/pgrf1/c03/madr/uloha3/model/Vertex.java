package cz.uhk.pgrf1.c03.madr.uloha3.model;


import transforms.Mat4;
import transforms.Point3D;

public class Vertex {

	private Point3D position;
	private final int color;
	// x,y,z barva nebo Position 3D
	public Vertex(Point3D position,int color)
	{
		this.position= position;
		this.color = color;
	}
	public Vertex(double x, double y, double z, int color) {
		this.position=new Point3D(x,y,z);
		this.color = color;
		
	}
	//imutable operace
	public Vertex mul(Mat4 matrix)
	{
		
		Point3D newPoint = position.mul(matrix);
		
		
		Vertex newVertex=new Vertex(newPoint,this.color);
		
		return newVertex;
	}
	public Point3D getPosition() {
		return position;
	}

	
}
