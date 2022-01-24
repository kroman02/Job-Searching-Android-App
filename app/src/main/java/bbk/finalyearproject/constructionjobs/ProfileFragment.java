package bbk.finalyearproject.constructionjobs;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    View view;
    TextView profileFname;
    TextView profileSname;
    TextView profileProfession;
    TextView profileUsername;
    TextView ratingLabel;
    RatingBar ratingBar;
    DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivity activity = (MainActivity) getActivity();
        User user = activity.getLoggedUser();

        // Initialising layout elements
        profileUsername = view.findViewById(R.id.profileUsername);
        profileFname = view.findViewById(R.id.profileFname);
        profileSname = view.findViewById(R.id.profileSname);
        profileProfession = view.findViewById(R.id.profileProfession);
        ratingLabel = view.findViewById(R.id.ratingLabel);
        ratingBar = view.findViewById(R.id.ratingBar);
        dbHelper = new DatabaseHelper(activity);

        if(user != null){
            profileUsername.setText(user.getUsername().toString());
            String fname = "First name: "+user.getName();
            profileFname.setText(fname.toString());
            String sname = "Last name: "+user.getSurname();
            profileSname.setText(sname.toString());
            String profession = "Profession: "+user.getProfession();
            profileProfession.setText(profession.toString());
            float rating = dbHelper.getAverageRate(user.getUid());
            ratingLabel.setText("Your average rate: "+rating);
            ratingBar.setRating(rating);
        }
    return view;
    }

}