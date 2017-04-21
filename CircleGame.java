
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class CircleGame implements ActionListener
{
    private int frameSize;
    private int score;//player score
    private boolean life;
    private JFrame frame = new JFrame("Circle Game");
    private BallComponent ballC;
    private NumberComponent nC;
    private BackgroundComponent bC;
    private Timer t;
    //instance field variables declared
    public CircleGame()
    {
        frameSize=900;
        score=0;
        life=true;//alive
    }

    public void drawStuff()
    {

        frame.setSize(frameSize, frameSize);//setsize method invoked on JFrame object frame, square
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ends the program when the window is closed by the player
        KeyListener kListener = new KListener();
        frame.addKeyListener(kListener);//keeps track of key presses for pausing or changing the music
        ballC = new BallComponent(frameSize);
        t = new Timer(45, ballC);//fires every 45 milliseconds, updates the ball position
        frame.add(ballC);
        frame.addKeyListener(ballC);//ballC uses the arrow keys to move
        frame.setVisible(true);

        t.start();//lets the timer start calling actionPerformed methods
        nC = new NumberComponent(frameSize);
        t.addActionListener(nC);
        frame.add(nC);
        frame.setVisible(true);

        for (int i=1; i<=10; i++)//creates 10 enemy circles to start
        {
            EnemyComponent enemy = new EnemyComponent(frameSize, ballC.getRadius());//constructs an EnemyComponent, the radius of ballC is passed in
            BallComponent.enemies.add(enemy);//adds the enemy to the ArrayList of circles. Enemies is an ArrayList in BallComponent, can be accessed without a method because enemies is public
            t.addActionListener(enemy);
        }
        this.drawEnemies();//draws the enemies to the screen

        bC = new BackgroundComponent(frameSize);
        frame.setVisible(true);

    }

    public void addEnemy()
    {

        EnemyComponent enemy = new EnemyComponent(frameSize, ballC.getRadius());
        BallComponent.enemies.add(enemy);
        t.addActionListener(enemy);

    }

    public void drawEnemies()
    {
        for (int i=0; i<BallComponent.enemies.size(); i++)//runs through the ArrayList of enemy circles
        {
            if (BallComponent.enemies.get(i).getLife()==true)//if the enemy is still alive
            {
                frame.add(BallComponent.enemies.get(i));//draw
                frame.setVisible(true);
            }
            else
            {
                t.removeActionListener(BallComponent.enemies.get(i));//remove from the list of ActionListeners
                BallComponent.enemies.remove(i);//remove from the ArrayList
                i=i-1;//minus one because all the objects afterwards in the ArrayList shift down because one was deleted
            }
        }
    }

    public void removeEnemies()
    {
        for (int i=0; i<BallComponent.enemies.size(); i++)//runs through the ArrayList of enemy circles
        {
            frame.remove(BallComponent.enemies.get(i));//removed from the frame
            if (BallComponent.enemies.get(i).returnX()>(frameSize/2)+BallComponent.enemies.get(i).getRadius())//off the screen
            {
                t.removeActionListener(BallComponent.enemies.get(i));//remove from the list of ActionListeners
                BallComponent.enemies.remove(i);//remove from the ArrayList
                i=i-1;//minus one because all the objects afterwards in the ArrayList shift down because one was deleted
            }
            else if (BallComponent.enemies.get(i).returnX()<(-frameSize/2)-BallComponent.enemies.get(i).getRadius())//off the screen
            {
                t.removeActionListener(BallComponent.enemies.get(i));
                BallComponent.enemies.remove(i);
                i=i-1;
            }
            else if (BallComponent.enemies.get(i).returnY()>(frameSize/2)+BallComponent.enemies.get(i).getRadius())//off the screen
            {
                t.removeActionListener(BallComponent.enemies.get(i));
                BallComponent.enemies.remove(i);
                i=i-1;
            }
            else if (BallComponent.enemies.get(i).returnY()<(-frameSize/2)-BallComponent.enemies.get(i).getRadius())//off the screen
            {
                t.removeActionListener(BallComponent.enemies.get(i));
                BallComponent.enemies.remove(i);
                i=i-1;
            }
        }
    }

    public boolean getLife()//called in Starter to find out if the player is still alive
    {
        return life;
    }

    public void actionPerformed(ActionEvent e)
    {

        if (KListener.getPause()==false)//if the game isn't paused
        {
            
            if (BallComponent.getLife()==false)//if the player died
            {
                t.stop();//stop the timer that points to BallComponent and EnemyComponent
                life=false;//player died
            }
            
            
            
            frame.remove(ballC);
            frame.remove(nC);
            this.removeEnemies();//erases all the enemies so that they can be drawn again in the updated positions, gets rid of enemies that are no longer on the screen
            frame.remove(bC);//removed in order to be redrawn underneath the other components

            frame.add(ballC);
            frame.setVisible(true);

            frame.add(nC);
            frame.setVisible(true);

            this.addEnemy();//another enemy circle is added to the game
            this.drawEnemies();

            frame.add(bC);
            frame.setVisible(true);
        }

    }

    public JFrame getFrame()
    {
        return frame;
    }

    public void closeFrame()
    {
        frame.dispose();//gets rid of the frame
    }

    public void pause()
    {
        t.stop();
    }

    public void unpause()
    {
        t.start();
    }
}
