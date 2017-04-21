
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
class KListener implements KeyListener
{
    public static boolean pause = false;//initialized as false because the game is running, static so that it can be used in static methods
    public void keyPressed(KeyEvent e)//method runs when a key is pressed
    {
        Integer code = e.getKeyCode();//finds which key was pressed in order for the keyPressed method to be called, assigns to code (Integer is a wrapper for int)

        if (code==KeyEvent.VK_SPACE)
        {
            pause=!pause;//changes pause to signify whether to pause or unpause the game when the getPause() method is run
        }
        if (code==KeyEvent.VK_F)
        {
            MusicPlayer.pauseMusic();//pauses the music in the MusicPlayer class
        }
        if (code==KeyEvent.VK_D)
        {
            MusicPlayer.nextSong();//next song in MusicPlayer
        }
        if (code==KeyEvent.VK_S)//restarts the current song
        {
            MusicPlayer.stopMusic();//stop the music
            MusicPlayer.playSong();//plays from the beginning the song that was already playing
        }
    }

    public static boolean getPause()//static so that classes can retrieve pause without constructing a KListener object
    {
        return pause;//this method is continuously called in other classes to find out whether the game should be paused or unpaused
    }
    
    public static void resetPause()//static so that classes can reset pause without constructing a KListener object
    {
        pause=false;
    }

    public void keyReleased(KeyEvent e)//method is here so that KListener can implement interface KeyListener, KeyListener has this method
    {
        
    }

    public void keyTyped(KeyEvent e)//method is here so that KListener can implement interface KeyListener, KeyListener has this method
    {
        
    }
}
