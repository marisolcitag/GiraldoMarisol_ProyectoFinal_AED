package Presentation;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import Model.NetworkDevice;
import Model.Principal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;


public class Main extends Application {

	private Graph graph;
	private static Principal principal;
	
	
	public static void main(String[] args) {
		principal = new Principal();
		launch(args);
	}
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		ToolBar toolbar = new ToolBar();
		FileChooser filechooser = new FileChooser();
		Button loadFile = new Button("Cargar Archivo");
		Button ReleaseBroadCast = new Button("Encontrar redes");
		Button changeToMatrix = new Button("Cambiar a Matriz");
		Button changeToList = new Button("Cambiar a Lista");
		Button ReleaseOSPF = new Button("Evaluar conectividad entre dispositivos");
	
		TextField txtStartDevice = new TextField("Insert Start Device here");
		TextField txtEndDevice = new TextField("Insert End Device here");
		graph = new Graph();
		toolbar.getItems().add(loadFile);
		toolbar.getItems().add(changeToList);
		toolbar.getItems().add(changeToMatrix);
		toolbar.getItems().add(ReleaseBroadCast);
		toolbar.getItems().add(ReleaseOSPF);
		toolbar.getItems().add(txtStartDevice);
		toolbar.getItems().add(txtEndDevice);
		root.setBottom(toolbar);
		
        

        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1024, 768);
        
    
        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();
        
        
        ReleaseOSPF.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				boolean value = principal.isMatrix();
				int startDevice = Integer.parseInt(txtStartDevice.getText());
				int endDevice = Integer.parseInt(txtEndDevice.getText());
				Object[] result = null;
				if(value==true) {
					result = principal.DijkstraAdjacencyMatrix((NetworkDevice)principal.getNetworkMatrix().getNode(startDevice));
				}else {
					result = principal.DijkstraAdjacencyList((NetworkDevice)principal.getNetworkMatrix().getNode(startDevice));
				}
				StringBuilder[] path = (StringBuilder[])result[1];
				System.out.println("La ruta efectiva para llegar de "+startDevice+" hacia "+endDevice+"es :");
				System.out.println(path[endDevice].toString());
				
				
				
				
				
				
			
				
			}
		});
		
		ReleaseBroadCast.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					int length = principal.getNetworkList().getCurrentSize();
					List<String> choices = new ArrayList<>();
					for (int i = 0; i < length; i++) {
						choices.add(i+"");
					}
					
					ChoiceDialog<String> dialog = new ChoiceDialog<>("0", choices);
					dialog.setTitle("BFS");
					dialog.setHeaderText("Selección de nodo");
					dialog.setContentText("Elige el ID del nodo");
					Optional<String> result = dialog.showAndWait();
					boolean value = principal.isMatrix();
					String response = "";
					if(result.isPresent()) {
						int choose = Integer.parseInt(result.get());
						if(value==true) {
							response = principal.DFSAdjacencyMatrix((NetworkDevice)principal.getNetworkMatrix().getNode(choose), "", new HashSet<NetworkDevice>(),1);
						}else {
							response = principal.DFSAdjacencyList((NetworkDevice)principal.getNetworkMatrix().getNode(choose), "", new HashSet<NetworkDevice>(),1);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		loadFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					principal = new Principal();
					File selected = filechooser.showOpenDialog(primaryStage);
					principal.readFile(selected);
					 primaryStage.close();
				      Platform.runLater( () -> {
						try {
							new Main().start(new Stage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		changeToMatrix.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					boolean value = principal.isMatrix();
					if(value==false) {
						principal.changeValueMatrix();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		changeToList.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					boolean value = principal.isMatrix();
					if(value==true) {
						principal.changeValueMatrix();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		
	}
	
	private void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();
        
        
        ArrayList<Integer> edges = principal.transformData();
        if(edges.size()>0) {
        	int length = edges.get(0);
            
            for (int i = 0; i < length; i++) {
    			model.addCell(i+"", CellType.RECTANGLE);
    		}
            
            for (int i = 1; i < edges.size()-1; i++) {
    			model.addEdge(edges.get(i)+"", edges.get(i+1)+"");
    		}
        }
        
        
//        model.addCell("Cell A", CellType.RECTANGLE);
//        model.addCell("Cell B", CellType.RECTANGLE);
//        model.addCell("Cell C", CellType.RECTANGLE);
//        model.addCell("Cell D", CellType.TRIANGLE);
//        model.addCell("Cell E", CellType.TRIANGLE);
//        model.addCell("Cell F", CellType.RECTANGLE);
//        model.addCell("Cell G", CellType.RECTANGLE);
//
//        model.addEdge("Cell A", "Cell B");
//        model.addEdge("Cell A", "Cell C");
//        model.addEdge("Cell B", "Cell C");
//        model.addEdge("Cell C", "Cell D");
//        model.addEdge("Cell B", "Cell E");
//        model.addEdge("Cell D", "Cell F");
//        model.addEdge("Cell D", "Cell G");

        graph.endUpdate();

    }
	
	

}
