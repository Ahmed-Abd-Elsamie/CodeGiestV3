package com.example.root.codegiest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.codegiest.post.Post;
import com.example.root.codegiest.post.PostAdapter;
import com.example.root.codegiest.user.Login;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainTest extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference reference , referenceUsers;
    private FirebaseAuth mAuth;
    private String uid;
    private StorageReference storageReference;
    private List<Post> list;
    private FloatingActionButton btn_post;
    private String Myname;
    private String MyImg;
    private ProgressBar progressBar;
    ListView listView ;
    private TextView txtState;
    public static List<String> keys;
    private static final int PLACE_PICKER_REQUEST = 1;
    private Place place;
    TextView txtLoc;
    String mapLoc;
    String lat, lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);



        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();

        reference = FirebaseDatabase.getInstance().getReference().child("posts");
        referenceUsers = FirebaseDatabase.getInstance().getReference().child("users");
        list = new ArrayList<>();
        keys = new ArrayList<>();
        //Select our list view from the layout
        listView = (ListView) findViewById(R.id.list);
        txtState = (TextView)findViewById(R.id.post_state);

        LayoutInflater inflater = getLayoutInflater();

        progressBar = (ProgressBar)findViewById(R.id.prog_loading);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                        */
                NewPost();

            }
        });

        //  Buttons actions






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.out){

                mAuth.signOut();
                startActivity(new Intent(MainTest.this , Login.class));
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        GetAllPosts();
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
                        post.setLoc(snapshot.child("loc").getValue().toString());
                        post.setLat(snapshot.child("lat").getValue().toString());
                        post.setLang(snapshot.child("lang").getValue().toString());

                        keys.add(snapshot.getKey().toString());

                        //Toast.makeText(getApplicationContext() , snapshot.child("like").getValue().toString() , Toast.LENGTH_SHORT).show();

                        list.add(post);

                    }

                    PostAdapter adapter = new PostAdapter(MainTest.this , list);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                        }
                    });



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





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == RESULT_OK) {
                LatLng latLng;

                place = PlacePicker.getPlace(this, data);
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
                mapLoc = place.getAddress().toString();
                latLng = place.getLatLng();

                lat = String.valueOf(latLng.latitude);
                lang = String.valueOf(latLng.longitude);

                txtLoc.setText(mapLoc);


            }

        }
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

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.new_post,null);

        final EditText txtDesc = (EditText) view.findViewById(R.id.txt_desc);
        txtLoc = (TextView) view.findViewById(R.id.txt_loc_set);
        CircleImageView btnLoc = (CircleImageView) view.findViewById(R.id.btn_set_loc);


        Button btnPOST = (Button) view.findViewById(R.id.btn_dialog);
        Button btnCancel  = (Button) view.findViewById(R.id.btn_dialog_cancel);
        final ProgressBar progressBar1 = (ProgressBar) view.findViewById(R.id.prog_post);





        final AlertDialog alertDialog = new AlertDialog.Builder(MainTest.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("New Post");

        // Setting Dialog Message
        alertDialog.setMessage("Please write a detailed post");
        alertDialog.setCancelable(false);


        // Setting Icon to Dialog

        // Setting OK Button

        alertDialog.setView(view);
        // Showing Alert Message
        alertDialog.show();

        btnPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (!TextUtils.isEmpty(txtDesc.getText().toString()) && mapLoc != null){

                    progressBar1.setVisibility(View.VISIBLE);



                    java.util.Map<String , String> map = new HashMap<>();

                    map.put("name" , Myname);
                    map.put("userImg" , MyImg);
                    map.put("date" , "default");
                    map.put("desc" , txtDesc.getText().toString());
                    map.put("like" , "like");
                    map.put("loc" , mapLoc);
                    map.put("lat" , lat);
                    map.put("lang" , lang);


                    reference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                progressBar1.setVisibility(View.INVISIBLE);
                                alertDialog.dismiss();
                            }
                        }
                    });


                }else {



                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MainTest.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });




    }






}
