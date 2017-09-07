package com.preangerstd.firebasefkg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText tbUsername,tbEmail,tbPass;
    private Button btnRegist;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private DatabaseReference mDatabase;
    private String defaultImage = "https://firebasestorage.googleapis.com/v0/b/fir-blog-dbc7c.appspot.com/o/Image_Profile%2Fprofile_default.jpg?alt=media&token=d51f06d3-dca0-4f4b-8287-8059989f3d66";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tbUsername = (EditText) findViewById(R.id.tbUname);
        tbEmail = (EditText) findViewById(R.id.tbEmail);
        tbPass = (EditText) findViewById(R.id.tbPassword);
        btnRegist = (Button) findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        dialog = new ProgressDialog(this);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }

    private void startRegister() {

        final String name = tbUsername.getText().toString().trim();
        String email = tbEmail.getText().toString().trim();
        String pass = tbPass.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            dialog.setMessage("Signing Up");
            dialog.show();
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userid = mAuth.getCurrentUser().getUid();
                        DatabaseReference user = mDatabase.child(userid);
                        user.child("name").setValue(name);
                        user.child("image").setValue(defaultImage);
                        dialog.dismiss();
                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }
    }
}
