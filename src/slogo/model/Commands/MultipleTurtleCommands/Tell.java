package slogo.model.Commands.MultipleTurtleCommands;

import slogo.model.TreeNode;
import slogo.model.Turtle;
import slogo.model.TurtleList;

import java.util.Arrays;

public class Tell extends MultipleTurtleCommand{
    public Tell(String name) { super(name);}

    @Override
    public void doCommand(TreeNode commandNode) {
        //DO error checking if not an int
        String list = getParamList().get(0).replaceFirst("\\[","").replaceFirst("\\]","");
        list = list.trim();
        String[] turtlesToActivate = list.split("\\s+");
        System.out.println("LIST: " + Arrays.toString(turtlesToActivate));
        for(Turtle turtle:turtles)
        {
            TurtleList.makeModelTurtleDeactivated(turtle.getId());
        }
        for(String activatedTurtle:turtlesToActivate)
        {
            int currentTurtleNum = Integer.parseInt(activatedTurtle);
            if(currentTurtleNum<=turtles.size())
            {
                TurtleList.makeModelTurtleActivated(currentTurtleNum);
            }
            else
            {
                System.out.println("TURTLES.LENGTH: "+ turtles.size());
                for(int i = turtles.size();i<currentTurtleNum;i++)
                {
                    Turtle newTurtle = new Turtle();
                    if(i!=currentTurtleNum-1)
                    {
                        newTurtle.setActivated(false);
                    }
                    TurtleList.addTurtleToModelList(newTurtle);
                }
            }
            commandNode.setResult(currentTurtleNum+"");
        }
    }
}