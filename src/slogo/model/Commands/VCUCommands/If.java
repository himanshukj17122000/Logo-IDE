package slogo.model.Commands.VCUCommands;
import slogo.model.CommandParser;
import slogo.model.TreeNode;


public class If extends VCUCommand {
    public If(String name)
    {
        super(name);
    }

    @Override
    public void doCommand(TreeNode commandNode) {
        //System.out.println("Do this many times: " + getParamList().get(0).trim());
        String test = "";
        String finalValue = "";
        CommandParser miniParser = new CommandParser(turtles,variables, language, customCommandStorage);
//        System.out.println("PARAM 1: " + getParamList().get(0));
//        System.out.println("TEST: " + test);
        if(Double.parseDouble(getParamList().get(0)) != 0)
        {
            finalValue = miniParser.miniParse(getParamList().get(1).trim().replaceFirst("\\[", ""));
            commandNode.setResult(finalValue);
        }
        else
        {
            commandNode.setResult("0");
        }
    }
}
