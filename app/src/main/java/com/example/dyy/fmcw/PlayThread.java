package com.example.dyy.fmcw;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dyy on 2016/10/25.
 */

public class PlayThread extends Thread {

    private AudioTrack mAudioTrack;
    // 采样率
    private int mSampleRateInHz = 44100;
    private int mChannelConfig = AudioFormat.CHANNEL_OUT_MONO;
    // 双声道

    private static final String TAG = "PlayThread";
    private short[] data;
    int bufferSize;
    int mFrequency=400;


    // actually dont know this, maybe a constructor? used for initialization a thread in line 32 in MainActivity
    public PlayThread(Activity activity) {

        }

    /**
     * get buffersize available, then use it in initializing AudioTrack object for acoustic signal generation
     */
    public void run() {

        bufferSize = AudioTrack.getMinBufferSize(mSampleRateInHz, mChannelConfig, AudioFormat.ENCODING_PCM_16BIT);
        mAudioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                mSampleRateInHz,
                mChannelConfig,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM);

        data=initData(bufferSize);

        try {
            if (null != mAudioTrack) {
                //produce acouctic sound from smartphone
                mAudioTrack.play();
                //write sine signal data to buffer(not sure it's buffer, but it just work ha!)
                if (AudioTrack.PLAYSTATE_STOPPED != mAudioTrack.getPlayState()) {
                        mAudioTrack.write(data, 0, data.length);
                        Log.e("writing!!!!!","writing!!!!!");
                }

                Log.e("playing!!!!!","playing!!!!!");

            }
            Log.e("ply function here","playfunctinhrer");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    //generate sine signal sampling data for aousric sound production
    private short[] initData(int buff) {
        double phase = 0.0;
        int amp = 30000;
        bufferSize=buff;
        short[] tdata = new short[bufferSize];
        Log.e("@@@buffersize", Integer.toString(bufferSize));


        double phaseIncrement = (2 * Math.PI * mFrequency) / mSampleRateInHz;
        Log.e("@@@phaseincrement", Double.toString(phaseIncrement));

        for (int i = 0; i < bufferSize; i++) {
            tdata[i] = (short) (amp * Math.sin(phase));
            Log.e("@@@data111", String.valueOf(tdata[i]));
            phase += phaseIncrement;
           // Log.i(TAG, "initData: isLeft = "  + "  buffer[" + i + "] = " + tdata[i]);
            Log.e("@@@data", String.valueOf(tdata[i]));
        }
        Log.e("init data","initdata");
        return tdata;

    }

//useful for later use, including set balance of channel, switch working status of channel
//    /**
//     * 设置左右声道平衡
//     *
//     * @param max     最大值
//     * @param balance 当前值
//     */
//    public void setBalance(int max, int balance) {
//        float b = (float) balance / (float) max;
//        Log.i(TAG, "setBalance: b = " + b);
//        if (null != mAudioTrack)
//            mAudioTrack.setStereoVolume(1 - b, b);
//    }
//
//    /**
//     * 设置左右声道是否可用
//     *
//     * @param left  左声道
//     * @param right 右声道
//     */
//    public void setChannel(boolean left, boolean right) {
//        if (null != mAudioTrack) {
//            mAudioTrack.setStereoVolume(left ? 1 : 0, right ? 1 : 0);
//            mAudioTrack.play();
//        }
//    }
//
    public void pause() {
        if (null != mAudioTrack)
            mAudioTrack.pause();
    }
//
//    public void play() {
////        if (null != mAudioTrack)
////            mAudioTrack.play();
//        bufferSize = AudioTrack.getMinBufferSize(mSampleRateInHz, mChannelConfig, AudioFormat.ENCODING_PCM_16BIT);
////        mAudioTrack = new AudioTrack(
////                AudioManager.STREAM_MUSIC,
////                mSampleRateInHz,
////                mChannelConfig,
////                AudioFormat.ENCODING_PCM_16BIT,
////                bufferSize,
////                AudioTrack.MODE_STREAM);
//        data=initData(bufferSize);
//        try {
//            if (null != mAudioTrack) {
//                mAudioTrack.play();
//                Log.e("playing!!!!!","playing!!!!!");
//                while (AudioTrack.PLAYSTATE_STOPPED != mAudioTrack.getPlayState()) {
//                    mAudioTrack.write(data, 0, data.length);
//                    Log.e("writing!!!!!","writing!!!!!");
//                }
//            }
//            Log.e("ply function here","playfunctinhrer");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void stopp() {
        releaseAudioTrack();
    }

    private void releaseAudioTrack() {
        if (null != mAudioTrack) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }


}

