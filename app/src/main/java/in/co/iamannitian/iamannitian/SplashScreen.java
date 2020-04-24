/*
* @Project "I Am An Nitian"
* @Date "16 April 2020"
*dhdvvfdfhb
 */

package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {

                    Intent intent= new Intent(SplashScreen.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();

            }
        },SPLASH_TIME_OUT);

    }
}
