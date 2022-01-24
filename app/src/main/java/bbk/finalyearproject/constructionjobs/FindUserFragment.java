package bbk.finalyearproject.constructionjobs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindUserFragment extends Fragment {
    MainActivity mainActivity;
    View view;
    EditText search_user;
    Button search;
    DatabaseHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_user, container, false);

        mainActivity = (MainActivity) getActivity();
        search_user = view.findViewById(R.id.search_user);
        search = view.findViewById(R.id.search);
        dbHelper = new DatabaseHelper(mainActivity);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToFind = search_user.getText().toString().trim();
                User user = dbHelper.getUserByUsername(userToFind);
                if (user == null) {
                    search_user.setError("User does not exist");
                } else {
                    Intent i = new Intent(getActivity(), OtherProfileActivity.class);
                    i.putExtra("viewed_id", user.getUid());
                    i.putExtra("logged_id", mainActivity.getLoggedUser().getUid());
                    startActivity(i);
                }
            }
        });
        return view;
    }
}
