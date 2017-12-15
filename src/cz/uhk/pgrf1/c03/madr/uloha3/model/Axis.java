package cz.uhk.pgrf1.c03.madr.uloha3.model;



import java.util.ArrayList;

public class Axis extends Solid {
	
	public Axis()
	{
		vertexBuffer.add(new Vertex(0,0,0,0Xff0000));
		vertexBuffer.add(new Vertex(1,0,0,0Xff0000));
		vertexBuffer.add(new Vertex(0,1,0,0Xff0000));
		vertexBuffer.add(new Vertex(0,0,1,0Xff0000));
		
	
		
		indexBuffer.add(0);
		indexBuffer.add(1);

		indexBuffer.add(0);
		indexBuffer.add(2);

		indexBuffer.add(0);
		indexBuffer.add(3);
	
		
		parts.add(new Part(Topology.LINES, 0,3));
		
		
	}

}
