package Model;

import java.util.HashMap;

public class AdjacencyList<T extends Object, K extends Number> implements IAdjacencyList<T, K> {
	
	private HashMap<T, HashMap<T, IEdge<T, K>>> list;
	private int currentSize;
	private int vertexTotal;
	
	public HashMap<T, HashMap<T, IEdge<T, K>>> getList() {
		return list;
	}

	public void setList(HashMap<T, HashMap<T, IEdge<T, K>>> list) {
		this.list = list;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getVertexTotal() {
		return vertexTotal;
	}

	public void setVertexTotal(int vertexTotal) {
		this.vertexTotal = vertexTotal;
	}

	public AdjacencyList(int devicesAmount) {
		if(devicesAmount>0) {
			list = new HashMap<T, HashMap<T,IEdge<T,K>>>();
			currentSize = devicesAmount;
			vertexTotal = 0;
		}
	}

	@Override
	public void insertNode(T node) {
		if(currentSize < vertexTotal-1) {
			list.put(node, null);
			currentSize++;
		}
	}

	@Override
	public T getNode(T node) {
		return list.containsKey(node) ? node : null;
	}

	@Override
	public void removeNode(T node) {
		if(currentSize > 0) {
			list.remove(node);
			currentSize--;
		}
	}

	@Override
	public void insertEdge(T firstNode, T secondNode, K value) {
		HashMap<T, IEdge<T, K>> nodes = list.get(firstNode);
		if(nodes!=null) {
			nodes.put(secondNode, new IEdge<T, K>(firstNode, secondNode, value));
			list.put(firstNode, nodes);
		}else {
			HashMap<T, IEdge<T, K>> edge = new HashMap<T, IEdge<T,K>>();
			edge.put(secondNode, new IEdge<T, K>(firstNode, 
					secondNode, value));			
			list.put(firstNode, edge );
		}
		
		
		//Insert in the Adjacent Vertex
		
		
		nodes = list.get(secondNode);
		if(nodes!=null) {
			nodes.put(firstNode, new IEdge<T, K>(secondNode, firstNode, value));
			list.put(secondNode, nodes);
		}else {
			HashMap<T, IEdge<T, K>> edge = new HashMap<T, IEdge<T,K>>();
			edge.put(firstNode, new IEdge<T, K>(secondNode, 
					firstNode, value));			
			list.put(secondNode, edge );
		}
	}

	@Override
	public K getEdge(T firstNode, T secondNode) {
		HashMap<T, IEdge<T, K>> nodes = list.get(firstNode);
		if(nodes!=null) {
			return nodes.get(secondNode)!=null ? nodes.get(secondNode).getWeight() : null;
		}else return null;
	}

	@Override
	public void deleteEdge(T firstNode, T secondNode) {
		HashMap<T, IEdge<T, K>> nodes = list.get(firstNode);
		if(nodes!=null) {
			nodes.remove(secondNode);
			list.put(firstNode, nodes);
		}
		
		//Remove Edge in the Adjacent Vertex
		
		nodes = list.get(secondNode);
		if(nodes!=null) {
			nodes.remove(firstNode);
			list.put(secondNode, nodes);
		}
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	

}
