package Model;

public class IEdge<T extends Object, K extends Number> {
	
	private T vertexOne;
	private T vertexTwo;
	private K weight;
	
	public IEdge(T vertexOne, T vertexTwo, K weight) {
		this.vertexOne = vertexOne;
		this.setVertexTwo(vertexTwo);
		this.weight = weight;
	}

	public T getVertexOne() {
		return vertexOne;
	}

	public void setVertexOne(T vertex) {
		this.vertexOne = vertex;
	}

	public K getWeight() {
		return weight;
	}

	public void setWeight(K weight) {
		this.weight = weight;
	}

	public T getVertexTwo() {
		return vertexTwo;
	}

	public void setVertexTwo(T vertexTwo) {
		this.vertexTwo = vertexTwo;
	}
	
	
	
	
}
