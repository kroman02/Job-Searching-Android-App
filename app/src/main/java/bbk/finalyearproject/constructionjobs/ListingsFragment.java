package bbk.finalyearproject.constructionjobs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ListingsFragment extends Fragment {
    View view;
    FloatingActionButton fab;
    TextView fragtext;
    EditText searchBar;
    List<Listing> allListings = new ArrayList<Listing>();
    ImageView refresh;

    //DB Helper
    DatabaseHelper dbHelper;

    // Variables for RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    MyRecyclerAdapter listingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       // Initialising variables
       view = inflater.inflate(R.layout.fragment_listings, container, false);
       dbHelper = new DatabaseHelper(getActivity());
       allListings = dbHelper.getAllListings();

       // Creating placeholder listings
       if(allListings.size() < 3) {
           fillAllListings();
       }
       fab = view.findViewById(R.id.fab);
       fragtext = view.findViewById(R.id.fragtext);
       View nested = view.findViewById(R.id.nested);
       searchBar = (EditText)nested.findViewById(R.id.search_bar);
       searchBar.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
                filter(s.toString());
           }
       });
       refresh = nested.findViewById(R.id.refresh);
       refresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               filter("");
           }
       });

       MainActivity activity = (MainActivity) getActivity();
       User user = activity.getLoggedUser();
       int loggedID = user.getUid();



       // Recycler view section
       recyclerView = view.findViewById(R.id.listingList);
       recyclerView.setHasFixedSize(true);

       // Layout Manager
       layoutManager = new LinearLayoutManager(getContext());
       recyclerView.setLayoutManager(layoutManager);
       listingAdapter = new MyRecyclerAdapter(allListings, getActivity(), loggedID);
       recyclerView.setAdapter(listingAdapter);

       fragtext.setText("Welcome "+user.getUsername().toString());

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent newListing = new Intent(getActivity(), NewListingActivity.class);
               newListing.putExtra("loggedID", loggedID);
               startActivity(newListing);
           }
       });
       return view;
    }

    private void refreshListings() {
        List<Listing> newList = dbHelper.getAllListings();
        listingAdapter.refreshList(newList);
    }

    private void fillAllListings() {
        Listing listing1 = new Listing(-1, "Looking for painter and decorator", "Are you a professional Painter in London? Would you like:\n" +
                "\n" +
                "• To choose your own working hours and take days off when you need them, staying in complete control of your schedule?\n" +
                "• To set your own rates according to the type of work you offer, your skills and expertise? You will also get to keep all the tips!\n" +
                "• To get jobs sent to you that suit your schedule and are convenient for you to commute to?\n" +
                "• To make good money on the side?", "074123913", "W11 J6T", "JobsCompany", "dd/MM/yyyy HH:mm:ss");

        Listing listing2 = new Listing(-1, "Electrician needed in London Today", "1 x level 3 qualified electrician\n" +
                "Ideally DBS checked/cscs card/UTR number\n" +
                "6 MONTHS work in residential area - Lewisham - South London (near the Asda store)\n" +
                "Day rate depends on experience\n" +
                "Start Monday 17th May 2021\n" +
                "Ideal if you have:-\n" +
                "Martindale or equivalent\n" +
                "Lock off kits and Fluke tester, plus own hand tools\n" +
                "You will need to have 5m for Employers Liability & Public Liability.", "081517242", "C1 3ED", "Gisajobs", "dd/MM/yyyy HH:mm:ss");
        Listing listing3 = new Listing(-1, "Carpentry Services London | Carpenter Jobs in London", "Are you looking to jobs concerning Carpentry Services London? Then connect with Vetted Trades Online to get the best quotes and Carpenter Jobs in London. Also, you can Joiner jobs in your local areas.", "0135123124", "UB6 9TX", "Vetted Trades","dd/MM/yyyy HH:mm:ss");

        dbHelper.addListing(listing1);
        dbHelper.addListing(listing2);
        dbHelper.addListing(listing3);
    }

    private ArrayList<Listing> getListings(){
        return (ArrayList<Listing>) this.allListings;
    }

    public void updateListings(){
        this.allListings = getListings();
    }

    private void filter(String s){
        List<Listing> filteredListings = new ArrayList<Listing>();
        for(Listing l : allListings){
            if(l.getTitle().toLowerCase().contains(s.toLowerCase())){
                filteredListings.add(l);
            }
        }

        listingAdapter.filterList(filteredListings);
    }


}