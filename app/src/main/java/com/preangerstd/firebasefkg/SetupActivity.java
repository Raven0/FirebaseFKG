package com.preangerstd.firebasefkg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SetupActivity extends AppCompatActivity {

    private ImageButton mSetupImg;
    private EditText tbName;
    private Button btnSetupSubmit;
    private Uri mIamgeUri = null;
    private DatabaseReference mDatabaseSetup;
    private FirebaseAuth mAuth;
    private StorageReference mStorageSetup;
    private ProgressDialog dialogSetup;

    private static final int GALLERY_REQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mSetupImg = (ImageButton) findViewById(R.id.btnSetupImg);
        tbName = (EditText) findViewById(R.id.tbSetupName);
        btnSetupSubmit = (Button) findViewById(R.id.btnSetupSubmit);
        mDatabaseSetup = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        mStorageSetup = FirebaseStorage.getInstance().getReference().child("Image_Profile");
        dialogSetup = new ProgressDialog(this);

        mSetupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQ);
            }
        });

        Toast.makeText(SetupActivity.this, "Please Setup your Account First", Toast.LENGTH_LONG).show();

        btnSetupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSetupAccount();
            }
        });
    }

    private void startSetupAccount() {
        final String name = tbName.getText().toString().trim();
        final String userid = mAuth.getCurrentUser().getUid();
        if(!TextUtils.isEmpty(name) && mIamgeUri != null){
            dialogSetup.setMessage("Setting up your Account");
            dialogSetup.show();

            StorageReference filepath = mStorageSetup.child(mIamgeUri.getLastPathSegment());

            filepath.putFile(mIamgeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests")
                    String downloadUri = taskSnapshot.getDownloadUrl().toString();

                    mDatabaseSetup.child(userid).child("name").setValue(name);
                    mDatabaseSetup.child(userid).child("image").setValue(downloadUri);
                    dialogSetup.dismiss();

                    Intent goMain = new Intent(SetupActivity.this, MainActivity.class);
                    goMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goMain);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQ && resultCode == RESULT_OK){

            mIamgeUri = data.getData();

            CropImage.activity(mIamgeUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mSetupImg.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
