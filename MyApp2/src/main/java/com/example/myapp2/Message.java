package com.example.myapp2;

import android.content.Context;
import android.widget.Toast;

/**
 * Prints message on screen
 */
public class Message {
    public static void message(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
