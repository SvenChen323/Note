package com.example.android.note;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 *
 */

public class BaseActivity extends AppCompatActivity {

    private static final int THEME_GREEN = 0;
    private static final int THEME_BLUE = 1;
    private static final int THEME_RED = 2;
    private static final int THEME_YELLOW = 3;
    private static final int THEME_PINK= 4;
    private static final int THEME_PURPLE= 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setBaseTheme();

    }

    /**
     * 设置主题
     */
    protected void setBaseTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                "theme", MODE_PRIVATE);
        int themeType = sharedPreferences.getInt("theme_type", 0);
        int themeId;
        switch (themeType){
            case THEME_GREEN:
                themeId = R.style.AppTheme_Base_Green;
                break;
            case THEME_BLUE:
                themeId = R.style.AppTheme_Base_Blue;
                break;
            case THEME_RED:
                themeId = R.style.AppTheme_Base_Red;
                break;
            case THEME_YELLOW:
                themeId = R.style.AppTheme_Base_Yellow;
                break;
            case THEME_PINK:
                themeId = R.style.AppTheme_Base_Pink;
                break;
            case THEME_PURPLE:
                themeId = R.style.AppTheme_Base_Purple;
                break;
            default:
                themeId = R.style.AppTheme;
                break;
        }

        setTheme(themeId);

    }


}
