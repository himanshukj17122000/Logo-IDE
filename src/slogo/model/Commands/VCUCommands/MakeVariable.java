package slogo.model.Commands.VCUCommands;
import slogo.model.TreeNode;

public class MakeVariable extends VCUCommand {
    public MakeVariable(String name)
    {
        super(name);
    }

    @Override
    public void doCommand(TreeNode commandNode) {
        System.out.println("Stored the variable " + getParamList().get(0)+ " as " + getParamList().get(1));
        //variables.putIfAbsent(getParamList().get(0),"");
        variables.put(getParamList().get(0),getParamList().get(1));
        commandNode.setResult(getParamList().get(1));
    }
}
