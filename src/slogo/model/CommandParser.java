package slogo.model;

import java.lang.reflect.Constructor;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CommandParser {

  // where to find resources specifically for this class
  private static final String RESOURCES_PACKAGE = CommandParser.class.getPackageName() + ".resources.languages.";

  private static final String THIS_PACKAGE = CommandParser.class.getPackageName() + ".";

  // "types" and the regular expression patterns that recognize those types
  // note, it is a list because order matters (some patterns may be more generic)
  private List<Entry<String, Pattern>> mySymbols;

  private Map<String, Command> stringToCommand;


  /**
   * Create an empty parser
   */
  public CommandParser () {
    mySymbols = new ArrayList<>();
  }

  /**
   * Initializes and adds values to the map, mapping strings in mySymbols
   * to Command objects.
   *
   * Note: every Command implementation should have a basic constructor that
   * just takes in a string
   */
  public void makeMap() {
    stringToCommand = new HashMap<>();
    for (Entry<String, Pattern> entry : mySymbols) {
      String string = entry.getKey();
      System.out.println(string);
      Command command;

      Constructor[] constructors = null;
      try {
        constructors = Class.forName(THIS_PACKAGE + string).getConstructors();
      } catch (ClassNotFoundException e) {
        constructors = null;
        //throw an exception
      }

      try {
        command = (Command) constructors[0].newInstance(string);
      } catch (Exception e) {
        command = null;
      }
      stringToCommand.put(string, command);
    }
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addPatterns (String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      mySymbols.add(new SimpleEntry<>(key,
          // THIS IS THE IMPORTANT LINE
          Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  public String getSymbol (String text) {
    final String ERROR = "NO MATCH";
    for (Entry<String, Pattern> e : mySymbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    // FIXME: perhaps throw an exception instead
    return ERROR;
  }


  // Returns true if the given text matches the given regular expression pattern
  private boolean match (String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return regex.matcher(text).matches();
  }

  //testing
  public static void main(String[] args) {
    CommandParser c = new CommandParser();

    String english = "English";
    String chinese = "Chinese";

    String forward = "forward";
    String chineseCommand = "nizhengqie";

    c.addPatterns(english);
    c.addPatterns(chinese);
//    System.out.println(c.getSymbol(forward));
//    System.out.println(c.getSymbol(chineseCommand));

    c.makeMap();

    forward = "Forward";

  }

}
