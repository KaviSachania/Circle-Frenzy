
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import sun.audio.*;
import java.util.ArrayList;
public class MusicPlayer implements ActionListener
{
    public static String songAddress;
    public static AudioStream audioStream=null;
    public static int songTime;
    public static int count = 0;//counts how many seconds a song has been playing for
    public static ArrayList<String> playedSongs= new ArrayList<>();//arraylist of songs already played
    public static boolean pause;
    public static int pauseCount;
    public MusicPlayer()
    {
        MusicPlayer.setMusicPlayer("Random Song");//set up to pick a random song when constructed if one isn't picked by the player
    }

    public static void setMusicPlayer(String choice0)//choice0 is the song String from the JComboBox in IntroComponent
    {
        songAddress=choice0;

        if (choice0.equals("Random Song"))
        {
            Random gen = new Random();
            int randomSong=gen.nextInt(20)+1;//1 to 20 inclusive
            switch (randomSong)
            {
                case 1:
                {
                    songAddress = "Another Day In Paradise - Quinn XCII.wav";//string is the file location
                    songTime = 255;//length of the song in seconds
                    break;
                }
                case 2:
                {
                    songAddress = "Brazil - Declan McKenna.wav";
                    songTime = 253;
                    break;
                }
                case 3:
                {
                    songAddress = "Cake By The Ocean - DNCE.wav";
                    songTime = 218;
                    break;
                }
                case 4:
                {
                    songAddress = "Can't Stop - Red Hot Chili Peppers.wav";
                    songTime = 269;
                    break;
                }
                case 5:
                {
                    songAddress = "Crossfire - Stephen.wav";
                    songTime = 269;
                    break;
                }
                case 6:
                {
                    songAddress = "Fallout - Catfish And The Bottlemen.wav";
                    songTime = 211;
                    break;
                }
                case 7:
                {
                    songAddress = "First - Cold War Kids.wav";
                    songTime = 200;
                    break;
                }
                case 8:
                {
                    songAddress = "Gimme Twice - The Royal Concept.wav";
                    songTime = 205;
                    break;
                }
                case 9:
                {
                    songAddress = "If I Ever Feel Better - Phoenix.wav";
                    songTime = 264;
                    break;
                }
                case 10:
                {
                    songAddress = "Island In The Sun - Weezer.wav";
                    songTime = 210;
                    break;
                }
                case 11:
                {
                    songAddress = "Never Be Like You (feat. Kai) - Flume.wav";
                    songTime = 233;
                    break;
                }
                case 12:
                {
                    songAddress = "Roses (feat. ROZES) - The Chainsmokers.wav";
                    songTime = 228;
                    break;
                }
                case 13:
                {
                    songAddress = "Santeria - Sublime.wav";
                    songTime = 184;
                    break;
                }
                case 14:
                {
                    songAddress = "Sun Models (feat. Madelyn Grant) - ODESZA.wav";
                    songTime = 160;
                    break;
                }
                case 15:
                {
                    songAddress = "Sunshine - Matisyahu.wav";
                    songTime = 213;
                    break;
                }
                case 16:
                {
                    songAddress = "Tongue Tied - Grouplove.wav";
                    songTime = 215;
                    break;
                }
                case 17:
                {
                    songAddress = "Tonight Is The Night - Outasight.wav";
                    songTime = 191;
                    break;
                }
                case 18:
                {
                    songAddress = "Trojans - Atlas Genius.wav";
                    songTime = 218;
                    break;
                }
                case 19:
                {
                    songAddress = "What You Know - Two Door Cinema Club.wav";
                    songTime = 191;
                    break;
                }
                case 20:
                {
                    songAddress = "Your Soul - Hippie Sabotage.wav";
                    songTime = 324;
                    break;
                }
            }

        }
        else if (!(songAddress.equals("No Music")))//the player didn't select "Random Song" or "No Music". A specific song was selected
        {

            boolean matchingSong = false;
            while (matchingSong==false)
            {
                MusicPlayer.setMusicPlayer("Random Song");//picks a random song. This is not recursion. It won't go any deeper after this call to setMusicPlayer.
                if ((choice0 + ".wav").equals(songAddress))//if the randomly selected song is the same as what the player wants
                {
                    matchingSong=true;//the song has been found
                }
            }
            //I know, this is super inefficient. My deepest apologies. If there was a large amount of songs, I would then be compelled to fix it.
        }

    }

    public static void playMusic()//plays a newly selected song
    {
        pause=false;//the new song is not paused
        count=0;//start over the seconds count for the length of the song
        playedSongs.add(songAddress);//adds the song to the ArrayList of played songs
        MusicPlayer.playSong();//run method that plays the music
    }

    public static void playSong()
    {

        try
        {
            InputStream in = new FileInputStream(songAddress);//gets the file location
            audioStream = new AudioStream(in);//audioStream uses the InputStream with the file
            if (!(songAddress.equals("No Music")))//don't play any music if "No Music" has been selected. When "No Music" is selected, a song address is still found. Just don't play the song though
            {
                AudioPlayer.player.start(audioStream);//play la musica
            }

        }
        catch (Exception e)//if the file location cannot be found
        {
            //do nothing
        }

    }

    public static void stopMusic()
    {
        AudioPlayer.player.stop(audioStream);//stops the music
    }

    public static void startMusic()
    {
        AudioPlayer.player.start(audioStream);//starts the music
    }

    public static void pauseMusic()
    {
        if (pause==false)//if the music is playing
        {
            MusicPlayer.stopMusic();//pause
            pauseCount=count;//keeps track of how long the song has been playing up to this point
        }
        else//if the music is already paused
        {
            MusicPlayer.startMusic();
            count=pauseCount;//resets the count to whatever it was when it was paused
        }
        pause=!pause;//the state has changed from paused to unpaused, or the other way around
    }

    public static void nextSong()
    {
        AudioPlayer.player.stop(audioStream);//stops the song
        String lastSong = "";
        if (playedSongs.size()==20)//if all 20 songs have been played
        {
            playedSongs.clear();//clear the arraylist, all songs are fair game now
            lastSong=songAddress;//because the arraylist is empty now, the last song needs to be kept track of so it isn't repeated
        }
        boolean songFound = true;
        while (songFound==true)
        {
            songFound=false;
            MusicPlayer.setMusicPlayer("Random Song");//picks a random song
            for (int i = 0; i<playedSongs.size(); i++)//runs through the arraylist
            {
                if (songAddress.equals(playedSongs.get(i)))//if the random song is in the playedSongs arraylist
                {
                    songFound=true;//the song has been played, loop and try another one
                }
            }
            
            if (songAddress.equals(lastSong))//only when the arraylist has been emptied
            {
                songFound=true;
            }
            

        }
        MusicPlayer.playMusic();

    }

    public void actionPerformed(ActionEvent e)//runs every second
    {

        count++;//keeps track of how long a song as been playing, goes up 1 every second

        if ((count>songTime)&&(pause==false)&&(!(songAddress.equals("No Music"))))//the song has ended, it isn't paused, and the player didn't choose "No Music"
        {

            MusicPlayer.nextSong();//next

        }

    }

}
