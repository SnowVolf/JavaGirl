package ru.SnowVolf.JavaGirl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Snow Volf on 11.12.2016.
 */

public class AboutActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
        setContentView(R.layout.about_fragment);
    }

    public void onBackArrowPressed(View v){
        finish();
    }
}
