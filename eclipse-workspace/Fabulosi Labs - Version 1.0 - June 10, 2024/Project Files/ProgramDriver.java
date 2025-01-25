import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* This is the program driver which has the main method.
* @version 1.0
* @author Fabulosi Labs
*/

public class ProgramDriver extends MouseAdapter {
   
   /**
   * The JFrame used as the main window frame for the game.
   */
   static JFrame mainFrame = new JFrame("Go with the Traffic Flow");
   
   /**
   * The primary object that displays graphics.
   */
   Drawing drawing = new Drawing();
   
   /**
   * The Timer used for animated graphics.
   */
   javax.swing.Timer timer = new javax.swing.Timer(1000/60, drawing);
   
   /**
   * The state of the program, or what level it is displaying (Level 1, Level 2, Main Menu for example).
   */
   private static String programState;
      
   /**
   * Stores the coordinates of the mouse cursor on the mainFrame.
   */
   private static int mouseX, mouseY;
   
   /**
   * Class constructor.
   */
   public ProgramDriver(){
      mainFrame.setSize(1164, 902);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.addMouseListener(this);
      mainFrame.addMouseMotionListener(this);
      mainFrame.add(drawing);
      mainFrame.setResizable(false);
      mainFrame.setVisible(true);
      
      programState = "splash screen";
      setup();
      timer.start();
   }
   
   /**
   * The main method. 
   */
   public static void main (String[] args){
      new ProgramDriver();
   }
   
   /**
   * Sets up the layout of the window based on the state of the program (such as Main Menu and Level 2).
   */
   public static void setup(){
      LayerManager.resetEverything();
      LayerManager.setBGImg(new BackgroundImg("Placeholder", Color.white));
      programState = programState.toLowerCase();
      
      if (programState.equals("main menu")) MainMenu.setup();
      else if (programState.equals("level 2")) Level2.setup();
      else if (programState.equals("level 1")) Level1.setup();
      else if (programState.equals("end screen")) EndScreen.setup(false);
      else if (programState.equals("questions screen")) EndScreen.setup(true);
      else if (programState.equals("quit screen")) QuitScreen.setup();
      else if (programState.equals("stage screen")) StageScreen.setup(SimulationData.stage);
      else if (programState.equals("splash screen")) SplashScreen.setup();
   }
   
   /**
   * Updates the UI according to the state of the program (such as Main Menu and Level 2).
   * @param mode The mode of the UI that is to be displayed.
   */
   public static void updateUI(String mode){
      if (programState.equals("level 2")) Level2.updateUI(mode);
      else if (programState.equals("level 1")) Level1.updateUI(mode);
   }
   
   /**
   * Gets the size of the main JFrame, which is the main window.
   * @return the size of the main JFrame
   */
   public static int[] getMainFrameSize() { //Gets the size of the mainFrame, returns an int array with 2 elements: width and height respectively
      int[] output = new int[2];
      output[0] = mainFrame.getContentPane().getWidth(); //Width
      output[1] = mainFrame.getContentPane().getHeight(); //Height
      return output;
   }
   
   /**
   * Gets the width of the main JFrame, which is the main window.
   * @return the width of the JFrame
   */
   public static int getMainFrameWidth() {
      return mainFrame.getContentPane().getWidth(); //Width
   }
   
   /**
   * Gets the width of the main JFrame, which is the main window.
   * @return the height of the JFrame
   */
   public static int getMainFrameHeight() {
      return mainFrame.getContentPane().getHeight(); //Width
   }
   
   /**
   * Gets the state of the program (such as Main Menu, Level 1, Level 2).
   * @return the current program state
   */
   public static String getProgramState(){
      return programState;
   }
   
   /**
   * Sets to a new state of the program (such as Main Menu, Level 1, Level 2).
   * @param val The new program state.
   */
   public static void setProgramState(String val){
      programState = val;
   }
   
   /**
   * The Drawing class which displays the graphics on the mainFrame.
   */
   public class Drawing extends JComponent implements ActionListener {
      
      /**
      * The main Debug Tracker, which tracks the mouse's position on the mainFrame.
      */
      DebugTracker tracker = new DebugTracker();
      
      /**
      * Draws and renders elements onto the drawing
      * @param g the Graphics object that the program will draw on.
      */
      public void paint (Graphics g){
         LayerManager.sortLayeredObjects();
         LayerManager.render(g);
         if (Level2.popupIsVisible()){
            g.setColor(new Color(255, 255, 255, 210));
            g.fillRect(0, 0, getMainFrameWidth(), getMainFrameHeight());
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));
            g.drawString("Enter all the necessary data in the popup window", 60, 310);
            g.drawString("and close it to continue the game!", 60, 342);
         }
         else if (Level1.popupIsVisible()){
            if (Level1.currentLineNum == 19 || Level1.currentLineNum == 22) Level1.readNextLine();
            g.setColor(new Color(255, 255, 255, 210));
            g.fillRect(0, 0, getMainFrameWidth(), getMainFrameHeight());
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));
            if (Level1.currentLineNum == 20){
               g.drawString("Here, you can see the data relevant to the intersection so", 30, 310);
               g.drawString("that you can manually set the first time. Whenever you’re", 30, 342);
               g.drawString("ready, exit the popup window and click \"Go\"!", 30, 374);
            }
            else if (Level1.currentLineNum == 23){
               g.drawString("Now, you’ll see a different popup window! It has", 30, 310);
               g.drawString("initialization settings, and also automatic changes needed!", 30, 342);
               g.drawString("Try to play with them and see what works!", 30, 374);
            }
            else {
               g.drawString("Enter all the necessary data in the popup window", 60, 310);
               g.drawString("and close it to continue the game!", 60, 342);
            }
         }
         tracker.draw(g);
         if (SimulationData.simStatus().equals("RUNNING")) SimulationData.updateTime(System.currentTimeMillis());
         if (ProgramDriver.getProgramState().equals("splash screen") && SplashScreen.played() && System.currentTimeMillis() - SplashScreen.lastTime() >= 2500){
            ProgramDriver.setProgramState("main menu");
            ProgramDriver.setup();
         }
      }
      
      /**
      * Runs when an action is performed onto the drawing.
      * @param e the Mouse Event.
      */
      public void actionPerformed(ActionEvent e){
         repaint();
      }
   }
      
      /**
      * Gets the current x-position of the mouse.
      * @return the current x-position of the mouse
      */
      public static int getMouseX(){
         return mouseX;
      }
      
      /**
      * Gets the current y-position of the mouse.
      * @return the current y-position of the mouse
      */
      public static int getMouseY(){
         return mouseY;
      }
      
      /**
      * Runs when the mouse is moving.
      * @param e the Mouse Event.
      */
      public void mouseMoved (MouseEvent e){
         mouseX = e.getX();
         mouseY = e.getY();
         if (Level2.popupIsVisible()) return;
         LayerManager.prioritizeButtons();
      }
      
      /**
      * Runs when the mouse clicks something.
      * @param e the Mouse Event
      */
      public void mouseClicked(MouseEvent e){
         mouseX = e.getX();
         mouseY = e.getY();
         System.out.println("Clicked: (" + e.getX() + ", " + e.getY() + ")");
         
         if (Level2.popupIsVisible()) return;
         
         ArrayList<TrafficController> traffList = LayerManager.getTrafficList();
         for (int i = 0; i < traffList.size(); i++) traffList.get(i).onMouseClick(mouseX, mouseY);
         
         if (programState.equals("level 2")) Level2.examineButtons(mouseX, mouseY);
         else if (programState.equals("main menu")) MainMenu.examineButtons(mouseX, mouseY);
         else if (programState.equals("level 1")) Level1.mouseClicked(mouseX, mouseY);
         else if (programState.equals("end screen") || programState.equals("questions screen")) EndScreen.mouseClicked(mouseX, mouseY);
         else if (programState.equals("quit screen")) QuitScreen.mouseClicked(mouseX, mouseY);
         else if (programState.equals("stage screen")) StageScreen.mouseClicked(mouseX, mouseY);
      }
      
      /**
      * Runs when the mouse is released.
      * @param e the Mouse Event.
      */
      public void mouseReleased(MouseEvent e){
         mouseX = e.getX();
         mouseY = e.getY();
      }
      
      /**
      * Runs when the mouse is dragged.
      * @param e the Mouse Event.
      */
      public void mouseDragged (MouseEvent e){
         mouseX = e.getX();
         mouseY = e.getY();
         
         LayerManager.getHoveredButtons();
      }
}

/**
* The class for the Debug Tracker, the circle which follows the mouse on the mainFrame.
*/
class DebugTracker {

   /**
   * The radius of the circle that tracks the mouse's current position on the mainFrame.
   */
   private final int RADIUS = 10;

   /**
   * Class constructor.
   */
   public DebugTracker(){
      
   }
   
   /**
   * Draws a "mouse cursor".
   * @param g the Graphics object that the object will be drawn on.
   */
   public void draw (Graphics g){
      g.setColor(Color.red);
      g.fillOval(ProgramDriver.getMouseX() - 2 * RADIUS, ProgramDriver.getMouseY() - 2 * RADIUS, 2 * RADIUS, 2 * RADIUS);
   }
}


