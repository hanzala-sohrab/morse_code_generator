// Tested on Windows 10 and Ubuntu 18.04 (Oracle jdk 1.8.0_131 and above) on June 29 2018
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.nio.file.Paths;

public class Morse_sound
{
    static String morse[] = {"._", "_...", "_._.", "_..", ".", ".._.", "__.", "....", "..", ".___", "_._", "._..",
            "__", "_.", "___", ".__.", "__._", "._.", "...", "_", "..__", "..._", ".__", "_.._", 
            "_.__", "__.."};
    static int ONE_UNIT = 250;

    public static void play(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public static void main(String[] args)throws Exception
    {
        // inserts correct file path separator on Linux and Windows
        // works on Linux and Windows
		// Converting Path object to String using normalize() and toString()
        String dot = Paths.get("sounds","dot.wav").normalize().toString();
		String dash = Paths.get("sounds", "dash.wav").normalize().toString();

        String s, code = "";
        if (args.length == 0)	 // when user didn't pass a command line argument
        {
            System.out.print("\nEnter a message : ");
            s = ((new BufferedReader(new InputStreamReader(System.in))).readLine());
        }
        else if (args.length == 1)	      		// when user passed a command line argument
            s = args[0];
		else {		// Too many arguments
			System.out.println("Too many arguments\nUsage:\tjava Morse_sound [MESSAGE]\n\tor\n\tjava Morse_sound");
			System.exit(1);
			s = " ";
		}

        s = s.toUpperCase();
        for (int i = 0; i < s.length(); ++i)
        {
            if (s.charAt(i) != ' ')
            {
                code = morse[s.charAt(i) - 65];
                for (int j = 0; j < code.length(); ++j)
                {
                    System.out.print(code.charAt(j));
                    if (code.charAt(j) == '.')
                    {
                        play(dot);
                        Thread.sleep(ONE_UNIT);
                    }
                    else if (code.charAt(j) == '_')
                    {
                        play(dash);
                        Thread.sleep(3 * ONE_UNIT);
                    }
                    Thread.sleep(ONE_UNIT);
                }
                Thread.sleep(2 * ONE_UNIT);
                System.out.print(" ");
            }
            else if (s.charAt(i) == ' ')
            {
                System.out.print("\n");
                Thread.sleep(4 * ONE_UNIT);
            }
        }
        System.out.println("\n");        
    }
}
