package slogo.View;

import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import slogo.Main;
import slogo.model.CommandParser;
import slogo.model.Turtle;

public class Visualizer {

  public static final int BUTTON_HEIGHT = 80;
  public static final double BUTTON_WIDTH = 200.0;
  private ResourceBundle myResources = Main.myResources;
  private static int WINDOW_WIDTH = 1500;
  private static int WINDOW_HEIGHT = 1000;
  private Stage myWindow;
  private CommandHistory myCommandHistory;
  private VariableHistory myVariableHistory;
  private BorderPane bp;
  private Turtle viewTurtle;
  private ImageView buttonImage;
  private CommandParser comParser;
  private javafx.scene.image.Image img;
  private static final String style = "-fx-background-color: rgba(0, 0, 0, 0.7);";

  /**
   * Constructor for the visualizer class
   *
   * @param window '
   */
  public Visualizer(Stage window, Turtle viewTurtle, StringProperty commandLineText,
                    BooleanProperty textUpdate, Language language, CommandParser parser) {
    myWindow = window;
    comParser=parser;
    myCommandHistory = new CommandHistory(comParser);
    myVariableHistory = new VariableHistory();
    this.viewTurtle = viewTurtle;

    img = new Image(myResources.getString("SlogoLogo"));
    buttonImage = new ImageView(img);
    buttonImage.setFitHeight(BUTTON_HEIGHT);
    buttonImage.setFitWidth(BUTTON_WIDTH);
    CommandLine cmdline = new CommandLine(commandLineText, textUpdate);
    TurtleGrid grid = new TurtleGrid(viewTurtle);
    Toolbar tool = new Toolbar(grid, language);
    setUpBorderPane(grid, cmdline, tool);
    makeHistory();
    Scene scene = new Scene(bp, WINDOW_WIDTH, WINDOW_HEIGHT);
    window.setScene(scene);
    window.show();
  }

  private void setUpBorderPane(TurtleGrid grid, CommandLine commandLine, Toolbar tool) {
    bp = new BorderPane();
    bp.setBackground(Background.EMPTY);
    bp.setStyle(style);
    bp.setBottom(commandLine.getCommandLineGroup());
    bp.setLeft(grid.getTurtleGrid());
    bp.setTop(tool.getToolBar());
  }

  private void makeHistory() {
    VBox historyVBox = new VBox();
    historyVBox.setAlignment(Pos.CENTER);
    historyVBox.getChildren()
        .addAll(buttonImage, myVariableHistory.getScene(), myCommandHistory.returnScene());
    bp.setRight(historyVBox);
  }

  public void makeNewBox(String newCommand){
    myCommandHistory.makeBox(newCommand);
    Button trial= myCommandHistory.returnButton();
    trial.setOnAction(e->comParser.parseText(newCommand));
  }

  public void makeNewVariableBox(HashMap<String,String> VariableMap){
    for(String variableKey:VariableMap.keySet()) {
      myVariableHistory.addVariable(variableKey, VariableMap.get(variableKey));
    }
  }

}
