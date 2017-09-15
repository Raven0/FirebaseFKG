package com.preangerstd.firebasefkg;

import android.os.Bundle;
import android.support.annotation.NonNull;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
=======
=======
>>>>>>> parent of 9db7805... commit
=======
>>>>>>> parent of 9db7805... commit
=======
>>>>>>> parent of 9db7805... commit
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> parent of 9db7805... commit

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
<<<<<<< HEAD
=======
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 9db7805... commit
=======
>>>>>>> parent of 9db7805... commit

public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 9db7805... commit
=======

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

>>>>>>> parent of 9db7805... commit
=======

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

>>>>>>> parent of 9db7805... commit
    private RecyclerView mahasiswaList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;
>>>>>>> parent of 9db7805... commit
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    /*Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);*/
                }
            }
        };
<<<<<<< HEAD
=======

        mahasiswaList = (RecyclerView) findViewById(R.id.mahasiswaList);
        mahasiswaList.setHasFixedSize(true);
        mahasiswaList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("tbMahasiswa");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUser.keepSynced(true);
>>>>>>> parent of 9db7805... commit
        checkUserExist();
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*if(mAuth.getCurrentUser() != null){
        }*/

        mAuth.addAuthStateListener(mAuthListener);



    }

    private void checkUserExist() {
        if(mAuth.getCurrentUser() != null) {
            final String userid = mAuth.getCurrentUser().getUid();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

<<<<<<< HEAD
=======
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mainmenu) {

            /*LinearLayout mainLayout = (LinearLayout) findViewById(R.id. main_container);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.content_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);*/

        } else if (id == R.id.nav_mahasiswa) {

            /*LinearLayout mainLayout = (LinearLayout) findViewById(R.id. main_container);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.content_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);*/

        } else if (id == R.id.nav_prodi) {

            /*LinearLayout mainLayout = (LinearLayout) findViewById(R.id. main_container);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.content_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);*/

        } else if (id == R.id.nav_detail) {

            /*LinearLayout mainLayout = (LinearLayout) findViewById(R.id. main_container);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.content_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Firebase Start Here

    @Override
    protected void onStart() {
        super.onStart();

        /*if(mAuth.getCurrentUser() != null){
        }*/

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<DataMahasiswa, PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataMahasiswa, PostViewHolder>(

                DataMahasiswa.class,
                R.layout.card_mahasiswa,
                PostViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, final DataMahasiswa model, int position) {

                final String postKey = getRef(position).getKey();
                viewHolder.setNamaMahasiswa(model.getNamaMahasiswa());
                viewHolder.setJenisKelamin(model.getJenisKelamin());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };

        mahasiswaList.setAdapter(firebaseRecyclerAdapter);

    }

    private void checkUserExist() {
        if(mAuth.getCurrentUser() != null){
            final String userid = mAuth.getCurrentUser().getUid();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(userid)){
                        Toast.makeText(MainActivity.this, "Please Setup your Account", Toast.LENGTH_LONG).show();
                        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupIntent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        View mView;

        ImageButton mBtnLike;

        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;

        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mAuth = FirebaseAuth.getInstance();
        }

        public void setNamaMahasiswa(String title){
            TextView postTitle = (TextView) mView.findViewById(R.id.namaMahasiswa);
            postTitle.setText(title);
        }

        public void setJenisKelamin(String content){
            TextView postContent = (TextView) mView.findViewById(R.id.jkMahasiswa);
            postContent.setText(content);
        }

        public void setImage(Context context, String image){
            ImageView postImage = (ImageView) mView.findViewById(R.id.imgMahasiswa);
            Picasso.with(context).load(image).into(postImage);
        }

    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 9db7805... commit
=======
>>>>>>> parent of 9db7805... commit
=======
>>>>>>> parent of 9db7805... commit
=======
>>>>>>> parent of 9db7805... commit
    private void logout() {
        mAuth.signOut(); //make sure AuthStateListener is added
    }
}
