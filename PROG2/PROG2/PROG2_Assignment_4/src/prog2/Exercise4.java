package prog2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;




import java.io.*;
import java.util.*;

public class Exercise4 implements Ex4 {
	private final Graph<Nodes.GraphNode> graph;

	public Exercise4(Graph<Nodes.GraphNode> graph) {
		this.graph = graph;
	}


	@Override
	public void loadLocationGraph(String filename) {



		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(filename));
			String [] nodesList = dis.readLine().split(";");
			 Map<String, Nodes.GraphNode> nodeMap = new HashMap<>();
			for(int i = 0; i < nodesList.length; i+=3){
				Nodes.GraphNode node = new Nodes.LocationNode(nodesList[i], Double.parseDouble(nodesList[i+1]), Double.parseDouble(nodesList[i+2]));
				graph.add(node);
				nodeMap.put(nodesList[i], node);
			}
			while(dis.available() > 0){
				String [] edgeInfo = dis.readLine().split(";");
				if (nodeMap.containsKey(edgeInfo[0]) && nodeMap.containsKey(edgeInfo[1]))
				graph.connect(nodeMap.get(edgeInfo[0]), nodeMap.get(edgeInfo[1]),edgeInfo[2],Integer.parseInt(edgeInfo[3]));
			}



		}catch (FileNotFoundException ignored){

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
