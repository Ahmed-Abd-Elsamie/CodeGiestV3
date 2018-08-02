package com.example.root.codegiest.post;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.codegiest.Maps.Maps;
import com.example.root.codegiest.R;
import com.example.root.codegiest.mainScreen.MainTest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 26/07/18.
 */


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> list;
    private Activity context;
    private DatabaseReference reference;
    private int n = 0;
    private FirebaseAuth mAuth;
    private String uid;
    private boolean likeCheck = false;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public TextView txtName;
        public TextView txtDate;
        public Button btnLocation;
        public TextView txtDesc;
        public CircleImageView userImg;
        public Button LikeBtn;
        public ImageButton LocationBtn;



        public ViewHolder(View v) {
            super(v);
            view = v;
            txtName = (TextView) view.findViewById(R.id.name_textView);
            txtDate = (TextView) view.findViewById(R.id.date_textView);
            txtDesc = (TextView) view.findViewById(R.id.desc_textView);
            btnLocation = (Button) view.findViewById(R.id.btnLoc);
            userImg = (CircleImageView) view.findViewById(R.id.imageView);
            LikeBtn = (Button) view.findViewById(R.id.like_button);
            LocationBtn = (ImageButton) view.findViewById(R.id.locate_button);



        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PostAdapter(List<Post> postList , Activity mContext) {
        list = postList;
        context = mContext;
        reference = FirebaseDatabase.getInstance().getReference().child("likes");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();

    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_screen_adapter, parent, false);

        PostAdapter.ViewHolder vh = new PostAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final PostAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.txtName.setText(mDataset[position]);

        final Post post = list.get(position);
        holder.txtName.setText(post.getName());
        holder.txtDate.setText(post.getDate());
        holder.txtDesc.setText(post.getDesc());
        holder.btnLocation.setText(post.getLoc());
        Picasso.with(context).load(post.getUserImg()).into(holder.userImg);
        holder.LikeBtn.setText(post.getLike());




        holder.LocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lat = post.getLat();
                String lang = post.getLang();

                Intent i = new Intent(context , Maps.class);
                i.putExtra("lat" , lat);
                i.putExtra("lang" , lang);

                context.startActivity(i);


            }
        });






        holder.LikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context , MainTest.keys.get(position) ,Toast.LENGTH_SHORT).show();
                //Toast.makeText(context , n ,Toast.LENGTH_SHORT).show();

                setLike(holder.LikeBtn , MainTest.keys.get(position));

            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }



    private void setLike(final Button btnLike , final String postKey){

        likeCheck = true;


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (likeCheck == true){


                    if (dataSnapshot.child(postKey).hasChild(uid)){

                        reference.child(postKey).child(uid).removeValue();
                        likeCheck = false;
                        n = (int) dataSnapshot.child(postKey).getChildrenCount();
                        btnLike.setText(n + "");
                        btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_gray,0,0,0);

                    }else {

                        reference.child(postKey).child(uid).setValue("Like");
                        n = (int) dataSnapshot.child(postKey).getChildrenCount();
                        likeCheck = false;
                        btnLike.setText(n + "");
                        btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_black,0,0,0);

                    }


                }else {



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }





}