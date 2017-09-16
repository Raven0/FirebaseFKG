package com.preangerstd.firebasefkg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText tbEmail,tbPass;
    private Button btnRegist;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tbEmail = (EditText) findViewById(R.id.tbEmail);
        tbPass = (EditText) findViewById(R.id.tbPassword);
        btnRegist = (Button) findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        String email = tbEmail.getText().toString().trim();
        String pass = tbPass.getText().toString().trim();

        if(pass.length() <= 5){
            Toast.makeText(this, "Pastikan password lebih dari 5 huruf", Toast.LENGTH_LONG).show();
        } else if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

            dialog.setMessage("Signing Up");
            dialog.show();
            mAuth.createUserWithEmailAndPassword(email,pass);
            dialog.dismiss();
            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        }
    }
}
