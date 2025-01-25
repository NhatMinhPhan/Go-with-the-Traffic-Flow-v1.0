import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
* Cutscene text used in Level 1
* @version 1.0
* @author Fabulosi Labs
*/

public class UICutsceneText extends UILabel {
   /**
   * List of lines to be displayed
   */
   private ArrayList<String> lines;
   
   /**
   * Full class constructor.
   * @param i Identifier
   * @param text Text before converting into lines to be displayed
   * @param labelColor Colour of the text
   * @param x X-position
   * @param y Y-position
   * @param fontSize The font size of the text
   * @param l Local layer within the UI domain.
   */
   public UICutsceneText(String i, String text, Color labelColor, int x, int y, int fontSize, int l){ //Includes font size
      super(i, text, labelColor, x, y, fontSize, l);
      lines = new ArrayList<>();
      interpretLine(text());
   }
   
   /**
   * Class constructor without setting up the font size.
   * @param i Identifier
   * @param text Text before converting into lines to be displayed
   * @param labelColor Colour of the text
   * @param x X-position
   * @param y Y-position
   * @param l Local layer within the UI domain.
   */
   public UICutsceneText(String i, String text, Color labelColor, int x, int y, int l){
      this(i, text, labelColor, x, y, 25, l);
   }
   
   /**
   * Class constructor without setting up text colour and font size
   * @param i Identifier
   * @param text Text before converting into lines to be displayed
   * @param x X-position
   * @param y Y-position
   * @param l Local layer within the UI domain.
   */
   public UICutsceneText(String i, String text, int x, int y, int l){ //No Color Constructor
      this(i, text, Color.black, x, y, 25, l);
   }
   
   /**
   * Class constructor without setting up text colour
   * @param i Identifier
   * @param text Text before converting into lines to be displayed
   * @param x X-position
   * @param y Y-position
   * @param fontSize The font size of the text
   * @param l Local layer within the UI domain.
   */
   public UICutsceneText(String i, String text, int x, int y, int fontSize, int l){ //No Color Constructor, includes font size
      this(i, text, Color.black, x, y, fontSize, l);
   }
   
   /**
   * Draws the object.
   * @param g Graphics used to draw the object
   */
   public void draw(Graphics g){
      g.setColor(labelColor());
      g.setFont(new Font("Monospaced", Font.BOLD, fontSize()));
      for (int i = 0; i < lines.size(); i++){
         g.drawString(lines.get(i), getX(), getY() + (fontSize() + 12) * i);
      }
   }
   
   /**
   * Converts the text into displayable lines.
   * @param line The unconverted text
   */
   private void interpretLine(String line){
      if (line.indexOf("\\n") < 0) lines.add(line);
      else {
         String l2 = line.substring(0, line.indexOf("\\n"));
         lines.add(l2);
         interpretLine(line.substring(line.indexOf("\\n") + 2));
      }
   }
}