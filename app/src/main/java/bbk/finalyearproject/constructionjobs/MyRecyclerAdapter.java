package bbk.finalyearproject.constructionjobs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter {

    List<Listing> listingList;
    Context context;
    int loggedUserId;

    public MyRecyclerAdapter(List<Listing> listingList, Context context, int id) {
        this.listingList = listingList;
        this.context = context;
        this.loggedUserId = id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_listing, parent, false);
        MyViewHolder viewHolderClass = new MyViewHolder(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        String title = listingList.get(position).getTitle();
        if(title.length() > 33){
            title = title.substring(0, 34) + "...";
        }

        myViewHolder.listingTitle.setText(title);

        myViewHolder.listingTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListingDetailActivity.class);
                int id = listingList.get(position).getLid();
                i.putExtra("lid", id);
                i.putExtra("uid", loggedUserId);
                context.startActivity(i);

            }
        });


        myViewHolder.listingLocation.setText(listingList.get(position).getLocation());
        myViewHolder.listingDate.setText(listingList.get(position).getDate());
        myViewHolder.oneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListingDetailActivity.class);
                int id = listingList.get(position).getLid();
                i.putExtra("lid", id);
                i.putExtra("uid", loggedUserId);
                context.startActivity(i);

            }
        });



    }

   /* @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listingTitle.setText(listingList.get(position).getTitle());
        holder.listingLocation.setText(listingList.get(position).getLocation());
        holder.listingDate.setText(listingList.get(position).getDate());

    }*/

    @Override
    public int getItemCount() {
        return listingList.size();
    }

    public void filterList(List<Listing> list){
        listingList = list;
        notifyDataSetChanged();
    }

    public void refreshList(List<Listing> newList) {
        listingList = newList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView listingImage;
        TextView listingTitle;
        TextView listingLocation;
        TextView listingDate;
        ConstraintLayout oneLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listingImage = itemView.findViewById(R.id.listingImage);
            listingTitle = itemView.findViewById(R.id.listingTitle);
            listingLocation = itemView.findViewById(R.id.listingLocation);
            listingDate = itemView.findViewById(R.id.listingDate);
            oneLayout = itemView.findViewById(R.id.oneListing);

        }
    }
}
