package view;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow {
    
    private int duration;
    
    public SplashScreen(int d) {
        duration = d;
    }
    
    // A simple little method to show a title screen in the center
    // of the screen for the amount of time given in the constructor
    public void showSplash() {
        
        JPanel content = (JPanel)getContentPane();
        content.setBackground(new Color(255, 140, 0, 255));
        
        // Set the window's bounds, centering the window
        int width = 600;
        int height = 300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
        
        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon("images/splash.png"));
        JLabel projectNaam = new JLabel("Vossen en Konijnen 2013", JLabel.CENTER);
        JLabel projectLeden = new JLabel("Mike Por, Alexander Postma en Stefan Yip", JLabel.CENTER);
        projectNaam.setForeground(Color.white);
        projectLeden.setForeground(Color.white);                      
        projectNaam.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        projectLeden.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(projectNaam, BorderLayout.NORTH);
        content.add(projectLeden, BorderLayout.SOUTH);
        Color borderColor = new Color(255, 255, 255, 255);
        content.setBorder(BorderFactory.createLineBorder(borderColor, 20));
        
        // Display it
        setVisible(true);
        
        // Wait a little while, maybe while loading resources
        try { Thread.sleep(duration); } catch (Exception e) {}
        
        setVisible(false);
        
    }
    
    public void showSplashAndExit() {
        
        showSplash();
        System.exit(0);
        
    }
}