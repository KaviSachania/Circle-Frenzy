import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;
public class IntroComponent extends JComponent
{
    private int frameW;
    private int frameH;
    private boolean window;
    private JFrame frame = new JFrame();//declared as instance field variables so that the scope reaches the inner class
    private JButton button = new JButton();
    private JComboBox musicBox = new JComboBox();
    private MusicPlayer mP = new MusicPlayer();
    private static Timer musicTimer;
    public IntroComponent()
    {
        frameW=505;
        frameH=550;
        window=true;
    }

    public void setPanels()
    {

        frame.setSize(frameW, frameH);
        frame.setResizable(false);//cannot be resized
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//program stops when window is closed by the player
        frame.setVisible(window);//window is false initially

        JPanel panel = new JPanel(new BorderLayout());//JPanel organized with a BorderLayout
        panel.setSize(frameW, frameH);
        panel.setVisible(true);
        frame.add(panel);//added to the JFrame

        JLabel intro = new JLabel();
        intro.setIcon(new ImageIcon("Kavi.jpg"));//ImageIcon with an image with the address "Kavi.jpg", added to the JLabel with setIcon
        panel.add(intro, BorderLayout.CENTER);//intro is added to the middle of panel

        ActionListener bListener = new ButtonListener();//constructs a ButtonListener object, referenced by an ActionListener label. ButtonListener implements ActionListener

        JPanel buttonPanel = new JPanel();//JPanel for the buttons
        panel.add(buttonPanel, BorderLayout.SOUTH);//placed at the bottom of the main JPanel

        String[] musicList = new String[22];//array of song names, adding ".wav" to these Strings will give the address of the corresponding music files
        musicList[0] = "Random Song";
        musicList[1] = "No Music";
        musicList[2] = "Another Day In Paradise - Quinn XCII";
        musicList[3] = "Brazil - Declan McKenna";
        musicList[4] = "Cake By The Ocean - DNCE";
        musicList[5] = "Can't Stop - Red Hot Chili Peppers";
        musicList[6] = "Crossfire - Stephen";
        musicList[7] = "Fallout - Catfish And The Bottlemen";
        musicList[8] = "First - Cold War Kids";
        musicList[9] = "Gimme Twice - The Royal Concept";
        musicList[10] = "If I Ever Feel Better - Phoenix";
        musicList[11] = "Island In The Sun - Weezer";
        musicList[12] = "Never Be Like You (feat. Kai) - Flume";
        musicList[13] = "Roses (feat. ROZES) - The Chainsmokers";
        musicList[14] = "Santeria - Sublime";
        musicList[15] = "Sun Models (feat. Madelyn Grant) - ODESZA";
        musicList[16] = "Sunshine - Matisyahu";
        musicList[17] = "Tongue Tied - Grouplove";
        musicList[18] = "Tonight Is The Night - Outasight";
        musicList[19] = "Trojans - Atlas Genius";
        musicList[20] = "What You Know - Two Door Cinema Club";
        musicList[21] = "Your Soul - Hippie Sabotage";

        
        musicBox = new JComboBox(musicList);//JComboBox with all the songs as options
        musicBox.addActionListener(bListener);
        buttonPanel.add(musicBox);

        button = new JButton("Start!");//button to start the game
        button.addActionListener(bListener);
        buttonPanel.add(button);

        frame.setVisible(true);
    }

    public void stopMusic()
    {
        mP.stopMusic();
        musicTimer.stop();
    }

    public boolean getWindow()//used by Starter to check if the start button has been clicked yet. If the window is false (the frame is also set to false), then the start button had to be clicked
    {
        return window;
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource()==button)//if the source is the start button
            {
                window=false;//the window will become not visible now
                frame.setVisible(window);
                musicTimer = new Timer(1000, mP);//constructs a Timer for the MusicPlayer object that goes off every second, used to keep track of how long a song has been playing
                musicTimer.start();
                MusicPlayer.playMusic();//start the music
            }
            if (e.getSource()==musicBox)//if the source is the music JComboBox
            {
                String choice = (musicBox.getSelectedItem()).toString();//get the selected String
                MusicPlayer.setMusicPlayer(choice);//set the selected song as the song to play in MusicPlayer

            }

        }
    }

}
