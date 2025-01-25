import java.awt.*;
import javax.swing.*;

/**
* This is a template for UIButtons.
* @version 1.0
* @author Fabulosi Labs
*/

public class UIButton extends UIObject {
   /**
   * Colour of the button
   */
   private Color buttonColor;
   /**
   * Colour of the button when the mouse hovers over it
   */
   private Color highlight;
   /**
   * Indicates whether the mouse hovers over it or not
   */
   private boolean hovered;
   /**
   * Button text
   */
   private String text;
   /**
   * Dimension of the button
   */
   private int width, height;
   /**
   * The font size of the button text
   */
   private int fontSize;
   
   /**
   * Full class constructor.
   * @param i Identifier
   * @param text Button text
   * @param buttonColor The button's colour
   * @param x The button's x-position
   * @param y The button's y-position
   * @param width The button's width
   * @param height The button's height
   * @param fontSize The button's font size
   * @param l Local layer within the UI domain
   */
   public UIButton(String i, String text, Color buttonColor, int x, int y, int width, int height, int fontSize, int l){
      super(i, x, y, l);
      this.text = text;
      this.buttonColor = buttonColor;
      highlight = this.buttonColor.brighter();
      hovered = false;
      this.width = width;
      this.height = height;
      this.fontSize = fontSize;
      LayerManager.add(this);
   }
   
   /**
   * Class constructor without implementing font size
   * @param i Identifier
   * @param text Button text
   * @param buttonColor The button's colour
   * @param x The button's x-position
   * @param y The button's y-position
   * @param width The button's width
   * @param height The button's height
   * @param l Local layer within the UI domain
   */
   public UIButton(String i, String text, Color buttonColor, int x, int y, int width, int height, int l){
      this(i, text, buttonColor, x, y, width, height, 22, l);
   }
   
   /**
   * Class constructor without implementing font size and colour
   * @param i Identifier
   * @param text Button text
   * @param x The button's x-position
   * @param y The button's y-position
   * @param width The button's width
   * @param height The button's height
   * @param l Local layer within the UI domain
   */
   public UIButton(String i, String text, int x, int y, int width, int height, int l){
      this(i, text, new Color(250, 128, 27), x, y, width, height, 22, l);
   }
   
   /**
   * Class constructor without implementing colour
   * @param i Identifier
   * @param text Button text
   * @param x The button's x-position
   * @param y The button's y-position
   * @param width The button's width
   * @param height The button's height
   * @param fontSize The button's font size
   * @param l Local layer within the UI domain
   */
   public UIButton(String i, String text, int x, int y, int width, int height, int fontSize, int l){
      this(i, text, new Color(250, 128, 27), x, y, width, height, fontSize, l);
   }
   
   /**
   * Draws this object.
   * @param g Graphics used to draw
   */
   public void draw(Graphics g){
      if (!hovered) g.setColor(buttonColor);
      else g.setColor(highlight);
      
      g.fillRect(getX(), getY(), width, height);
      g.setColor(Color.black);
      g.drawRect(getX(), getY(), width, height);
      //Compare contrast, so as to make text black or white
      g.setColor(Color.black); //Temporary code
      g.setFont(new Font("Monospaced", Font.BOLD, fontSize));
      g.drawString(text, (int) (getX()+ (width/2) - (fontSize * text.length())/3.5), getY() + (height * 3/4));
   }
   
   /**
   * Sets hovered status to a certain value
   * @param val a certain boolean value
   */
   public void setHovered(boolean val){
      hovered = val;
   }
   
   /**
   * Checks if the mouse is hovering on the button.
   * @return True if the mouse is hovering on the button, and False if not.
   */
   public boolean mouseOnButton(){
      int mouseX = ProgramDriver.getMouseX();
      int mouseY = ProgramDriver.getMouseY();
      
      return (mouseX >= getX() && mouseX <= getX() + width)
         && (mouseY >= getY() && mouseY <= getY() + height);
   }
}