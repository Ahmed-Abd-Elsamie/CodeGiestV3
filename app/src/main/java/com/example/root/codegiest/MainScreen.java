package com.example.root.codegiest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.codegiest.post.Post;
import com.example.root.codegiest.post.PostAdapter;
import com.example.root.codegiest.user.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.*;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent ;

    //Initialize the list view
    ListView listView ;
    private TextView txtState;

    //Initialize the drawer layout
    private DrawerLayout drawerLayout ;

    //Set the ActionBarDrawerToggle
    private ActionBarDrawerToggle toggle ;

    //Set the navigation view
    private NavigationView navigationView ;

    private DatabaseReference reference , referenceUsers;
    private FirebaseAuth mAuth;
    private String uid;
    private StorageReference storageReference;
    private List<Post> list;
    private FloatingActionButton btn_post;
    private String Myname;
    private String MyImg;
    private ProgressBar progressBar;



    //Setup the arrays that contain the date of single list item
    int[] images = {R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo ,
            R.drawable.logo ,
            R.drawable.logo};
    String[] names = {"Name 1" , "Name 2", "Name 3" ,"Name 4" ,"Name 5"};
    String[] date = {"date 1" , "date 2", "date 3" ,"date 4" ,"date 5"};
    String[] desc = {"desc 1" , "desc 2", "desc 3" ,"desc 4" ,"desc 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main_screen.xml layout file
        setContentView(R.layout.activity_main_screen);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();

        reference = FirebaseDatabase.getInstance().getReference().child("posts");
        referenceUsers = FirebaseDatabase.getInstance().getReference().child("users");
        list = new ArrayList<>();
        //Select our list view from the layout
        //listView = (ListView) findViewById(R.id.list);
        txtState = (TextView)findViewById(R.id.post_state);
        btn_post = (FloatingActionButton) findViewById(R.id.fab_add_post);
        //progressBar = (ProgressBar) findViewById(R.id.prog_loading);

        //Select the drawer view from the xml file
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        //Set the button in the action bar to the drawer layout
        toggle = new ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close);

        //Add the drawer layout into the button in action bar
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Display the action bar

        //Select the required navigation view from activity_main_screen.xml file
        navigationView = (NavigationView) findViewById(R.id.nav);

        //Set click listener for the navigation view
        navigationView.setNavigationItemSelectedListener(this);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPost();
            }
        });

        //Create object from our class that adapt every single list item

       /* CustomAdapter customAdapter = new CustomAdapter();

        //Put the custom adapter into the list view
        listView.setAdapter(customAdapter);
       */


        progressBar.setVisibility(View.VISIBLE);

        GetAllPosts();



    }



    /**
     * That class to adapt every single item in his right place
     */


    class CustomAdapter extends BaseAdapter{

        /**
         * Get the number of items in the list view
         * @return
         */

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Select the adapter layout to set on the list view
            convertView = getLayoutInflater().inflate(R.layout.main_screen_adapter , null);

            //Select all the date in every single item in the list view
            CircleImageView imageView = (CircleImageView) convertView.findViewById(R.id.imageView);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.name_textView);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.date_textView);
            TextView descTextView = (TextView) convertView.findViewById(R.id.desc_textView);
            ImageButton likeBtn = (ImageButton) convertView.findViewById(R.id.like_button);
            ImageButton locateBtn = (ImageButton) convertView.findViewById(R.id.locate_button);
            ImageButton mentionBtn = (ImageButton) convertView.findViewById(R.id.mention_button);
            ImageButton favoriteBtn = (ImageButton) convertView.findViewById(R.id.favorite_button);

            //Put the date in its right place in every single item in the list view
            imageView.setImageResource(images[position]);
            nameTextView.setText(names[position]);
            dateTextView.setText(date[position]);
            descTextView.setText(desc[position]);

            //Setup evey one of the four buttons to move to its activity

            //When locate button is clicked --> Go to Location activity
            locateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*intent = new Intent(MainScreen.this , LocateBtn.class);
                    startActivity(intent);
                    */
                }
            });

            //When favorite button is clicked --> Go to Favorite activity
            favoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(MainScreen.this , Favorite.class);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    /**
     *To open the menu in the navigation view
     * @param item
     * @return
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)){
            return true ; //Hint :- it is return false by default so we have to handle it
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * TO handle the clicked on every item in the navigation menu
     * @param item
     * @return
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            //If the the home button is clicked
            case R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;

            //If the the home profile is clicked
            case R.id.profile :
                //Go to Profile activity
                intent = new Intent(MainScreen.this , Profile.class);
                startActivity(intent);
                break;

            //If the the home history is clicked
            case R.id.history :
                //Go to History activity
                intent = new Intent(MainScreen.this , History.class);
                startActivity(intent);
                break;


        }

        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();



    }

    private void GetAllPosts(){



        final List<Post> list = new ArrayList<>();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()){

                    list.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        Post post = new Post();

                        post.setName(snapshot.child("name").getValue().toString());
                        post.setDesc(snapshot.child("desc").getValue().toString());
                        post.setLike(snapshot.child("like").getValue().toString());
                        post.setUserImg(snapshot.child("userImg").getValue().toString());
                        post.setDate(snapshot.child("date").getValue().toString());

                        Toast.makeText(getApplicationContext() , snapshot.child("like").getValue().toString() , Toast.LENGTH_SHORT).show();

                        list.add(post);

                    }

                    //PostAdapter adapter = new PostAdapter(MainScreen.this , list);
                    //listView.setAdapter(adapter);

                    progressBar.setVisibility(View.INVISIBLE);





                }else {

                    txtState.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void NewPost(){

        // getting my data


        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Myname = dataSnapshot.child(uid).child("name").getValue().toString();
                MyImg = dataSnapshot.child(uid).child("img").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        AddDialog();


    }


    private void AddDialog(){


    }



}
