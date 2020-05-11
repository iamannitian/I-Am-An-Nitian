package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    private Button click_to_sign_up;
    private TextView go_to_login;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        email    = findViewById(R.id.email);
        password = findViewById(R.id.password);
        click_to_sign_up = findViewById(R.id.click_to_sign_up);
        go_to_login = findViewById(R.id.go_to_login);


        click_to_sign_up.setOnClickListener(new View.OnClickListener() {
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

                if(!user_name.isEmpty())
                {
                    //if not empty then check whether it is valid
                    //name should not be less than 3 in length
                    String name_regex =
                            "[a-zA-Z]{3,}\\s{1}[a-zA-Z]{3,}\\s{1}[a-zA-Z]{3,}|" +
                            "[a-zA-Z]{3,}\\s{1}[a-zA-Z]{3,}|" +
                            "[a-zA-Z]{3,}";
                    Pattern p = Pattern.compile(name_regex);
                    Matcher m = p.matcher(user_name);
                    if(!m.matches())
                    {
                        username.requestFocus();
                        username.setError("invalid name");
                        return;
                    }
                }
                else
                    {
                    username.requestFocus();
                    username.setError("required");
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
                       // email.requestFocus();
                        email.setError("invalid email");
                        return;
                    }

                }
                else
                {
                    email.requestFocus();
                    email.setError("required");
                    return;
                }

                //checking user password

                if(!user_password.isEmpty()){
                    //if not empty then check password length
                    if(user_password.length() < 6)
                    {
                        password.requestFocus();
                        password.setError("password too sort");
                        return;
                    }
                }
                else
                {
                    password.requestFocus();
                    password.setError("required");
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
        String url = "http://blog.iamannitian.co.in/signup.php";
        StringRequest sr = new StringRequest(1, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1"))
                        {
                            email.setText("");
                            password.setText("");
                            username.setText("");

                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        }
                      else
                        {
                            Toast.makeText(SignupActivity.this,"Sign Up Failed!", Toast.LENGTH_LONG).show();
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