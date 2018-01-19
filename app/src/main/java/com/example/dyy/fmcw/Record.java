package com.example.dyy.fmcw;

/**
 * Created by dyy on 2016/10/29.
 */
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class Record extends Thread {
    final int SAMPLE_RATE = 44100; // The sampling rate

    short[] audioBuffer;



    public void run() {
        // buffer size in bytes
        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = SAMPLE_RATE * 2;
        }

        //save acoustic data to audioBuffer
        audioBuffer = new short[bufferSize / 2];

        //generate AudioRecord object for audio recording
        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e("failed", "Audio Record can't initialize!");
            return;
        }

        //start recording
        record.startRecording();


        int numberOfShort = record.read(audioBuffer, 0, audioBuffer.length);

//for test , output number of shorts we hear and short value recorded
        Log.e("number of short we hear", String.valueOf(numberOfShort));
        for (int i=0;i<numberOfShort;i++){
            Log.e("short we hear", String.valueOf(audioBuffer[i]));
        }
        // Do something with the audioBuffer

//relaese the record resources
          record.stop();
        record.release();

    }

    public void stopRecord() {

    }



    /**
     * get recorded short list and return the list
     * @return audio signal
     */

    public short[] getRecord(){

        return audioBuffer;

    }
}
