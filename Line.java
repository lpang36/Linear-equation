import java.util.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Line
{
    private String equation;
    private double a;
    private double b;
    private double c;

    public Line (String s)
    {
        equation = s;
        //splits string into left and right side
        String ls = s.substring (0, s.indexOf ("="));
        String rs = s.substring (s.indexOf ("=") + 1);
        Line m = this.readLine (ls);
        Line n = this.readLine (rs);
        //line is the difference of two sides
        a = m.getA () - n.getA ();
        b = m.getB () - n.getB ();
        c = m.getC () - n.getC ();
    }


    public Line (double x, double y, double z)
    {
        a = x;
        b = y;
        c = z;
        equation = this.toString ();
    }

    //accessor methods
    public double getA ()
    {
        return a;
    }


    public double getB ()
    {
        return b;
    }


    public double getC ()
    {
        return c;
    }

    public double xIntercept ()
    {
        return -c / a;
    }


    public double yIntercept ()
    {
        return -c / b;
    }


    public double slope ()
    {
        return -a / b;
    }


    public boolean isVertical ()
    {
        return b == 0;
    }


    public boolean isHorizontal ()
    {
        return a == 0;
    }

    public Point intersect (Line l)
    {
        double x,y;
        //calculate intersection point
        if (!(l.isVertical()||isVertical()))
        {
                x = (l.yIntercept()-yIntercept())/(slope()-l.slope());
                y = slope()*x+yIntercept();
        }
        else if (l.isVertical())
        {
            x = l.xIntercept();
            y = slope()*x+yIntercept();
        }
        else 
        {
            x = xIntercept();
            y = l.slope()*x+l.yIntercept();
        }
        Point p = new Point (x,y);
        return p;
    }
         
    public void draw (Graphics g, int xL, int xH, int yL, int yH)
    {
        int mlPixel, mrPixel, mlXs, mrXs; 
        if (!isVertical())
         {
             //calculate end points and connect them
               double mlValue = yIntercept()+slope()*xL;
               double mhValue = yIntercept()+slope()*xH;
               mlPixel = 400-(int)((mlValue-yL)/(yH-yL)*400);
               mrPixel = 400-(int)((mhValue-yL)/(yH-yL)*400);
               mlXs=0;
               mrXs=400;
         }       
         else {
             //calculate end points and connect them
             int mValue = (int)((xIntercept()-xL)/(xH-xL)*400);
             mlPixel=0;
             mrPixel=400;
             mlXs=mValue;
             mrXs=mValue;
         }
         //draw line
         g.drawLine (mlXs,mlPixel,mrXs,mrPixel);
    }

    public String toString ()
    {
        String output = "";
        //x term
        if (a == 0)
        {
        }
        else if (a == 1)
            output = "x";
        else if (a == -1)
            output += "-x";
        else
            output = Math.round(a) + "x";
        //y term
        if (b == 0)
        {
        }
        else if (b == 1&&a == 0)
            output += "y";
        else if (b == 1)
            output += "+y";
        else if (b == -1)
            output += "-y";
        else if (b < 0)
            output = output + "-" + Math.round(Math.abs (b)) + "y";
        else if (a == 0)
            output = output + Math.round(b) + "y";
        else
            output = output + "+" + Math.round(b) + "y";
        //constant term
        if (c == 0 && a == 0 && b == 0)
            output = "0";
        else if (c == 0)
        {
        }
        else if (c < 0)
            output = output + "-" + Math.round(Math.abs (c));
        else if (a == 0&&b == 0)
            output = output + Math.round(c);
        else
            output = output + "+" + Math.round(c);
        //right side
        output += "=0";
        return output;        //return a+"x "+b+"y "+c;
    }
    
    public Line readLine (String b)
    {
        double x = 0;
        double y = 0;
        double z = 0;
        String a = "";
        //eliminate spaces
        for (int i = 0; i < b.length (); i++)
        {
            if (b.charAt(i)!=' ') a+=b.charAt(i);
        }
        //list of terms in the equation
        ArrayList < String > newEq = new ArrayList < String > ();
        String term = "";
        //initialize string with a + sign if positive
        if (a.charAt (0) != '-')
            a = "+" + a;
        for (int i = 0 ; i < a.length () ; i++)
        {
            //split equation into a new term whenever there is a + or - sign
            if (a.charAt (i) != '+' && a.charAt (i) != '-')
            {
                term += a.charAt (i);
            }
            else
            {
                newEq.add (term);
                term = "";
                term += a.charAt (i);
            }
        }
        newEq.add (term);
        for (int i = 1 /*<--not an error*/ ; i < newEq.size () ; i++)
        {
            //if the term is x
            if (newEq.get (i).indexOf ('x') != -1)
            {
                //add term coefficient to x total
                int sign = (newEq.get (i).charAt (0) == '+')?1:0;
                if (newEq.get(i).length()==2) x+=sign*2-1;
                else
                    x += (sign*2-1)*(Double.parseDouble (newEq.get (i).substring (1, newEq.get (i).length () - 1)));
            }
            //if the term is y
            else if (newEq.get (i).indexOf ('y') != -1)
            {
                //add term coefficient to y total
                int sign = (newEq.get (i).charAt (0) == '+')?1:0;
                if (newEq.get(i).length()==2) y+=sign*2-1;
                else
                    y += (sign*2-1)*(Double.parseDouble (newEq.get (i).substring (1, newEq.get (i).length () - 1)));
            }
            //if the term is a constant
            else
            {
                //add constant to total
                int sign = (newEq.get (i).charAt (0) == '+')?1:0;
                z += (sign*2-1)*(Double.parseDouble (newEq.get (i).substring (1)));
            }
        }
        Line l = new Line (x, y, z); //create new line with a, b, c initialized
        return l;
    }
}
