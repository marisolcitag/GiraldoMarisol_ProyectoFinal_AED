package Presentation;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class RectangleCell extends Cell {

    public RectangleCell( String id) {
        super(id);
        Text text = new Text(50,50,id);
        text.setStroke(Color.DARKGRAY);
        text.setFill(Color.DARKGRAY);
        StackPane stack = new StackPane();
        Rectangle view = new Rectangle( 50,50);
        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);
        stack.getChildren().addAll(text,view);
        setView( stack);

    }

}
