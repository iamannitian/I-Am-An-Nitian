package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD:app/src/main/java/in/co/iamannitian/iamannitian/loginscreen.java

public class loginscreen extends AppCompatActivity {

    Button btn1;
=======
>>>>>>> e3adb12d007f531d606a462e7e187cc4f9387902:app/src/main/java/in/co/iamannitian/iamannitian/LoginOrSignupActivity.java

public class LoginOrSignupActivity extends AppCompatActivity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);

        btn1= findViewById(R.id.logsignbtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(LoginOrSignupActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });
    }
}
