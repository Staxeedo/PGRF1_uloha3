package cz.uhk.pgrf1.c03.madr.uloha3.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {

	public enum Topology {
		TRIANGLES, LINES
	};

	protected final List<Vertex> vertexBuffer = new ArrayList<>();
	// jak se ma pospojovat
	protected final List<Integer> indexBuffer = new ArrayList<>();
	protected final List<Part> parts = new ArrayList<>();

	public class Part {

		private Topology type;
		private int start;
		private int count;

		public Part(Topology type, int start, int count) {
			this.type = type;
			this.start = start;
			this.count = count;

		}

		public Topology getTopology()

		{
			return this.type;
		}

		public int getStartIndex() {
			return this.start;
		}

		public int getCount() {
			return this.count;
		}

	}


	public List<Vertex> getVertexBuffer() {
		return vertexBuffer;
	}

	public List<Integer> getIndexBuffer() {
		return indexBuffer;
	}

	public List<Part> getParts() {
		return parts;
	}
}
