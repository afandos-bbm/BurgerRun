package alejandrofan2.BurgerRun.shapes;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Yalchin Aliyev
 * @version 0.01 28.02.2016
 *
 */
public class ShapeContainer implements Iterable{
    
    // properties
    private ArrayList<Shape> shapes;
    
    // constructors
    public ShapeContainer() {
        
        shapes = new ArrayList<Shape>();
    }
    
    // methods
    public void add( Shape s) {
        
        shapes.add(s);
    }
    
    public double getArea() {
        
        double area;
        
        area = 0;
        for (int i = 0; i < shapes.size(); i++)
            area = area + shapes.get(i).getArea();
        return area;
    }
    
    public String toString() {
        
        String str;
        
        str = "";
        for (Shape shape : shapes) {
            
            str = str + shape.toString() + "\n";
        }
        return str;
    }
    
    public Shape contains( int x, int y) {
        
        for (Shape shape : shapes) {
            if ( ((Selectable) shape).contains( x, y) != null)
                return shape;
        }
        return null;
    }
    
    public void remove() {
        
        for ( int i = 0; i < shapes.size(); i++) {
            if ( ( ((Selectable) shapes.get(i)).getSelected())) {
                shapes.remove(i);
                i--;
            }
        }
    }
    
    public int size() {
    	
    	return shapes.size();
    }
    
    public int selectAllAt( int x, int y) {
    	
    	int containers;
    	
    	containers = 0;
    	for (Shape shape : shapes) {
            if ( ((Selectable) shape).contains( x, y) != null) {
            	((Selectable) shape).setSelected(true);
            	containers++;
            } 
        }
    	return containers;
    	
    }

	@Override
	public Iterator iterator() {

		return shapes.iterator();
	}
	
	public Shape getShape( int i) {
		return shapes.get(i);
	}
}
