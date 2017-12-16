package cz.uhk.pgrf1.c03.madr.uloha3.model;

import java.awt.Color;

public class Cube extends Solid{
	public Cube()
	{
		vertexBuffer.add(new Vertex(3, 0, 0,Color.WHITE));
		vertexBuffer.add(new Vertex(3, 3, 0,Color.WHITE));
		vertexBuffer.add(new Vertex(0, 3, 0,Color.WHITE));
		vertexBuffer.add(new Vertex(0, 0, 0,Color.WHITE));
		vertexBuffer.add(new Vertex(3, 0, 3,Color.WHITE));
		vertexBuffer.add(new Vertex(3, 3, 3,Color.WHITE));
		vertexBuffer.add(new Vertex(0, 3, 3,Color.WHITE));
		vertexBuffer.add(new Vertex(0, 0, 3,Color.WHITE));

		
		indexBuffer.add(0);
		indexBuffer.add(1);
		
		indexBuffer.add(0);
		indexBuffer.add(4);
		
		indexBuffer.add(1);
		indexBuffer.add(2);
		
		indexBuffer.add(1);
		indexBuffer.add(5);
		
		indexBuffer.add(2);
		indexBuffer.add(3);
		
		indexBuffer.add(2);
		indexBuffer.add(6);
		
		indexBuffer.add(3);
		indexBuffer.add(0);
		
		indexBuffer.add(3);
		indexBuffer.add(7);
		
		indexBuffer.add(4);
		indexBuffer.add(5);
		
		indexBuffer.add(5);
		indexBuffer.add(6);
		
		indexBuffer.add(6);
		indexBuffer.add(7);
		
		indexBuffer.add(7);
		indexBuffer.add(4);
		
	
		
	
		
	
		
	
		
		parts.add(new Part(Topology.LINES,0,12));
	}

}
