package com.example.problemsolver;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ChangeCursorColor {
    public static void changeCursor(EditText editText, int drawable){
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");//mCursorDrawableRes
            f.setAccessible(true);
            Log.d("colordebug","Inside the method");
            f.set(editText,drawable);
            Log.d("colordebug","Inside the method 2");
        } catch (Exception ignored) {
        }
    }
}
