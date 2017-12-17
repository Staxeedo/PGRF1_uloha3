package cz.uhk.pgrf1.c03.madr.uloha3.model;



import java.awt.Color;
import java.util.ArrayList;

public class Axis extends Solid {
	
	public Axis()
	{
		vertexBuffer.add(new Vertex(0,0,0,Color.RED));
		vertexBuffer.add(new Vertex(5,0,0,Color.RED));
		vertexBuffer.add(new Vertex(0,5,0,Color.GREEN));
		vertexBuffer.add(new Vertex(0,0,5,Color.BLUE));
		
	
		
		indexBuffer.add(0);
		indexBuffer.add(1);

		indexBuffer.add(0);
		indexBuffer.add(2);

		indexBuffer.add(0);
		indexBuffer.add(3);
	
		
		parts.add(new Part(Topology.LINES, 0,3));
		
		
	}

}
