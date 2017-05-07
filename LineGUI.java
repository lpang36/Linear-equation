import java.util.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class LineGUI extends JFrame
{
    private JTextField eqField = new JTextField (10);
    private JTextField intersectField = new JTextField (10);
    private DrawArea display = new DrawArea (400,400);
    private JPanel stats = new JPanel();
    private JPanel content = new JPanel();
    private JPanel north = new JPanel();
    private static int xL;
    private static int xH;
    private static int yL;
    private static int yH;
    private ArrayList<Line> list = new ArrayList<Line> ();
    JLabel xIntercept = new JLabel ();
    JLabel yIntercept = new JLabel ();
    JLabel slope = new JLabel ();
    JLabel hOrV = new JLabel ();
    JLabel intersect = new JLabel ();
    Line m = new Line (0,1,yH+100);
    Point q = new Point (0,xH+100);

    public LineGUI ()
    {
        // 1... Create/initialize buttons
        JButton graphBtn = new JButton ("Graph");
        graphBtn.addActionListener (new GraphBtnListener ()); // Connect button to listener class
        JButton resetBtn = new JButton ("Reset");
        resetBtn.addActionListener (new ResetBtnListener ());
        JButton intersectBtn = new JButton ("Find intersection");
        intersectBtn.addActionListener (new IntersectBtnListener ());
        JButton settingsBtn = new JButton ("Settings");
        settingsBtn.addActionListener (new SettingsBtnListener ());

        // 2... Create panels, set layout
        content.setLayout (new GridLayout (1,2)); //content panel
        north.setLayout (new ColumnLayout()); //initial entering panel and buttons
        north.setPreferredSize (new Dimension (150,200)); 
        stats.setLayout (new ColumnLayout()); //line facts and intersection panel
        stats.setPreferredSize (new Dimension (250,200));
        JPanel displayPanel = new JPanel (); //contains the draw area
        displayPanel.setLayout (new FlowLayout());
        displayPanel.setPreferredSize (new Dimension(400,400));
        JPanel combine = new JPanel (); //combines north and stats panels
        combine.setLayout (new ColumnLayout());
        combine.setPreferredSize (new Dimension (250,400));

        // 3... Add the components to the input area.
        //add buttons and prompt to north
        north.add (new JLabel ("Enter an equation: ")); // Create, add label
        north.add (eqField);            // Add input field
        north.add (graphBtn);             // Add button
        north.add (resetBtn);
        north.add (settingsBtn);
        //add draw area to display panel
        displayPanel.add (display);
        //add stats and intersect to stats panel
        stats.add (xIntercept);
        stats.add (yIntercept);
        stats.add (slope);
        stats.add (hOrV);
        stats.add (new JLabel ("Enter an equation to find the intersection: "));
        stats.add (intersectField);
        stats.add (intersectBtn);
        stats.add (intersect);
        //combine north and stats panels
        combine.add(north);
        combine.add(stats);
        //add to content pane
        content.add (displayPanel);
        content.add (combine);

        // 4... Set this window's attributes.
        setContentPane (content);
        setSize (800,450);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window.
    }

    //accessor methods
    public void setXL (int j) {xL = j;}

    public void setXH (int j) {xH = j;}

    public void setYL (int j) {yL = j;}

    public void setYH (int j) {yH = j;}

    class GraphBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            String eq = eqField.getText (); // Retrieve values from text fields
            Line l = new Line (eq); //create line
            list.add (l); //add to list of lines
            repaint (); //graph
            //display x intercept
            xIntercept.setText("X-intercept: ");
            if (l.getA()!=0) //checks if x intercept exists
                xIntercept.setText(xIntercept.getText()+String.valueOf((int)(l.xIntercept()*100)/100.00));
            else
                xIntercept.setText(xIntercept.getText()+"does not exist");
            //display y intercept
            yIntercept.setText("Y-intercept: ");
            if (l.getB()!=0) //checks if y intercept exists
                yIntercept.setText(yIntercept.getText()+String.valueOf((int)(l.yIntercept()*100)/100.00));
            else
                yIntercept.setText(yIntercept.getText()+"does not exist");
            //display slope
            slope.setText("Slope: "); 
            if (l.getB()!=0) //checks if slope is infinite
                slope.setText(slope.getText()+String.valueOf((int)(l.slope()*100)/100.00));
            else
                slope.setText(slope.getText()+"infinity");
            //displays if line is horizontal or vertical
            if (l.isVertical()) hOrV.setText("This graph is vertical.");
            if (l.isHorizontal()) hOrV.setText("This graph is horizontal.");
        }
    }

    class ResetBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            list.clear(); //clear all lines from list
            //reset limits
            xL = -10;
            xH = 10;
            yL = -10;
            yH = 10;
            //moves intersect line and point to outside display area
            m = new Line (0,1,yH+100);
            q = new Point (0,xH+100);
            //clears stats panel
            xIntercept.setText("");
            yIntercept.setText("");
            slope.setText("");
            hOrV.setText("");
            intersect.setText("");
            eqField.setText("");
            intersectField.setText("");
            repaint(); //redraws area
        }
    }

    class IntersectBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            Line n = list.get(list.size()-1); //existing line
            String eq = intersectField.getText(); //retrieve string
            m = new Line (eq); //create intersection line
            q = n.intersect(m); //point
            if (m.isVertical()&&n.isVertical()&&m.xIntercept()==n.xIntercept()) //checks if lines are the same
            {
                intersect.setText ("The lines are the same");
                q = new Point (0,xH+100); //moves point to outside display area
            }
            else if (m.slope()==n.slope()&&m.yIntercept()==n.yIntercept()&&!m.isVertical()) //checks if lines are the same
            {
                intersect.setText ("The lines are the same");
                q = new Point (0,xH+100); //moves point to outside display area
            }
            else if ((m.isVertical()&&n.isVertical())||(m.isHorizontal()&&n.isHorizontal())||(m.slope()==n.slope())) //checks if lines don't intersect
            {
                intersect.setText ("No intersection");
                q = new Point (0,xH+100); //moves point to outside display area
            }
            else
                intersect.setText ("The point of intersection is "+q.toString()); //displays intersection point
            repaint(); //redraws area
        }
    }

    class SettingsBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            //create new settings window
            Settings s = new Settings (LineGUI.this);
            s.setVisible (true);
            s.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        if (intersectField.getText().equals("")) //checks if intersect panel is empty
                        {
                            //moves intersection line and point to outside display area
                            m = new Line (0,1,yH+100);
                            q = new Point (0,xH+100);
                        }
                        repaint(); //redraws area
                    }
                });
        }
    }

    class DrawArea extends JPanel
    {
        int lPixel;
        int rPixel;
        int lX;
        int rX;

        public DrawArea (int width, int height)
        {
            this.setPreferredSize (new Dimension (width, height)); // size
        }

        public double xTickLength () //calculates tick length for x axis
        {
            double y = Math.max (Math.abs(xL/2), Math.abs(xH/2));
            int z = (int)(Math.log10(y));
            return Math.pow(10,z);
        }

        public double yTickLength () //calculates tick length for y axis
        {
            double y = Math.max (Math.abs(yL/2), Math.abs(yH/2));
            int z = (int)(Math.log10(y));
            return Math.pow(10,z);
        }

        public int xPixel (double m) //converts input number to x pixel value
        {
            return (int)((m-xL)/(xH-xL+0.0)*400);
        }

        public int yPixel (double m) //converts input number to y pixel value
        {
            return 400+(int)((yL-m)/(yH-yL+0.0)*400);
        }

        public void paintComponent (Graphics g)
        {
            g.setColor (Color.black);
            int yZero = xPixel (0);
            int xZero = yPixel (0);
            //draws axis lines
            g.drawLine (0,xZero,400,xZero);
            g.drawLine (yZero,0,yZero,400);
            int count = 0;
            //draws grid lines for x>0
            for (double z = xTickLength(); z<=xH; z+=xTickLength())
            {
                g.setColor (new Color (200,200,200));
                Line v = new Line (1,0,-z); //creates new line which is the grid line
                v.draw(g, xL, xH, yL, yH);
                count++;
                if (count%2==0) //labels every other grid line
                {
                    g.setColor (Color.black);
                    g.drawString (String.valueOf(z),xPixel(z),yPixel(0));
                }
            }
            count = 0;
            //draws grid lines for x<0
            for (double z = -xTickLength(); z>=xL; z-=xTickLength())
            {
                g.setColor (new Color (200,200,200));
                Line v = new Line (1,0,-z); //creates new line which is the grid line
                v.draw(g, xL, xH, yL, yH);
                count++;
                if (count%2==0) //labels every other grid line
                {
                    g.setColor (Color.black);
                    g.drawString (String.valueOf(z),xPixel(z),yPixel(0));
                }
            }
            count = 0;
            //draws grid lines for y>0
            for (double z = yTickLength(); z<=yH; z+=yTickLength())
            {
                g.setColor (new Color (200,200,200));
                Line v = new Line (0,1,-z); //creates new line which is the grid line
                v.draw(g, xL, xH, yL, yH);
                count++;
                if (count%2==0) //labels every other grid line
                {
                    g.setColor (Color.black);
                    g.drawString (String.valueOf(z),xPixel(0),yPixel(z));
                }
            }
            count = 0;
            //draws grid lines for y<0
            for (double z = -yTickLength(); z>=yL; z-=yTickLength())
            {
                g.setColor (new Color (200,200,200));
                Line v = new Line (0,1,-z); //creates new line which is the grid line
                v.draw(g, xL, xH, yL, yH);
                count++;
                if (count%2==0) //labels every other grid line
                {
                    g.setColor (Color.black);
                    g.drawString (String.valueOf(z),xPixel(0),yPixel(z));
                }
            }
            //draws all lines in list
            for (int i = 0; i<list.size(); i++)
            {
                if (i == list.size()-1)
                {
                    g.setColor (Color.red); //set color to red for newest line
                    g.drawString (list.get(i).toString(),5,10); //label line
                }
                list.get(i).draw(g, xL, xH, yL, yH); //draw line
            }       
            g.setColor(new Color (0,100,0)); //dark green
            m.draw(g, xL, xH, yL, yH); //draw intersect line
            if (!intersectField.getText().equals("")) //if the intersect line exists label it
                g.drawString (m.toString(),5,25);
            g.setColor(Color.blue);
            //draw intersection point
            int xC = (int)((q.getX()-xL)/(xH-xL)*400);
            int yC = 400-(int)((q.getY()-yL)/(yH-yL)*400);
            g.fillOval (xC-5,yC-5,10,10);
        }
    }

    public static void main (String[] args)
    {
        //set initial limits
        xL = -10;
        xH = 10;
        yL = -10;
        yH = 10;
        //create new window
        LineGUI window = new LineGUI ();
        window.setVisible (true);
    }
}
