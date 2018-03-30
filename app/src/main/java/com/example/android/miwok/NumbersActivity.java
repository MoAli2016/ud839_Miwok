/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private ArrayList<Word> mWords;
    private boolean mAudioFocusGranted = false;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            release();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

// get a reference to the listview
        ListView listView = (ListView) findViewById(R.id.list);

        mWords = initialiseList();
// create the array adapter
        WordAdapter wordAdapter = new WordAdapter(this, mWords, R.color.category_numbers);

// set the adapter on listview
        listView.setAdapter(wordAdapter);

// configure onclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                release();
                Word word = mWords.get(position);

                manageAndGrantAudioFocus();
                if (mAudioFocusGranted) {
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mListener);
                }
            }
        });
    }

    private ArrayList<Word> initialiseList() {
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na aacha", R.drawable.number_ten, R.raw.number_ten));

        return words;
    }

    //1. request the audio focus
    //2. create an instance of OnAudioFocusChangeListener and implement callback
    //3. Adapt playback behaviour when Audio focus state changes
    //4.Release the audio focus resource when no longer needed

    //2
    AudioManager.OnAudioFocusChangeListener focusListener = new AudioManager.OnAudioFocusChangeListener() {
        //3
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.i("NumbersActivity", "AUDIOFOCUS_LOSS_TRANSIENT");
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;

                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.i("NumbersActivity", "AUDIOFOCUS_LOSS");
                    release();
                    break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.i("NumbersActivity", "default");
                    mMediaPlayer.start();
                    break;
            }
        }
    };

    private void manageAndGrantAudioFocus() {
        //1
        mAudioManager = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        int result = mAudioManager.requestAudioFocus(
                focusListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mAudioFocusGranted = true;
        } else {//if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED){
            Toast.makeText(NumbersActivity.this, "autfocus request failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        release();
    }

    private void release() {
/**
 * Clean up the media player by releasing its resources.
 */
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(focusListener);
        }
    }


}
