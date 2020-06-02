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

public class codingasgnmnt extends AppCompatActivity {
    TextView t1;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codingasgnmnt);
        t1=(TextView)findViewById(R.id.textboxcoding);
        t1.setMovementMethod(new ScrollingMovementMethod());
        myref = FirebaseDatabase.getInstance().getReference("QUESTIONS");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Upload up = dataSnapshot.getValue(Upload.class);
                String s1 = up.getCodingques();
                t1.setText(s1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
