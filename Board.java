import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import java.util.List;

import java.util.Random;

public class Board extends JPanel {

    private Dimension d;
    private List<Alien> aliens;
    public Player player;
    private Bullet bullet;

    private int direction = -1;
    private int deaths = 0;
    private int currentLevel;
    private boolean isLevelOver;
    
    
    protected static int xOfPlayer;
    protected static boolean isStopped = false;
    

    private boolean inGame = true;
    private String message = "Game Over";

    private Timer timer;


    public Board() {
        initBoard();
        LevelInit_1();

    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();

    }


    private void LevelInit_1() {
        currentLevel = 1;
        aliens = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {

                var alien = new Alien(Commons.ALIEN_INIT_X + 18 * j, Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);



            }

        }
        player = new Player();
        bullet = new Bullet();
    }
    private void levelInit_2(){
        System.out.println("start game l2");
        deaths = 0;
        currentLevel = 2;
        aliens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                var alien = new Alien(Commons.ALIEN_INIT_X + 18 * j, Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);


            }

        }

        System.out.println("test game l2" +  aliens.size());
        player = new Player();
        bullet = new Bullet();
    }
    private  void levelInit_3(){
        currentLevel = 3;
        deaths = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {

                        var alien = new Alien(Commons.ALIEN_INIT_X + 18 * j, Commons.ALIEN_INIT_Y + 18 * i);
                        aliens.add(alien);

                    }

                }

        player = new Player();
        bullet = new Bullet();

    }


    private void drawAliens(Graphics g) {

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {
        	
        	xOfPlayer = player.getX();
            g.drawImage(player.getImage(), xOfPlayer, player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {

        if (bullet.isVisible()) {

            g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame) {

            g.drawLine(0, Commons.GROUND,
            		Commons.BOARD_WIDTH, Commons.GROUND);

            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
        		Commons.BOARD_WIDTH / 2);
    }

    private void update() {
        if (deaths == aliens.size()) {


            System.out.println("level: " + currentLevel );

            switch (currentLevel) {
                case 1: {
                    JOptionPane.showMessageDialog(null, "Good Work, you have completed level " + (currentLevel));
                    System.out.println(currentLevel);
                    levelInit_2();
                    break;
                }
                case 2: {
                    JOptionPane.showMessageDialog(null, "Good Work, you have completed level " + (currentLevel));
                    System.out.println(currentLevel);
                    levelInit_3();
                    break;
                }
                case 3: {
                    System.out.println(currentLevel);

                        JOptionPane.showMessageDialog(null, "Good Work, you have completed level " + (currentLevel));
                        JOptionPane.showMessageDialog(null, "You Have Protected The World From an Alien Invasion!");
                        break;


                }

            }

        }

        // player
        player.act();

        // shot
        String explImg = "res/explosion.png" ;
        if (bullet.isVisible()) {

            int shotX = bullet.getX();
            int shotY = bullet.getY();

            for (Alien alien : aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && bullet.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        var ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        bullet.die();
                    }
                }
            }

            int y = bullet.getY();
            y -= 4;

            if (y < 0) {
                bullet.die();
            } else {
                bullet.setY(y);
            }
        }

        // aliens

        for (Alien alien : aliens) {

            int x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {

                direction = -1;

                for (Alien a2 : aliens) {

                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {

                direction = 1;

                for (Alien a : aliens) {

                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        var generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();

            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            xOfPlayer = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (xOfPlayer)
                        && bombX <= (xOfPlayer + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    var ii = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {
        if(isLevelOver) {
           // LevelInit();
            isLevelOver = false;

        }
            update();
            repaint();

    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
            isStopped = true;
        }
       
        	
        			       	
        

        @Override
        public void keyPressed(KeyEvent e) {
// add mouse event
            player.keyPressed(e);

            xOfPlayer = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {

                    if (!bullet.isVisible()) {

                        bullet = new Bullet(xOfPlayer, y);
                    }
                }
            }
            else if(key == KeyEvent.VK_RIGHT) {
        		isStopped = false;
        	}
            else if(key == KeyEvent.VK_LEFT) {
        		isStopped = false;
        	}
        	
            
        }
    }
}
