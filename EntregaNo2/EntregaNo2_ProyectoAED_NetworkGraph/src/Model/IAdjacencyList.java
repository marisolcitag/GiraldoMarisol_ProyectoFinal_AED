package Model;

public interface IAdjacencyList<T extends Object, K extends Number> {
	
	public void insertNode(T node);
	
	public T getNode(T node);
	
	public void removeNode(T node);
	
	public void insertEdge(T firstNode, T secondNode, K value);
	
	public K getEdge(T firstNode, T secondNode);
	
	public void deleteEdge(T firstNode, T secondNode);
	
	public boolean isEmpty();
}
