import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Scanner;
public class NumberComponent extends JComponent implements ActionListener
{
    private int frameW;
    private int frameH;
    private File highScoresF;
    private static int highScore = 0;//initialized as 0, will be a higher number after the file is read
    public NumberComponent()
    {

    }

    public NumberComponent(int frameSize0)//scores are passed in to simply be displayed
    {
        frameW=frameSize0;
        frameH=frameSize0;
        try
        {
            highScoresF = new File("High Scores.txt");//constructs a File object with the address of "High Scores.txt" within this folder
            Scanner scoreReader = new Scanner(highScoresF);//constructs a Scanner object that can read through the text file

            int score=scoreReader.nextInt();//there is always just one number in the file, reads and stroes it as an int

            highScore=score;//the number read from the file is the all-time high score. it doesnt change between games unless beaten

        }
        catch (Exception e)
        {

        }

    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        this.drawNumbers(g2);//draws the score

    }

    public void drawNumbers(Graphics2D g2)
    {
        int numSize=(int)Math.round(frameW/20);//size of the font
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, numSize));//font style
        int playerScore = (int)((BallComponent.getRadius()*10)-frameH/10);//score is just the radius, minus frameH/10 because the starting radius is frameH/10 and the score should start at 0
        if (BallComponent.getLife()==true)//game is still being played
        {
            g2.setColor(Color.WHITE);//number is white during the game
            g2.drawString(Integer.toString(playerScore), frameW/30, frameH/20);//draws the score in the top left corner
        }
        else//player died
        {
            g2.setColor(Color.CYAN.darker());//score is dark cyan when presented after the game

            g2.drawString("Score: " + Integer.toString(playerScore), (frameW/2)-(frameW/10), 2*(frameH/3));//draws the score in the bottom half of the frame

            if (playerScore>highScore)//if the player beat the high score that was read from the file earlier
            {
                highScore=playerScore;//the player score is the new high score
                try
                {
                    PrintStream filePrint = new PrintStream(highScoresF);//constructs a PrintStream object that can print text to the high scores file
                    filePrint.println(Integer.toString(playerScore));//prints the new high score to the file
                }
                catch (Exception e)
                {

                }

            }
            g2.drawString("High Score: " + Integer.toString(highScore), (frameW/2)-(5*frameW/30)-(frameW/160), 13*(frameH/18));//draws the high score below the current game's score

        }
    }

    public void actionPerformed(ActionEvent e)
    {
        this.repaint();//needs to be repainted so that the printed score updates
    }

}