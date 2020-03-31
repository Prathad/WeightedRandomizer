package com.example.problemsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RealSettings extends AppCompatActivity {
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static SharedPreferences settings;
    SharedPreferences.Editor prefEditor;
    RadioButton radioButtonWatermelon;
    RadioButton radioButtonDark;
    TextView textView2;
    public static String mainColor = "";
    public static String mainColorDark = "";
    public static String mainColorText = "";
    public static String secondColor = "";
    public static String secondColorDark = "";
    public static String secondColorText = "";
    int originalID = 0;
    boolean firstChange = true;
    ColorStateList colorStateList;
    View root;
    //For the transition
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;

    //For the Transition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_settings);
        View someView = findViewById(R.id.rootView);
        root = someView.getRootView();
        textView2 = findViewById(R.id.textView2);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        settings = getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        prefEditor = settings.edit();
        String gang = settings.getString("Theme", "like");
        if (false) {
            prefEditor.clear();
            prefEditor.putString("Theme", "dark");
            prefEditor.apply();
        }
        radioButtonWatermelon = findViewById(R.id.radioButtonWatermelon);
        radioButtonDark = findViewById(R.id.radioButtonDark);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group == findViewById(R.id.radioGroup)) {
                    int currentChecked = 0;
                    Log.d("colornot", "id:" + checkedId);
                    if (firstChange) {
                        originalID = checkedId;
                        firstChange = false;
                    }
                    Log.d("radio", "the radio group has worked");
                    if (checkedId == 2131230856) {
                        currentChecked = 2131230856;
                        Log.d("colornot", "changedtodark");
                        prefEditor.clear();
                        prefEditor.putString("Theme", "dark");
                        prefEditor.apply();
                    } else if (checkedId == 2131230857) {
                        currentChecked = 2131230857;
                        Log.d("colornot", "changedtowatermelon");
                        prefEditor.clear();
                        prefEditor.putString("Theme", "watermelon");
                        prefEditor.apply();
                    }
                    if (currentChecked != originalID) {
                        Toast.makeText(RealSettings.this, "Restart App for Changes to Properly Apply", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        String currentTheme = settings.getString("Theme", "like");
        Log.d("check", "We made it here");
        if (currentTheme.equals("watermelon")) {
            Log.d("check", "We made it here 2");
            radioButtonWatermelon.toggle();
            mainColor = "#5ddc74";
            mainColorDark = "#2ed14c";
            mainColorText = "#143119";
            secondColor = "#ed134a";
            secondColorDark = "#aa134a";
            secondColorText = "#470516";
           /* root.setBackgroundColor(Color.parseColor(mainColor));
            radioButtonDark.setTextColor(Color.parseColor(mainColorText));
            radioButtonWatermelon.setTextColor(Color.parseColor(mainColorText));
            colorStateList = new ColorStateList(
                    new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}}, new int[]{Color.parseColor(mainColorText) , Color.parseColor(mainColorText)});
            radioButtonDark.setButtonTintList(colorStateList);//set the color tint list
            radioButtonWatermelon.setButtonTintList(colorStateList);
            textView2.setTextColor(Color.parseColor(mainColorText));*/
            updateColors();

        } else if (currentTheme.equals("dark")) {
            Log.d("check", "We made it here 3");
            radioButtonDark.toggle();
            mainColor = "#2E2E2E";
            mainColorDark = "#636363";
            mainColorText = "#FFFFFF";
            secondColor = "#06067D";
            secondColorDark = "#05054F";
            secondColorText = "#020217";
            updateColors();
        } else {
            Log.d("check", "We made it here 4");
            radioButtonWatermelon.toggle();
            mainColor = "#5ddc74";//#ed134a
            mainColorDark = "#2ed14c";//#aa134a
            mainColorText = "#143119";//#470516
            secondColor = "#ed134a";//#5ddc74
            secondColorDark = "#aa134a";//#2ed14c
            secondColorText = "#470516";//#143119
            updateColors();
        }
        Log.d("check", "We made it here 5");
       /* int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("radio","ID Selected:" + selectedId);
        if(selectedId == 2131230853){
            prefEditor.clear();
            prefEditor.putString("Theme","dark");
            prefEditor.apply();
            Log.d("radio","ID: Dark");
        }else if(selectedId == 2131230854){
            prefEditor.clear();
            prefEditor.putString("Theme","watermelon");
            prefEditor.apply();
            Log.d("radio","ID: Watermelon");
        }*/
        //prefEditor.putString("Theme","watermelon");
        //prefEditor.apply();

        //For the transition
        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.rootView);

        if (savedInstanceState == null && intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) && intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }
        //For the Transition
    }

    public void updateColors() {
        root.setBackgroundColor(Color.parseColor(mainColor));
        radioButtonDark.setTextColor(Color.parseColor(mainColorText));
        radioButtonWatermelon.setTextColor(Color.parseColor(mainColorText));
        colorStateList = new ColorStateList(
                new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}}, new int[]{Color.parseColor(mainColorText), Color.parseColor(mainColorText)});
        radioButtonDark.setButtonTintList(colorStateList);//set the color tint list
        radioButtonWatermelon.setButtonTintList(colorStateList);
        textView2.setTextColor(Color.parseColor(mainColorText));
    }

    public static String getDefaults(String key, String defaultValue) {
        String gotit = settings.getString(key, defaultValue);
        return gotit;
    }

    protected void revealActivity(int x, int y) {
        float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
        circularReveal.setDuration(400);
        circularReveal.setInterpolator(new AccelerateInterpolator());

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    protected void unRevealActivity() {
        float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                rootLayout, revealX, revealY, finalRadius, 0);

        circularReveal.setDuration(400);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rootLayout.setVisibility(View.INVISIBLE);
                finish();
            }
        });


        circularReveal.start();
    }
}
