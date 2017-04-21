import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
public class ReplayComponent extends JComponent
{

    private int frameW;
    private int frameH;
    private int replay;
    JFrame frame = new JFrame();//declared as instance field variables so that the scope includes the inner class
    JButton buttonPlay = new JButton();//button to play the game again
    JButton buttonExit = new JButton();//button to exit the program
    public ReplayComponent()
    {
        frameW=505;
        frameH=550;
        replay=0;//0 means no button has been pressed yet
    }

    public void setPanels()
    {

        frame.setSize(frameW, frameH);
        frame.setResizable(false);//cannot be resized
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//will end the program if the window is closed by the player
        frame.setVisible(true);

        JPanel panel = new JPanel(new BorderLayout());//constructs a JPanel that will be organized using a BorderLayout
        panel.setSize(frameW, frameH);//same size as the grame
        panel.setVisible(true);
        frame.add(panel);//add the panel to the frame
        
        JLabel image = new JLabel();//constructs a JLabel to put the image in
        image.setIcon(new ImageIcon("Kavi.jpg"));//constructs an ImageIcon the uses the image at "Kavi.jpg", this ImageIcon is added to the JLabel with the setIcon method
        panel.add(image, BorderLayout.CENTER);//the JLabel is placed in the center of the panel

        ActionListener bListener = new ButtonListener();//constructs an instance of the inner class ButtonListener
        
        JPanel buttonPanel = new JPanel();//constructs a JPanel that will have the buttons on it
        panel.add(buttonPanel, BorderLayout.SOUTH);//buttonPanel is added to the lower part of panel
        
        
        buttonPlay = new JButton("Play Again!");//constructs the play again button with "Play Again!" displayed on it
        buttonPlay.addActionListener(bListener);//adds bListener as an ActionListener to the play again button, pressing the button will be registered as an event now
        buttonPanel.add(buttonPlay);//adds the button to buttonPanel
        
        buttonExit = new JButton("Exit");//constructs the exit button with "Exit" displayed on it
        buttonExit.addActionListener(bListener);//adds bListener as an ActionListener to the exit button, pressing the button will be registered as an event now
        buttonPanel.add(buttonExit);//adds the button to buttonPanel

        frame.setVisible(true);//for good measure
    }

    public int getReplay()//Starter class uses this method to find out whether or what button has been pressed
    {
        return replay;
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)//when a button is pressed
        {
            if (e.getSource()==buttonPlay)//if the button pressed is the play again button
            {
                replay=1;//1 means that the player wants to play the game again
                KListener.resetPause();//resetting the pause in KListener lets all the classes that use KListener know that the game is not paused anymore if it was, because there is now a new game
            }
            if (e.getSource()==buttonExit)//if the button pressed is the exit button
            {
                replay=2;//2 means that the program should be ended
            }
            
            frame.setVisible(false);//a button has been pressed, this window doesn't need to be visible to the user anymore

        }
    }

}