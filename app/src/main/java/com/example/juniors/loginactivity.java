package com.example.juniors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class loginactivity extends AppCompatActivity {
    Button b1;
    EditText e1,e2;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.password);
        b1=(Button)findViewById(R.id.loginbutton);
        rg=(RadioGroup)findViewById(R.id.radiogroup);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }
    public void check(){
        String s1 = e1.getText().toString().trim();
        String s2 = e2.getText().toString().trim();
        if(s1.equals("theteam") && s2.equals("theTeam")){
            int selectedid = rg.getCheckedRadioButtonId();
            if(selectedid!=-1) {
                RadioButton radioButton = (RadioButton) rg.findViewById(selectedid);
                String str = radioButton.getText().toString();
                if (str.equals("AssignWork")) {
                    Intent intent = new Intent(getApplicationContext(), assignwork.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), talkuploadactivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Select Option", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(loginactivity.this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }
}
