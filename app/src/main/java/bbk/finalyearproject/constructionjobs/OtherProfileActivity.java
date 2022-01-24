package bbk.finalyearproject.constructionjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class OtherProfileActivity extends AppCompatActivity {
    int logged_id;
    int viewed_id;
    TextView theirUsername;
    TextView theirFname;
    TextView theirSname;
    TextView theirProfession;
    TextView theirNumber;
    TextView ratingLabel;
    RatingBar ratingBar;
    Button rateUser;
    DatabaseHelper dbHelper;
    User viewedUser;
    User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        dbHelper = new DatabaseHelper(this);
        Bundle data = getIntent().getExtras();
        if(data != null) {
            viewed_id = data.getInt("viewed_id");
            logged_id = data.getInt("logged_id");
            viewedUser = dbHelper.getUser(viewed_id);
            loggedUser = dbHelper.getUser(logged_id);
        }



        theirUsername = findViewById(R.id.theirUsername);
        theirFname = findViewById(R.id.theirFname);
        theirSname = findViewById(R.id.theirSname);
        theirProfession = findViewById(R.id.theirProfession);
        theirNumber = findViewById(R.id.theirNumber);
        ratingLabel = findViewById(R.id.ratingLabel);
        ratingBar = findViewById(R.id.ratingBar);
        rateUser = findViewById(R.id.rateUser);


        theirUsername.setText(viewedUser.getUsername().toString());
        theirFname.setText("First name: "+viewedUser.getName().toString());
        theirSname.setText("Last name: "+viewedUser.getSurname().toString());
        theirProfession.setText("Profession: "+viewedUser.getProfession().toString());
        theirNumber.setText("Contact: "+viewedUser.getContact().toString());

        rateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dbHelper.ratingExists(loggedUser.getUid(), viewedUser.getUid())){
                    Rating rating = new Rating(loggedUser.getUid(), viewedUser.getUid(), ratingBar.getRating());
                    dbHelper.addRating(rating);
                    Toast.makeText(OtherProfileActivity.this, "Rated "+rating.getRate()+"!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OtherProfileActivity.this, "You already rated this user", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ratingLabel.setText("Average rating: "+dbHelper.getAverageRate(viewedUser.getUid()));



    }


}