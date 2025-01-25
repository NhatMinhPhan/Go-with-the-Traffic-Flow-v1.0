import java.awt.*;
import javax.swing.*;

/**
* This is a template for UI Labels.
* @version 1.0
* @author Fabulosi Labs
*/

public class UILabel extends UIObject {
   /**
   * Text color
   */
   private Color labelColor;
   /**
   * Text of this UI Label
   */
   private String text;
   /**
   * Font size of this UI Label
   */
   private int fontSize;
   
   /**
   * Full class constructor
   * @param i Identifier
   * @param text Text of the label
   * @param labelColor Text colour
   * @param x X-position
   * @param y Y-position
   * @param fontSize Font size of the text
   * @param l Local layer within the UI domain
   */
   public UILabel(String i, String text, Color labelColor, int x, int y, int fontSize, int l){ //Includes font size
      super(i, x, y, l);
      this.text = text;
      this.labelColor = labelColor;
      this.fontSize = fontSize;
      LayerManager.add(this);
   }
   
   /**
   * Class constructor without setting up font size
   * @param i Identifier
   * @param text Text of the label
   * @param labelColor Text colour
   * @param x X-position
   * @param y Y-position
   * @param l Local layer within the UI domain
   */
   public UILabel(String i, String text, Color labelColor, int x, int y, int l){
      this(i, text, labelColor, x, y, 25, l);
   }
   
   /**
   * Class constructor without setting up text colour and font size
   * @param i Identifier
   * @param text The label's text
   * @param x X-position
   * @param y Y-position
   * @param l Local layer within the UI domain
   */
   public UILabel(String i, String text, int x, int y, int l){
      this(i, text, Color.black, x, y, 25, l);
   }
   
   /**
   * Class constructor without stting up text colour
   * @param i Identifier
   * @param text Text of the label
   * @param x X-position
   * @param y Y-position
   * @param fontSize Font size of the text
   * @param l Local layer within the UI domain
   */
   public UILabel(String i, String text, int x, int y, int fontSize, int l){ //No Color Constructor, includes font size
      this(i, text, Color.black, x, y, fontSize, l);
   }
   
   /**
   * Draws the object.
   * @param g Graphics used to draw it
   */
   public void draw(Graphics g){
      g.setColor(labelColor);
      g.setFont(new Font("Monospaced", Font.BOLD, fontSize));
      g.drawString(text, getX(), getY());
   }
   
   /**
   * Returns the text colour.
   * @return text colour
   */
   public Color labelColor(){
      return labelColor;
   }
   
   /**
   * Returns the font size
   * @return font size
   */
   public int fontSize(){
      return fontSize;
   }
   
   /**
   * Returns the text of the label
   * @return the label's text
   */
   public String text(){
      return text;
   }
}