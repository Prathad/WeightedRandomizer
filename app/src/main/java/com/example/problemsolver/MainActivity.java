package com.example.problemsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> holdAllValues;
    TextView textView;
    View root;
    Button generateResponse;
    public static boolean adIntializerCalled = false;
    private View.OnClickListener doStuff = new View.OnClickListener() {
        public void onClick(View v) {
            Collections.shuffle(holdAllValues);
            String selectedPhrase = holdAllValues.get(0);
            textView.setText(selectedPhrase);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.mainActivityRoot);
        holdAllValues = new ArrayList<>();
        textView = findViewById(R.id.textView);
        textView.setText("?");
        generateResponse = findViewById(R.id.buttonreal);
        generateResponse.setOnClickListener(doStuff);
        for (int x = 0; x < GetInfo.holdInt.size(); x++) {
            for (int y = 0; y < Integer.valueOf(GetInfo.holdInt.get(x)); y++) {
                holdAllValues.add(GetInfo.holdString.get(x));
            }
        }
        changeColors();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Settings.class);
        GetInfo.hopLimit = 0;
        GetInfo.holdInt.clear();
        GetInfo.holdString.clear();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void changeColors() {
        Log.d("settingcheck", "Wemadeithere");
        String currentTheme = RealSettings.getDefaults("Theme", "like");
        Log.d("settingcheck", "CurrentTheme:" + currentTheme);
        if (currentTheme.equals("watermelon")) {
            Log.d("check", "We made it here 2");
            RealSettings.mainColor = "#5ddc74";//#ed134a
            RealSettings.mainColorDark = "#2ed14c";//#aa134a
            RealSettings.mainColorText = "#143119";//#470516
            RealSettings.secondColor = "#ed134a";//#5ddc74
            RealSettings.secondColorDark = "#aa134a";//#2ed14c
            RealSettings.secondColorText = "#470516";//#143119
            generateResponse.setBackgroundResource(R.drawable.randomizebuttonwatermelon);
        } else if (currentTheme.equals("dark")) {
            RealSettings.mainColor = "#2E2E2E";
            RealSettings.mainColorDark = "#636363";
            RealSettings.mainColorText = "#FFFFFF";
            RealSettings.secondColor = "#327fa8";
            RealSettings.secondColorDark = "#05054F";
            RealSettings.secondColorText = "#020217";
            generateResponse.setBackgroundResource(R.drawable.randomizebuttondark);
           // generateResponse.setTextColor(Integer.parseInt(RealSettings.mainColorText));
        }
        updateColors();
    }

    public void updateColors() {
        root.setBackgroundColor(Color.parseColor(RealSettings.mainColor));
    }
}
