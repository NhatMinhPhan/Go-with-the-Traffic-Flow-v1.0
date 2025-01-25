import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
* This renders graphics according to layer number.
* @version 0.3
* @author Fabulosi Labs
*/

public class LayerManager{
   /**
   * List of UI Objects to be rendered
   */
   private static ArrayList<UIObject> UIList = new ArrayList<>();
   /**
   * List of Animate Objects to be rendered
   */
   private static ArrayList<AnimateObject> animateList = new ArrayList<>();
   /**
   * List of Static Objects to be rendered
   */
   private static ArrayList<StaticObject> staticList = new ArrayList<>();
   /**
   * List of Traffic Controllers to be rendered
   */
   private static ArrayList<TrafficController> trafficList = new ArrayList<>();
   /**
   * List of Layered Objects to be rendered
   */
   private static ArrayList<LayeredObject> orderedList = new ArrayList<>();
   
   /**
   * Background Image to be rendered
   */
   private static BackgroundImg bgImg = new BackgroundImg("Placeholder", Color.white);
   /**
   * Camera to be rendered
   */
   private static Camera camera;
   /**
   * Indicates whether or not camera is being rendered
   */
   private static boolean cameraOn = false;
   
   /**
   * Sets the Background Image bgImg to a certain BackgroundImg object.
   * @param obj BackgroundImg object that bgImg is to be set to
   */
   public static void setBGImg(BackgroundImg obj){
      bgImg = obj;
   }
   
   /**
   * Adds a UI Object to uiList.
   * @param obj UI Object to be added
   */
   public static void add(UIObject obj){
      UIList.add(obj);
   }
   
   /**
   * Adds an Animate Object to animateList.
   * @param obj Animate Object to be added
   */
   public static void add(AnimateObject obj){
      animateList.add(obj);
   }
   
   /**
   * Adds a Static Object to staticList.
   * @param obj Static object to be added
   */
   public static void add(StaticObject obj){
      staticList.add(obj);
   }
   
   /**
   * Adds a Traffic Controller to trafficList.
   * @param obj Traffic Controller object to be added
   */
   public static void add(TrafficController obj){
      trafficList.add(obj);
   }
   
   /**
   * Removes an Animate Object from animateList.
   * @param obj Animate Object to be removed
   */
   public static void remove(AnimateObject obj){
      if (camera != null && camera.getTarget() == obj){
         setCameraActivity(false);
         setCamera(null);
      }
      
      /*if (camera != null && camera.getTarget() == obj){
         if (animateList.size() - 1 > 0){
            setCamera(new Camera(animateList.get((int) (Math.random() * animateList.size())), 112, 90));
         }
         else{
            setCameraActivity(false);
            setCamera(null);
         }
      }*/
      
      animateList.remove(obj);
      orderedList.remove(obj);
   }
   
   /**
   * Removes a TrafficController Object from trafficList.
   * @param obj TrafficController Object to be removed
   */
   public static void remove(TrafficController obj){
      trafficList.remove(obj);
      orderedList.remove(obj);
   }
   
   /**
   * Removes a UI Object from UIList.
   * @param obj UI Object to be removed
   */
   public static void remove(UIObject obj){
      UIList.remove(obj);
      orderedList.remove(obj);
   }
   
   /**
   * Removes a Static Object from staticList.
   * @param obj Static Object to be removed
   */
    public static void remove(StaticObject obj){
      staticList.remove(obj);
      orderedList.remove(obj);
   }
   
   /**
   * Sorts all Layered Objects for rendering.
   */
   public static void sortLayeredObjects(){
      reset();
      //Ignore BackgroundImg
   
      //Simulation Layers
      ArrayList<SimulationObject> list = new ArrayList<>();
      for (StaticObject obj : staticList) list.add(obj);
      for (AnimateObject obj : animateList) list.add(obj);
      for (TrafficController obj : trafficList) list.add(obj);
      int[] simRanks = new int[list.size()];
      for (int i = 0; i < simRanks.length; i++) simRanks[i] = list.get(i).getLocalLayer();
      int[] simOrder = getDecreasingOrder(simRanks);
      for (int index : simOrder) orderedList.add(list.get(index));
   
      //UI Elements are rendered last, so appear last in orderedList.
      int[] ranks = new int[UIList.size()];
      for (int i = 0; i < ranks.length; i++) ranks[i] = UIList.get(i).getLocalLayer();
      int[] order = getDecreasingOrder(ranks); //2+ is the backmost, 1 is the frontmost.
      for (int index : order) orderedList.add(UIList.get(index));
      
      autoLocalLayer(list);
   }
   
   /**
   * Gets a list of buttons that the mouse is hovering on.
   * @return A list of buttons that the mouse is hovering on
   */
   public static ArrayList<UIButton> getHoveredButtons(){
      ArrayList<UIButton> output = new ArrayList<>();
      for (LayeredObject obj : orderedList){
         if (obj instanceof UIButton){
            UIButton button = (UIButton) obj;
            if (button.mouseOnButton()) output.add(button);
            else button.setHovered(false);
         }
      }
      return output;
   }
   
   /**
   * Resets every list in the LayerManager class (UIList, animateList, staticList, trafficList, and orderedList) as well as the bgImg (BackgroundImg).
   */
   public static void resetEverything(){
      UIList = new ArrayList<>();
      animateList = new ArrayList<>();
      staticList = new ArrayList<>();
      trafficList = new ArrayList<>();
      orderedList = new ArrayList<>();
      bgImg = null;
   }
   
   /**
   * Analyzes overlapping and non-overlapping buttons and returns the prioritized button, which has the greater local layer and is being hovered over by the mouse.
   * @return The UIButton that is currently prioritized
   */
   public static UIButton prioritizeButtons(){
      ArrayList<UIButton> buttons = getHoveredButtons();
      if (buttons.size() <= 0) return null;
      UIButton prioritized = buttons.get(0);
      if (buttons.size() > 1){
         for (int i = 1; i < buttons.size(); i++){
            if (buttons.get(i).getLocalLayer() > prioritized.getLocalLayer())
            {
               prioritized.setHovered(false);
               prioritized = buttons.get(i);
            }
         }
      }
      
      prioritized.setHovered(true);
      return prioritized;
   }
   
   /**
   * Checks trueY() of each simulation object and set the local layer based on their trueY()
   * @param list Array List of Simulation Objects
   */
   public static void autoLocalLayer(ArrayList<SimulationObject> list){
      int[] trueY = new int[list.size()];
      for (int i = 0; i < list.size(); i++) trueY[i] = list.get(i).trueY();
      
      int[] order = getDecreasingOrder(trueY);
      
      for (int i = 0; i < order.length; i++){
         list.get(list.size() - 1 - i).setLocalLayer(i + 1);
      }
   }
   
   /**
   * Insertion Sort in increasing order and return the order using indices of the original array (arr)
   * @param arr Original array / Input array
   * @return An integer array showing the order using indices of the original array (arr).
   */
   public static int[] getIncreasingOrder(int[] arr){
      int[] output = new int[arr.length];
      
      for (int i = 0; i < output.length; i++) output[i] = i;
      
      for (int i = 1; i < arr.length; i++){
         int j = i - 1;
         int temp = arr[i];
         int outTemp = output[i];
         while (j >= 0 && arr[j] > temp){
            arr[j + 1] = arr[j];
            output[j + 1] = output[j];
            j--;
         }
         arr[j + 1] = temp;
         output[j + 1] = outTemp;
      }
      
      return output;
   }
   
   /**
   * Insertion Sort in decreasing order and return the order using indices of the original array (arr)
   * @param arr Original array / Input array
   * @return An integer array showing the order using indices of the original array (arr).
   */
   public static int[] getDecreasingOrder(int[] arr){
      int[] output = new int[arr.length];
      
      for (int i = 0; i < output.length; i++) output[i] = i;
      
      for (int i = 1; i < output.length; i++){
         int j = i - 1;
         int temp = arr[i];
         int outTemp = output[i];
         while (j >= 0 && arr[j] < temp){
            arr[j + 1] = arr[j];
            output[j + 1] = output[j];
            j--;
         }
         arr[j + 1] = temp;
         output[j + 1] = outTemp;
      }
      
      return output;
   }
   
   /**
   * Resets order of rendering every time
   */
   public static void reset(){
      orderedList = new ArrayList<>();
   }
   
   /**
   * Resets rendering and simulation
   */
   public static void resetSimulation(){
      animateList = new ArrayList<>();
      orderedList = new ArrayList<>();
   }
   
   /**
   * Renders and draws object on the mainFrame.
   * @param g Graphics used to draw and render objects
   */
   public static void render(Graphics g){
      bgImg.draw(g);
      for (int i = 0; i < orderedList.size(); i++){
         if (cameraOn && orderedList.get(i) instanceof UIObject && ((i - 1 >= 0 && !(orderedList.get(i - 1) instanceof UIObject)) || (orderedList.get(0) instanceof UIObject && i == 0) )) camera.draw(g);
         orderedList.get(i).draw(g);
      }
   }
   
   /**
   * Sets whether or not the camera is used.
   * @param val Boolean value which if true activates the use of the camera or deactivates it if false
   */
   public static void setCameraActivity(boolean val){
      cameraOn = val;
   }
   
   /**
   * Set camera to a Camera object.
   * @param obj A Camera object
   */
   public static void setCamera(Camera obj){
      camera = obj;
   }
   
   /**
   * Returns list of UI Objects to be rendered.
   * @return List of UI Objects to be rendered
   */
   public static ArrayList<UIObject> getUIList(){
      return UIList;
   }
   
   /**
   * Returns list of Animate Objects to be rendered.
   * @return List of Animate Objects to be rendered
   */
   public static ArrayList<AnimateObject> getAnimateList(){
      return animateList;
   }
   
   /**
   * Returns list of Static Objects to be rendered.
   * @return List of Static Objects to be rendered
   */
   public static ArrayList<StaticObject> getStaticList(){
      return staticList;
   }
   
   /**
   * Returns list of Traffic Controllers to be rendered.
   * @return List of Traffic Controllers to be rendered
   */
   public static ArrayList<TrafficController> getTrafficList(){
      return trafficList;
   }
   
   /**
   * Returns list of Layered Objects to be rendered.
   * @return List of Layered Objects to be rendered
   */
   public static ArrayList<LayeredObject> getOrderedList(){
      return orderedList;
   }
}