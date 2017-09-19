package com.preangerstd.firebasefkg;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class InputMahasiswaActivity extends AppCompatActivity {

    private Calendar myCalendar = Calendar.getInstance();
    private EditText edittext;
    private DatabaseReference mDatabaseProdi;

    private ImageButton btnImg;
    private EditText tbNama, tbAlamat;
    private Button btnSubmit;
    private static final int GALLERY_REQ = 1;
    private StorageReference storage;
    private DatabaseReference mDatabaseMahasiswa;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    private Spinner agama,darah,tahun,negara,cbProdi,jk;

    RadioButton rb1,rb2;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mahasiswa);

        storage = FirebaseStorage.getInstance().getReference();
        mDatabaseMahasiswa = FirebaseDatabase.getInstance().getReference().child("tbMahasiswa");
        btnImg = (ImageButton) findViewById(R.id.btnSetupImg);
        tbNama = (EditText) findViewById(R.id.tbNamaMahasiswa);
        tbAlamat = (EditText) findViewById(R.id.tbAlamatMahasiswa);
        agama = (Spinner)findViewById(R.id.cbAgamaMahasiswa);
        darah = (Spinner)findViewById(R.id.cbDarahMahasiswa);
        jk = (Spinner)findViewById(R.id.cbGenderMahasiswa);
        btnSubmit = (Button) findViewById(R.id.btnSubmitMhsw);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQ);
            }
        });

        //TGL LAHIR

        edittext= (EditText) findViewById(R.id.tbTglMahasiswa);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InputMahasiswaActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // KEWARGANEGARAAN

        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        negara = (Spinner)findViewById(R.id.cbNegaraMahasiswa);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        negara.setAdapter(adapter);

        //ANGKATAN


        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterAngkatan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        tahun = (Spinner)findViewById(R.id.cbAngkatanMahasiswa);
        tahun.setAdapter(adapterAngkatan);

        //Firebase Start Here

        mDatabaseProdi = FirebaseDatabase.getInstance().getReference().child("tbProdi");

        // PROGRAM STUDI

        mDatabaseProdi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> prodi = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String prodiName = areaSnapshot.child("programStudi").getValue(String.class);
                    prodi.add(prodiName);
                }

                cbProdi = (Spinner) findViewById(R.id.cbProdiMahasiswa);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(InputMahasiswaActivity.this, android.R.layout.simple_spinner_item, prodi);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cbProdi.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void startPosting() {
        progressDialog.setMessage("Uploading");
        final String name_val = tbNama.getText().toString().trim();
        final String address_val = tbAlamat.getText().toString().trim();
        final String birth_val = edittext.getText().toString().trim();
        final String gender_val = jk.getSelectedItem().toString();
        final String religion_val = agama.getSelectedItem().toString();
        final String blood_val = darah.getSelectedItem().toString();
        final String year_val = tahun.getSelectedItem().toString();
        final String nation_val = negara.getSelectedItem().toString();
        final String prodi_val = cbProdi.getSelectedItem().toString();
        if(imageUri != null){
            progressDialog.show();
            StorageReference filepath = storage.child("gallery").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests")
                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    final DatabaseReference newPost = mDatabaseMahasiswa.push();

                    mDatabaseMahasiswa.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newPost.child("agama").setValue(religion_val);
                            newPost.child("alamat").setValue(address_val);
                            newPost.child("angkatan").setValue(year_val);
                            newPost.child("golDarah").setValue(blood_val);
                            newPost.child("jenisKelamin").setValue(gender_val);
                            newPost.child("kewarganegaraan").setValue(nation_val);
                            newPost.child("namaMahasiswa").setValue(name_val);
                            newPost.child("selectProdi").setValue(prodi_val);
                            newPost.child("tglLahir").setValue(birth_val);
                            newPost.child("urlPhoto").setValue(downloadUrl.toString());
                            newPost.child("id").setValue(newPost.getKey().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(InputMahasiswaActivity.this, MainActivity.class));
                                    }else {
                                        Toast.makeText(InputMahasiswaActivity.this, "Error Posting", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    progressDialog.dismiss();


                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQ && resultCode == RESULT_OK){
            imageUri = data.getData();
            btnImg.setImageURI(imageUri);
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
