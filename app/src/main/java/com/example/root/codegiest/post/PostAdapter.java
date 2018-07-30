package com.example.root.codegiest.post;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.root.codegiest.MainTest;
import com.example.root.codegiest.Maps.Maps;
import com.example.root.codegiest.R;
import com.firebase.client.snapshot.ChildKey;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 26/07/18.
 */


public class PostAdapter extends ArrayAdapter<Post> {

    private Activity context;
    private List<Post> postList;
    private DatabaseReference referenceLike;
    private List<ChildKey> childKeys;
    private String uid;
    private FirebaseAuth auth;
    String like = "";
    Button btnLike;
    int currentPos = 0;
    public static final int PLACE_PICKER_REQUEST = 1;
    LatLng latLng;
    private boolean check = false;

    public PostAdapter(Activity context, List<Post> postList) {
        super(context, R.layout.main_screen_adapter , postList);
        this.context = context;
        this.postList = postList;
        referenceLike = FirebaseDatabase.getInstance().getReference().child("posts");
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid().toString();
        latLng = new LatLng(156,615);


    }



    @Override
    public int getCount() {
        return this.postList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.main_screen_adapter , parent , false);

        TextView name = (TextView) view.findViewById(R.id.name_textView);
        TextView date = (TextView) view.findViewById(R.id.date_textView);
        TextView desc = (TextView) view.findViewById(R.id.desc_textView);
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLoc);

        ImageButton btnFav = (ImageButton) view.findViewById(R.id.favorite_button);
        ImageButton btnMention = (ImageButton) view.findViewById(R.id.mention_button);
        ImageButton btnLocation = (ImageButton) view.findViewById(R.id.locate_button);
        btnLike = (Button) view.findViewById(R.id.like_button);
        currentPos = position;

        CircleImageView img = (CircleImageView)view.findViewById(R.id.imageView);


        final Post itemPost = getItem(position);



        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , Maps.class);
                intent.putExtra("lat" , itemPost.getLat());
                intent.putExtra("long" , itemPost.getLang());

                context.startActivity(intent);

            }
        });


        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (check == true){

                    btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_gray , 0 , 0 , 0);
                    check = false;

                }else {

                    btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_black , 0 , 0 , 0);
                    check = true;

                }


                String key = MainTest.keys.get(position);
                //setLike(btnLike , key);




            }
        });


        name.setText(itemPost.getName());
        date.setText(itemPost.getDate());
        desc.setText(itemPost.getDesc());
        txtLocation.setText(itemPost.getLoc());



        Picasso.with(context).load(itemPost.getUserImg()).into(img);

        return view;

    }




/*

    private void setLike(final Button Like , final String k){


        referenceLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(k).child("like").hasChild(uid)){

                    int num = (int) dataSnapshot.child(k).child("like").getChildrenCount();

                    if (num ==  1){

                        like = "1";
                        Like.setText("1");

                    }else if (num > 1){

                        like = "more";
                        Like.setText(num + "");

                    }


                }
                else {

                    like = "no";

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        if (like.equals("1")){

            referenceLike.child(k).child("like").setValue("like");
            Like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like , 0 , 0 , 0);
            //like = "";


        }else if (like.equals("more")){

            referenceLike.child(k).child("like").child(uid).removeValue();
            Like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like , 0 , 0 , 0);
            //like = "";


        }else if (like.equals("no")){

            Like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_black , 0 , 0 , 0);
            referenceLike.child(k).child("like").child(uid).setValue("like");
            //like = "";


        }




    }

*/



}