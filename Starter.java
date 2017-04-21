import javax.swing.Timer;
public class Starter
{
    public static void main(String[] args)
    {

        IntroComponent introC = new IntroComponent();//constructs the IntroComponent used for the start screen
        introC.setPanels();//creates and displays the panels with the game information and the music and start buttons

        boolean start = true;
        while (start==true)//loops until the start button is clicked
        {
            start=introC.getWindow();//returns true if the start screen window is still open, returns false if closed. window only closes when the start button is clicked
        }
        //start button has been clicked, game can start
        
        CircleGame game = new CircleGame();//constructs a new game, only for the purpose of the timer to have something to initially point to
        Timer tGame = new Timer(300, game);//constructs a timer that fires every 300 milliseconds, adds game as an ActionListener
        //If the game lags, change 300 to 500. The frequency that circles appear will go down.
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        
        boolean replay=true;
        while (replay==true)//while the player wants to keep playing the game
        {
            tGame.removeActionListener(game);//removes the first CircleGame as an ActionListener, it was just a placeholder
            game = new CircleGame();//this is the real CircleGame
            tGame.addActionListener(game);//added to the Timer as an ActionListener
            tGame.start();//lets the timer start calling actionPerformed methods
            boolean run=true;
            game.drawStuff();//runs the actual game
            while (run==true)//while still alive, constantly checks to see if the game is over yet
            {
                if (game.getLife()==false)//if the player died
                {
                    tGame.stop();//stop the Timer
                    run=false;//escape the while loop
                }
            }
            
            
            ReplayComponent replayC = new ReplayComponent();//constructs the ReplayComponent because the player died
            replayC.setPanels();//creates and displays the panels with the game information and the replay or exit buttons

            while (replayC.getReplay()==0)//constantly checks if no button has been selected yet
            {
                //This while loop is here to wait for a button to be selected. I purposefully did not want to add any sort of ActionListener to accomplish this.
                //This way of waiting for a button to be pressed is simpler because this is the only place that Starter interacts with ReplayComponent.
                //If replayC returns a value of 0, it means that no button has been selected yet. That value changes to 1 or 2 once a button is clicked.
            }
            if (replayC.getReplay()==1)//player chose to play again
            {
                replay=true;//keep running the game
            }
            if (replayC.getReplay()==2)//player chose to exit
            {
                replay=false;//don't loop through the game again
                introC.stopMusic();//stops the music, the MusicPlayer object is in IntroComponent
            }

            game.closeFrame();//close the frame where the actual gameplay is because either the game has ended or a new one will be constructed

        }
        
        
    }
}
