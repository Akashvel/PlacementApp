package com.example.juniors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class talkuploadactivity extends AppCompatActivity {

    Spinner sp1;
    EditText e1,e2,e3;
    CheckBox c1;
    Button b1,b2;
    ProgressBar mprogess;
    public Uri filePath1;
    DatabaseReference myref;
    private StorageReference Folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talkuploadactivity);


        e1=(EditText)findViewById(R.id.nameet);
        e2=(EditText)findViewById(R.id.yearet);
        sp1=(Spinner)findViewById(R.id.spcmp);

        mprogess=(ProgressBar)findViewById(R.id.progressBar);
        c1=(CheckBox)findViewById(R.id.checkBox);
        mprogess.setVisibility(View.GONE);
        b1=(Button)findViewById(R.id.selectvideobutt);
        b2=(Button)findViewById(R.id.uploadbutton);
        c1.setEnabled(false);
        Folder = FirebaseStorage.getInstance().getReference().child("VideosPlacementTalk");
        myref = FirebaseDatabase.getInstance().getReference("Link");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadvideo();
            }
        });

    }
    public void showFileChooser(int code) {
        Intent intent = new Intent();
        intent.setType("video/mp4");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),code);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath1 = data.getData();
            c1.setEnabled(true);
            c1.setChecked(true);
        }
    }
    public void uploadvideo(){
        final String s1 = e1.getText().toString().trim();
        final String s2 = e2.getText().toString().trim();
        if(c1.isChecked() && !(s1.equals("")) && !(s2.equals(""))) {
            mprogess.setVisibility(View.VISIBLE);
            final String s3 = sp1.getSelectedItem().toString().toLowerCase();
            final StorageReference videofile = Folder.child("Videos" + filePath1.getLastPathSegment());
            videofile.putFile(filePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(talkuploadactivity.this,"UPLOADED",Toast.LENGTH_SHORT).show();
                    mprogess.setVisibility(View.GONE);
                    videofile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myref = FirebaseDatabase.getInstance().getReference("Link").child(s2).child(s3);

                            Upload1 upload = new Upload1(s1,String.valueOf(uri));
                            String id = myref.push().getKey();
                            myref.child(id).setValue(upload);
                        }
                    });

                    Intent intent = new Intent(talkuploadactivity.this,loginactivity.class);
                    startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    //mprogess.setProgress((int)progress);
                    Toast.makeText(talkuploadactivity.this,"UPLOADING PLEASE WAIT",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(talkuploadactivity.this,"Enter all fields and Select The File",Toast.LENGTH_SHORT).show();
        }

    }
}
