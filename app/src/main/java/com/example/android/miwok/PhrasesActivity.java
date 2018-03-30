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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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
        WordAdapter wordAdapter = new WordAdapter(this, mWords, R.color.category_phrases);

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
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mListener);
                }
            }
        });
    }

    private ArrayList<Word> initialiseList() {
        ArrayList<Word> list = new ArrayList<Word>();
        list.add(new Word("Where are you going?","minto wuksus", R.raw.phrase_where_are_you_going));
        list.add(new Word("What is your name?","tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        list.add(new Word("My name is...","oyaaset...", R.raw.phrase_my_name_is));
        list.add(new Word("How are you feeling?","michәksәs?", R.raw.phrase_how_are_you_feeling));
        list.add(new Word("I’m feeling good.","kuchi achit", R.raw.phrase_im_feeling_good));
        list.add(new Word("Are you coming?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        list.add(new Word("Yes, I’m coming.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        list.add(new Word("I’m coming.","әәnәm", R.raw.phrase_im_coming));
        list.add(new Word("Let’s go.","yoowutis", R.raw.phrase_lets_go));
        list.add(new Word("Come here.","әnni'nem", R.raw.phrase_come_here));

        return list;
    }

    AudioManager.OnAudioFocusChangeListener focusListener = new AudioManager.OnAudioFocusChangeListener() {
        //3
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.i("PhrasesActivity", "AUDIOFOCUS_LOSS_TRANSIENT");
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;

                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.i("PhrasesActivity", "AUDIOFOCUS_LOSS");
                    release();
                    break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.i("PhrasesActivity", "default");
                    mMediaPlayer.start();
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        release();
    }

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
            Toast.makeText(PhrasesActivity.this, "autfocus request failed", Toast.LENGTH_SHORT).show();
        }
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
