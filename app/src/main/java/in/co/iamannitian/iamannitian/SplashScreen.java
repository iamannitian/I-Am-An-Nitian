/*
* @Project "I Am An Nitian"
* @Date "16 April 2020"
*/

package in.co.iamannitian.iamannitian;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;




public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT=4000;

    //Animation
    private Animation top_animation, bottom_animation, middle_animation;
    private View first, second, third, fourth,  fifth, main;
    private TextView splash_msg;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_aimation);
        middle_animation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        bottom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        main = findViewById(R.id.main);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);
        splash_msg = findViewById(R.id.splash_msg);
        logo = findViewById(R.id.logo);


        main.setAnimation(top_animation);
        first.setAnimation(top_animation);
        second.setAnimation(top_animation);
        third.setAnimation(top_animation);
        fourth.setAnimation(top_animation);
        fifth.setAnimation(top_animation);

        logo.setAnimation(middle_animation);

        splash_msg.setAnimation(bottom_animation);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                Intent intent= new Intent(SplashScreen.this, LoginOrSignupActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}