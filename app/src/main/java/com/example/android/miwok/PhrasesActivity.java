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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

// get a reference to the listview
        ListView listView = (ListView) findViewById(R.id.list);

// create the array adapter
        WordAdapter wordAdapter = new WordAdapter(this, initialiseList());

// set the adapter on listview
        listView.setAdapter(wordAdapter);
    }

    private ArrayList<Word> initialiseList() {
        ArrayList<Word> list = new ArrayList<Word>();
        list.add(new Word("Where are you going?","minto wuksus"));
        list.add(new Word("What is your name?","tinnә oyaase'nә"));
        list.add(new Word("My name is...","oyaaset..."));
        list.add(new Word("How are you feeling?","michәksәs?"));
        list.add(new Word("I’m feeling good.","kuchi achit"));
        list.add(new Word("Are you coming?","әәnәs'aa?"));
        list.add(new Word("Yes, I’m coming.","hәә’ әәnәm"));
        list.add(new Word("I’m coming.","әәnәm"));
        list.add(new Word("Let’s go.","yoowutis"));
        list.add(new Word("Come here.","әnni'nem"));

        return list;
    }
}
