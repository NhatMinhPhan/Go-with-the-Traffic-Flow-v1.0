import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

/**
* This displays the End Screen or the Questions Screen.
* @version 0.4
* @author Fabulosi Labs
*/

public class EndScreen {
   /**
   * This decides whether the End Screen or Questions Screen.
   */
   private static boolean questionsMode = false;
   
   /**
   * The next button that shows up in the end screen
   */
   private static UIButton nextButton;
   
   /**
   * Sets up the screen.
   * @param q If true, the Questions screen is set up. If false, the End Screen is set up.
   */
   public static void setup(boolean q){
      questionsMode = q;
      if (q){
         new BackgroundImg("Questions Background", new Color(56, 56, 56));
         String t = "FOOD FOR THOUGHT...\\n\\nNow that you’ve successfully fulfilled your job, let's think\\nfor a moment:";
         t += "\\n\\n1. This AI technology is dependent on electronic equipment, so\\nmalfunctions or power failures can happen. Can traffic still be safe\\naround intersections? Should police traffic officers take control\\nof these areas in this case?";
         t += "\\n\\n2. Is there a way for this technology to function even when the\\nelectronic equipment on which it depends no longer works? Or\\nin other words, how possible is it to design such a technology\\nwhich remains functional, even independently from energy sources or\\nother equipment that can someday malfunction?";
         new UICutsceneText("Thought Text", t, Color.white, 60, 60, 2);
      }
      else {
         new BackgroundImg("End Background", new Color(97, 95, 95));
         
         int[] savedData = getSavedData();
         
         String t = "WELL DONE!\\n\\n";
         if (savedData == null || (SimulationData.stage < savedData[0] || SimulationData.budget() > savedData[1])){
            t += "NEW HIGH SCORE:\\n\\n";
            writeFile();
         }
         t += "- You've finished upgrading the town's traffic system in only " + SimulationData.stage + " stages.\\n";
         t += "- You now have $" + SimulationData.budget() + " remaining!\\n";
         t += "- You've shown thorough understanding of the basics of AI in traffic,\\nand how important AI has become in our society!";
         if (savedData != null && (SimulationData.stage >= savedData[0] || SimulationData.budget() <= savedData[1])){
            t += "\\n\\nYour previous high score is " + savedData[0] + " stages with $" + savedData[1] + " remaining.";
            t += "\\nCan you beat this high score by playing fewer stages and have\\nmore money remaining?";
         }
         new UICutsceneText("Score Text", t, Color.white, 60, 60, 2);
      }
      if (nextButton != null) LayerManager.remove(nextButton);
      nextButton = new UIButton("Next Button", "NEXT", ProgramDriver.getMainFrameWidth() - 160, ProgramDriver.getMainFrameHeight() - 70, 120, 30, 1);
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
   * Writes and updates "High Score.txt" to store the user's high score.
   */
   public static void writeFile(){
      File highScoreFile = new File("High Score.txt");
      try {
         if (!highScoreFile.exists()) {
            highScoreFile.createNewFile();
         }
         FileWriter writer = new FileWriter(highScoreFile);
         writer.write("Stages: " + SimulationData.stage + "\nRemaining: " + SimulationData.budget());
         writer.close();
      }
      catch (Exception e){
         e.printStackTrace();
      }
   }
   
   /**
   * Returns questionsMode.
   * @return questionsMode
   */
   public static boolean questionsMode(){
      return questionsMode;
   }
   
   /**
   * Sets questionsMode to val.
   * @param val a certain boolean value
   */
   public static void setQuestionsMode(boolean val){
      questionsMode = val;
   }
   
   /**
   * Runs when a mouse click is made.
   * @param mouseX The mouse's x-position
   * @param mouseY The mouse's y-position
   */
   public static void mouseClicked(int mouseX, int mouseY){
      UIButton prioritized = LayerManager.prioritizeButtons();
      if (prioritized == nextButton && nextButton != null){
         if (!questionsMode){
            ProgramDriver.setProgramState("questions screen");
            ProgramDriver.setup();
         }
         else {
            ProgramDriver.setProgramState("main menu");
            ProgramDriver.setup();
         }
      }
   }
}

/**
* This displays the Quit Screen.
* @version 0.3
* @author Fabulosi Labs
*/

class QuitScreen {
   /**
   * Button for the Quit Screen
   */
   private static UIButton yes, no;
   
   /**
   * Sets up the screen.
   */
   public static void setup(){
      new BackgroundImg("Quit Background", new Color(97, 95, 95));
      String t = "Created, written, directed, designed and developed by:\\nMinh Phan\\n";
      t += "\\n\\nFabulosi Labs © 2024\\n";
      t += "\\n\\nAre you sure you want to quit?";
      new UICutsceneText("Stage Text", t, Color.white, 60, 60, 2);
      if (yes != null) LayerManager.remove(yes);
      if (no != null) LayerManager.remove(no);
      yes = new UIButton("Yes Button", "Yes", 240, 500, 120, 30, 1);
      no =  new UIButton("No Button", "No", 240, 540, 120, 30, 1);
   }
   
   /**
   * Runs when a mouse click is made.
   * @param mouseX The mouse's x-position
   * @param mouseY The mouse's y-position
   */
   public static void mouseClicked(int mouseX, int mouseY){
      UIButton prioritized = LayerManager.prioritizeButtons();
      if (prioritized == yes && yes != null){
         ProgramDriver.mainFrame.dispatchEvent(new WindowEvent(ProgramDriver.mainFrame, WindowEvent.WINDOW_CLOSING));
      }
      else if (prioritized == no && no != null){
         ProgramDriver.setProgramState("main menu");
         ProgramDriver.setup();
      }
   }
}

/**
* This displays the Stage Screen.
* @version 0.3
* @author Fabulosi Labs
*/

class StageScreen {
   /**
   * Stage number to be displayed
   */
   private static int stage;
   
   /**
   * Next button
   */
   private static UIButton nextButton;
   
   /**
   * Sets up the screen.
   * @param s The stage number that should be set up.
   */
   public static void setup(int s){
      stage = s;
      int budget = 0; //Change in budget
      if (s == 1) budget = 180000;
      else budget = (int) (Math.random() * 100000 + 80000);
      new BackgroundImg("Stage Background", new Color(56, 56, 56));
      String t = "Stage " + stage + "\\n\\n";
      t += "- The town just gave you $" + budget + " for improving their traffic system.\\n";
      SimulationData.changeBudget(budget);
      t += "- Your goal is to upgrade as much of the traffic system as possible.\\n";
      t += "- Each stage is 60 seconds long, and as long as the\\nsatisfaction level remains above or equal to 50%,\\nyou can move onto the next stage, and get more\\nfunding from the municipal government.";
      t += "\\n- If you’ve made a mistake during setup, don’t be afraid to\\nuse the undo button.";
      t += "\\n- The simulation always stops when an accident happens.";
      new UICutsceneText("Stage Text", t, Color.white, 60, 60, 2);
      nextButton = new UIButton("Next Button", "NEXT", ProgramDriver.getMainFrameWidth() - 160, ProgramDriver.getMainFrameHeight() - 70, 120, 30, 1);
   }
   
   /**
   * Runs when a mouse click is made.
   * @param mouseX The mouse's x-position
   * @param mouseY The mouse's y-position
   */
   public static void mouseClicked(int mouseX, int mouseY){
      UIButton prioritized = LayerManager.prioritizeButtons();
      if (prioritized == nextButton && nextButton != null){
         ProgramDriver.setProgramState("level 2");
         ProgramDriver.setup();
      }
   }
}

/**
* This displays the Splash Screen.
* @version 0.3
* @author Fabulosi Labs
*/

class SplashScreen {
   /**
   * Last time for keeping track of time so that the program exits the Splash Screen.
   */
   private static long lastTime;
   
   /**
   * Indicates if the splash screen has been played or not.
   */
   private static boolean played = false;
   
   /**
   * Sets up the screen.
   */
   public static void setup(){
      new BackgroundImg("Splash Background", Color.white);
      new StaticObject("Logo", "Assets/logo.png", 1, ProgramDriver.getMainFrameWidth() / 2 - 250, ProgramDriver.getMainFrameHeight() / 2 - 250, 500, 500);
      lastTime = System.currentTimeMillis();
      played = true;
   }
   
   /**
   * Returns lastTime.
   * @return lastTime
   */
   public static long lastTime(){
      return lastTime;
   }
   
   /**
   * Returns played.
   * @return played
   */
   public static boolean played(){
      return played;
   }
}