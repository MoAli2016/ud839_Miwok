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

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private ArrayList<Word> mWords;

    private MediaPlayer.OnCompletionListener mListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Helper.release(mediaPlayer);
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
                Word word = mWords.get(position);
                Helper.release(mMediaPlayer);
                mMediaPlayer  = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mListener);
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
}
