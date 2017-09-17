package com.preangerstd.firebasefkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProdiActivity extends AppCompatActivity {

    private RecyclerView prodiList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodi);

        //Firebase Start Here

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(ProdiActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        prodiList = (RecyclerView) findViewById(R.id.prodiList);
        prodiList.setHasFixedSize(true);
        prodiList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("tbProdi");
        checkUserExist();
    }

    //Firebase Start Here

    @Override
    protected void onStart() {
        super.onStart();

        /*if(mAuth.getCurrentUser() != null){
        }*/

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<DataProgramstudi, ProdiViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataProgramstudi, ProdiViewHolder>(

                DataProgramstudi.class,
                R.layout.card_prodi,
                ProdiViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ProdiViewHolder viewHolder, final DataProgramstudi model, int position) {

                final String cardKey = getRef(position).getKey();
                viewHolder.setTitle(model.getProgramStudi());
                /*viewHolder.setContent(model.getKonsentrasi());*/

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ProdiActivity.this,"You Clicked a Data" + cardKey, Toast.LENGTH_LONG).show();
                        /*Intent singlePost = new Intent(MainActivity.this, SinglePostActivity.class);
                        singlePost.putExtra("postId",postKey);
                        startActivity(singlePost);*/
                    }
                });
            }
        };

        prodiList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ProdiViewHolder extends RecyclerView.ViewHolder{

        View mView;

        FirebaseAuth mAuth;

        public ProdiViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mAuth = FirebaseAuth.getInstance();
        }

        public void setTitle(String title){
            TextView postTitle = (TextView) mView.findViewById(R.id.namaProdi);
            postTitle.setText(title);
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
