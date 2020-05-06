package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupScreen extends AppCompatActivity {

    public static final String emailregex="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        final EditText username= (EditText)findViewById(R.id.username);
        final EditText email= (EditText)findViewById(R.id.email);
        final EditText phone= (EditText)findViewById(R.id.phone);
        final EditText pass= (EditText)findViewById(R.id.pass);





        Button signbtn=(Button)findViewById(R.id.signup);

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setError(null);
                email.setError(null);
                pass.setError(null);
                phone.setError(null);
                if(username.getText().toString().isEmpty()){
                    username.setError("Required");
                    return;
                }
                if(email.getText().toString().isEmpty()){
                    email.setError("Required");
                    return;
                }
                if(pass.getText().toString().isEmpty()){
                    pass.setError("Enter Password");
                    return;
                }
                if(phone.getText().toString().isEmpty()){
                    phone.setError("Enter phone number");
                    return;
                }
                if(!email.getText().toString().matches(emailregex)){
                    email.setError("enter valid email");
                    return;
                }
                if(phone.getText().toString().length()!=10){
                    phone.setError("enter valid phone number");
                }



                else {
                    Intent intent4= new Intent(SignupScreen.this,logindetails.class);
                    startActivity(intent4);}

            }





        });
    }




}

