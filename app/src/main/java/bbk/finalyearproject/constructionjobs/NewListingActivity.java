package bbk.finalyearproject.constructionjobs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewListingActivity extends Activity {

    // Variables
    TextView pageTitle;
    EditText postTitle;
    EditText postBody;
    TextView wordCount;
    EditText postLocation;
    CheckBox contactConfirm;
    Button btnCreateListing;
    TextWatcher textWatcher;
    int userID;
    User loggedUser;
    // Db helper
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Popup windows settings
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_newlisting);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int)(height*.7));

        //Initialising variables
        pageTitle = findViewById(R.id.pageTitle);
        postTitle = findViewById(R.id.postTitle);
        postBody = findViewById(R.id.postBody);
        wordCount = findViewById(R.id.wordCount);
        textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                wordCount.setText("Characters left: "+String.valueOf(1000 - s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        postBody.addTextChangedListener(textWatcher);
        postLocation = findViewById(R.id.postLocation);
        contactConfirm = findViewById(R.id.contactConfirm);
        btnCreateListing = findViewById(R.id.btnCreateListing);
        dbHelper = new DatabaseHelper(this);

        // Retrieving user from the MainActivity
        Bundle data = getIntent().getExtras();
        if(data != null) {
            userID = data.getInt("loggedID");
            loggedUser = dbHelper.getUser(userID);
        }

        // Dynamically displaying the user's name
        String authorListing = "New Job Listing by " + loggedUser.getUsername();
        pageTitle.setText(authorListing);

        // Action button
        btnCreateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;

                // Sanitising post title
                String title = postTitle.getText().toString();
                if(title.equalsIgnoreCase("")){
                    postTitle.setError("Title cannot be empty");
                }else if(title.length() > 50){
                    postTitle.setError("Maximum 50 characters");
                }else if(title.length() < 5) {
                    postTitle.setError("Too short, min 5 characters");
                } else{
                    flag = true;
                }

                String body = postBody.getText().toString();
                if(flag) {
                    flag = false;
                    // Sanitising post body
                    if (body.equalsIgnoreCase("")) {
                        postBody.setError("Body cannot be empty");
                    }else if(body.length() > 1000){
                        postBody.setError("Post too long, max 1000 characters");
                    }else if(body.length() < 20){
                        postBody.setError("Too short, min 20 characters");
                    }else{
                        flag = true;
                    }
                }
                String location = postLocation.getText().toString();

                if(flag){
                    flag = false;
                    //Sanitising address
                    if(location.equalsIgnoreCase("")){
                        postLocation.setError("Location cannot be empty");
                    }else if(location.length() < 3 || location.length() > 20){
                        postLocation.setError("Too short or too long");
                    }else{
                        flag = true;
                    }
                }

                String contact = "";
                if(flag) {
                    if (contactConfirm.isChecked()) {
                        contact = loggedUser.getContact();
                    } else {
                        contact = "Hidden";
                    }

                    String author = loggedUser.getUsername();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = ""+dtf.format(now);

                Listing listing = new Listing(-1, title, body, contact, location, author, date);
                if(dbHelper.addListing(listing)){
                    finish();
                }else{
                    Toast.makeText(NewListingActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
                }


            }
        });

    }

}
