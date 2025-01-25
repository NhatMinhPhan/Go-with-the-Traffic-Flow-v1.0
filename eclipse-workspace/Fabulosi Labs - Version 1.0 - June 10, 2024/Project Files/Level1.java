import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
* This is the class responsible for Level 1's design, data and structure.
* @version 1.0
* @author Fabulosi Labs
*/

public class Level1 {
   /**
   * Black background image for Level 1.
   */
   private static BackgroundImg black;
   /**
   * Cutscene text for Level 1
   */
   private static UICutsceneText text;
   /**
   * Script file for Level 1
   */
   private static File script;
   /**
   * Current line being read for Level 1
   */
   private static String line;
   /**
   * The file reader
   */
   private static Scanner reader;
   
   /**
   * Current number of the line being read.
   */
   public static int currentLineNum = 0;
   
   /**
   * Next button to progress through the cutscene
   */
   private static UIButton nextButton;
   
   /**
   * Phone image, used to illustrate the cutscene.
   */
   private static StaticObject phone;
   
   /**
   * The background image for the simulation in Level 1.
   */
   private static BackgroundImg bimg;
   
   /**
   * Satisfaction label in the simulation's interface
   */
   private static UILabel satisfactionLabel;
   
   /**
   * Budget label in the simulation's interface
   */
   private static UILabel budgetLabel;
   
   /**
   * Progress label in the simulation's interface
   */
   private static UILabel progressLabel;
   
   /**
   * Upgrade button in the simulation's interface
   */
   private static UIButton upgradeButton;
   
   /**
   * "Set Configuration" button in the simulation's interface
   */
   private static UIButton configButton;
   /**
   * Go button in the simulation's interface
   */
   private static UIButton goButton;
   /**
   * Background button in the simulation's interface
   */
   private static UIBackground background;
   /**
   * Pop-up Window for configuration of Traffic Lights
   */
   private static PopUpWindow popup = new PopUpWindow();
   
   /**
   * A label showing up in the simulation's interface
   */
   private static UILabel timeLabel, statusLabel;
   /**
   * A button in the simulation's interface
   */
   private static UIButton cancelButton, tryAgainButton, proceedButton;
   /**
   * Undo button
   */
   private static UIButton undoButton;
   
   /**
   * Stores commands for undoing
   */
   private static ArrayList<String> undoList;
   
   /**
   * Sets up Level 1.
   */
   public static void setup(){
      getScript();
      undoList = new ArrayList<>();
      black = new BackgroundImg("Background", Color.black);
      readNextLine();
      nextButton = new UIButton("Next Button", "NEXT", new Color(171, 163, 162), ProgramDriver.getMainFrameWidth() - 150, ProgramDriver.getMainFrameHeight() - 90, 120, 60, 22, 1);
      LayerManager.setCamera(new Level1Camera());
      LayerManager.setCameraActivity(false);
   }
   
   /**
   * Gets the script file.
   */
   private static void getScript(){
      try {
         script = new File("Level 1 Script.txt");
         if (!script.exists()) throw new IOException();
         reader = new Scanner(script);
      }
      catch (IOException e){
         e.printStackTrace();
      }
   }
   
   /**
   * Reads the next line in the script file, and runs corresponding code.
   */
   public static void readNextLine(){
      currentLineNum++;
      if (LayerManager.getStaticList().contains(phone)) LayerManager.remove(phone);
      phone = null;
      
      if ((currentLineNum >= 3 && currentLineNum <= 4) || (currentLineNum >= 8 && currentLineNum <= 11)){
         phone = new StaticObject("Phone", "Assets/phone.png", 1, 30, 140, 1000, 621);
      }
      else if (currentLineNum >= 5 && currentLineNum <= 7){
         phone = new StaticObject("Phone", "Assets/phone-pickedup.png", 1, -30, -244, 1000, 1005);
      }
      else if (currentLineNum == 15){
         bimg = new BackgroundImg("Street Background", "Assets/l1streets.png");
         LayerManager.setCameraActivity(true);
      }
      else if (currentLineNum == 16){
         LayerManager.resetSimulation();
         populateIntro();
      }
      else if (currentLineNum == 17){
         LayerManager.resetSimulation();
         TrafficOfficer t1 = new TrafficOfficer("Middle", "4", 580, 420, 5);
      }
      else if (currentLineNum == 18){
         LayerManager.resetSimulation();
         populateIntro();
      }
      else if (currentLineNum == 19){
         SimulationData.initializeBudget(200000);
         LayerManager.remove(nextButton);
         nextButton = null;
         LayerManager.resetSimulation();
         updateUI("default");
      }
      else if (currentLineNum == 21){
         ArrayList<UIObject> ui = LayerManager.getUIList();
         for (int i = ui.size() - 1; i >= 0; i--){
            if (ui.get(i) instanceof UICutsceneText == false) LayerManager.remove(ui.get(i));
         }
         nextButton = new UIButton("Next Button", "NEXT", new Color(171, 163, 162), ProgramDriver.getMainFrameWidth() - 150, ProgramDriver.getMainFrameHeight() - 90, 120, 60, 22, 1);
      }
      else if (currentLineNum == 23){
         LayerManager.remove(nextButton);
         nextButton = null;
         LayerManager.resetSimulation();
         updateUI("default");
      }
      else if (currentLineNum == 24){
         ArrayList<UIObject> ui = LayerManager.getUIList();
         for (int i = ui.size() - 1; i >= 0; i--){
            if (ui.get(i) instanceof UICutsceneText == false) LayerManager.remove(ui.get(i));
         }
         nextButton = new UIButton("Next Button", "NEXT", new Color(171, 163, 162), ProgramDriver.getMainFrameWidth() - 150, ProgramDriver.getMainFrameHeight() - 90, 120, 60, 22, 1);
      }
      
      line = reader.nextLine();
      if (LayerManager.getUIList().contains(text)) LayerManager.remove(text);
      text = new UICutsceneText("Level 1 Text", line, Color.white, 55, 60, 25, 1);
   }
   
   /**
   * Runs code when there is a mouse click.
   * @param mouseX The mouse's x-position
   * @param mouseY The mouse's y-position
   */
   public static void mouseClicked(int mouseX, int mouseY){
      ArrayList<TrafficController> traffList = LayerManager.getTrafficList();
      UIButton prioritized = LayerManager.prioritizeButtons();
      if (prioritized == nextButton && nextButton != null){
         if (currentLineNum < 24) readNextLine();
         else {
            LayerManager.setCameraActivity(false);
            LayerManager.setCamera(null);
            ProgramDriver.setProgramState("stage screen");
            ProgramDriver.setup();
         }
      }
      else if (prioritized == upgradeButton && upgradeButton != null){
         for (int i = 0; i < traffList.size(); i++)
            if (traffList.get(i).getChosenStatus()){
               undoList.add(traffList.get(i).getIdentifier());
               traffList.get(i).upgrade();
               updateUI("individual");
               break;
            }
      }
      
      else if (prioritized == proceedButton && proceedButton != null) readNextLine();
      
      else if (prioritized == configButton && configButton != null){
         config();
      }
         
      else if (prioritized == goButton && goButton != null) {
         if (currentLineNum == 20 || currentLineNum == 23){
            readNextLine();
         }
         for (int i = 0; i < traffList.size(); i++){
            traffList.get(i).setChosenStatus(false);
         }
         if (currentLineNum != 21 && currentLineNum != 24){
            SimulationData.setSimStatus("RUNNING");
            updateUI("simulation");
         }
         populateLevel();
         
      }
         
      else if (prioritized == cancelButton && cancelButton != null){
         LayerManager.resetSimulation();
         SimulationData.setSimStatus("IN WAITING");
         SimulationData.resetTime();
         updateUI("default");
      }
      
      else if (prioritized == undoButton && undoButton != null){
         undo();
      }
   }
   
   /**
   * Configures, or changes properties of the chosen Traffic Controller in the simulation.
   */
   public static void config(){
      ArrayList<TrafficController> traffList = LayerManager.getTrafficList();
      TrafficController active = null;
      for (int i = 0; i < traffList.size(); i++){
         if (traffList.get(i).getChosenStatus()){
            active = traffList.get(i);
            break;
         }
      }
            
      if (active instanceof AITrafficLights){
         popup.setController(active);
         popup.setWindow(true);
      }
      else if (active instanceof TrafficLights){
         popup.setController(active);
         popup.setWindow(false);
      }
   }
   
   /**
   * Undoes a previous action (usually a Traffic Controller upgrade)
   */
   private static void undo(){ //Undo once
      if (undoList.size() <= 0) return;
      
      String command = undoList.get(undoList.size() - 1).trim();
      
      ArrayList<TrafficController> traffList = LayerManager.getTrafficList();
      for (int i = 0 ; i < traffList.size(); i++){
         if (traffList.get(i).getIdentifier().equals(command)){
            System.out.println("REMOVED: " + traffList.get(i).getIdentifier());
            traffList.get(i).setChosenStatus(false);
            traffList.get(i).degrade();
            undoList.remove(undoList.size() - 1);
            Level2.updateUI("default");
            break;
         }
         else traffList.get(i).setChosenStatus(false);
      }
   }
   
   /**
   * The first version of the populating algorithm, used before introducing the simulator's UI.
   */
   public static void populateIntro(){
      SimulationData.resetSatisfaction();
      int[][] pedestrian_coordinates = Pedestrian.getCoordinates();
      int spawn = (int) Math.random() * pedestrian_coordinates.length;
      for (int i = 1; i <= 6; i++){ //Generates pedestrians
         int[] randomCoordinates = pedestrian_coordinates[spawn];
         new Pedestrian("Pedestrian " + (1 + i), Math.random() * 0.15 + 0.1, randomCoordinates[0] + (int) (Math.random() * 10 + 1), randomCoordinates[1] + (int) (Math.random() * 10 + 1), 7 + i);
         //new Pedestrian("Pedestrian " + i, (int) (Math.random() * getMainFrameWidth()), (int) (Math.random() * getMainFrameHeight()), (int) (Math.random() * getMainFrameHeight()), 2 + i);
           
         if (spawn == pedestrian_coordinates.length - 1) spawn = 0;
         else spawn++;
      }
      
      
      //Pedestrian p2 = new Pedestrian("Pedestrian 2", Math.random() * 0.15 + 0.1, 84, 546, 4);
      Pedestrian p1 = new Pedestrian("Pedestrian 1", Math.random() * 0.15 + 0.1, 873, 329, 3);
      
   }
   
   /**
   * The second version of the populating algorithm, used later on after the first version.
   */
   public static void populateLevel(){
      SimulationData.resetSatisfaction();
      int[][] pedestrian_coordinates = Pedestrian.getCoordinates();
      int spawn = (int) Math.random() * pedestrian_coordinates.length;
      for (int i = 1; i <= 18; i++){ //Generates pedestrians
         int[] randomCoordinates = pedestrian_coordinates[spawn];
         new Pedestrian("Pedestrian " + (1 + i), Math.random() * 0.15 + 0.1, randomCoordinates[0] + (int) (Math.random() * 10 + 1), randomCoordinates[1] + (int) (Math.random() * 10 + 1), 7 + i);
         //new Pedestrian("Pedestrian " + i, (int) (Math.random() * getMainFrameWidth()), (int) (Math.random() * getMainFrameHeight()), (int) (Math.random() * getMainFrameHeight()), 2 + i);
           
         if (spawn == pedestrian_coordinates.length - 1) spawn = 0;
         else spawn++;
      }
      
      
      //Pedestrian p2 = new Pedestrian("Pedestrian 2", Math.random() * 0.15 + 0.1, 84, 546, 4);
      Pedestrian p1 = new Pedestrian("Pedestrian 1", Math.random() * 0.15 + 0.1, 873, 329, 3);
      
   }
   
   /**
   * Updates the simulator's UI according to the mode.
   * @param mode The mode the simulator is in. It includes: "default" (not choosing anything), "individual" (after choosing a Traffic Controller), "simulation" (when the simulation plays out), "loss" (an accident happens), "win" (moving onto the next stage).
   */
   public static void updateUI(String mode){
      ArrayList<UIObject> uilist = LayerManager.getUIList();
      for (int i = uilist.size() - 1; i >= 0; i--){
         if (uilist.get(i) instanceof UIBackground == false){
            LayerManager.remove(uilist.get(i));
         }
      }
   
      SimulationData.checkProgress();
      
      
      upgradeButton = null;
      configButton = null;
      goButton = null;
      budgetLabel = null;
      progressLabel = null;
      background = null;
      satisfactionLabel = null;
      timeLabel = null;
      statusLabel = null;
      cancelButton = null;
      tryAgainButton = null;
      proceedButton = null;
      undoButton = null;
   
      mode = mode.toLowerCase();
      
      switch (mode){
         case "default":
            background = new UIBackground("Background", Color.orange, 160, 10);
            budgetLabel = new UILabel("Budget Label", "Budget: $" + SimulationData.budget(), 60, ProgramDriver.getMainFrameHeight() - 110, 9);
            progressLabel = new UILabel("Budget Label", "Work Progress: " + SimulationData.workProgress() + "%", 830, ProgramDriver.getMainFrameHeight() - 110, 9);
            undoButton = new UIButton("Undo Button", "UNDO", Color.pink, ProgramDriver.getMainFrameWidth() - 80, ProgramDriver.getMainFrameHeight() - 60, 80, 60, 9);
            if (currentLineNum != 19 && currentLineNum != 22) goButton = new UIButton("Go Button", "GO!", new Color(20, 112, 28), 780, ProgramDriver.getMainFrameHeight() - 80, 50, 30, 7);
            break;
         case "individual": //When checking the traffic controllers
            background = new UIBackground("Background", Color.orange, 160, 10);
            ArrayList<TrafficController> traffList = LayerManager.getTrafficList();
            TrafficController chosen = null;
            for (int i = 0 ; i < traffList.size(); i++){
               if (traffList.get(i).getChosenStatus()){
                  chosen = traffList.get(i);
                  break;
               }
            }
            if (chosen != null) System.out.println("Individual Selected: " + chosen.getClass().getSimpleName());
            else  System.out.println("Individual Selected: NULL");
            budgetLabel = new UILabel("Budget Label", "Budget: $" + SimulationData.budget(), 60, ProgramDriver.getMainFrameHeight() - 110, 9);
            progressLabel = new UILabel("Budget Label", "Work Progress: " + SimulationData.workProgress() + "%", 830, ProgramDriver.getMainFrameHeight() - 110, 9);
            undoButton = new UIButton("Undo Button", "UNDO", Color.pink, ProgramDriver.getMainFrameWidth() - 80, ProgramDriver.getMainFrameHeight() - 60, 80, 60, 9);
            if (chosen != null && chosen instanceof AITrafficLights == false){
               if (currentLineNum != 19 || (currentLineNum == 19 && chosen instanceof TrafficLights == false)){
                  String upgradeText = "Upgrade (";
                  if (chosen instanceof TrafficOfficer) upgradeText += "$180,000)";
                  else upgradeText += "$20,000)";
                  upgradeButton = new UIButton("Upgrade Button", upgradeText, 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
               }
            }
            if (!(chosen instanceof TrafficOfficer)) configButton = new UIButton("Configuration Button", "Set Configuration", 200, ProgramDriver.getMainFrameHeight() - 80, 270, 30, 7);
            if (currentLineNum != 19 || (currentLineNum == 19 && !(chosen instanceof TrafficOfficer))) goButton = new UIButton("Go Button", "GO!", new Color(20, 112, 28), 780, ProgramDriver.getMainFrameHeight() - 80, 50, 30, 7);
            break;
         case "simulation":
            background = new UIBackground("Background", Color.orange, 160, 10);
            timeLabel = new UILabel("Time Label", "Time Elapsed: " + SimulationData.timeElapsed() + " seconds", 60, ProgramDriver.getMainFrameHeight() - 110, 9);
            satisfactionLabel = new UILabel("Satisfaction Label", "Satisfaction: " + SimulationData.satisfaction() + "%", ProgramDriver.getMainFrameWidth() / 2 - 110, ProgramDriver.getMainFrameHeight() - 110, 9);
            statusLabel = new UILabel("Status Label", "Status: " + SimulationData.simStatus(), 830, ProgramDriver.getMainFrameHeight() - 110, 9);
            cancelButton = new UIButton("Cancel Button", "CANCEL", Color.pink, 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
            break;
         case "loss":
            background = new UIBackground("Background", Color.red, 160, 10);
            timeLabel = new UILabel("Time Label", "Time Elapsed: " + SimulationData.timeElapsed() + " seconds", Color.white, 60, ProgramDriver.getMainFrameHeight() - 110, 9);
            statusLabel = new UILabel("Status Label", "Status: " + SimulationData.simStatus(), Color.white, 830, ProgramDriver.getMainFrameHeight() - 110, 9);
            tryAgainButton = new UIButton("Try Again Button", "TRY AGAIN", 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
            break;
         case "win":
            background = new UIBackground("Background", Color.orange, 160, 10);
            timeLabel = new UILabel("Time Label", "Time Elapsed: " + SimulationData.timeElapsed() + " seconds", 60, ProgramDriver.getMainFrameHeight() - 110, 9);
            satisfactionLabel = new UILabel("Satisfaction Label", "Satisfaction: " + SimulationData.satisfaction() + "%", ProgramDriver.getMainFrameWidth() / 2 - 110, ProgramDriver.getMainFrameHeight() - 110, 9);
            statusLabel = new UILabel("Status Label", "Status: " + SimulationData.simStatus(), 830, ProgramDriver.getMainFrameHeight() - 110, 9);
            proceedButton = new UIButton("Proceed Button", "PROCEED", Color.green, 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
            break;
      }
   }
   
   /**
   * Returns the visibility of popup.
   * @return popup's visibility
   */
   public static boolean popupIsVisible(){
      return popup.isVisible();
   }
}