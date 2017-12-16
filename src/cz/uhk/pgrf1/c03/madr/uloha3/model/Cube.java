package cz.uhk.pgrf1.c03.madr.uloha3.model;

public class Cube extends Solid{
	public Cube()
	{
		vertexBuffer.add(new Vertex(3, 0, 0,0x00ff00));
		vertexBuffer.add(new Vertex(3, 3, 0,0x00ff00));
		vertexBuffer.add(new Vertex(0, 3, 0,0x00ff00));
		vertexBuffer.add(new Vertex(0, 0, 0,0x00ff00));
		vertexBuffer.add(new Vertex(3, 0, 3,0x00ff00));
		vertexBuffer.add(new Vertex(3, 3, 3,0x00ff00));
		vertexBuffer.add(new Vertex(0, 3, 3,0x00ff00));
		vertexBuffer.add(new Vertex(0, 0, 3,0x00ff00));

		
		indexBuffer.add(0);
		indexBuffer.add(1);
		
		indexBuffer.add(1);
		indexBuffer.add(2);
		
		indexBuffer.add(2);
		indexBuffer.add(3);
		
		indexBuffer.add(3);
		indexBuffer.add(0);
		
		indexBuffer.add(4);
		indexBuffer.add(5);
		
		indexBuffer.add(5);
		indexBuffer.add(6);
		
		indexBuffer.add(6);
		indexBuffer.add(7);
		
		indexBuffer.add(7);
		indexBuffer.add(4);
		
		indexBuffer.add(0);
		indexBuffer.add(4);
		
		indexBuffer.add(1);
		indexBuffer.add(5);
		
		indexBuffer.add(2);
		indexBuffer.add(6);
		
		indexBuffer.add(3);
		indexBuffer.add(7);
		
		parts.add(new Part(Topology.LINES,0,12));
	}

}
