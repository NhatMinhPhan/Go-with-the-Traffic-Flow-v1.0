import java.awt.*;
import javax.swing.*;

/**
* This class is used to create a camera that follows an object.
* @version 0.3
* @author Fabulosi Labs
*/

public class Camera extends LayeredObject {
   /**
   * Stores the radius of the camera
   */
   private int radiusX, radiusY;
   
   /**
   * Stores the Animate Object the object must follow, if there is one.
   */
   private AnimateObject obj;
   
   /**
   * Class constructor with Animate Object
   * @param i Identifier
   * @param obj Animate Object that the object must follow
   * @param x Horizontal radius
   * @param y Vertical radius
   */
   public Camera(String i, AnimateObject obj, int x, int y){
      super(i, obj.trueX(), obj.trueY());
      this.obj = obj;
      radiusX = x;
      radiusY = y;
   }
   
   /**
   * Class constructor without setting a custom identifier
   * @param obj Animate Object that the object must follow
   * @param x Horizontal radius
   * @param y Vertical radius
   */
   public Camera(AnimateObject obj, int x, int y){
      this("Camera", obj, x, y);
   }
   
   /**
   * Class constructor without using an Animate Object (for Level1Camera)
   * @param i Identifier
   * @param x X-position
   * @param y Y-position
   * @param rx Horizontal radius
   * @param ry Vertical radius
   */
   public Camera(String i, int x, int y, int rx, int ry){
      super(i, x, y);
      radiusX = rx;
      radiusY = ry;
   }
   
   /**
   * Draws the Camera
   * @param g Graphics used to draw
   */
   public void draw (Graphics g){
      g.setColor(Color.black);
      g.fillRect(0, 0, obj.trueX() - radiusX, ProgramDriver.getMainFrameHeight());
      g.fillRect(0, 0, ProgramDriver.getMainFrameWidth(), obj.trueY() - radiusY - 18);
      g.fillRect(obj.trueX() + radiusX, 0, ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight());
      g.fillRect(0, obj.trueY() + radiusY, ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight());
   }
   
   /**
   * Gets the target of the camera
   * @return the camera's target which is an Animate Object
   */
   public AnimateObject getTarget(){
      return obj;
   }
   
   /**
   * Returns the horizontal radius
   * @return the horizontal radius
   */
   public int radiusX(){
      return radiusX;
   }
   
   /**
   * Returns the vertical radius
   * @return the vertical radius
   */
   public int radiusY(){
      return radiusY;
   }
}