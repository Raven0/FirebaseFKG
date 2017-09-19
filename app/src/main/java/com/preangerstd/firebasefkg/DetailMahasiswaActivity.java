package com.preangerstd.firebasefkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailMahasiswaActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView dNama, dNamaB, dGender, dAgama, dAlamat, dBlood, dNation, dYear, dTgl, dProdi;
    private ImageView dFoto;
    private String postKey = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);

        postKey = getIntent().getExtras().getString("postId");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("tbMahasiswa");
        mAuth = FirebaseAuth.getInstance();

        dNama = (TextView) findViewById(R.id.NamaSingleMahasiswa);
        dNamaB = (TextView) findViewById(R.id.NamaBSingleMahasiswa);
        dGender = (TextView) findViewById(R.id.GenderSingleMahasiswa);
        dAlamat = (TextView) findViewById(R.id.AlamatSingleMahasiswa);
        dAgama = (TextView) findViewById(R.id.AgamaSingleMahasiswa);
        dBlood = (TextView) findViewById(R.id.DarahSingleMahasiswa);
        dNation = (TextView) findViewById(R.id.NegaraSingleMahasiswa);
        dYear = (TextView) findViewById(R.id.AngkatanSingleMahasiswa);
        dTgl = (TextView) findViewById(R.id.TglSingleMahasiswa);
        dProdi = (TextView) findViewById(R.id.ProdiSingleMahasiswa);
        dFoto = (ImageView) findViewById(R.id.ImgSingleMahasiswa);

        mDatabase.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namaDepan = (String) dataSnapshot.child("namaDepan").getValue();
                String namaBelakang = (String) dataSnapshot.child("namaBelakang").getValue();
                String genderMahasiswa = (String) dataSnapshot.child("jenisKelamin").getValue();
                String alamatMahasiswa = (String) dataSnapshot.child("alamat").getValue();
                String agamaMahasiswa = (String) dataSnapshot.child("agama").getValue();
                String bloodMahasiswa = (String) dataSnapshot.child("golDarah").getValue();
                String nationMahasiswa = (String) dataSnapshot.child("kewarganegaraan").getValue();
                String yearMahasiswa = (String) dataSnapshot.child("angkatan").getValue();
                String tglMahasiswa = (String) dataSnapshot.child("tglLahir").getValue();
                String prodiMahasiswa = (String) dataSnapshot.child("selectProdi").getValue();
                String fotoMahasiswa = (String) dataSnapshot.child("urlPhoto").getValue();

                dNama.setText(namaDepan);
                dNamaB.setText(namaBelakang);
                dGender.setText(genderMahasiswa);
                dAlamat.setText(alamatMahasiswa);
                dAgama.setText(agamaMahasiswa);
                dBlood.setText(bloodMahasiswa);
                dNation.setText(nationMahasiswa);
                dYear.setText(yearMahasiswa);
                dTgl.setText(tglMahasiswa);
                dProdi.setText(prodiMahasiswa);

                Picasso.with(DetailMahasiswaActivity.this).load(fotoMahasiswa).into(dFoto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
