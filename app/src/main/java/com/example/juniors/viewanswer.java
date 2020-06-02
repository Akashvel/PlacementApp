package com.example.juniors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewanswer extends AppCompatActivity {

    ListView listView;
    DatabaseReference mdatabaseReference;
    private List<Upload1> uploadList = new ArrayList<Upload1>();
    private List<Upload1> uploadList1 = new ArrayList<Upload1>();
    int i,j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewanswer);
        listView=(ListView)findViewById(R.id.listv);
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("File Link");
        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload1 up = postSnapshot.getValue(Upload1.class);
                    uploadList.add(up);
                }
                for(i=uploadList.size()-1;i>=0;i--){
                    Upload1 up = uploadList.get(i);
                    uploadList1.add(up);
                }
                String[] uploads = new String[uploadList.size()];
                //String[] uploadsone = new String[uploadList.size()];
                for (i = 0,j=uploadList.size()-1; i < uploads.length; i++,j--) {
                    uploads[j] = uploadList.get(i).getName();
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Upload1 upload = uploadList1.get(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });
    }
}
