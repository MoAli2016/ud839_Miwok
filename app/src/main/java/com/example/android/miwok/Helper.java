package com.example.android.miwok;

import android.media.MediaPlayer;

/**
 * Created by User on 25/03/2018.
 */

public final class Helper {
    public static void release(MediaPlayer mediaPlayer) {
/**
 * Clean up the media player by releasing its resources.
 */
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }
}
