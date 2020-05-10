package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText email, username, password;
    private Button click_to_signup;
    private TextView go_to_login;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        email    = findViewById(R.id.email);
        password = findViewById(R.id.password);
        click_to_signup = findViewById(R.id.click_to_signup);
        go_to_login = findViewById(R.id.go_to_login);


        //setting auto full property
//        username.setAutofillHints(View.AUTOFILL_HINT_USERNAME);
//        email.setAutofillHints(View.AUTOFILL_HINT_EMAIL_ADDRESS);
  //      password.setAutofillHints(View.AUTOFILL_HINT_PASSWORD);


        click_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting errors
                username.setError(null);
                email.setError(null);
                password.setError(null);

                //getting credentials on click the sign up button
                String user_name = username.getText().toString().trim();
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                //checking user name

                if(user_name.isEmpty()){
                    username.setError("");
                    return;
                }


                //checking user email

                if(!user_email.isEmpty())
                {
                   //if not empty then check whether valid or not
                    final String email_regex =
                            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
                            "@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                            "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                            "{0,61}[a-zA-Z0-9])?)*$";

                    Pattern p = Pattern.compile(email_regex);
                    Matcher m = p.matcher(user_email);
                    if(!m.matches()) //if email is invalid
                    {
                        email.setError("invalid email");
                        return;
                    }

                }
                else
                {
                    email.setError("");
                    return;
                }

                //checking user password

                if(!user_password.isEmpty()){
                    //if not empty then check password length
                    if(user_password.length() < 6)
                    {
                        password.setError("password is too sort");
                        return;
                    }
                }
                else
                {
                    password.setError("");
                    return;
                }


                //if every thing is ok then proceed to sign up
                proceedToSignup(user_name, user_email , user_password);

            }


        });



        //go to login activity
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

  private void proceedToSignup(final String user_name,final String user_email,final String user_password)
    {
        String url = "https://www.iamannitian.co.in/imn_app/signup.php";
        StringRequest sr = new StringRequest(1, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1"))
                        {
                            email.setText("");
                            password.setText("");
                            username.setText("");
                            Toast.makeText(SignupActivity.this,"Signup Suuccessful!", Toast.LENGTH_LONG).show();
                        }
                      else
                        {
                            Toast.makeText(SignupActivity.this,"Signup Failed1", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() { //error
            @Override
            public void onErrorResponse(VolleyError error) {
              //if error occurs
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map =  new HashMap<>();
                map.put("nameKey", user_name);
                map.put("emailKey", user_email);
                map.put("passwordKey", user_password);
                return map;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(SignupActivity.this);
        rq.add(sr);
    }
}