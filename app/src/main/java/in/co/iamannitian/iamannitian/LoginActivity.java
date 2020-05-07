package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView link;
    TextView link2;
    public static final String emailregex="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email= findViewById(R.id.email);
        final EditText password= findViewById(R.id.password);

        final Button login= findViewById(R.id.loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                password.setError(null);
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("Required");
                    email.requestFocus();
                } else if(password.getText().toString().trim().isEmpty()){
                    password.setError("Required");
                    password.requestFocus();
                } else if(!email.getText().toString().matches(emailregex)){
                    email.setError("enter valid email");
                    email.requestFocus();
                } else {
                    Intent intent2= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent2);
                    finish();
                }
            }
        });


        link= findViewById(R.id.textView3);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent3);


            }
        });

        link2= findViewById(R.id.textView2);
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent5);
            }
        });
    }
}








