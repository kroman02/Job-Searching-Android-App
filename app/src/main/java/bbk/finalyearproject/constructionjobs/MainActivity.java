package bbk.finalyearproject.constructionjobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        Bundle data = getIntent().getExtras();
        if(data != null) {
            loggedUser = (User) data.getParcelable("loggedUser");
        }else{
            loggedUser = new User(-1, "notlogged", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxxx", "xxxx");
        }

    }

    public User getLoggedUser(){
        return this.loggedUser;
    }
}