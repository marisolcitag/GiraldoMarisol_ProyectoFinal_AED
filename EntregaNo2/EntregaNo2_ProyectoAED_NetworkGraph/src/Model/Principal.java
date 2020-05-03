package Model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Principal {
	
	private AdjacencyMatrix<NetworkDevice, Integer> networkMatrix;
	private AdjacencyList<NetworkDevice, Integer> networkList;
	private boolean isMatrix;
	
	public ArrayList<Integer> transformData() {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		if(networkMatrix!=null) {
			if(isMatrix) {
				Number[][] matrix = networkMatrix.getNetwork();
				int lenght = networkMatrix.getCurrentSize();
				for (int i = 0; i < lenght-1; i++) {
					int z = i+1;
					for (int j = z; j < lenght; j++) {
						System.out.println(i+" "+j);
						if(matrix[i][j]!=null) {
							if(matrix[i][j].intValue()!=0) {
								edges.add(i);
								edges.add(j);
							}
						}
					}
				}
			}else {
				Object[] keys = networkList.getList().keySet().toArray();
				edges.add(keys.length);
				for (int i = 0; i < keys.length; i++) {
					int z = i+1;
					for (int j = z; j < keys.length; j++) {
						
						if(networkList.getEdge((NetworkDevice)keys[i],(NetworkDevice) keys[j])!=null) {
							edges.add(i);
							edges.add(j);
						}
					}
				}
			}
		}
		return edges;
	}
	
	public void readFile(File selectedfile) {
		if(selectedfile!=null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(selectedfile));
				String line = br.readLine();
				int count = 0;
				int length = Integer.parseInt(line);
				networkList = new AdjacencyList<NetworkDevice, Integer>(length);
				networkMatrix = new AdjacencyMatrix<NetworkDevice, Integer>(length);
				NetworkDevice[] devices = new NetworkDevice[length];
				
				line = br.readLine();
				//Node Creation:
				//Data[0] to IP Address, Data[1] to MAC Address, count to identifier,
				//Data[2] to type of network device
				//Edge Creation:
				//Data[0] to position of Vertex One, Data[1] to position of Vertex Two,
				//Data[2] to weight of the Vertex
				while(line!=null) {
					String[] data = line.split(" ");
					if(count<length) {
						devices[count] = new NetworkDevice(data[0], data[1], count, data[2]);
						networkList.insertNode(devices[count]);
						networkMatrix.insertNode(devices[count]);
						count++;					
					}else {
						int positionVertexOne = Integer.parseInt(data[0]);
						int positionVertexTwo = Integer.parseInt(data[1]);
						int weight = Integer.parseInt(data[2]);
						networkMatrix.insertEdge(positionVertexOne, positionVertexTwo, weight);
						networkList.insertEdge(devices[positionVertexOne], 
								devices[positionVertexTwo], weight);
					}
					line = br.readLine();
				}
				br.close();
				System.out.println("LOGRADO");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public Principal() {
		isMatrix = true;
	}
	
	public Principal(int networkAmount) {
		networkMatrix = new AdjacencyMatrix<>(networkAmount);
		networkList = new AdjacencyList<>(networkAmount);
		isMatrix = true;
	}
	

	public AdjacencyMatrix<NetworkDevice, Integer> getNetworkMatrix() {
		return networkMatrix;
	}

	public void setNetworkMatrix(AdjacencyMatrix<NetworkDevice, Integer> networkMatrix) {
		this.networkMatrix = networkMatrix;
	}

	public AdjacencyList<NetworkDevice, Integer> getNetworkList() {
		return networkList;
	}

	public void setNetworkList(AdjacencyList<NetworkDevice, Integer> networkList) {
		this.networkList = networkList;
	}

	public boolean isMatrix() {
		return isMatrix;
	}

	public void changeValueMatrix() {
		isMatrix = !isMatrix;
	}
	
	public String BFSAdjacencyMatrix(NetworkDevice sourceNetwork) {
		HashSet<NetworkDevice> visitedVerteces = new HashSet<>();
		Queue<NetworkDevice> verteces = new LinkedList<>();
		verteces.add(sourceNetwork);
		visitedVerteces.add(sourceNetwork);
		String response =  sourceNetwork.getId()+" ";
		int length = networkMatrix.getCurrentSize();
		while(verteces.peek()!=null) {
			NetworkDevice networkDevice = verteces.poll();
			int position = networkDevice.getId();
			for (int i = 0; i < length; i++) {
				NetworkDevice child = networkMatrix.getNode(i);
				if(!visitedVerteces.contains(child) && networkMatrix.getEdge(position, i)!=null) {
					verteces.add(child);
					visitedVerteces.add(child);
					response += child.getId()+" ";
				}
			}
		}
		return response;
	}
	
	public String BFSAdjacencyList(NetworkDevice sourceNetwork) {
		HashSet<NetworkDevice> visitedVerteces = new HashSet<>();
		Queue<NetworkDevice> verteces = new LinkedList<>();
		verteces.add(sourceNetwork);
		visitedVerteces.add(sourceNetwork);
		String response =  sourceNetwork.getId()+" ";
		while(verteces.peek()!=null) {
			NetworkDevice networkDevice = verteces.poll();
			HashMap<NetworkDevice, IEdge<NetworkDevice, Integer>> nodes = networkList.getList().get(networkDevice);
			if(nodes!=null) {
				Iterator<IEdge<NetworkDevice, Integer>> iterator = nodes.values().iterator();
				while(iterator.hasNext()) {
					IEdge<NetworkDevice, Integer> edge = iterator.next();
					if(!visitedVerteces.contains(edge.getVertexTwo())) {
						verteces.add(edge.getVertexTwo());
						visitedVerteces.add(edge.getVertexTwo());
						response += edge.getVertexTwo().getId()+" ";
					}
				}
				
			}
		}
		return response;
	}
	
	
	public String DFSAdjacencyMatrix(NetworkDevice sourceNetwork, String response, HashSet<NetworkDevice> visitedVerteces) {
		response += sourceNetwork.getId()+" ";
		visitedVerteces.add(sourceNetwork);
		int position = sourceNetwork.getId();
		boolean hasChilds = false;
		for (int i = 0; i < networkMatrix.getNetwork()[0].length && !hasChilds; i++) {
			if(networkMatrix.getEdge(position, i)!=null && position!=i) hasChilds = true;
		}
		if(!hasChilds) {
			return response;
		}else {
			for (int i = 0; i < networkMatrix.getNetwork()[0].length; i++) {
				NetworkDevice child = networkMatrix.getNode(i);
				if(!visitedVerteces.contains(child) && networkMatrix.getEdge(position, i)!=null) {
					DFSAdjacencyMatrix(child, response, visitedVerteces);
				}
			}
		}
		return response;
		
	}
	
	public String DFSAdjacencyList(NetworkDevice sourceNetwork, String response, HashSet<NetworkDevice> visitedVerteces) {
		HashMap<NetworkDevice, IEdge<NetworkDevice, Integer>> nodes = networkList.getList().get(sourceNetwork);
		response += sourceNetwork.getId()+" ";
		visitedVerteces.add(sourceNetwork);
		if(nodes==null) {
			return response;
		}else {
			Iterator<IEdge<NetworkDevice, Integer>> iterator = nodes.values().iterator();
			while(iterator.hasNext()) {
				IEdge<NetworkDevice, Integer> edge = iterator.next();
				if(!visitedVerteces.contains(edge.getVertexTwo())) {
					DFSAdjacencyList(edge.getVertexTwo(), response, visitedVerteces);					
				}
			}
		}
		return response;
	}
	
	public Object[] DijkstraAdjacencyMatrix(NetworkDevice sourceNode) {
		Object[] response = new Object[2];
		int[] weights = new int[networkMatrix.getCurrentSize()];
		StringBuilder[] paths = new StringBuilder[networkMatrix.getCurrentSize()];
		boolean[] visitedNodes = new boolean[networkMatrix.getCurrentSize()];
		int position = sourceNode.getId();
 		for (int i = 0; i < weights.length; i++) {
 			paths[i].append("");
			if(networkMatrix.getNetwork()[position][i]==0 && i!=position) {
				weights[i] = Integer.MAX_VALUE - 1000000;
			}else {
				weights[i] = networkMatrix.getNetwork()[position][i];
			}
		}
 		paths[position].append(networkMatrix.getDevices()[position].getId()+"");
		for (int i = 0; i < visitedNodes.length; i++) {
			int value = LowestValueNotVisited(weights, visitedNodes);
			visitedNodes[value] = true;
			for (int j = 0; j < visitedNodes.length; j++) {
				if(weights[j] > weights[value] + 
						networkMatrix.getNetwork()[value][j]) {
					weights[j] = weights[value] + networkMatrix.getNetwork()[value][j];
					if(paths[j].length()>0) {
						char aux = paths[j].charAt(paths[j].length()-1);
						paths[j].deleteCharAt(paths[j].length()-1);
						paths[j].append(networkMatrix.getDevices()[value]+" "+aux);
					}else {
						paths[j].append(sourceNode.getId()+" "+networkMatrix.getDevices()[j].getId());
					}
				}
			}
		}
		response[0] = weights;
		response[1] = paths;
		return response;
	}
	
	public Object[] DijkstraAdjacencyList(NetworkDevice sourceNode) {
		Object[] response = new Object[2];
		int[] weights = new int[networkList.getCurrentSize()];
		StringBuilder[] paths = new StringBuilder[networkList.getCurrentSize()];
		boolean[] visitedNodes = new boolean[networkList.getCurrentSize()];
		int position = sourceNode.getId();
 		for (int i = 0; i < weights.length; i++) {
 			paths[i].append("");
			weights[i] = Integer.MAX_VALUE - 1000000;
		}
 		paths[position].append(sourceNode.getId()+"");
 		weights[position] = 0; 
 		NetworkDevice[] nodes = (NetworkDevice[]) networkList.getList().keySet().toArray();
		for (int i = 0; i < visitedNodes.length; i++) {
			int value = LowestValueNotVisited(weights, visitedNodes);
			visitedNodes[value] = true;
			for (int j = 0; j < visitedNodes.length; j++) {
				IEdge<NetworkDevice, Integer> edge = networkList.getList().get(nodes[value]).get(nodes[j]);
				if(edge!=null) {
					if(weights[j] > weights[value] + 
							edge.getWeight()) {
						weights[j] = weights[value] + edge.getWeight();
						if(paths[j].length()>0) {
							char aux = paths[j].charAt(paths[j].length()-1);
							paths[j].deleteCharAt(paths[j].length()-1);
							paths[j].append(edge.getVertexTwo()+" "+aux);
						}else {
							paths[j].append(sourceNode.getId()+" "+edge.getVertexTwo().getId());
						}
					}
					
				}
				
			}
		}
		response[0] = weights;
		response[1] = paths;
		return response;
	}
	
	public int[][] FloydWarshallAdjacencyMatrix() {
		int[][] response = new int[networkMatrix.getCurrentSize()][networkMatrix.getCurrentSize()];
		NetworkDevice[] devices = networkMatrix.getDevices();
		for (int i = 0; i < devices.length; i++) {
			response[i] = (int[]) DijkstraAdjacencyMatrix(devices[i])[0];
		}
		return response;
	}
	
	public int[][] FloydWarshallAdjacencyList() {
		int[][] response = new int[networkMatrix.getCurrentSize()][networkMatrix.getCurrentSize()];
 		NetworkDevice[] nodes = (NetworkDevice[]) networkList.getList().keySet().toArray();
 		for (int i = 0; i < nodes.length; i++) {
			response[i] = (int[]) DijkstraAdjacencyList(nodes[i])[0];
		}
		return response;
	}
	
	public ArrayList<IEdge<NetworkDevice, Integer>> PrimAdjacencyMatrix(){
		Random r = new Random();
		int sourceNode = r.nextInt(networkMatrix.getCurrentSize());
		boolean end = false;
		HashSet<Integer> visitedNodes = new HashSet<>();
		ArrayList<IEdge<NetworkDevice, Integer>> edges = new ArrayList<IEdge<NetworkDevice,Integer>>();
		ArrayList<IEdge<NetworkDevice, Integer>> response = new ArrayList<IEdge<NetworkDevice,Integer>>();
		NetworkDevice vertexOne = networkMatrix.getDevices()[sourceNode];
		visitedNodes.add(vertexOne.getId());
		int minimum = Integer.MAX_VALUE - 10;
		int position = -1;
		for (int i = 0; i < networkMatrix.getNetwork()[sourceNode].length; i++) {
			if(networkMatrix.getNetwork()[sourceNode][i]!=0) {
				NetworkDevice vertexTwo = networkMatrix.getDevices()[i];
				int weight = networkMatrix.getNetwork()[sourceNode][i];
				edges.add(new IEdge<NetworkDevice, Integer>(vertexOne, vertexTwo, weight));
				if(weight < minimum) {
					minimum = weight;
					position++;
				}
			}
		}
		
		if(position!=-1) {
			response.add(edges.get(position));
			int vertexId = edges.get(position).getVertexTwo().getId();
			while(!end) {
				for (int i = 0; i < networkMatrix.getNetwork()[vertexId].length; i++) {
					if(networkMatrix.getNetwork()[vertexId][i]!=0) {
						NetworkDevice vertexTwo = networkMatrix.getDevices()[i];
						int weight = networkMatrix.getNetwork()[vertexId][i];
						edges.add(new IEdge<NetworkDevice, Integer>(vertexOne, vertexTwo, weight));
					}
				}
				
				visitedNodes.add(edges.get(position).getVertexTwo().getId());
				
				minimum = Integer.MAX_VALUE - 10;
				position = -1;
				for (int i = 0; i < edges.size(); i++) {
					IEdge<NetworkDevice, Integer> edge = edges.get(i);
					if(minimum > edge.getWeight()) {
						if(!visitedNodes.contains(edge.getVertexOne().getId()) || 
								!visitedNodes.contains(edge.getVertexTwo().getId())) {
							minimum = edge.getWeight();
							position = i;
						}	
					}
				}
				if(position!=-1) {
					response.add(edges.get(position));
					vertexId = edges.get(position).getVertexTwo().getId();
				}else end = true;
			}
			return response;
		}else return response;
	}
	
	public ArrayList<IEdge<NetworkDevice, Integer>> PrimAdjacencyList(){
		Random r = new Random();
		int sourceNode = r.nextInt(networkList.getCurrentSize());
		boolean end = false;
		HashSet<Integer> visitedNodes = new HashSet<>();
		ArrayList<IEdge<NetworkDevice, Integer>> edges = new ArrayList<IEdge<NetworkDevice,Integer>>();
		ArrayList<IEdge<NetworkDevice, Integer>> response = new ArrayList<IEdge<NetworkDevice,Integer>>();
		NetworkDevice[] devices = (NetworkDevice[]) networkList.getList().keySet().toArray();
		NetworkDevice vertexOne = devices[sourceNode];
		visitedNodes.add(vertexOne.getId());
		int minimum = Integer.MAX_VALUE - 10;
		int position = -1;
		IEdge<NetworkDevice, Integer>[] values = (IEdge<NetworkDevice, Integer>[]) 
				networkList.getList().get(vertexOne).values().toArray();
		if(values!=null) {
			
			for (int i = 0; i < values.length; i++) {
				if(values[i].getWeight()!=0) {
					NetworkDevice vertexTwo = values[i].getVertexTwo();
					int weight = values[i].getWeight();
					edges.add(new IEdge<NetworkDevice, Integer>(vertexOne, vertexTwo, weight));
					if(weight < minimum) {
						minimum = weight;
						position++;
					}
				}
			}
			if(position!=-1) {
				response.add(edges.get(position));
				while(!end) {
					
					values = (IEdge<NetworkDevice, Integer>[]) 
							networkList.getList().get(edges.get(position).getVertexTwo().getId())
							.values().toArray();
					if(values!=null) {						
						for (int i = 0; i < values.length; i++) {
							if(values[i].getWeight()!=0) {
								NetworkDevice vertexTwo = values[i].getVertexTwo();
								int weight = values[i].getWeight();
								edges.add(new IEdge<NetworkDevice, Integer>(vertexOne, vertexTwo, weight));
							}
						}
					}
					visitedNodes.add(edges.get(position).getVertexTwo().getId());
					minimum = Integer.MAX_VALUE - 10;
					position = -1;
					for (int i = 0; i < edges.size(); i++) {
						IEdge<NetworkDevice, Integer> edge = edges.get(i);
						if(minimum > edge.getWeight()) {
							if(!visitedNodes.contains(edge.getVertexOne().getId()) || 
									!visitedNodes.contains(edge.getVertexTwo().getId())) {
								minimum = edge.getWeight();
								position = i;
							}	
						}
					}
					if(position!=-1) {
						response.add(edges.get(position));
					}else end = true;
				}
				return response;
			}else return null;
			
		}else return null;
	}
	

	public ArrayList<IEdge<NetworkDevice, Integer>> KruskalAdjacencyMatrix(){
		HashSet<Integer> visitedNodes = new HashSet<>();
		ArrayList<IEdge<NetworkDevice, Integer>> edges = new ArrayList<IEdge<NetworkDevice,Integer>>();
		ArrayList<IEdge<NetworkDevice, Integer>> response = new ArrayList<IEdge<NetworkDevice,Integer>>();
		for (int i = 0; i < networkMatrix.getNetwork().length; i++) {
			int z = i+1;
			for (int j = z; j < networkMatrix.getNetwork()[0].length; j++) {
				NetworkDevice vertexOne = networkMatrix.getDevices()[i];
				NetworkDevice vertexTwo = networkMatrix.getDevices()[j];
				int weight = networkMatrix.getNetwork()[i][j];
				edges.add(new IEdge<NetworkDevice, Integer>(vertexOne, vertexTwo, weight));
			}
		}
		boolean end = false;
		while(visitedNodes.size()!=networkMatrix.getCurrentSize() && !end) {
			int weight = Integer.MAX_VALUE - 100;
			int position = -1;
			for (int i = 0; i < edges.size(); i++) {
				if(weight > edges.get(i).getWeight()) {
					if(!visitedNodes.contains(edges.get(i).getVertexOne().getId()) || 
							!visitedNodes.contains(edges.get(i).getVertexTwo().getId())) {
						weight = edges.get(i).getWeight();
						position = i;
					}
				}
			}
			if(position!=-1) {
				response.add(edges.get(position));
				visitedNodes.add(edges.get(position).getVertexOne().getId());
				visitedNodes.add(edges.get(position).getVertexTwo().getId());
				edges.remove(position);
			}else end = true;
		}
		return response;
	}
	
	public ArrayList<IEdge<NetworkDevice, Integer>> KruskalAdjacencyList(){
		HashSet<Integer> visitedNodes = new HashSet<>();
		ArrayList<IEdge<NetworkDevice, Integer>> edges = new ArrayList<IEdge<NetworkDevice,Integer>>();
		ArrayList<IEdge<NetworkDevice, Integer>> response = new ArrayList<IEdge<NetworkDevice,Integer>>();
		NetworkDevice[] devices = (NetworkDevice[]) networkList.getList().keySet().toArray();
		for (int i = 0; i < devices.length; i++) {
			edges.addAll(networkList.getList().get(devices[i]).values());
		}
		boolean end = false;
		while(visitedNodes.size()!=networkList.getCurrentSize() && !end) {
			int weight = Integer.MAX_VALUE - 100;
			int position = -1;
			for (int i = 0; i < edges.size(); i++) {
				if(weight > edges.get(i).getWeight()) {
					if(!visitedNodes.contains(edges.get(i).getVertexOne().getId()) || 
							!visitedNodes.contains(edges.get(i).getVertexTwo().getId())) {
						weight = edges.get(i).getWeight();
						position = i;
					}
				}
			}
			if(position!=-1) {
				response.add(edges.get(position));
				visitedNodes.add(edges.get(position).getVertexOne().getId());
				visitedNodes.add(edges.get(position).getVertexTwo().getId());
				edges.remove(position);
			}else end = true;
		}
		return response;
	}
	
	public int LowestValueNotVisited(int[] weights, boolean[] visitedNodes) {
		int value = weights[0];
		int position = 0;
		for (int i = 1; i < weights.length; i++) {
			if(value > weights[i] && !visitedNodes[i]) {
				position = i;
				value = weights[i];
			}
		}
		return position;
	}
	

}
