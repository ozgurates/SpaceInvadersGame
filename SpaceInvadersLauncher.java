import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class SpaceInvadersLauncher extends JFrame {
	


    public SpaceInvadersLauncher() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setTitle("Space Invaders");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args)
    {
            var ex = new SpaceInvadersLauncher();
            ex.setVisible(false);
            
            IntroMenu w  = new IntroMenu();
            w.setSize(400,200); //400 200
            w.setVisible(true);
            w.setResizable(false);
            
            w.jbPlay.addActionListener(new ActionListener() {
				
				@Override
			     public void actionPerformed(ActionEvent e) {
				    ex.setVisible(true);
					w.drawTimer();
					w.drawAngle();
				    w.drawVelocity();
				}
				
			});
            
            
            
           
        
    	
    }
}


