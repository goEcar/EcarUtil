package urils.ecaray.com.ecarutils.Utils;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

public class MusicHelper implements OnCompletionListener {

	MediaPlayer mediaPlayer;
	Activity activity;
	 
	AssetManager assets;
	AssetFileDescriptor assetDescriptor;
	
	
	boolean isPrepared = false;
	public MusicHelper(Activity activity){
		 
		this.activity = activity;
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		assets=activity.getAssets();
	}
	
	public void InviData(String str){
		if(mediaPlayer==null){
			
		}else{
			distroy();
		}
		mediaPlayer = new MediaPlayer();
		try {
			assetDescriptor=assets.openFd("" +str);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
					assetDescriptor.getStartOffset(),
					assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load music");
		}
	}
	
	public MusicHelper(int resid){
		if(mediaPlayer==null){
			
		}else{
			distroy();
		}
		mediaPlayer = MediaPlayer.create(this.activity, resid);
		try {
			mediaPlayer.prepare();
			isPrepared=true;
		
			mediaPlayer.setOnCompletionListener(this);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void distroy() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer=null;
	}

	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	public boolean isStopped() {
		return !isPrepared;
	}

	public void pause() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	public void play() {
		if (mediaPlayer.isPlaying())
			return;

		try {
			synchronized (this) {
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	public void setVolume(float volume) {
		if(!isPrepared){
			return;
		}
		mediaPlayer.setVolume(volume, volume);
	}

	public void stop() {
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
		}
	}

	public void onCompletion(MediaPlayer player) {
		synchronized (this) {
			isPrepared = false;
		}
	}
	
	public  void initMusic(String name) {
		InviData(name);
		setLooping(false);
	}
	public  void startMusic() {
		setVolume(1);
		play();

	}
	
	

	
}
