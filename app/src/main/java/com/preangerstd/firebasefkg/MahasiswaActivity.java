package com.preangerstd.firebasefkg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MahasiswaActivity extends AppCompatActivity {

    private RecyclerView mahasiswaList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        //Firebase Start Here

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(MahasiswaActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        mahasiswaList = (RecyclerView) findViewById(R.id.mahasiswaList);
        mahasiswaList.setHasFixedSize(true);
        mahasiswaList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("tbMahasiswa");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUser.keepSynced(true);
        checkUserExist();
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
                        Toast.makeText(MahasiswaActivity.this, "Please Setup your Account", Toast.LENGTH_LONG).show();
                        Intent setupIntent = new Intent(MahasiswaActivity.this, SetupActivity.class);
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

    private void logout() {
        mAuth.signOut(); //make sure AuthStateListener is added
    }
}
