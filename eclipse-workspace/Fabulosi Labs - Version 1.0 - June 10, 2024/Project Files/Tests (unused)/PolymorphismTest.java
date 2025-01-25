//This file is written by Minh Phan
//Description: This is a polymorphism test and will not be included in the main program.
//Version 0.1

import java.util.*;

public class PolymorphismTest {
   private ArrayList<PolymorphismTest> test = new ArrayList<>();
   
   public void say(){
      System.out.println("I am " + this.getClass().getSimpleName());
   }
   
   public void add(PolymorphismTest obj){
      test.add(obj);
   }
   
   public ArrayList<PolymorphismTest> getTest(){
      return test;
   }
   
   public static void main (String[] args){
      PolymorphismTest t = new PolymorphismTest();
      t.add(new PolymorphismTest());
      t.add(new SmallTest());
      t.add(new SmallTest());
      t.add(new PolymorphismTest());
      for (PolymorphismTest obj : t.getTest()) obj.say();
   }
}

class SmallTest extends PolymorphismTest {
   public void say(){
      super.say();
      System.out.println("Yes. It is " + (this instanceof PolymorphismTest));
   }
}