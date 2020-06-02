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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class assignwork extends AppCompatActivity {
    EditText mq1,mq2,cq1,mcqtopic,word;
    Button upld;
    Button mans,cans,answerupload;
    DatabaseReference myref;
    public Uri filePath1;
    public Uri filePath2;
    CheckBox c1,c2;
    private StorageReference Folder;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignwork);
        mcqtopic=(EditText)findViewById(R.id.topictextbox);
        mq1=(EditText)findViewById(R.id.quesone);
        mq2=(EditText)findViewById(R.id.questwo);
        cq1=(EditText)findViewById(R.id.codingques);
        word=(EditText)findViewById(R.id.textboxword);

        upld=(Button)findViewById(R.id.uploadbutton);
        mans = (Button)findViewById(R.id.mcqanswer);
        cans = (Button)findViewById(R.id.codanswer);
        answerupload=(Button)findViewById(R.id.uploadanswer);
        c1=(CheckBox)findViewById(R.id.checkbox1);
        c2=(CheckBox)findViewById(R.id.checkbox2);
        c1.setEnabled(false);
        c2.setEnabled(false);

        //Date
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());



        Folder = FirebaseStorage.getInstance().getReference().child("PDF Folder");
        myref = FirebaseDatabase.getInstance().getReference("QUESTIONS");
        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtask();
            }
        });
        mans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(1);
            }
        });
        cans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(2);
            }
        });
        answerupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    public void uploadtask(){
        String s1 = mcqtopic.getText().toString();
        String s2 = mq1.getText().toString();
        String s3 = mq2.getText().toString();
        String s4 = cq1.getText().toString();
        String s5 = word.getText().toString();
        Upload up = new Upload(s1,s2,s3,s4,s5);
        if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")){
            Toast.makeText(assignwork.this,"Insert All The Fields",Toast.LENGTH_SHORT).show();
        }
        else {
            myref.setValue(up);
            Toast.makeText(assignwork.this, "UPLOADED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(assignwork.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void showFileChooser(int code) {
        Intent intent = new Intent();
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),code);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath1 = data.getData();
            c1.setChecked(true);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath2 = data.getData();
            c2.setChecked(true);
        }
    }
    public void upload(){
        if(c1.isChecked() && c2.isChecked()) {

            final String s1 = date;
            final StorageReference Imagename = Folder.child("Answer" + filePath1.getLastPathSegment());
            Imagename.putFile(filePath1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(assignwork.this,"UPLOADED",Toast.LENGTH_SHORT).show();


                    Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myref = FirebaseDatabase.getInstance().getReference("File Link");

                            Upload1 upload = new Upload1(s1 + "mcq", String.valueOf(uri));
                            String id = myref.push().getKey();
                            myref.child(id).setValue(upload);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            //second
            final StorageReference Imagename1 = Folder.child("Answer" + filePath2.getLastPathSegment());
            Imagename1.putFile(filePath2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(assignwork.this,"UPLOADED",Toast.LENGTH_SHORT).show();


                    Imagename1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myref = FirebaseDatabase.getInstance().getReference("File Link");

                            Upload1 upload = new Upload1(s1 + "code", String.valueOf(uri));
                            String id = myref.push().getKey();
                            myref.child(id).setValue(upload);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            Toast.makeText(assignwork.this,"UPLOADED",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(assignwork.this,"Select The Files",Toast.LENGTH_SHORT).show();
        }

    }
}
