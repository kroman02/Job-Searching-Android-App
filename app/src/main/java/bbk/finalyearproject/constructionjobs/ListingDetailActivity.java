package bbk.finalyearproject.constructionjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ListingDetailActivity extends AppCompatActivity {
    int lid;
    int uid;
    //Listing to display
    Listing listing;

    //Variables from layout
    TextView detail_title;
    TextView detail_body;
    TextView detail_author;
    TextView detail_date;
    TextView detail_contact;

    //dbHelper
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

        //Initialising layout variables
        detail_title = findViewById(R.id.detail_title);
        detail_body = findViewById(R.id.detail_body);
        detail_author = findViewById(R.id.detail_author);
        detail_date = findViewById(R.id.detail_date);
        detail_contact = findViewById(R.id.detail_contact);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            lid = extras.getInt("lid");
            uid = extras.getInt("uid");
        }
        Log.d("DETAIL_LOGGED", ""+uid);
        dbHelper = new DatabaseHelper(this);
        listing = dbHelper.getListing(lid);

        if(listing != null) {
            detail_title.setText(listing.getTitle().toString());
            detail_body.setText(listing.getBody().toString());
            detail_author.setText(listing.getAuthor());
            detail_date.setText(listing.getDate().toString());
            detail_contact.setText("Contact: " + listing.getContact().toString());
        }
        User logged = dbHelper.getUser(uid);
        detail_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = dbHelper.getUserByUsername(listing.getAuthor().toString());
                if(user != null){
                    Intent a = new Intent(ListingDetailActivity.this, OtherProfileActivity.class);
                    a.putExtra("viewed_id", user.getUid());
                    a.putExtra("logged_id", logged.getUid());
                    if(user.getUid() == logged.getUid()){
                        Toast.makeText(ListingDetailActivity.this, "This is your profile", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(a);
                    }
                }else{
                    Toast.makeText(ListingDetailActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}