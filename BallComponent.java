
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
public class BallComponent extends JComponent implements ActionListener, KeyListener
{
    private int frameW;
    private int frameH;
    private double x;
    private double y;
    private int count;
    private static double radius;
    private static boolean life;
    public static ArrayList<EnemyComponent> enemies = new ArrayList<EnemyComponent>();
    private ArrayList<Integer> keys = new ArrayList<Integer>();
    public BallComponent()
    {

    }

    public BallComponent(int frameSize)
    {
        frameW=frameSize;
        frameH=frameSize;
        x=0;
        y=0;
        radius=frameSize/100;//starting radius
        life=true;
        if (enemies.size()>0)//if this isn't the first time the game is being played
        {
            for (int i = enemies.size()-1; i>=0; i--)//runs through the ArrayList of enemies
            {
                enemies.remove(i);//removes all the enemies, this is done to clear the ArrayList for every game the player wants to play
            }
        }

    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(frameW/2, frameH/2);//middle origin

        this.drawBall(g2);//draws the ball
    }

    public void drawBall(Graphics2D g2)
    {

        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2);//ball is always in the middle of the screen

        g2.setColor(Color.WHITE);

        g2.draw(ball);
        g2.fill(ball);

    }

    public void checkPos()//checks whether the player circle intersects any enemy circles
    {
        for (int i=0; i<enemies.size(); i++)//runs through the ArrayList of enemy circles
        {
            if (this.checkIntersects(i))//do the player circle and the enemy circle intersect
            {
                if (enemies.get(i).getRadius()<=radius)//the enemy is smaller
                {
                    this.addSize(enemies.get(i).getRadius());//make the player's circle larger, uses the size of the enemy circle
                    enemies.get(i).setLife();//enemy is dead
                }
                if (enemies.get(i).getRadius()>radius)//the enemy is larger
                {
                    life=false;//player is dead
                }

            }
        }
    }

    public boolean checkIntersects(int i)
    {
        if (Math.sqrt(Math.pow(enemies.get(i).returnX()-x, 2)+Math.pow(enemies.get(i).returnY()-y, 2))<=enemies.get(i).getRadius()+radius)//distance formula used to figure out whether the player and enemy circles intersect
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void addSize(double size0)
    {
        double size=size0;
        radius=Math.sqrt(((Math.PI*Math.pow(radius, 2))+(Math.sqrt(Math.PI*Math.pow(size, 2))*1.4))/Math.PI);//adds the area of the two together, finds the new radius. The second area is square rooted and then multiplied by 1.4 as an adjustment
    }

    public static double getRadius()
    {
        return radius;
    }

    public static boolean getLife()
    {
        return life;
    }

    public int getCount()
    {
        return count;
    }

    public static void addEnemy(EnemyComponent eC)
    {
        enemies.add(eC);
    }

    public static EnemyComponent getEnemy(int i)
    {
        return enemies.get(i);
    }

    public void keyPressed(KeyEvent e)//method runs when a key is pressed
    {
        Integer code = e.getKeyCode();//finds which key was pressed in order for the keyPressed method to run, assigns to code (Integer is a wrapper for int)

        boolean checkAdd=true;

        for (int i = 0; i<keys.size(); i++)//runs through all the keys that are pressed right now
        {
            if (code==keys.get(i))//if the pressed key is already in the arraylist then don't add to the arraylist
            {
                checkAdd=false;
            }
        }
        if (checkAdd==true)//if the pressed key was not found in the arraylist then add it
        {
            keys.add(code);
        }
    }

    public void keyReleased(KeyEvent e)
    {
        Integer code = e.getKeyCode();
        keys.remove(code);//the key has been released so therefore it does not need to be in the arraylist of pressed keys anymore

    }

    public void keyTyped(KeyEvent e)//not used, here just to fufill the requirements of interface KeyListener
    {

    }

    public void actionPerformed(ActionEvent e)
    {

        if (KListener.getPause()==false)//if the game isn't paused
        {
            double speedFactor = 1;//1 means go at a normal speed

            if (((keys.indexOf(KeyEvent.VK_UP)>=0)||(keys.indexOf(KeyEvent.VK_DOWN)>=0))&&((keys.indexOf(KeyEvent.VK_RIGHT)>=0)||(keys.indexOf(KeyEvent.VK_LEFT)>=0)))//combo of vertical and horizontal movement
            {
                if (((keys.indexOf(KeyEvent.VK_UP)==-1)||(keys.indexOf(KeyEvent.VK_DOWN)==-1))&&((keys.indexOf(KeyEvent.VK_RIGHT)==-1)||(keys.indexOf(KeyEvent.VK_LEFT)==1)))//only 1 vertical key is pressed, only 1 horizontal key is pressed
                {
                    speedFactor = (Math.sqrt(2))/2;//(square root of 2)/2. the circle moves at a 45 degree angle. at 45 degrees, both the x and y movement need to be multiplied by this factor because trigonometry
                }
            }

            for (int i=0; i<keys.size(); i++)//runs through the ArrayList of keys that are pressed
            {
                switch (keys.get(i))//gets the Integer code for the key
                {
                    case KeyEvent.VK_UP:
                    {
                        if (y>=(-frameH/2) + radius + frameH/100)//if the ball isn't at the top border
                        {
                            y=y-((frameH/100)*speedFactor);//moves up at a speed of frameH/100
                        }
                        break;
                    }
                    case KeyEvent.VK_DOWN:
                    {
                        if (y<=(frameH/2)-(radius + 6*frameH/100))//if the ball isn't at the bottom border
                        {
                            y=y+((frameH/100)*speedFactor);//moves down at a speed of frameH/100
                        }
                        break;
                    }
                    case KeyEvent.VK_RIGHT:
                    {
                        if (x<=(frameW/2)-(radius + 3*frameW/100))//if the ball isn't at the right border
                        {
                            x=x+((frameW/100)*speedFactor);//moves right at a speed of frameW/100
                        }
                        break;
                    }
                    case KeyEvent.VK_LEFT:
                    {
                        if (x>=-(frameW/2)+ radius + frameW/100)//if the ball isn't at the left border
                        {
                            x=x-((frameW/100)*speedFactor);//moves left at a speed of frameW/100
                        }
                        break;
                    }
                }
            }

            this.checkPos();//checks whether the player circle intersects any enemy circles
            count=count+1;//keeps time
            this.repaint();

        }

    }
}
