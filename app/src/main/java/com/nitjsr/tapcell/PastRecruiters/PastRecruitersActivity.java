package com.nitjsr.tapcell.PastRecruiters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjsr.tapcell.Modals.InterviewExperience;
import com.nitjsr.tapcell.R;

import java.util.ArrayList;
import java.util.List;

public class PastRecruitersActivity extends Activity {

    DatabaseReference mMessagesDatabaseReference;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_recruiters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child("PastRecruiters");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progressBar);

        final List<Recruiters> pastRecruitersList = new ArrayList<>();

        layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                pastRecruitersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Recruiters recruiters = dataSnapshot.getValue(Recruiters.class);
                    pastRecruitersList.add(recruiters);
                }

                PastRecruitersAdapter adapter = new PastRecruitersAdapter(getApplicationContext(), pastRecruitersList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}