import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
* This is a template for a pop-up window.
* @version 1.0
* @author Fabulosi Labs
*/

public class PopUpWindow extends MouseAdapter implements ActionListener {
   /**
   * Pop-up window
   */
   private JFrame popup;
   
   /**
   * Indicates whether the Traffic Controller the popup is referring to uses AI or not.
   */
   private boolean useAI;
   
   /**
   * Panels to be displayed on popup
   */
   private JPanel formPanel, tablePanel;
   /**
   * Text field used for non AI window
   */
   private JTextField non1, non2;
   /**
   * Text field used for AI window
   */
   private JTextField t1, t2, t3, t4, t5, t6, t7;
   /**
   * The Traffic Controller that the popup window is configuring
   */
   private TrafficController controller;
   
   /**
   * Class constructor.
   */
   public PopUpWindow(){
      popup = new JFrame("Popup Window");
      //popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      popup.setLayout(new GridLayout(2, 1));
      formPanel = new JPanel();
      tablePanel = new JPanel();
      
      non1 = new JTextField(20);
      non2 = new JTextField(20);
      t1 = new JTextField(20);
      t2 = new JTextField(20);
      t3 = new JTextField(20);
      t4 = new JTextField(20);
      t5 = new JTextField(20);
      t6 = new JTextField(20);
      t7 = new JTextField(20);
      
      non1.addActionListener(this);
      non2.addActionListener(this);
      t1.addActionListener(this);
      t2.addActionListener(this);
      t3.addActionListener(this);
      t4.addActionListener(this);
      t5.addActionListener(this);
      t6.addActionListener(this);
      t7.addActionListener(this);
      
      popup.add(formPanel);
      //popup.add(tablePanel);
      
      
   }
   
   /**
   * Sets controller to a certain Traffic Controller.
   * @param controller a certain Traffic Controller
   */
   public void setController (TrafficController controller){
      this.controller = controller;
   }
   
   /**
   * Runs when an action is detected.
   * @param e Action Event
   */
   public void actionPerformed(ActionEvent e){
      if (e.getSource() == non1){
         TrafficLights t = (TrafficLights) controller;
         t.setPedestrianWait((int) Math.round(Double.parseDouble(non1.getText().trim()) * 1000));
      }
      else if (e.getSource() == non2){
         TrafficLights t = (TrafficLights) controller;
         t.setPedestrianWait((int) Math.round(Double.parseDouble(non2.getText().trim()) * 1000));
      }
   }
   
   /**
   * Sets up the popup window according to whether it uses AI or not.
   * @param useAI Whether the Traffic Controller the popup window configures uses AI or not
   */
   public void setWindow(boolean useAI){
      popup = new JFrame("Popup Window");
      popup.setLayout(new GridLayout(1, 1));
      formPanel = new JPanel();
      tablePanel = new JPanel();
      popup.add(formPanel);
      //popup.add(tablePanel);
      
      popup.addWindowListener(new WindowAdapter(){
         @Override
         public void windowClosing (WindowEvent e){
            if (!useAI){
               TrafficLights t = (TrafficLights) controller;
               if (non1.getText() != null && non1.getText().trim().length() > 0) t.setPedestrianWait((int) Math.round(Double.parseDouble(non1.getText().trim()) * 1000));
               if (non2.getText() != null && non2.getText().trim().length() > 0) t.setVehicleWait((int) Math.round(Double.parseDouble(non2.getText().trim()) * 1000));
            }
            else if (useAI){
               AITrafficLights t = (AITrafficLights) controller;
               if (t1.getText() != null && t1.getText().trim().length() > 0){
                  t.setPedestrianWait((int) Math.round(Double.parseDouble(t1.getText().trim()) * 1000));
               }
               if (t2.getText() != null && t2.getText().trim().length() > 0){
                  t.setVehicleWait((int) Math.round(Double.parseDouble(t2.getText().trim()) * 1000));
               }
               if (t3.getText() != null && t3.getText().trim().length() > 0){
                  t.countMax = (int) Math.round(Double.parseDouble(t3.getText().trim()));
               }
               if (t4.getText() != null && t4.getText().trim().length() > 0){
                  t.carChange = (int) Math.round(Double.parseDouble(t4.getText().trim()));
               }
               if (t5.getText() != null && t5.getText().trim().length() > 0){
                  t.expectedCarChange = (int) Math.round(Double.parseDouble(t5.getText().trim()));
               }
               if (t6.getText() != null && t6.getText().trim().length() > 0){
                  t.pedChange = (int) Math.round(Double.parseDouble(t6.getText().trim()));
               }
               if (t7.getText() != null && t7.getText().trim().length() > 0){
                  t.expectedPedChange = (int) Math.round(Double.parseDouble(t7.getText().trim()));
               }
            }
         }
      });
      
      this.useAI = useAI;
      if (!useAI){
         popup.setSize(500, 140);
         popup.setTitle("Popup Window for Traffic Lights");
         formPanel.setLayout(new GridLayout(2, 2));
         tablePanel.setLayout(new GridLayout(4, 3));
         
         formPanel.add(new JLabel("Waiting time for pedestrians (seconds):"));
         formPanel.add(non1);
         formPanel.add(new JLabel("Waiting time for vehicles (seconds):"));
         formPanel.add(non2);
         
         tablePanel.add(new JLabel("Time (seconds)"));
         tablePanel.add(new JLabel("Avg. # of Pedestrians"));
         tablePanel.add(new JLabel("Avg. # of Vehicles"));
         int minTime = 0, maxTime = 0;
         for (int i = 0; i < 3; i++){
            maxTime += 30;
            tablePanel.add(new JLabel(minTime + " - " + maxTime));
            minTime = maxTime + 1;
            tablePanel.add(new JLabel((int) (Math.random() * 9 + 2) + ""));
            tablePanel.add(new JLabel((int) (Math.random() * 5 + 8) + ""));
         }
      }
      else {
         popup.setSize(540, 300);
         popup.setTitle("Popup Window for AI Traffic Lights");
         formPanel.setLayout(new GridLayout(10, 1));
         tablePanel.setLayout(new GridLayout(4, 3));
         
         formPanel.add(new JLabel("At initialization:"));
         
         JPanel hPanel1 = new JPanel();
         hPanel1.setLayout(new FlowLayout());
         hPanel1.add(new JLabel("Waiting time for pedestrians (seconds):"));
         hPanel1.add(t1);
         
         JPanel hPanel2 = new JPanel();
         hPanel2.setLayout(new FlowLayout());
         hPanel2.add(new JLabel("Waiting time for vehicles (seconds):"));
         hPanel2.add(t2);
         
         formPanel.add(hPanel1);
         formPanel.add(hPanel2);
         formPanel.add(new JLabel(""));
         
         formPanel.add(new JLabel("Adjustments:"));
         
         JPanel hPanel3 = new JPanel();
         hPanel3.setLayout(new FlowLayout());
         hPanel3.add(new JLabel("Within "));
         hPanel3.add(t3);
         hPanel3.add(new JLabel(" traffic flows (enter a number):"));
         
         JPanel hPanel4 = new JPanel();
         hPanel4.setLayout(new FlowLayout());
         hPanel4.add(new JLabel("If cars in/decrease by more than/equal to "));
         hPanel4.add(t4);
         hPanel4.add(new JLabel(","));
         
         JPanel hPanel5 = new JPanel();
         hPanel5.setLayout(new FlowLayout());
         hPanel5.add(new JLabel(" in/decrease expected car # by "));
         hPanel5.add(t5);
         hPanel5.add(new JLabel("."));
         
         JPanel hPanel6 = new JPanel();
         hPanel6.setLayout(new FlowLayout());
         hPanel6.add(new JLabel("If pedestrians in/decrease by more than/equal to "));
         hPanel6.add(t6);
         hPanel6.add(new JLabel(","));
         
         JPanel hPanel7 = new JPanel();
         hPanel7.setLayout(new FlowLayout());
         hPanel7.add(new JLabel(" in/decrease expected pedestrian # by "));
         hPanel7.add(t7);
         hPanel7.add(new JLabel("."));
         
         formPanel.add(hPanel3);
         formPanel.add(hPanel4);
         formPanel.add(hPanel5);
         formPanel.add(hPanel6);
         formPanel.add(hPanel7);
         
         tablePanel.add(new JLabel("Time (seconds)"));
         tablePanel.add(new JLabel("Avg. # of Pedestrians"));
         tablePanel.add(new JLabel("Avg. # of Vehicles"));
         int minTime = 0, maxTime = 0;
         for (int i = 0; i < 3; i++){
            maxTime += 30;
            tablePanel.add(new JLabel(minTime + " - " + maxTime));
            minTime = maxTime + 1;
            tablePanel.add(new JLabel((int) (Math.random() * 9 + 2) + ""));
            tablePanel.add(new JLabel((int) (Math.random() * 5 + 8) + ""));
         }
      }
      popup.setVisible(true);
   }
   
   /**
   * Returns the visibility of the popup.
   * @return popup's visibility
   */
   public boolean isVisible(){
      return popup.isVisible();
   }
}