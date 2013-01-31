package view;

import java.io.*;

import sun.audio.*;


public class Sound
{
  public Sound(String path) throws Exception {
	  	InputStream in = new FileInputStream(path);
	  
	    // create an audiostream from the inputstream
	    AudioStream audioStream = new AudioStream(in);

	    // play the audio clip with the audioplayer class
	    AudioPlayer.player.start(audioStream);

	}
}