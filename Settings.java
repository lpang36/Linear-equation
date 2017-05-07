import java.util.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Settings extends JFrame
{
    private JTextField xLField = new JTextField (10);
    private JTextField xHField = new JTextField (10);
    private JTextField yLField = new JTextField (10);
    private JTextField yHField = new JTextField (10);
    LineGUI window;

    public Settings (LineGUI input)
    {
        window = input;

        //input text labels
        JLabel xL = new JLabel ();
        xL.setText ("Lower x limit");

        JLabel xH = new JLabel ();
        xH.setText ("Upper x limit");

        JLabel yL = new JLabel ();
        yL.setText ("Lower y limit");

        JLabel yH = new JLabel ();
        yH.setText ("Upper y limit");

        //input areas
        JPanel xLPanel = new JPanel();
        xLPanel.setPreferredSize (new Dimension (150,50));
        xLPanel.add (xLField);
        xLPanel.add (xL);

        JPanel xHPanel = new JPanel();
        xHPanel.setPreferredSize (new Dimension (150,50));
        xHPanel.add (xHField);
        xHPanel.add (xH);

        JPanel yLPanel = new JPanel();
        yLPanel.setPreferredSize (new Dimension (150,50));
        yLPanel.add (yLField);
        yLPanel.add (yL);

        JPanel yHPanel = new JPanel();
        yHPanel.setPreferredSize (new Dimension (150,50));
        yHPanel.add (yHField);
        yHPanel.add (yH);

        //add areas to content panel
        JPanel content = new JPanel();
        content.setLayout (new GridLayout (2,2));
        content.add(xLPanel);
        content.add(xHPanel);
        content.add(yLPanel);
        content.add(yHPanel);

        //set limit button
        JButton setBtn = new JButton ("Set limits");
        setBtn.addActionListener (new SetBtnListener());

        JPanel setPanel = new JPanel();
        setPanel.setPreferredSize (new Dimension (100,100));
        setPanel.add(setBtn);

        JPanel all = new JPanel ();
        all.setLayout (new BorderLayout ());
        all.add (content, "Center");
        all.add (setPanel, "East");

        // 4... Set this window's attributes.
        setContentPane (all);
        setSize (800,100);
        setLocationRelativeTo (null);    
    } 

    class SetBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            //try...catch needed so fields can be left empty without error
            //set each limit in text field when button is pressed
            try {
                int x = Integer.valueOf(xLField.getText());
                window.setXL (x);} catch (NumberFormatException f) {}
            try {
                int y = Integer.valueOf(xHField.getText());
                window.setXH (y);} catch (NumberFormatException f) {}
            try {
                int z = Integer.valueOf(yLField.getText());
                window.setYL (z);} catch (NumberFormatException f) {}
            try {
                int w = Integer.valueOf(yHField.getText());
                window.setYH (w);} catch (NumberFormatException f) {}
        }
    }
}
