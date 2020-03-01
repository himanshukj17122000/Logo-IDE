package slogo.model;


import java.util.ResourceBundle;

import slogo.View.Language;
import slogo.model.Commands.Command;
import slogo.model.Commands.CommandFactoryInterface;
import java.util.*;
import java.util.regex.Pattern;

public class CommandTreeExecutor {

  private static final String MAKE_VARIABLE = "MakeVariable";
  private static final String MAKE_USER_INSTRUCTION = "MakeUserInstruction";
  private static final String THIS_PACKAGE = "slogo.model.Commands.";
  private static final String VCU_COMMAND = THIS_PACKAGE + "VCUCommands" + "." + "CustomCommand";
  private Pattern constantPattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
  private Pattern commandPattern = Pattern.compile("[a-zA-Z_]+(\\?)?");
  private Pattern variablePattern = Pattern.compile(":[a-zA-Z_]+");
  private CommandFactoryInterface commandFactory;
  private Turtle turtle;
  private List<Map.Entry<String, Pattern>> mySymbols;
  private Language language;
  private HashMap<Pattern, String> translations;
  private String finalValue = "";

  private static final String RESOURCES_PACKAGE =
      "resources.";

  public static ResourceBundle commandPackageNames = ResourceBundle
      .getBundle(RESOURCES_PACKAGE + "CommandPackages");

  public static ResourceBundle commandParameterNumbers = ResourceBundle
      .getBundle(RESOURCES_PACKAGE + "ParameterNumbers");

  private static final String ERRORS = RESOURCES_PACKAGE + "ErrorMessages";

  //make a properties file for errors
  private ResourceBundle errors = ResourceBundle.getBundle(ERRORS);

  // private static final String COMMAND_PACKAGES = CommandTreeExecutor.class.getPackageName() + ".resources.packages.CommandPackages.properties";


  public CommandTreeExecutor(CommandFactoryInterface factory, Turtle turtle,
      HashMap<Pattern, String> translations, Language language) {
    this.language = language;
    this.turtle = turtle;
    commandFactory = factory;
    this.translations = translations;
  }

  public String executeTrees(List<TreeNode> elementNodes) {
    for (TreeNode element : elementNodes) {
      executeSubTree(element);
      System.out.println("EXECUTED NODES: " + element.getName());
      for (TreeNode child : element.getChildren()) {
        System.out.println("Children: " + child.getName());
      }
    }
    System.out.println("Last commands result " + elementNodes.get(elementNodes.size() - 1).getResult());
    finalValue = elementNodes.get(elementNodes.size() - 1).getResult();
    return finalValue;
  }


  private boolean isMakeVariableCommand(TreeNode element) {
    return element.getName().equals(MAKE_VARIABLE);
  }

  private boolean isMakeUserInstruction(TreeNode element) {
    return element.getName().equals(MAKE_USER_INSTRUCTION);
  }

  private void executeSubTree(TreeNode element) {
    if (match(element.getName(), commandPattern)) {
      ArrayList<TreeNode> children = element.getChildren();
      ArrayList<String> parameters = new ArrayList<>();
      // will also need to check for to commands
      
      if (isMakeVariableCommand(element) || isMakeUserInstruction(element)) {
        System.out.println("Is make variable OR is make user");
        parameters.add(children.get(0).getName());
        children.remove(0);
      }
      for (TreeNode child : children) {
        executeSubTree(child);
        parameters.add(child.getResult());
        //finalValue = child.getResult();
      }
      String commandClass = "";
      if (CustomCommandMap.isACustomCommand(element.getName())) {
        commandClass = VCU_COMMAND;
      } else { //not a custom command
        commandClass = getCommandClass(element, parameters);
      }
      //System.out.println("Command class = " + commandClass);
      Command commandObject = commandFactory.createCommand(commandClass);
      for (String s : parameters) {
        System.out.println("Param of " + element.getName() + ": " + s);
      }

      commandObject.setParams(parameters);
      commandObject.setTurtle(turtle);
      commandObject.setMiniParserLanguage(language);
      commandObject.doCommand(element);
      //nd.setData(replacementValue);
    } else if (match(element.getName(), variablePattern)) {
      element.setResult(VariableHashMap.getVarValue(element.getName()));
    }
  }
  // for variables later on

  private String getPackageName(String commandName) {
    System.out.println(commandName);
    return commandPackageNames.getString(commandName);
  }

  private String getCommandClass(TreeNode element, List<String> parameters) {
    String commandName = element.getName();
    String commandClass = THIS_PACKAGE + getPackageName(commandName) + "."
            + commandName;

    int numParamsShouldHave = Integer.parseInt(commandParameterNumbers.getString(commandName));
    if (parameters.size() != numParamsShouldHave) {
      throw new CommandException(errors.getString("WrongParameterNumber"));
    }

    return commandClass;
  }

  private boolean match(String text, Pattern regex) {
    return regex.matcher(text).matches();
  }

//        if(type.equals(VARIABLE_KEY)){
//            nd.setData(myVars.getVariable(nd.getName()));
//        }
}
