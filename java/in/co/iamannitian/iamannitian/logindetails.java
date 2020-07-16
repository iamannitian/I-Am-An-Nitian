package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class logindetails extends AppCompatActivity {

    TextView link;
    TextView link2;
    public static final String emailregex="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindetails);

        final EditText email= (EditText)findViewById(R.id.email);
        final EditText password=(EditText)findViewById(R.id.password);

        final Button login= (Button)findViewById(R.id.loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                password.setError(null);
                if(email.getText().toString().isEmpty()){
                    email.setError("Required");
                    return;
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("Required");
                    return;
                }
                if(!email.getText().toString().matches(emailregex)){
                   email.setError("enter valid email");
                   return;
                }
                else {
                Intent intent2= new Intent(logindetails.this,MainActivity.class);
                startActivity(intent2);}
            }
        });


        link= (TextView)findViewById(R.id.textView3);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(logindetails.this,SignupScreen.class);
                startActivity(intent3);

          link2= (TextView)findViewById(R.id.textView2);
          link2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent5=new Intent(logindetails.this,forgotpass.class);
                  startActivity(intent5);
              }
          });

            }
        });







    }
}
