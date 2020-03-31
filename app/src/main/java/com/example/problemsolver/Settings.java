package com.example.problemsolver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.CircularPropagation;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.problemsolver.RealSettings.GAME_PREFERENCES;
import static com.example.problemsolver.RealSettings.mainColor;
import static com.example.problemsolver.RealSettings.secondColor;
import static com.example.problemsolver.RealSettings.settings;

public class Settings extends AppCompatActivity {
    //@Override
   String mainColor = "";
    String mainColorDark = "";
    String mainColorText = "";
    String secondColor = "";
    String secondColorDark = "";
    String secondColorText = "";
    View root;
    Button button;
    public boolean adIntializerCalled = false;
    public static AdView mAdView;
    public static AdRequest adRequest;
    Button setting = null;
    boolean stillOnIt = false;
    EditText editText;
    TextView textViewHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        textViewHelper = findViewById(R.id.textView3);
        editText = findViewById(R.id.text245);
        setting = findViewById(R.id.settingsbutton);
        setting.setOnClickListener(settingsListener);
        //setting.setOnTouchListener(mCorkyTouch);
        if (!adIntializerCalled) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }
        RealSettings.settings = getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        root = findViewById(R.id.rootViewSettings);
        mAdView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        button = findViewById(R.id.button3);
        button.setOnClickListener(mCorkyListener);
        changeColors();
        // mEditor.putString("tag", "69").commit();
        //For the transition

        //For the transition
    }
    /*protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_settings);
        changeColors();
    }*/

    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        int trueValue;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onClick(View v) {
            String extract = editText.getText().toString();
            try {
                trueValue = Integer.valueOf(extract);
                GetInfo.hopLimit = trueValue;
                openGetInfo();
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please Enter A Number", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private View.OnClickListener settingsListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onClick(View v) {
            openRealSettings();
        }
    };
    public View.OnTouchListener mCorkyTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setting.setBackgroundResource(R.drawable.darksettingsicon);
                stillOnIt = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                setting.setBackgroundResource(R.drawable.settingsicon);
               /* if (stillOnIt) {
                    openRealSettings();
                }*/
                stillOnIt = false;
            }
            if (event.getAction() == MotionEvent.ACTION_CANCEL) {

                Log.d("value", "Action Canceled");
                setting.setBackgroundResource(R.drawable.settingsicon);
                stillOnIt = false;
            }
            Log.d("value", "Value of stillClicked: " + stillOnIt);
            return true;
        }
    };

    public void openGetInfo() {
        Intent intent = new Intent(this, GetInfo.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openRealSettings() {
        presentActivity(setting);
        /*Intent intent = new Intent(this, RealSettings.class);
        getWindow().setExitTransition(new);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());*/

    }

    public void changeColors() {
        Log.d("settingcheck", "Wemadeithere");
        String currentTheme = RealSettings.getDefaults("Theme", "like");
        Log.d("settingcheck", "CurrentTheme:" + currentTheme);
        if (currentTheme.equals("watermelon")) {
            Log.d("check", "We made it here 2");
            mainColor = "#5ddc74";//#ed134a
            mainColorDark = "#2ed14c";//#aa134a
            mainColorText = "#143119";//#470516
            secondColor = "#ed134a";//#5ddc74
            secondColorDark = "#aa134a";//#2ed14c
            secondColorText = "#470516";//#143119
           /* mainColor = "#5ddc74";//#ed134a
            RealSettings.mainColorDark = "#2ed14c";//#aa134a
            RealSettings.mainColorText = "#143119";//#470516
            secondColor = "#ed134a";//#5ddc74
            RealSettings.secondColorDark = "#aa134a";//#2ed14c
            RealSettings.secondColorText = "#470516";//#143119*/
            textViewHelper.setBackgroundResource(R.drawable.nextbutton);
            setting.setBackgroundResource(R.drawable.settingsicon);
            button.setBackgroundResource(R.drawable.nextbutton);
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}}, new int[]{Color.parseColor(secondColor), Color.parseColor(secondColor)});
            editText.setBackgroundTintList(colorStateList);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                editText.setTextCursorDrawable(R.drawable.watermeloncursorcolor);
            } else {
                Log.d("colordebug", "We changed it!");
                ChangeCursorColor.changeCursor(editText, R.drawable.watermeloncursorcolor);
            }
        } else if (currentTheme.equals("dark")) {
            mainColor = "#2E2E2E";
            mainColorDark = "#636363";
            mainColorText = "#FFFFFF";
            secondColor = "#327fa8";
            secondColorDark = "#05054F";
            secondColorText = "#020217";
            /*RealSettings.mainColorDark = "#636363";
            RealSettings.mainColorText = "#FFFFFF";
            secondColor = "#327fa8";
            RealSettings.secondColorDark = "#05054F";
            RealSettings.secondColorText = "#020217";*/
            textViewHelper.setBackgroundResource(R.drawable.darkmodenextbutton);
            editText.setTextColor(Color.WHITE);
            setting.setBackgroundResource(R.drawable.darksettingsicon);
            button.setBackgroundResource(R.drawable.darkmodenextbutton);
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}}, new int[]{Color.parseColor(secondColor), Color.parseColor(secondColor)});
            editText.setBackgroundTintList(colorStateList);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                editText.setTextCursorDrawable(R.drawable.cursorcolor);
            } else {
                Log.d("colordebug", "We changed it!");
                ChangeCursorColor.changeCursor(editText, R.drawable.cursorcolor);
            }
        }else{
            mainColor = "#5ddc74";//#ed134a
            mainColorDark = "#2ed14c";//#aa134a
            mainColorText = "#143119";//#470516
            secondColor = "#ed134a";//#5ddc74
            secondColorDark = "#aa134a";//#2ed14c
            secondColorText = "#470516";//#143119
        }
        updateColors();
    }

    public void updateColors() {
            root.setBackgroundColor(Color.parseColor(mainColor));
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, RealSettings.class);
        intent.putExtra(RealSettings.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(RealSettings.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}