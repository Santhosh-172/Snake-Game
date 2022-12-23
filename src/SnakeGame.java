import javax.swing.*;

public class SnakeGame  extends JFrame {

    private GameBoard gameBoard;
    SnakeGame(){
        gameBoard = new GameBoard();
        add(gameBoard);
        //setBounds(100,100,400,400);
        setResizable(false);
        pack();
        setVisible(true);
    }
    public static void main(String[] args)
    {
        JFrame frame = new SnakeGame();
    }
}