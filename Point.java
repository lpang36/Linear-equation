import java.util.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Point
{
    private double x;
    private double y;
    
    public Point (double a, double b)
    {
        x = a;
        y = b;
    }
    
    //accessor methods
    public double getX () {return x;}
    public double getY () {return y;}
    
    public String toString ()
    {
        //convert point to string with 2 digits accuracy
        return "("+(int)(x*100)/100.00+","+(int)(y*100)/100.00+")";
    }
}
