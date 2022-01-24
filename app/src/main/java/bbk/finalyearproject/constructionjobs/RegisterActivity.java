package bbk.finalyearproject.constructionjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText et_username;
    EditText et_password;
    EditText et_fname;
    EditText et_sname;
    Spinner spinProfession;
    EditText et_contact;
    EditText et_location;
    Button registerBtn;
    Button register_LoginBtn;
    DatabaseHelper dbHelper;
    Boolean accountCreatedFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        // Initialising layout elements
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_fname = findViewById(R.id.et_fname);
        et_sname = findViewById(R.id.et_sname);
        spinProfession = (Spinner) findViewById(R.id.spin_profession);
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.professions));
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinProfession.setAdapter(spinAdapter);
        et_contact = findViewById(R.id.et_contact);
        et_location = findViewById(R.id.et_location);
        registerBtn = findViewById(R.id.register_RegisterBtn);
        register_LoginBtn = findViewById(R.id.register_LoginBtn);
        accountCreatedFlag = false;
        // Initialising database helper
        dbHelper = new DatabaseHelper(this);

        //
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = false;
                // Collecting username
                String username = et_username.getText().toString().trim();
                if(dbHelper.usernameExists(username)) {
                    et_username.setError("Username already exists");
                } else {
                    flag = sanitizeString(username, et_username);
                }

                // Collecting password
                String password = et_password.getText().toString().trim();
                if(flag) {
                    if(password.length() < 5){
                        et_password.setError("Password must be at least 5 characters long");
                        flag = false;
                    }
                }

                // Collecting first name
                String firstName = et_fname.getText().toString().trim();
                if(flag) {
                    if(containsNumbers(firstName)){
                        et_fname.setError("Cannot contain numbers");
                        flag = false;
                    }else {
                        flag = sanitizeString(firstName, et_fname);
                    }
                }

                //Collecting last name
                String lastName = et_sname.getText().toString().trim();
                if(flag){
                    if(containsNumbers(lastName)){
                        et_sname.setError("Cannot contain numbers");
                        flag = false;
                    }else{
                        flag = sanitizeString(lastName, et_sname);
                    }

                }

                //Collecting profession
                String profession = spinProfession.getSelectedItem().toString();
                if(flag){
                    if(profession.equals("---")){
                        TextView error = (TextView)spinProfession.getSelectedView();
                        error.setError("Select your profession");
                        flag = false;
                    }
                }

                //Collecting contact
                String contact = et_contact.getText().toString().trim();
                if(flag){
                    if (!contact.matches("[0-9]+")){
                        et_contact.setError("Only numbers allowed");
                        flag = false;
                    } else if(contact.length() > 13) {
                        et_contact.setError("Number too long");
                        flag = false;
                    }
                }

                //Collecting location
                String location = et_location.getText().toString().trim();
                if(flag){
                    flag = false;
                    if(location.length() > 10){
                        et_location.setError("Too long, max 10 characters");
                    }else if(location.equalsIgnoreCase("")) {
                        et_location.setError("Field required");
                    }else{
                        flag = true;
                    }
                }

                if(flag) {
                    User user = new User(-1, username, password, firstName, lastName, profession, contact, location);
                    if(dbHelper.addUser(user)){
                        accountCreatedFlag = true;
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("created", accountCreatedFlag);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Creation failed", Toast.LENGTH_LONG).show();
                    };
                }

            }
        });

        // Login instead link
        register_LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(l);
            }
        });
    }

    /**
     * Helper Method used to sanitize strings for username, name, surname and location
     * @param string
     * @return true if string is clean, false otherwise
     */
    private boolean sanitizeString(String string, EditText edittext){
        if(string.equalsIgnoreCase("")){
            edittext.setError("Required field");
        }else if(string.length() > 15){
            edittext.setError("Value too long: max 15 characters");
        } else if(containsSpecialCharacter(string)) {
            edittext.setError("Invalid characters, use numbers and letters only");
        } else if(string.length() < 3){
            edittext.setError("Too short, at least 3 characters");
        }
        if(edittext.getError() == null){
            return true;
        }
        return false;
    }

    /**
     * Helper Method to check for special characters in a string
     * @param string
     * @return true or false
     * @Author Subham Mittal
     */
    private boolean containsSpecialCharacter(String string){
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for (int i=0; i < string.length() ; i++) {
            char ch = string.charAt(i);
            if(specialCharactersString.contains(Character.toString(ch))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsNumbers(String string) {
        String numbers = "1234567890";
        for(int i = 0; i < string.length(); i++){
            char ch = string.charAt(i);
            if(numbers.contains(Character.toString(ch))){
                return true;
            }
        }
        return false;
    }

}