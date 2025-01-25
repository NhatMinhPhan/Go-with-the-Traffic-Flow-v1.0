import java.awt.*;
import javax.swing.*;

/**
* This is a template for backgrounds for UI.
* @version 1.0
* @author Fabulosi Labs
*/

public class UIBackground extends UIObject {
   /**
   * Colour for the background for the simulator UI.
   */
   private Color bgColor;
   
   /**
   * Class constructor.
   * @param i Identifier
   * @param bgColor New background colour
   * @param height Height of the background
   * @param l Local layer within the simulator
   */
   public UIBackground(String i, Color bgColor, int height, int l){
      super(i, 0, ProgramDriver.getMainFrameHeight() - height, l);
      this.bgColor = bgColor;
      LayerManager.add(this);
   }
   
   /**
   * Draws the object.
   * @param g Graphics used to draw it.
   */
   public void draw(Graphics g){
      int width = ProgramDriver.getMainFrameWidth();
      int height = ProgramDriver.getMainFrameHeight() - getY();
      g.setColor(bgColor);
      g.fillRect(0, getY(), width, height);
      g.setColor(Color.black);
      g.drawRect(0, getY(), width, height);
   }
}