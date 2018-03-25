package com.example.android.miwok;

/**
 * Created by User on 24/03/2018.
 */

public final class Word {

    private int mAudioResourceId;
    private String mtDefaultTranslation;
    private String mtMiwokTranslation;
    private int mImageResourceId;
    private boolean mHasImage;
    private final int NO_IMAGE_PROVIDED = -1;


    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        this.mtDefaultTranslation = defaultTranslation;
        this.mtMiwokTranslation = miwokTranslation;
        this.mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId){
        this.mtDefaultTranslation = defaultTranslation;
        this.mtMiwokTranslation = miwokTranslation;
        this.mImageResourceId = imageResourceId;
        this.mAudioResourceId = audioResourceId;
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
        return mHasImage = mImageResourceId > NO_IMAGE_PROVIDED;
    }

    public String ToString(){
        return mtDefaultTranslation + " " + mtMiwokTranslation;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
