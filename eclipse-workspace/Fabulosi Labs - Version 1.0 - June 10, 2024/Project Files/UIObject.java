import java.util.*;

/**
* This is a template for UI Objects.
* @version 0.3
* @author Fabulosi Labs
*/

public abstract class UIObject extends LayeredObject {
   /**
   * The local layer within the UI domain
   */
   private int localLayer;
   
   /**
   * Class constructor.
   * @param i Identifier
   * @param x X-position
   * @param y Y-position
   * @param l Local layer
   */
   public UIObject(String i, int x, int y, int l){
      super(i, x, y);
      localLayer = l;
   }
   
   /**
   * Returns the object's local layer within the UI domain.
   * @return The object's local layer within the UI domain
   */
   public int getLocalLayer(){
      return localLayer;
   }
}