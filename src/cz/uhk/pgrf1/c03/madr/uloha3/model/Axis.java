package cz.uhk.pgrf1.c03.madr.uloha3.model;



import java.util.ArrayList;

public class Axis extends Solid {
	
	public Axis()
	{
		vertexBuffer.add(new Vertex(0,0,0,0xFF00));
		vertexBuffer.add(new Vertex(0,0,1.1,0xFF00));
		//vertexBuffer.add(new Vertex());
		
		indexBuffer.add(0);
		indexBuffer.add(1);
		indexBuffer.add(0);
		indexBuffer.add(2);
		indexBuffer.add(0);
		indexBuffer.add(3);
		indexBuffer.add(4);
		// chci vykreslit trojuhelnik, ktery zacina na indexBufferu na indexu 6 a chci 3 polozky
		parts.add(new Part(Topology.TRIANGLES,6,3));
		parts.add(new Part(Topology.LINES,0,3));
	}

}
