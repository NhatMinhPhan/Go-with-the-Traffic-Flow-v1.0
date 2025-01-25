import java.util.*;

/**
* This is a file for managing simulation data.
* @version 1.0
* @author Fabulosi Labs
*/

public class SimulationData {
   /**
   * Satisfaction value
   */
   private static int satisfaction = 100;
   /**
   * Budget remaining for the simulator
   */
   private static int budget;
   /**
   * Progress remaining, 100% when all the Traffic Controllers in the simulator are AI Traffic Lights.
   */
   private static int workProgress;
   /**
   * Time that has elasped since the simulation begins.
   */
   private static int timeElapsed = -1;
   /**
   * The current status of the simulation: RUNNING, ENDED (failure), COMPLETED (win), IN WAITING
   */
   private static String simStatus = "IN WAITING";
   /**
   * The current stage of the simulator
   */
   public static int stage = 1;
   
   /**
   * Stores memory of Traffic Controllers from previous stages, used by Level 2.
   */
   public static TrafficController[] list2 = new TrafficController[2];
   /**
   * Stores the last time frame for keeping track of time
   */
   private static long lastTime = System.currentTimeMillis();
   
   /**
   * Resets the satisfaction level.
   */
   public static void resetSatisfaction(){
      satisfaction = 100;
   }
   
   /**
   * Initializes the budget to val
   * @param val a certain value
   */
   public static void initializeBudget(int val){
      budget = val;
   }
   
   /**
   * Changes the budget by val
   * @param val a certain value
   */
   public static void changeBudget(int val){
      if (budget + val >= 0) budget += val;
      else budget = 0;
   }
   
   /**
   * Changes the satisfaction level by val
   * @param val a certain value
   */
   public static void changeSatisfaction (int val){
      if (val < 0 && satisfaction - val >= 0) satisfaction -= val;
      else if (val > 0 && satisfaction + val <= 100) satisfaction += val;
   }
   
   /**
   * Resets work progress.
   */
   public static void resetWorkProgress(){
      workProgress = 0;
   }
   
   /**
   * Updates work progress based on the types of all Traffic Controllers.
   */
   public static void checkProgress(){
      ArrayList<TrafficController> list = LayerManager.getTrafficList();
      double progress, total;
      progress = 0.0;
      total = list.size();
      for (TrafficController obj : list){
         if (obj instanceof TrafficOfficer){
            progress += (3.0 * total / 3.0) / (3.0 * total);
         }
         else if (obj instanceof AITrafficLights){
            progress += (3.0 * total / 3.0 * 3.0) / (3.0 * total);
         }
         else if (obj instanceof TrafficLights){
            progress += (3.0 * total / 3.0 * 2.0) / (3.0 * total);
         }
      }
      workProgress = (int) Math.round(progress / total * 100.0);
   }
   
   /**
   * Increases work progress by val.
   * @param val a certain value
   */
   public static void increaseProgress(int val){
      if (val < 0) return;
      if (workProgress + val <= 100) workProgress += val;
      else workProgress = 100;
   }
   
   /**
   * Resets the time elapsed.
   */
   public static void resetTime(){
      timeElapsed = -1;
   }
   
   /**
   * Updates the time based on thisTime
   * @param thisTime the current time frame
   */
   public static void updateTime(long thisTime){
      if (!simStatus.equals("RUNNING")){
         return;
      }
      
      if (thisTime - lastTime >= 1000){
         lastTime = thisTime;
         timeElapsed++;
         
         if (timeElapsed >= 60){
            LayerManager.reset();
            if (satisfaction >= 50){
               simStatus = "COMPLETED";
               ProgramDriver.updateUI("win");
               return;
            }
            else {
               simStatus = "ENDED";
               ProgramDriver.updateUI("loss");
               return;
            }
         }
         
         ProgramDriver.updateUI("simulation");
      }
      else if (timeElapsed < 0){
         timeElapsed = 0;
         ProgramDriver.updateUI("simulation");
      }
   }
   
   /**
   * Sets the status of the simulation.
   * @param val New status of the simulation
   */
   public static void setSimStatus(String val){
      try {
         val = val.toUpperCase();
         if (!val.equals("IN WAITING") && !val.equals("RUNNING") && !val.equals("ENDED") && !val.equals("COMPLETED")) throw new Exception();
         else simStatus = val;
      }
      catch (Exception e){
         System.out.println("Error: Invalid value for simulation status");
         e.printStackTrace();
      }
   }
   
   /**
   * Returns the satisfaction level
   * @return current satisfaction level
   */
   public static int satisfaction(){
      return satisfaction;
   }
   
   /**
   * Returns the current remaining budget
   * @return current remaining budget
   */
   public static int budget(){
      return budget;
   }
   
   /**
   * Returns the current work progress
   * @return current work progress
   */
   public static int workProgress(){
      return workProgress;
   }
   
   /**
   * Returns the time elapsed
   * @return current time elapsed
   */
   public static int timeElapsed(){
      return timeElapsed;
   }
   
   /**
   * Returns the current status of the simulator.
   * @return current status of the simulator
   */
   public static String simStatus(){
      return simStatus;
   }
}