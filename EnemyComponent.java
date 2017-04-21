import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;
public class EnemyComponent extends JComponent implements ActionListener//, KeyListener
{
    private int frameW;
    private int frameH;
    private double x;//x position of the middle of the circle
    private double y;//y position of the middle of the circle
    private int speed;
    private double radius;
    private int color;
    private int direction;
    private boolean life;
    private Random gen = new Random();
    public EnemyComponent()
    {

    }

    public EnemyComponent(int frameSize0, double playerSize0)
    {
        frameW=frameSize0;
        frameH=frameSize0;
        color=gen.nextInt(7);//picks a random color when the EnemyComponent is constructed
        life=true;//enemy is alive

        
        if (gen.nextInt(2)==0)//50/50 chance that the circle is smaller or bigger than the player's circle
        {
            radius=playerSize0*(1/((Math.random()+0.2)*3));//small circle, same formula as for a big circle but the factor multiplied to the playerSize0 is the inverse
        }
        else
        {
            radius=playerSize0*((Math.random()+0.2)*3);//big circle
        }
        
        speed=100;
        direction=gen.nextInt(90)+1;//1 to 90 inclusive, range of randomness for the direction

        switch (gen.nextInt(4))
        {
            case 0:
            {
                x=(double)(gen.nextInt(frameW)-frameW/2);//random x starting location
                y=(double)(-((frameH/2)+radius));//top of the screen
                direction=direction+135;//sets what the general direction is, based on where the circle is constructed so that it goes towards the center of the frame and not away from it
                break;
            }
            case 1:
            {
                x=(double)((frameW/2)+radius);//right of the screen
                y=(double)(gen.nextInt(frameH)-frameH/2);//random y starting location
                direction=direction+225;
                break;
            }
            case 2:
            {
                x=(double)(gen.nextInt(frameW)-frameW/2);//random x starting location
                y=(double)((frameH/2)+radius);//bottom of the screen
                direction=direction+315;
                break;
            }
            case 3:
            {
                x=(double)(-((frameW/2)+radius));//left of the screen
                y=(double)(gen.nextInt(frameH)-frameH/2);//random y starting location
                direction=direction+45;
                break;
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

        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2);

        switch (color)
        {
            case 0:
            {
                g2.setColor(Color.GREEN);
                break;
            }
            case 1:
            {
                g2.setColor(Color.RED);
                break;
            }
            case 2:
            {
                g2.setColor(Color.YELLOW);
                break;
            }
            case 3:
            {
                g2.setColor(Color.BLUE);
                break;
            }
            case 4:
            {
                g2.setColor(Color.CYAN);
                break;
            }
            case 5:
            {
                g2.setColor(Color.MAGENTA);
                break;
            }
            case 6:
            {
                g2.setColor(Color.ORANGE);
                break;
            }
        }

        g2.draw(ball);
        g2.fill(ball);

    }

    public void setLife()
    {
        life=false;
    }

    public boolean getLife()
    {
        return life;
    }

    public double returnX()
    {
        return x;
    }

    public double returnY()
    {
        return y;
    }

    public double getRadius()
    {
        return radius;
    }

    public void actionPerformed(ActionEvent e)
    {


        if (KListener.getPause()==false)//if the game isn't paused
        {

            x=x+(Math.sin(direction*(Math.PI/180)))*(frameW/speed);//the x changes based on the direction of the circle
            y=y-(Math.cos(direction*(Math.PI/180)))*(frameH/speed);//the y changes based on the direction of the circle

            this.repaint();
        }

    }
}
