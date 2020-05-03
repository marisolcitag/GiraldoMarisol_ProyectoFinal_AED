package Model;

public interface IAdjacencyMatrix<T extends Object,K extends Number> {
	
	public void insertNode(T node);
	
	public T getNode(int position);
	
	public void removeNode(int position);
	
	public void insertEdge(int firstNode, int secondNode, K value);
	
	public K getEdge(int firstNode, int secondNode);
	
	public void deleteEdge(int firstNode, int secondNode);
	
	public boolean isEmpty();
}
