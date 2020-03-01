package slogo.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javax.lang.model.element.Element;
import java.util.HashMap;

public class VariableHistory {
    private HashMap<String, String> Variables;
    private ListView variablesHolder;
    private static final Paint textColor= Color.BLACK;
    private Label variable;
    private static final String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
    private static TableColumn nameCol;
    private static TableColumn valCol;
    private static VBox VariableHis;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;


    public VariableHistory(String name, String value){
            this.firstName = new SimpleStringProperty(name);
            this.lastName = new SimpleStringProperty(value);
        }


    public VariableHistory(){
        VariableHis= new VBox();
        Variables=new HashMap<>();
        variablesHolder= new ListView();
        variablesHolder.setPrefHeight(310.0);
        variablesHolder.setPrefWidth(300.0);
        nameCol= new TableColumn("Name");
        valCol= new TableColumn("Value");
        variable = new Label("Variables History");
        variable.setTextFill(textColor);
        variable.setMaxHeight(10);
        variable.setMaxWidth(10);
        variablesHolder.setBackground(Background.EMPTY);
        variablesHolder.setStyle(style);
        VariableHis.getChildren().addAll(variable,variablesHolder);
    }

    public void addVariable(String name, String value){
        Label newNameBar= new Label(name);
        Label newValueBar= new Label(value);
        HBox vari= new HBox();
        vari.getChildren().addAll(newNameBar,newValueBar);
        variablesHolder.getItems().add(vari);
    }


    public String getVariable(String name){
             if(Variables.containsKey(name)){
                return Variables.get(name);
            }
             return null;
    }

    protected Node getScene(){
      return variablesHolder;
    }

}
