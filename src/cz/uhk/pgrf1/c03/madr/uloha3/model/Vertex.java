package cz.uhk.pgrf1.c03.madr.uloha3.model;


import java.awt.Color;

import transforms.Mat4;
import transforms.Point3D;
/**
 * Vrcholy
 * 
 * @author stand
 *
 */
public class Vertex {

	private Point3D position;
	private final Color color;
	public Vertex(Point3D position,Color color)
	{
		this.position= position;
		this.color = color;
	}
	public Vertex(double x, double y, double z, Color color) {
		this.position=new Point3D(x,y,z);
		this.color = color;
		
	}
	public Vertex mul(Mat4 matrix)
	{
		
		Point3D newPoint = position.mul(matrix);
		
		
		Vertex newVertex=new Vertex(newPoint,this.color);
		
		return newVertex;
	}
	public Point3D getPosition() {
		return position;
	}
	public Color getColor()
	{
		return this.color;
	}

	
}
