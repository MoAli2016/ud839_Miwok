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

public class FamilyActivity extends AppCompatActivity {
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
        WordAdapter wordAdapter = new WordAdapter(this, mWords,R.color.category_family);

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
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mListener);
                }
            }
        });
    }

    private ArrayList<Word> initialiseList() {
        ArrayList<Word> list = new ArrayList<Word>();
        list.add(new Word("father","әpә", R.drawable.family_father, R.raw.family_father));
        list.add(new Word("mother","әṭa", R.drawable.family_mother, R.raw.family_mother));
        list.add(new Word("son","angsi", R.drawable.family_son, R.raw.family_son));
        list.add(new Word("daughter","tune", R.drawable.family_daughter, R.raw.family_daughter));
        list.add(new Word("older brother","taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        list.add(new Word("younger brother","chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        list.add(new Word("older sister","teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        list.add(new Word("younger sister","kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        list.add(new Word("grandmother","ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        list.add(new Word("grandfather","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        return list;
    }

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
            Toast.makeText(FamilyActivity.this, "autfocus request failed", Toast.LENGTH_SHORT).show();
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
