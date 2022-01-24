package bbk.finalyearproject.constructionjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {

    EditText et_loginUsername;
    EditText et_loginPassword;
    Button login_LoginBtn;
    Button login_RegisterBtn;
    DatabaseHelper dbHelper;


    boolean accountCreatedFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            accountCreatedFlag = extras.getBoolean("created");
        }
        if(accountCreatedFlag){
            Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_LONG).show();
            accountCreatedFlag = false;
        }

        // Initialising layout elements
        et_loginUsername = findViewById(R.id.et_loginUsername);
        et_loginPassword = findViewById(R.id.et_loginPassword);
        login_LoginBtn = findViewById(R.id.login_LoginBtn);
        login_RegisterBtn = findViewById(R.id.login_RegisterBtn);

        // Initialising database
        dbHelper = new DatabaseHelper(this);

        login_LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_loginUsername.getText().toString();
                String password = et_loginPassword.getText().toString();
                if(username.equalsIgnoreCase("")){
                    et_loginUsername.setError("Username cannot be empty");
                } else if(password.equalsIgnoreCase("")){
                    et_loginPassword.setError("Passowrd cannot be empty");
                }else{
                    User user = dbHelper.logIn(username, password);
                    if(user != null){
                        Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent2.putExtra("loggedUser", user);
                        startActivity(intent2);
                        finish();
                    } else {
                        et_loginUsername.setError("Wrong user or password");
                    }
                }
            }
        });

        login_RegisterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent r = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(r);
            }
        });

    }
}