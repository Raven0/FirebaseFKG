package com.preangerstd.firebasefkg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MahasiswaActivity extends AppCompatActivity {

    private RecyclerView mahasiswaList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CardView inputCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        inputCard = (CardView) findViewById(R.id.InpMahasiswa);
        inputCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputData = new Intent(MahasiswaActivity.this, InputMahasiswaActivity.class);
                startActivity(inputData);
            }
        });

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

                final String cardKey = getRef(position).getKey();
                viewHolder.setTitle(model.getNamaDepan());
                viewHolder.setContent(model.getSelectProdi());
                viewHolder.setImage(getApplicationContext(), model.getUrlPhoto());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singlePost = new Intent(MahasiswaActivity.this, DetailMahasiswaActivity.class);
                        singlePost.putExtra("postId",cardKey);
                        startActivity(singlePost);
                    }
                });
            }
        };

        mahasiswaList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        View mView;

        FirebaseAuth mAuth;

        public PostViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mAuth = FirebaseAuth.getInstance();
        }

        public void setTitle(String title){
            TextView postTitle = (TextView) mView.findViewById(R.id.namaMahasiswa);
            postTitle.setText(title);
        }

        public void setContent(String content){
            TextView postContent = (TextView) mView.findViewById(R.id.prodiMahasiswa);
            postContent.setText(content);
        }

        public void setImage(Context context, String image){
            ImageView postImage = (ImageView) mView.findViewById(R.id.imgMahasiswa);
            Picasso.with(context).load(image).into(postImage);
        }

    }

    private void checkUserExist() {
        if(mAuth.getCurrentUser() != null){
            final String userid = mAuth.getCurrentUser().getUid();
        }
    }

    private void logout() {
        mAuth.signOut(); //make sure AuthStateListener is added
    }
}
