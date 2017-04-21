import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
public class BackgroundComponent extends JComponent
{
    private int frameW;
    private int frameH;
    
    public BackgroundComponent()
    {
        
    }

    public BackgroundComponent(int frameSize0)
    {
        frameW=frameSize0;
        frameH=frameSize0;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;//type cast from Graphics to Graphics2D because Graphics2D is a subclass of Graphics

        this.drawBackground(g2);

    }

    public void drawBackground(Graphics2D g2)//draws the black background
    {

        Rectangle bg = new Rectangle(0, 0, frameW, frameH);//rectangle the size of the entire frame
        g2.setColor(Color.BLACK);//black background
        g2.draw(bg);
        g2.fill(bg);

    }

}