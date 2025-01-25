import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
* This is the template for SimulationObject objects, which are used in the simulation itself.
* @version 0.4
* @author Fabulosi Labs
*/

public class MainMenu {
   
   /**
   * Background image for the main menu
   */
   static BackgroundImg bimg;
   /**
   * The game's title
   */
   static UILabel title;
   /**
   * Menu button
   */
   static UIButton startButton, quitButton;
   
   /**
   * Sets up the main menu
   */
   public static void setup(){
      LayerManager.resetEverything();
      bimg = new BackgroundImg("Title Background", Color.gray);
      title = new UILabel("Title", "GO WITH THE TRAFFIC FLOW", Color.white, 70, 110, 1);
      
      startButton = new UIButton("Start Button", "Begin Game", 70, 140, 190, 55, 2);
      quitButton = new UIButton("Quit Button", "Quit", 70, 205, 190, 55, 2);
      
      File highScore = new File("High Score.txt");
      if (highScore.exists()){
         try {
            Scanner reader = new Scanner(highScore);
            String s = "Level 2 High Score: ";
            int[] savedData = getSavedData();
            if (savedData != null && savedData.length == 2){
               if (savedData[0] > 1) s += savedData[0] + " stages, $" + savedData[1] + " left";
               else s += savedData[0] + " stage, $" + savedData[1] + " left";
            }
            new UILabel("High Score 1", s, Color.white, 70, 700, 1);
            new UILabel("High Score 2", "Can you manage to beat the score?", Color.white, 70, 740, 1);
         }
         catch (IOException e){
            e.printStackTrace();
         }
      }
      else{
         new UILabel("Heading", "Instructions:", Color.white, 70, 700, 1);
         new UILabel("Instructions", "Use your mouse to interact with the program.", Color.white, 70, 740, 1);
      }
      
   }
   
   /**
   * Checks if there is a file in the system named "High Score.txt".
   * If there is, returns an integer array of saved data if the necessary data is there.
   * The integer array consists of the stage number and the budget remaining in that order.
   * If there is no file, returns null.
   * If there is the file with the name "High Score.txt" but it does not have the necessary data, returns null.
   * @return Null or An integer array with the stage number and the budget remaining in that order.
   */
   public static int[] getSavedData(){
      int[] output = {Integer.MIN_VALUE, Integer.MIN_VALUE};
      
      File highScoreFile = new File("High Score.txt");
      try {
         if (!highScoreFile.exists()) return null;
         Scanner reader = new Scanner(highScoreFile);
         while (reader.hasNextLine()){
            String line = reader.nextLine();
            if (line.indexOf("Stages: ") == 0){
               output[0] = Integer.parseInt(line.substring(8));
            }
            else if (line.indexOf("Remaining: ") == 0){
               output[1] = Integer.parseInt(line.substring(11));
            }
            else return null;
         }
      }
      catch (Exception e){
         return null;
      }
      if (output[0] < 0 || output[1] < 0) return null;
      else return output;
   }
   
   /**
   * Examines the buttons that are in this program state, and runs certain code if a certain button is clicked on.
   * @param mouseX The mouse's x-position
   * @param mouseY The mouse's y-position
   */
   public static void examineButtons(int mouseX, int mouseY){
      UIButton prioritized = LayerManager.prioritizeButtons();
         
      if (prioritized == startButton){
         ProgramDriver.setProgramState("level 1");
         ProgramDriver.setup();
      }
      
      else if (prioritized == quitButton){
         //ProgramDriver.mainFrame.dispatchEvent(new WindowEvent(ProgramDriver.mainFrame, WindowEvent.WINDOW_CLOSING));
         ProgramDriver.setProgramState("quit screen");
         ProgramDriver.setup();
      }
      
   }
}