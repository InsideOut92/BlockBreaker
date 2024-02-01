import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
                                                                             // by xhuljan Mouse......
public class GamePlay extends JPanel implements KeyListener, ActionListener, MouseMotionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;

    private int playerX = 320;

    private int ballposX = 120;
    private int ballposY = 350;

    private int ballXdir =-2;
    private int ballYdir =-2;

    private MapGenerator map;

    private MouseEvent e;
    public GamePlay(){

        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();


        // by xhuljan  // Mouse Added
        addMouseListener(this);
        addMouseMotionListener(this);

    }


    // by xhuljan // Mouse Added
    private void addMouseListener(GamePlay gamePlay) {
    }



    public void paint(Graphics g){
        // Hintergrund
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Map zeichnen
        map.draw((Graphics2D) g);

        // Rahmen
        g.setColor(Color.orange);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Punktzahl
        g.setColor(Color.cyan);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 59, 30);

        // Paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // Ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Du hast gewonnen!", 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Drücke Enter um neu zu starten", 230, 350);
        }

        if (ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over!", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Drücke Enter um neu zu starten", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }

            A: for(int i = 0; i < map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            // Anpassung der Ballrichtung nach der Kollision
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX < 0 || ballposX > 670){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = -ballYdir;
            }
            if(ballposY > 570){
                play = false;
                ballXdir = 0;
                ballYdir = 0;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }



    private void moveRight() {
        play = true;
        playerX += 20;
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }




// by xhuljan   // Mouse Added
    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        if (mouseX >= 0 && mouseX <= 600) {
            playerX = mouseX - 15;
        }
        play = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

}
