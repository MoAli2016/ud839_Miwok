package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 24/03/2018.
 */

public final class  WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(@NonNull Context context, ArrayList<Word> words) {
        super(context, 0, words);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item, parent,false);
        }

        Word word = this.getItem(position);

        TextView miwokWordView = (TextView ) listItemView.findViewById(R.id.miwok_text_view);
        miwokWordView.setText(word.getMiwokTranslation());

        TextView defaultWordView = (TextView ) listItemView.findViewById(R.id.default_text_view);
        defaultWordView.setText(word.getDefaultTranslation());

        //find the image view with id image
        ImageView mImageView = (ImageView) listItemView.findViewById(R.id.imageItem);
        
        if(word.hasImage()) {

            //get the imageResource get and set it as source of the image view
            mImageView.setImageResource(word.getImageResourceId());

            //make the image view visible
            mImageView.setVisibility(View.VISIBLE);
        }
        else {
            mImageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
