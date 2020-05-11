package in.co.iamannitian.iamannitian;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView forgot_password, go_to_sign_up;
    private Button click_to_login;

    public static final String emailregex="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        forgot_password = findViewById(R.id.forgot_password);
        go_to_sign_up = findViewById(R.id.go_to_sign_up);

        click_to_login = findViewById(R.id.click_to_login);

        click_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initializing errors

                email.setError(null);
                password.setError(null);

                //getting credentials on click the login button

                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();


                //checking user email

                if (!user_email.isEmpty()) {
                    //if not empty then check whether valid or not
                    final String email_regex =
                            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
                                    "@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                                    "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                                    "{0,61}[a-zA-Z0-9])?)*$";

                    Pattern p = Pattern.compile(email_regex);
                    Matcher m = p.matcher(user_email);
                    if (!m.matches()) //if email is invalid
                    {
                        // email.requestFocus();
                        email.setError("invalid email");
                        return;
                    }

                } else {
                    email.requestFocus();
                    email.setError("required");
                    return;
                }

                //checking user password

                if (!user_password.isEmpty()) {
                    //if not empty then check password length
                    if (user_password.length() < 6) {
                        password.requestFocus();
                        password.setError("password too sort");
                        return;
                    }
                } else {
                    password.requestFocus();
                    password.setError("required");
                    return;
                }


                proceedToLogin(user_email, user_password);

              }

        });

        go_to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
    }


    private void proceedToLogin(final String user_email, final String user_password)
    {
        String url = "http://blog.iamannitian.co.in/login.php";
        StringRequest sr = new StringRequest(1, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1"))
                        {
                            email.setText("");
                            password.setText("");

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Login Failed!", Toast.LENGTH_LONG).show();
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
                map.put("emailKey", user_email);
                map.put("passwordKey", user_password);
                return map;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
        rq.add(sr);
    }


}









