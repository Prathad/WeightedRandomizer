package com.example.problemsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import static com.example.problemsolver.RealSettings.secondColor;

public class GetInfo extends AppCompatActivity {
    public static int hopLimit = 0;
    public static ArrayList<String> holdString = new ArrayList<>();
    public static ArrayList<String> holdInt = new ArrayList<>();
    Button next;
    View rootviewinfo;
    EditText editTextNum;
    EditText editTextString;
    TextView textViewNumOfPeople;
    TextView textViewRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        AdView mAdView = findViewById(R.id.adView);
        editTextNum = findViewById(R.id.realnum);
        editTextString = findViewById(R.id.realtext);
        textViewNumOfPeople = findViewById(R.id.numofpeeptextview);
        textViewRequest = findViewById(R.id.prompttextview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        next = findViewById(R.id.next);
        next.setOnClickListener(nextGetInfo);
        rootviewinfo = findViewById(R.id.rootviewinfo);
        changeColors();
    }

    public void openGetInfo() {
        Intent intent = new Intent(this, GetInfo.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private View.OnClickListener nextGetInfo = new View.OnClickListener() {
        public void onClick(View v) {
            String phrase = editTextString.getText().toString();
            holdString.add(phrase);
            int howMany = 0;
            try {
                howMany = Integer.parseInt(editTextNum.getText().toString());
                holdInt.add(String.valueOf(howMany));
                Log.d("test", "Value of hopLimit:" + hopLimit);
                hopLimit--;
                if (hopLimit > 0) {
                    //GetInfo.hopLimit--;
                    openGetInfo();
                } else {
                    openMainActivity();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Enter a number for # of people", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void changeColors() {
        Log.d("settingcheck", "Wemadeithere");
        String currentTheme = RealSettings.getDefaults("Theme", "like");
        Log.d("settingcheck", "CurrentTheme:" + currentTheme);
          if (currentTheme.equals("dark")) {
            RealSettings.mainColor = "#2E2E2E";
            RealSettings.mainColorDark = "#636363";
            RealSettings.mainColorText = "#FFFFFF";
            RealSettings.secondColor = "#327fa8";
            RealSettings.secondColorDark = "#05054F";
            RealSettings.secondColorText = "#020217";
            textViewNumOfPeople.setBackgroundResource(R.drawable.darkmodenextbutton);
            textViewRequest.setBackgroundResource(R.drawable.darkmodenextbutton);
            editTextNum.setTextColor(Color.WHITE);
            editTextString.setTextColor(Color.WHITE);
            next.setBackgroundResource(R.drawable.darkmodenextbutton);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                editTextNum.setTextCursorDrawable(R.drawable.cursorcolor);
            }else{
                ChangeCursorColor.changeCursor(editTextNum,R.drawable.cursorcolor);
            }
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}}, new int[]{Color.parseColor(secondColor) , Color.parseColor(secondColor)});
            editTextNum.setBackgroundTintList(colorStateList);
            editTextString.setBackgroundTintList(colorStateList);
            ChangeCursorColor.changeCursor(editTextString,R.drawable.cursorcolor);
        }else {
              RealSettings.mainColor = "#5ddc74";//#ed134a
              RealSettings.mainColorDark = "#2ed14c";//#aa134a
              RealSettings.mainColorText = "#143119";//#470516
              RealSettings.secondColor = "#ed134a";//#5ddc74
              RealSettings.secondColorDark = "#aa134a";//#2ed14c
              RealSettings.secondColorText = "#470516";//#143119
              // Log.d("check", "We made it here 2");
              textViewNumOfPeople.setBackgroundResource(R.drawable.nextbutton);
              textViewRequest.setBackgroundResource(R.drawable.nextbutton);
              next.setBackgroundResource(R.drawable.nextbutton);
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                  editTextNum.setTextCursorDrawable(R.drawable.watermeloncursorcolor);
              }else{
                  ChangeCursorColor.changeCursor(editTextNum,R.drawable.watermeloncursorcolor);
              }
              ColorStateList colorStateList = new ColorStateList(
                      new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}}, new int[]{Color.parseColor(secondColor) , Color.parseColor(secondColor)});
              editTextNum.setBackgroundTintList(colorStateList);
              editTextString.setBackgroundTintList(colorStateList);
              ChangeCursorColor.changeCursor(editTextString,R.drawable.watermeloncursorcolor);
          }
        updateColors();
    }

    public void updateColors() {
        rootviewinfo.setBackgroundColor(Color.parseColor(RealSettings.mainColor));
    }
}