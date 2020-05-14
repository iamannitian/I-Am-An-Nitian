package in.co.iamannitian.iamannitian;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

    private ProgressDialog progressDialog;

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

        //initializing progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false); //prevent disappearing

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

                        email.requestFocus();
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
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //finish all previous activities
                startActivity(intent);
            }
        });

    }

  private void proceedToSignup(final String user_name,final String user_email,final String user_password)
    {
        // show progress bar first
        progressDialog.setMessage("Processing....");
        progressDialog.show();
        // disable user interaction when progress dialog appears
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        String url = "http://blog.iamannitian.co.in/signup.php";
        StringRequest sr = new StringRequest(1, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          String response_array[] = response.split(",");

                        if(response_array[0].equals("1"))
                        {
                            email.setText("");
                            password.setText("");
                            username.setText("");

                            //dismiss the progress dialog when sign up successful
                            progressDialog.dismiss();

                            /*=========================== shared preferences saving user data started ============================*/
                            SharedPreferences sharedPreferences = getSharedPreferences("appData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId",response_array[1]);
                            editor.putString("userName", response_array[2]);
                            editor.putString("userEmail",response_array[3]);
                            editor.apply();
                            /*=========================== shared preferences saving user data finished ============================*/

                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //finish all previous activities
                            startActivity(intent);

                        }
                        else if(response_array[0].equals("2"))
                        {
                            Toast.makeText(SignupActivity.this,
                                    "The email is already taken", Toast.LENGTH_LONG).show();
                        }
                       else
                        {
                            progressDialog.dismiss();
                            //on dialog dismiss back to interaction mode
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(SignupActivity.this,"failed to sign up!", Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() { //error
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                //on dialog dismiss back to interaction mode
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map =  new HashMap<>();
                map.put("nameKey", user_name);
                map.put("emailKey", user_email);
                map.put("passwordKey", user_password);
                map.put("codeKey", "J6T32A-Pubs7/=H~".trim());
                return map;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(SignupActivity.this);
        rq.add(sr);
    }
}