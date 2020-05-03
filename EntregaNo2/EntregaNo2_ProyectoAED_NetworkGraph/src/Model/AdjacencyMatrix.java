package Model;

public class AdjacencyMatrix<T extends Object,K extends Number> implements IAdjacencyMatrix<T, K>{
	
	private T[] devices;
	private K[][] network;
	private int currentSize;
	
	
	public AdjacencyMatrix(int devicesAmount){
		network =  (K[][]) new Number[devicesAmount][devicesAmount];
		devices = (T[]) new Object[devicesAmount];
		currentSize = 0;
	}
	
	@Override
	public void insertNode(T node) {
		if(currentSize < devices.length) {
			devices[currentSize] = node;
			currentSize++;
		}
	}
	
	@Override
	public T getNode(int position) {
		return (position >= 0 && position < devices.length) ? devices[position] : null;
	}
	
	public T[] getDevices() {
		return devices;
	}

	public void setDevices(T[] devices) {
		this.devices = devices;
	}

	public K[][] getNetwork() {
		return network;
	}

	public void setNetwork(K[][] network) {
		this.network = network;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	@Override
	public void removeNode(int position) {
		if(position >= 0 && position < devices.length) {
			devices[position] = null;
			currentSize--;
		}
	}
	
	@Override
	public void insertEdge(int firstNode, int secondNode, K value) {
		if(firstNode >= 0 && firstNode < devices.length && secondNode >=0 && secondNode < devices.length) {
			network[firstNode][secondNode] = value;
			network[secondNode][firstNode] = value;
		}
	}
	
	@Override
	public K getEdge(int firstNode, int secondNode) {
		return (firstNode >= 0 && firstNode < devices.length && secondNode >=0 && secondNode < devices.length) 
				? network[firstNode][secondNode] : null;
	}
	
	@Override
	public void deleteEdge(int firstNode, int secondNode) {
		if(firstNode >= 0 && firstNode < devices.length && secondNode >=0 && secondNode < devices.length) {
			network[firstNode][secondNode] = null;
			network[secondNode][firstNode] = null;
		}
	}

	@Override
	public boolean isEmpty() {
		return devices.length==0;
	}
	
	

}
