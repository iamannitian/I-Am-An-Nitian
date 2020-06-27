package in.co.iamannitian.iamannitian;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SignupActivity extends AppCompatActivity {

    private EditText email, username, password;
    private Button click_to_sign_up;
    private TextView go_to_login;
    private BottomSheetDialog bottomSheetDialog;
    private ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*=========>>> Setting Up dark Mode <<<==========*/
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.DarkTheme);
        }
        else
        {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                String user_email = email.getText().toString().trim().replaceAll("\\s+","");
                String user_password = password.getText().toString().trim().replaceAll("\\s+","");

                //checking user name

                if(user_name.isEmpty())
                {
                    username.requestFocus();
                    username.setError("required");
                    return;
                }


                //checking user email

                if(user_email.isEmpty())
                {
                    email.requestFocus();
                    email.setError("required");
                    return;
                }

                //checking user password

                if(user_password.isEmpty())
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
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        // disable user interaction when progress dialog appears
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        String url = "https://iamannitian.co.in/app/signup.php";
        StringRequest sr = new StringRequest(1, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          String response_array[] = response.split(",");

                        if(response_array[0].equals("1"))
                        {

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

                           /* Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //finish all previous activities
                            startActivity(intent);*/

                           View bottomSheetView = getLayoutInflater()
                                   .inflate(R.layout.bootm_sheet_layout,null);
                            bottomSheetDialog = new BottomSheetDialog(SignupActivity.this, R.style.BottomSheetDialogTheme);
                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();

                        }
                        else if(response_array[0].equals("0"))
                        {
                            progressDialog.dismiss();
                            //on dialog dismiss back to interaction mode
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            Toast.makeText(SignupActivity.this,
                                    response_array[1], Toast.LENGTH_LONG).show();

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