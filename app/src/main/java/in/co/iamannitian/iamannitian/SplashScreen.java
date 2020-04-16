/*
* @Project "I Am An Nitian"
* @Date "16 April 2020"
 */

package in.co.iamannitian.iamannitian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT=4000;
    public static final String LoginData = "LoginData";

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp = getSharedPreferences(LoginData, Context.MODE_PRIVATE);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(sp.contains("isLoggedIn")) {
                    Intent intent= new Intent(SplashScreen.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent= new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);

    }
}
