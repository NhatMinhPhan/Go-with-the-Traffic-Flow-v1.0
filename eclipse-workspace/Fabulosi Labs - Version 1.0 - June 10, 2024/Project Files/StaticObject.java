import java.awt.*;
import javax.swing.*;
import java.io.*;

import javax.imageio.*;
import java.awt.image.*;

/**
* This is a template for static objects in simulations, i.e. buildings.
* @version 0.4
* @author Fabulosi Labs
*/

public class StaticObject extends SimulationObject {
   /**
   * If true, the object displays an image. Otherwise, it displays a colour.
   */
   private boolean imgMode;
   /**
   * Link to the image file the object is to display, if there is one.
   */
   private String filePath;
   /**
   * The colour used if imgMode is false.
   */
   private Color color;
   /**
   * A dimension of the Static Object
   */
   private int width, height;
   /**
   * The image to be displayed, if imgMode is true.
   */
   private BufferedImage img;
   
   /**
   * Class constructor using an Image.
   * @param i Identifier
   * @param filePath File path to the image to be displayed
   * @param layer Local layer within the simulation
   * @param x X-position according to the mainFrame
   * @param y Y-position according to the mainFrame
   * @param width The Static Object's width
   * @param height The Static Object's height
   */
   public StaticObject(String i, String filePath, int layer, int x, int y, int width, int height){
      super(i, x, y, layer);
      if (filePath == null) imgMode = false;
      else {
         imgMode = true;
         this.filePath = filePath;
         this.width = width;
         this.height = height;
         try {
            File f = new File(filePath);
            img = ImageIO.read(f);
         }
         catch (IOException e){
            System.out.println("Drawing " + getIdentifier() + " Failed.");
            e.printStackTrace();
         }
      }
      LayerManager.add(this);
   }
   
   /**
   * Class constructor using a Colour.
   * @param i Identifier
   * @param color Colour to be used
   * @param layer Local layer within the simulation
   * @param x X-position according to the mainFrame
   * @param y Y-position according to the mainFrame
   * @param width The Static Object's width
   * @param height The Static Object's height
   */
   public StaticObject(String i, Color color, int layer, int x, int y, int width, int height){
      super(i, x, y, layer);
      imgMode = false;
      this.color = color;
      this.width = width;
      this.height = height;
      LayerManager.add(this);
   }
   
   /**
   * Draws this object.
   * @param g Graphics used to draw the object
   */
   public void draw(Graphics g){
      if (!imgMode){
         if (color == null) g.setColor(Color.green);
         else g.setColor(color);
         g.fillRect(getX(), getY(), width, height);
         g.setColor(Color.black);
         g.drawRect(getX(), getY(), width, height);
         return;
      }
      
      g.drawImage(img, getX(), getY(), width, height, null);
   }
   
   /**
   * Returns the x-position as seen on the simulation.
   * @return the x-position according to the simulation
   */
   public int trueX(){
      return getX() + (width / 2);
   }
   
   /**
   * Returns the y-position as seen on the simulation.
   * @return the y-position according to the simulation
   */
   public int trueY (){
      return getY() + height;
   }
   
   /**
   * Sets the x-position as seen on the simulation.
   * @param val the x-position according to the simulation.
   */
   public void setTrueX(int val){
      setX(val - (width/2));
   }
   
   /**
   * Sets the y-position as seen on the simulation.
   * @param val the y-position according to the simulation.
   */
   public void setTrueY(int val){
      setY(val - height);
   }
}