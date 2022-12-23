import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {

    int width = 400;
    int height = 400;

    int x[] = new int[width * height];
    int y[] = new int[width * height];

    int dots, dot_size = 10;
    int apple_x = 100, apple_y = 100;

    Image apple, head , body;

    boolean leftDirection = true, rightDirection = false,
            upDirection = false, downDirection = false;

    Timer timer;
    int Delay = 400;
    int num = 39;
    boolean inGame = true;

   public GameBoard(){
       addKeyListener(new TAdapter());
        setFocusable(true);
       setPreferredSize(new Dimension(width , height));
        setBackground(Color.BLACK);
        loadImages();
        initGame();

    }

    public  void initGame(){
        dots = 3;
        for(int i = 0; i < dots; i++){
            x[i] = 150 + i*dot_size;
            y[i] = 150;
        }

        timer = new Timer(Delay, this);
        timer.start();
    }

    public void loadImages(){

        ImageIcon image_apple = new ImageIcon("src/resources/apple.png");
        apple = image_apple.getImage();

        ImageIcon image_head = new ImageIcon("src/resources/head.png");
        head = image_head.getImage();

        ImageIcon image_body = new ImageIcon("src/resources/dot.png");
        body = image_body.getImage();
    }

    @Override
    public  void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        if(inGame){
            graphics.drawImage(apple, apple_x, apple_y ,this);
            for(int i = 0; i < dots; i++){
                if(i == 0){
                    graphics.drawImage(head , x[i], y[i], this);
                }
                else{
                    graphics.drawImage(body, x[i], y[i] ,this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(graphics);
        }
    }

    public void move(){

       for(int i = dots - 1; i > 0; i--){
           x[i] = x[i - 1];
           y[i] = y[i - 1];
       }

       if(leftDirection){
           x[0] -= dot_size;
       }
       if(rightDirection){
           x[0] += dot_size;
       }
       if(upDirection){
           y[0] -= dot_size;
       }
       if(downDirection){
           y[0] += dot_size;
       }
    }

    private  void locateApple(){
       int r =  (int)(Math.random()*num);
       apple_x = r * dot_size;

       r = (int)(Math.random()*num);
       apple_y = r * dot_size;

    }

    private  void checkApple(){
       if(x[0] == apple_x && y[0] == apple_y){
           dots++;
           locateApple();
       }
    }

    private  void checkCollision(){
       if(x[0] < 0 || x[0] >= width)
           inGame = false;
       if(y[0] < 0 || y[0] >= height)
           inGame = false;

       for(int i = dots - 1; i >=4; i--){
           if(x[0] == x[i] && y[0] == y[i])
               inGame = false;
       }
    }

    private void gameOver(Graphics graphics){
       String msg = "Game Over";
       Font small = new Font("Helvatica" , Font.BOLD, 14);
       FontMetrics metrics = getFontMetrics(small);
       graphics.setColor(Color.WHITE);
       graphics.setFont(small);
       graphics.drawString(msg, (width - metrics.stringWidth(msg))/2, height/2);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
       if(inGame){
           checkApple();
           checkCollision();

           move();
       }
       repaint();
    }

    public  class TAdapter extends KeyAdapter{

       @Override
        public void keyPressed(KeyEvent keyEvent){
           int key = keyEvent.getKeyCode();

           if(key == keyEvent.VK_LEFT && (!rightDirection)){
               leftDirection = true;
               upDirection = false;
               downDirection = false;
           }
           if(key == keyEvent.VK_RIGHT && (!leftDirection)){
               rightDirection = true;
               upDirection = false;
               downDirection = false;
           }
           if(key == keyEvent.VK_UP && (!downDirection)){
               leftDirection = false;
               upDirection = true;
               rightDirection = false;
           }
           if(key == keyEvent.VK_DOWN && (!upDirection)){
               leftDirection = false;
               rightDirection = false;
               downDirection = true;
           }
       }
    }

}
