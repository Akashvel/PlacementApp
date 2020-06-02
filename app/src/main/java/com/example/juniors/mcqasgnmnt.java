package com.example.juniors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mcqasgnmnt extends AppCompatActivity {
    TextView t1,t2,t3;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqasgnmnt);
        t1=(TextView)findViewById(R.id.textboxtopic);
        t2=(TextView)findViewById(R.id.textboxquesone);
        t3=(TextView)findViewById(R.id.textboxquestwo);
        t2.setMovementMethod(new ScrollingMovementMethod());
        t3.setMovementMethod(new ScrollingMovementMethod());
        myref = FirebaseDatabase.getInstance().getReference("QUESTIONS");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Upload up = dataSnapshot.getValue(Upload.class);
                    String s1 = up.getTopic();
                    String s2 = up.getQuesone();
                    String s3 = up.getQuestwo();
                    t1.setText(s1);
                    t2.setText(s2);
                    t3.setText(s3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
