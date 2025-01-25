import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
* This class manages Level 2 of the program.
* @version 0.3
* @author Fabulosi Labs
*/

public class Level2 {
   /**
   * Background image for Level 2
   */
   private static BackgroundImg bimg;
   
   /**
   * Satisfaction label for Level 2
   */
   private static UILabel satisfactionLabel;
   /**
   * Budget label
   */
   private static UILabel budgetLabel;
   /**
   * Progress label
   */
   private static UILabel progressLabel;
   /**
   * Upgrade button
   */
   private static UIButton upgradeButton;
   /**
   * Configuration button
   */
   private static UIButton configButton;
   /**
   * Go button
   */
   private static UIButton goButton;
   /**
   * Background for the simulator UI
   */
   private static UIBackground background;
   /**
   * Popup window
   */
   private static PopUpWindow popup = new PopUpWindow();
   /**
   * Labels for the simulator's UI
   */
   private static UILabel timeLabel, statusLabel;
   /**
   * Simulator button with its function described by its name
   */
   private static UIButton cancelButton, tryAgainButton, proceedButton;
   /**
   * Undo button
   */
   private static UIButton undoButton;
   /**
   * List of commands to be undone
   */
   private static ArrayList<String> undoList;
   
   /**
   * Sets up Level 2
   */
   public static void setup(){
      System.out.println("TTTTTTT");
      bimg = new BackgroundImg("Street Background", "Assets/l2streets.png");
      
      SimulationData.resetSatisfaction();
      SimulationData.checkProgress();
      SimulationData.resetTime();
      
      TrafficController t2, t3;
      
      if (SimulationData.stage <= 1){
         SimulationData.initializeBudget(180000);
         SimulationData.resetWorkProgress();
         SimulationData.checkProgress();
      
         t2 = new TrafficLights("Middle", "4", 447, 420, 5);
         t3 = new TrafficOfficer("2ndRightmost", "3E", 1003, 428, 6);
         
         SimulationData.list2[0] = t2;
         SimulationData.list2[1] = t3;
      }
      else {
         t2 = SimulationData.list2[0];
         t3 = SimulationData.list2[1];
         
         LayerManager.add(t2);
         LayerManager.add(t3);
      }
      
      /*
      StaticObject mall = new StaticObject("Shopping Mall", "Assets/shopping mall.png", 2, 48, 50, 410 - 48 - 20, 299 - 50 - 20);
      StaticObject park1 = new StaticObject("Park", "Assets/park.png", 1, 738, 234, 853 - 738, 303 - 234);
      new StaticObject("Park 2", "Assets/park.png", 1, 738 + 120, -30, 853 - 738, 303 - 234);
      new StaticObject("Police Station", "Assets/police station.png", 1, 870 + 120, -30, 69, 69);
      new StaticObject("House 1", "Assets/house.png", 1, -30, 520, 115, 115);
      new StaticObject("House 2", "Assets/house.png", 1, 290, 520, 115, 115);
      new StaticObject("House 3", "Assets/house.png", 1, 730, 520, 115, 115);
      */
      
      undoList = new ArrayList<>();
      
      /*
      LayerManager.setCamera(new Camera(p1, 112, 90));
      LayerManager.setCameraActivity(true);
      */
      
      //satisfactionLabel = new UILabel("Satisfaction Label", "Satisfaction: " + SimulationData.satisfaction() + "%", 60, ProgramDriver.getMainFrameHeight() - 110, 9);
      updateUI("default");
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
            undoButton = new UIButton("Undo Button", "UNDO", new Color(168, 14, 3), ProgramDriver.getMainFrameWidth() - 80, ProgramDriver.getMainFrameHeight() - 60, 80, 60, 9);
            goButton = new UIButton("Go Button", "GO!", new Color(20, 112, 28), 780, ProgramDriver.getMainFrameHeight() - 80, 50, 30, 7);
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
            undoButton = new UIButton("Undo Button", "UNDO", new Color(168, 14, 3), ProgramDriver.getMainFrameWidth() - 80, ProgramDriver.getMainFrameHeight() - 60, 80, 60, 9);
            if (chosen != null && chosen instanceof AITrafficLights == false){
               String upgradeText = "Upgrade (";
               if (chosen instanceof TrafficOfficer) upgradeText += "$180,000)";
               else upgradeText += "$20,000)";
               upgradeButton = new UIButton("Upgrade Button", upgradeText, 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
            }
            if (!(chosen instanceof TrafficOfficer)) configButton = new UIButton("Configuration Button", "Set Configuration", 200, ProgramDriver.getMainFrameHeight() - 80, 270, 30, 7);
            goButton = new UIButton("Go Button", "GO!", new Color(20, 112, 28), 780, ProgramDriver.getMainFrameHeight() - 80, 50, 30, 7);
            break;
         case "simulation":
            background = new UIBackground("Background", Color.orange, 160, 10);
            timeLabel = new UILabel("Time Label", "Time Elapsed: " + SimulationData.timeElapsed() + " seconds", 60, ProgramDriver.getMainFrameHeight() - 110, 9);
            satisfactionLabel = new UILabel("Satisfaction Label", "Satisfaction: " + SimulationData.satisfaction() + "%", ProgramDriver.getMainFrameWidth() / 2 - 110, ProgramDriver.getMainFrameHeight() - 110, 9);
            statusLabel = new UILabel("Status Label", "Status: " + SimulationData.simStatus(), 830, ProgramDriver.getMainFrameHeight() - 110, 9);
            cancelButton = new UIButton("Cancel Button", "CANCEL", new Color(168, 14, 3), 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
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
            proceedButton = new UIButton("Proceed Button", "PROCEED", new Color(36, 128, 60), 475, ProgramDriver.getMainFrameHeight() - 80, 300, 30, 8);
            break;
      }
   }
   
   /**
   * Configures a chosen Traffic Controller
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
   * Populates level 2 with Pedestrians and Vehicles.
   */
   public static void populateLevel2(){
      SimulationData.resetSatisfaction();
      
      //new Vehicle("Test vehicle", Math.random() * 0.15 + 0.1, 1, 390, 1);
      
      int[][] pedestrian_coordinates = Pedestrian.getCoordinates();
      int spawn = (int) Math.random() * pedestrian_coordinates.length;
      for (int i = 1; i <= 26; i++){ //Generates pedestrians
         int[] randomCoordinates = pedestrian_coordinates[spawn];
         new Pedestrian("Pedestrian " + i, Math.random() * 0.15 + 0.1, randomCoordinates[0] + (int) (Math.random() * 10 + 1), randomCoordinates[1] + (int) (Math.random() * 10 + 1), 7 + i);
         //new Pedestrian("Pedestrian " + i, (int) (Math.random() * getMainFrameWidth()), (int) (Math.random() * getMainFrameHeight()), (int) (Math.random() * getMainFrameHeight()), 2 + i);
           
         if (spawn == pedestrian_coordinates.length - 1) spawn = 0;
         else spawn++;
      }
   }
   
   /**
   * Returns the visibility of the popup window.
   * @return visiblity of popup
   */
   public static boolean popupIsVisible(){
      return popup.isVisible();
   }
   
   /**
   * Checks which button is pressed upon a mouse click and runs the appropriate code
   * @param mouseX The mouse's x-position
   * @param mouseY The mouse's y-position
   */
   public static void examineButtons(int mouseX, int mouseY){
      ArrayList<TrafficController> traffList = LayerManager.getTrafficList();
      UIButton prioritized = LayerManager.prioritizeButtons();
      if (prioritized != null) System.out.println("Prioritized Button: " + prioritized.getIdentifier());
      if (prioritized == upgradeButton && upgradeButton != null){
         for (int i = 0; i < traffList.size(); i++){
            if (traffList.get(i).getChosenStatus()){
               if ((traffList.get(i) instanceof TrafficOfficer && SimulationData.budget() >= 180000)
                  || (traffList.get(i) instanceof TrafficLights && SimulationData.budget() >= 20000)){
                  undoList.add(traffList.get(i).getIdentifier());
                  traffList.get(i).upgrade();
                  updateUI("individual");
               }
               break;
            }
         }
      }
         
      else if (prioritized == configButton && configButton != null){
         config();
      }
         
      else if (prioritized == goButton && goButton != null) {
         for (int i = 0; i < traffList.size(); i++){
            traffList.get(i).setChosenStatus(false);
         }
         SimulationData.setSimStatus("RUNNING");
         updateUI("simulation");
         populateLevel2();
      }
      
      else if (prioritized == proceedButton && proceedButton != null){
         for (TrafficController t : traffList){
            if (t instanceof AITrafficLights == false){
               SimulationData.stage++;
               ProgramDriver.setProgramState("stage screen");
               ProgramDriver.setup();
               return;
            }
         }
         ProgramDriver.setProgramState("end screen");
         ProgramDriver.setup();
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
      
      else if (prioritized == null) {
         for (int i = 0; i < traffList.size(); i++){
            if (traffList.get(i).onMouseClick(mouseX, mouseY)){
               return;
            }
            else {
               traffList.get(i).setChosenStatus(false);
            }
         }
         updateUI("default");
      }
   }
   
   /**
   * Finds the latest command to be undone in the undoList, and undoes that command.
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
}