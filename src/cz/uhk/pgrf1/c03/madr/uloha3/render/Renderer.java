package cz.uhk.pgrf1.c03.madr.uloha3.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cz.uhk.pgrf1.c03.madr.uloha3.model.Solid;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Solid.Part;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Vertex;
import cz.uhk.pgrf1.c03.madr.uloha3.raster.LineRasterizer;
import cz.uhk.pgrf1.c03.madr.uloha3.raster.TriangleRasterizer;
import transforms.Mat4;
import transforms.Vec3D;

public class Renderer{

	private List<Vertex> transVertexB = new ArrayList<>();
	private Mat4 model, view, projection;
	private TriangleRasterizer tren;
	private LineRasterizer lren;
	double halfOfWidth;
	double halfOfHeigh;



	public Renderer(TriangleRasterizer triangleRasterizer,LineRasterizer lineRasterizer, BufferedImage img) {
		tren = triangleRasterizer;
		lren = lineRasterizer;
		halfOfHeigh = img.getHeight()/2;
		halfOfWidth= img.getWidth()/2;

	}

	public Mat4 getModel() {
		return model;
	}

	public void setModel(Mat4 model) {
		this.model = model;
	}

	public Mat4 getView() {
		return view;
	}

	public void setView(Mat4 view) {
		this.view = view;
	}

	public Mat4 getProjection() {
		return projection;
	}

	public void setProjection(Mat4 projection) {
		this.projection = projection;
	}

	public void render(Solid solid)
	{
		List<Vertex> vertexBuffer = solid.getVertexBuffer();
		// M V P transformace
		Mat4 matMVP = model.mul(view).mul(projection);
		//kazdy vrchol se projde a ztranformuje se
		for(Vertex v : vertexBuffer)
		{	
		    Vertex newVertex = v.mul(matMVP);		
			transVertexB.add(newVertex);
		}
		
		
		int i1 = 0,i2 = 0,i3 = 0;
		for(Part parts:solid.getParts())
		{	
			int startIndex = parts.getStartIndex();
			
			for(int partNumber=0;partNumber<parts.getCount();partNumber++)
			{
				
			
			
			switch(parts.getTopology())
			{
			case TRIANGLES:
				// veme vrchol na indexu
				// inkrementace starindexu o 3
				i1 =solid.getIndexBuffer().get(startIndex);
				i2=solid.getIndexBuffer().get(startIndex+1);
				i3 = solid.getIndexBuffer().get(startIndex+2);
				triangle(transVertexB.get(i1),transVertexB.get(i2),transVertexB.get(i3));
				startIndex+=3;
				break;
			case LINES:
				//line(startIndex,startIndex+1)
				
				i1 =solid.getIndexBuffer().get(startIndex);
				i2=solid.getIndexBuffer().get(startIndex+1);
				line(transVertexB.get(i1),transVertexB.get(i2));
				startIndex+=2;
				break;
			}
		}
		}
		
	
	}

	private void triangle(Vertex a, Vertex b, Vertex c) {
		// TODO clip, vyhazuje co nam vycuhuje ven, podminky musime zkontrolovat hranice
		
		// dehomog
		if (!a.getPosition().dehomog().isPresent()){
			return;// neregulerni trojuhelnik(w=0), zahodime
		
		}
		// 4D do 3D
		Vec3D va = a.getPosition().dehomog().get();// vraci optional
		// splasknuti x,y a zahodime z
		Vec3D vb = b.getPosition().dehomog().get();
		Vec3D vc = c.getPosition().dehomog().get();

		// dalsi krok viewPort (transformace do okna )

		// otoceni y (y je -y)
		va = new Vec3D(va.getX()+1, -va.getY()+1, 0);// vynasobit vektorem (1,-1,0)
		vb = new Vec3D(vb.getX()+1, -vb.getY()+1, 0);
		vc = new Vec3D(vc.getX()+1, -vc.getY()+1, 0);
		// pricteme k x a y 1, bod [-1,-1] se zmeni na [0,0]

		Vec3D v1 = new Vec3D(va.getX()*halfOfWidth,va.getY()*halfOfHeigh,0);
		Vec3D v2 = new Vec3D(vb.getX()*halfOfWidth,vb.getY()*halfOfHeigh,0);
		Vec3D v3 = new Vec3D(vc.getX()*halfOfWidth,vc.getY()*halfOfHeigh,0);

		tren.draw(v1, v2, v3);

	}

	private void line(Vertex a, Vertex b) {
		
		
		
		if (!a.getPosition().dehomog().isPresent() || !b.getPosition().dehomog().isPresent()){
			return;
		}
		// 4D do 3D
				Vec3D va = a.getPosition().dehomog().get();
				Vec3D vb = b.getPosition().dehomog().get();
				
		// viewPort
				/*
				va = new Vec3D(va.getX()+1, -va.getY()+1, 0);// vynasobit vektorem (1,-1,0)
				vb = new Vec3D(vb.getX()+1, -vb.getY()+1, 0);
				*/
				double vZ = 0;
				double vX=((va.getX() + 1)*halfOfWidth);
				double vX2= ((vb.getX() + 1)*halfOfWidth);
				double vY = ((-va.getY() + 1)*halfOfHeigh);
				double vY2= ((-vb.getY() + 1)*halfOfHeigh);
				Vec3D v1 = new Vec3D(vX,vY,vZ);
				Vec3D v2 = new Vec3D(vX2,vY2,vZ);
				/*
				Vec3D v1 = new Vec3D(va.getX()*halfOfWidth,va.getY()*halfOfHeigh,0);
				Vec3D v2 = new Vec3D(vb.getX()*halfOfWidth,vb.getY()*halfOfHeigh,0);
				*/
				//System.out.println(vX+" "+vY);
				lren.draw(v1, v2);
				
	}
	
	
	

}
