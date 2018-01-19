package com.example.dyy.fmcw;

/**
 * Created by dyy on 2016/10/29.
 */
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class PlayRecord extends Thread {
    private AudioTrack rAudioTrack;
    // 采样率
    private int rSampleRateInHz = 44100;
    private int rChannelConfig = AudioFormat.CHANNEL_OUT_MONO;

    private short[] data;
    int bufferSize;
    int mFrequency=400;
    short[] inputdata;

    //
    public PlayRecord(short[] inputrecord){
        for (int i=0;i<inputrecord.length;i++){
            Log.e("short we hear", String.valueOf(inputrecord[i]));
        }
        inputdata=inputrecord;
    }

    public void run() {
        super.run();

        bufferSize = AudioTrack.getMinBufferSize(rSampleRateInHz, rChannelConfig, AudioFormat.ENCODING_PCM_16BIT);
        rAudioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                rSampleRateInHz,
                rChannelConfig,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM);

        try {
            if (null != rAudioTrack) {
                //produce acouctic sound from smartphone
                rAudioTrack.play();
                //write sine signal data to buffer(not sure it's buffer, but it just work ha!)
                if (AudioTrack.PLAYSTATE_STOPPED != rAudioTrack.getPlayState()) {
                    rAudioTrack.write(inputdata, 0, inputdata.length);
                    Log.e("writing!!!!!","writing!!!!!");
                }

                Log.e("playing!!!!!","playing!!!!!");

            }
            Log.e("ply function here","playfunctinhrer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
