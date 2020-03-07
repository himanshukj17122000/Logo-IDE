package slogo.View;


import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import slogo.Main;
import slogo.model.CommandParser;
import slogo.model.Turtle;

/**
 * consider: what if I wanna add more buttons?
 * This class encapsulates the command line object, which is where the user enters the commands to control the turtle.
 * @author Michelle Tai
 */
public class CommandLine{
  public static ResourceBundle myResources = Main.MY_RESOURCES;
  private TextArea inputArea;
  private ViewButton runButton;
  private ViewButton clearButton;
  private HBox commandLineGroup;
  private static final int BUTTON_HEIGHT = 60;
  private static final int BUTTON_WIDTH = 100;
  private static final int COMMAND_LINE_TEXTAREA_HEIGHT = BUTTON_HEIGHT * 2 + 10;
  private static final int SPACING_VALUE = 10;
  private static final int TEXTAREA_FONTSIZE = 20;
  private StringProperty commandLineText;
  private static final String EMPTY ="";
  private static final String RUN ="Run";
  private static final String FORWARD_50 ="Forward 50";
  private static final String BACKWARD_50 ="Backward 50";
  private static final String RIGHT_45 ="Right 45";
  private static final String LEFT_45 ="Left 45";
  private static final String CLEAR ="Clear";
  private static final String FONT_NAME="Avenir";
  private static final String PROMPT="EnterCommandPrompt";
  private BooleanProperty textUpdate;
  private ObservableList<Turtle> activatedTurtles;


  /**
   * Constructor for the command line group, which includes the run and clear button, as well as an area
   * where the user can type in commands to control the turtle.
   */
  public CommandLine(StringProperty commandLineText, BooleanProperty textUpdate, ObservableList<Turtle> activatedTurtles
  , CommandParser parser){
    this.activatedTurtles = activatedTurtles;
    this.textUpdate = textUpdate;
    this.commandLineText = commandLineText;
    setInputArea(parser);
    setButtons();
    formatCommandLineGroup();
  }

  /**
   * @return commandLineGroup, which is a Node that has the text area where users type in commands and the run and clear button.
   */
  public Node getCommandLineGroup(){
    return commandLineGroup;
  }

  private void setInputArea(CommandParser parser){
    inputArea = new TextArea();
    inputArea.setOnKeyPressed(e-> keyMovementHandler(e, parser) );
    inputArea.setMaxHeight(COMMAND_LINE_TEXTAREA_HEIGHT);
    inputArea.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, TEXTAREA_FONTSIZE));
    inputArea.setPromptText(myResources.getString(PROMPT));
  }

  private void keyMovementHandler(KeyEvent e, CommandParser parser) {
      if ((e.getCode() == KeyCode.UP)) {
        parser.miniParse(FORWARD_50);
      }
      if ((e.getCode() == KeyCode.DOWN)) {
        parser.miniParse(BACKWARD_50);
      }
      if ((e.getCode() == KeyCode.RIGHT)) {
        parser.miniParse(RIGHT_45);
      }
      if ((e.getCode() == KeyCode.LEFT)) {
        parser.miniParse(LEFT_45);
      }
  }

  private void setButtons(){
    runButton = new ViewButton(myResources.getString(RUN), BUTTON_HEIGHT, BUTTON_WIDTH);
    runButton.setOnAction(e -> {
      commandLineText.set(inputArea.getText()+ EMPTY);
      textUpdate.set(!textUpdate.getValue());
    });
    clearButton = new ViewButton(myResources.getString(CLEAR), BUTTON_HEIGHT, BUTTON_WIDTH);
    clearButton.setOnAction(e -> {
      inputArea.setText(EMPTY);
    });
  }

  private void formatCommandLineGroup(){
    VBox commandLineButtons = new VBox(SPACING_VALUE);
    commandLineButtons.getChildren().addAll(runButton, clearButton);
    commandLineGroup = new HBox(SPACING_VALUE);
    commandLineGroup.getChildren().addAll(inputArea, commandLineButtons);
  }

}
