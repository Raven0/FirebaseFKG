package com.preangerstd.firebasefkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailProdiActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView pNama, pKonsentrasi, pAlamat, pGelar, pEmail;
    private String postKey = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prodi);

        postKey = getIntent().getExtras().getString("postId");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("tbProdi");
        mAuth = FirebaseAuth.getInstance();

        pNama = (TextView) findViewById(R.id.NamaSingleProdi);
        pKonsentrasi = (TextView) findViewById(R.id.KonsentrasiSingleProdi);
        pAlamat = (TextView) findViewById(R.id.AlamatSingleProdi);
        pGelar = (TextView) findViewById(R.id.GelarSingleProdi);
        pEmail = (TextView) findViewById(R.id.EmailSingleProdi);

        mDatabase.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namaProdi = (String) dataSnapshot.child("programStudi").getValue();
                String konsenProdi = (String) dataSnapshot.child("konsentrasi").getValue();
                String alamatProdi = (String) dataSnapshot.child("alamatProdi").getValue();
                String gelarProdi = (String) dataSnapshot.child("gelar").getValue();
                String emailProdi = (String) dataSnapshot.child("email").getValue();

                pNama.setText(namaProdi);
                pKonsentrasi.setText(konsenProdi);
                pAlamat.setText(alamatProdi);
                pGelar.setText(gelarProdi);
                pEmail.setText(emailProdi);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
