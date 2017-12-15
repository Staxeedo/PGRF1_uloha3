package cz.uhk.pgrf1.c03.madr.uloha3.model;

public class TetraHedron extends Solid {
	public TetraHedron()
	{
	
    /*
    vertexBuffer.add(new Vertex(-0.5,-0.5,-0.5, 0x00ff00));
	vertexBuffer.add(new Vertex(-1.5,-0.5,-0.5, 0x00ff00));
	vertexBuffer.add(new Vertex(-0.5,-1.5,-0.5, 0x00ff00));
	vertexBuffer.add(new Vertex(-0.5,-0.5,-1.5, 0x00ff00));
	*/
	vertexBuffer.add(new Vertex(-2, -2, -2,0x00ff00));
	vertexBuffer.add(new Vertex(2, -2, 2,0x00ff00));
	vertexBuffer.add(new Vertex(-2, 2, 2,0x00ff00));
	vertexBuffer.add(new Vertex(2, 2, -2,0x00ff00));
	indexBuffer.add(0);
	indexBuffer.add(1);
	
	indexBuffer.add(2);
	indexBuffer.add(0);
	
	indexBuffer.add(2);
	indexBuffer.add(3);
	
	indexBuffer.add(0);
	indexBuffer.add(3);
	
	indexBuffer.add(1);
	indexBuffer.add(3);
	
	indexBuffer.add(2);
	indexBuffer.add(1);
	// 6 bodu propojeni indexbuffer
	parts.add(new Part(Topology.LINES, 0,6));
	}
}
