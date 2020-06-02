package com.example.juniors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class talkviewactivity extends AppCompatActivity {

    Spinner sp1,sp2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talkviewactivity);
        sp1 = (Spinner)findViewById(R.id.spinner1);
        sp2 = (Spinner)findViewById(R.id.spinner2);
        b1=(Button)findViewById(R.id.getbutt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed();
            }
        });
    }

    public void proceed(){
        String s1 = String.valueOf(sp1.getSelectedItem());
        String s2 = String.valueOf(sp2.getSelectedItem());
        Toast.makeText(talkviewactivity.this,s1+" "+s2,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(talkviewactivity.this,talkviewlistactivity.class);
        intent.putExtra("company",s1);
        intent.putExtra("year",s2);
        startActivity(intent);
    }
}
