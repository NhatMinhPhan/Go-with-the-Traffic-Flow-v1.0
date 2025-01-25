//This file is written by Minh Phan
//Description: This is a GUI test and will not be included in the main program.
//Version 0.1

import java.awt.*;
import javax.swing.*;
import java.util.*;

import java.awt.event.*;
import java.io.*;

public class WindowTest implements ActionListener {
   JFrame frame = new JFrame("This is a test. It won't be used in the final version.");
   //JFrame popWindow = new JFrame("Example PopWindow");
   JButton popup = new JButton("Popup");
   boolean poppedup = false;
   File file;
   private Scanner scanner;
   
   private int lineNum = 0;
   
   public WindowTest (){
      /*
      frame.setLayout(new FlowLayout());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(popup);
      frame.setSize(500, 500);
      popup.addActionListener(this);
      frame.setVisible(true);
      File temp = new File("WindowTest.java");
      String tempDir = temp.getAbsolutePath();
      tempDir = tempDir.substring(0, tempDir.indexOf("Tests") - 1);
      file = new File(tempDir + "/Level 1 Script.txt");
      try {
         scanner = new Scanner(file);
      }
      catch (IOException e){
         System.out.println("READER FILE NOT FOUND!");
      }
      //popWindow.setSize(300, 300);
      */
      
      frame.setSize(1164, 991);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new Drawing(1164, 991));
      frame.setVisible(true);
   }
   
   public void actionPerformed (ActionEvent e){
      //poppedup = !poppedup;
      //popWindow.setVisible(poppedup);
      reader();
      
   }
   
   public static void main (String[] args){
      new WindowTest();
   }
   
   public void reader(){
      if (scanner.hasNextLine()){
         System.out.print(lineNum + "  ");
         displayLine(scanner.nextLine());
      }
      else return;
      
      //switch (lineNum){
         
      //}
      
      lineNum++;
   }
   
   private void displayLine(String line){
      if (line.indexOf("\\n") < 0) System.out.println(line);
      else {
         String l2 = line.substring(0, line.indexOf("\\n"));
         System.out.println(l2);
         displayLine(line.substring(line.indexOf("\\n") + 2));
      }
   }
   
   public class Drawing extends JComponent implements ActionListener {
      private int x, y;
      private javax.swing.Timer timer = new javax.swing.Timer(1000/60, this);
      
      public Drawing(int wWidth, int wHeight){
         x = wWidth / 2;
         y = wHeight / 2;
         System.out.println(wWidth + " " + wHeight);
         timer.start();
      }
      
      public void actionPerformed(ActionEvent e){
         repaint();
         x -= 5;
      }
   
      public void paint (Graphics g){
         g.setColor(Color.black);
         g.setFont(new Font("Monospaced", Font.PLAIN, 37));
         g.drawString("Lorem ipsum dolor sit amet", 37, 38);
         g.drawString("Lorem ipsum dolor sit amet", -25, 60);
         
         g.fillOval(x - 25, y - 25, 50, 50);
      }
   }
}