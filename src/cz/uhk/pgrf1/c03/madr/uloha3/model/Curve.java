package cz.uhk.pgrf1.c03.madr.uloha3.model;

import java.awt.Color;

import transforms.Cubic;
import transforms.Point3D;

public class Curve extends Solid{
	
	public Curve() {
		
		// ridici body
		Point3D r1 = new Point3D(3,1,1);
		Point3D r2 = new Point3D(1.5,2,2); 
		Point3D r3 = new Point3D(1,1,3);
		Point3D r4 = new Point3D(1,3,3);
		
		Cubic cub = new Cubic(Cubic.BEZIER, r1,r2,r3,r4);
		double param = 0.1;
		for(double i = 0; i <= 1; i+=param) 
		{
			
			Point3D res = cub.compute(i);
			vertexBuffer.add(new Vertex(res,Color.ORANGE));
		}		
		
		indexBuffer.add(0);
		indexBuffer.add(1);
		
		indexBuffer.add(1);
		indexBuffer.add(2);
		
		indexBuffer.add(2);
		indexBuffer.add(3);
		
		indexBuffer.add(3);
		indexBuffer.add(4);
		
		indexBuffer.add(4);
		indexBuffer.add(5);
		
		indexBuffer.add(5);
		indexBuffer.add(6);
		
		indexBuffer.add(6);
		indexBuffer.add(7);

		indexBuffer.add(7);
		indexBuffer.add(8);
		
		indexBuffer.add(8);
		indexBuffer.add(9);
		
		indexBuffer.add(9);
		indexBuffer.add(10);
		
	
		
		
		parts.add(new Part(Topology.LINES, 0, 10));
		
	}

}
