package com.example.android.miwok;

/**
 * Created by User on 24/03/2018.
 */

public final class Word {

    private String mtDefaultTranslation;
    private String mtMiwokTranslation;
    private int mImageResourceId;
    private boolean mHasImage;


    public Word(String defaultTranslation, String miwokTranslation){
        this.mtDefaultTranslation = defaultTranslation;
        this.mtMiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId){
        this.mtDefaultTranslation = defaultTranslation;
        this.mtMiwokTranslation = miwokTranslation;
        this.mImageResourceId = imageResourceId;
    }

    public String getDefaultTranslation(){
        return mtDefaultTranslation;
    }

    public String getMiwokTranslation(){
        return mtMiwokTranslation;
    }

    public int getImageResourceId(){
        return  this.mImageResourceId;
    }
    
    public boolean hasImage(){
        return mHasImage;
    }

    public String ToString(){
        return mtDefaultTranslation + " " + mtMiwokTranslation;
    }
}
