
package dodgeballmovement;

import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;
public class DodgeballMovement extends JFrame {
    public DodgeballMovement(){
        
        initUI();
        
    }
    
    private void initUI(){
        setTitle("Dodgeball Madness");
        add(new DrawingSurface());
        setSize(1000+23,670+57);
        
    }
    
    public static void main(String[] args) {
        /**
        JFrame f = new JFrame();
        DrawingSurface s = new DrawingSurface();
        f.add(s);
        f.setVisible(true);
        
        f.setSize(1000,670)
        **/
        DodgeballMovement windowFrame = new DodgeballMovement();
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stop the build when you press the x button to close the JFrame 
       //windowFrame.addMouseListener(new MouseHandler());
        //windowFrame.addMouseMotionListener(new MouseMotionHandler());
        windowFrame.requestFocus();
        windowFrame.setVisible(true);
        
        
    }
    
}
