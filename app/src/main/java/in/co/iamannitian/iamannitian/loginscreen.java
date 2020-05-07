package in.co.iamannitian.iamannitian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class loginscreen extends AppCompatActivity {

    Button btn1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        btn1= (Button)findViewById(R.id.logsignbtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(loginscreen.this,logindetails.class);
                startActivity(intent1);
            }
        });
    }
}
