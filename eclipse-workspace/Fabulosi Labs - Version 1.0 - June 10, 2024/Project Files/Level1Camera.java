import java.awt.*;
import javax.swing.*;

/**
* This is the camera used for Level 1, which blocks most of the view.
* @version 0.4
* @author Fabulosi Labs
*/

public class Level1Camera extends Camera {
   /**
   * Class Constructor
   */
   public Level1Camera(){
      super("Level 1 Camera", ProgramDriver.getMainFrameWidth() / 2, ProgramDriver.getMainFrameHeight() / 2, 230, 180);
   }
   
   /**
   * Draws the Level 1 Camera.
   * @param g Graphics used to draw the object
   */
   public void draw(Graphics g){
      g.setColor(Color.black);
      g.fillRect(0, 0, ProgramDriver.getMainFrameWidth() / 2 - radiusX(), ProgramDriver.getMainFrameHeight());
      g.fillRect(0, 0, ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight() / 2 - radiusY() - 18);
      g.fillRect(ProgramDriver.getMainFrameWidth() / 2 + radiusX(), 0, ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight());
      g.fillRect(0, ProgramDriver.getMainFrameHeight() / 2 + radiusY(), ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight());
   }
}