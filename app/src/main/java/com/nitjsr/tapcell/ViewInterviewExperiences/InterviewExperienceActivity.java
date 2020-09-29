package com.nitjsr.tapcell.ViewInterviewExperiences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjsr.tapcell.Modals.InterviewExperience;
import com.nitjsr.tapcell.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterviewExperienceActivity extends Activity {

    DatabaseReference mMessagesDatabaseReference;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TextView toolbarTitle;
    ImageView searchIcon;
    ProgressBar progressBar;
    static List<InterviewExperience> interviewExperienceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_experience);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        progressBar=findViewById(R.id.progressBar);
        searchIcon = (ImageView)findViewById(R.id.searchIcon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String expType = getIntent().getStringExtra("ExpType");
        toolbarTitle.setText(getIntent().getStringExtra("Title"));

        mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child("2020").child(expType);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        interviewExperienceList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(InterviewExperienceActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final InterviewExperienceAdapter adapter = new InterviewExperienceAdapter(getApplicationContext(), interviewExperienceList);
        recyclerView.setAdapter(adapter);

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                interviewExperienceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    InterviewExperience interviewExperience = dataSnapshot.getValue(InterviewExperience.class);
                    if (interviewExperience.getCnfStatus()==1)
                         interviewExperienceList.add(interviewExperience);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Search activity
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterviewExperienceActivity.this, SearchActivity.class));
            }
        });
    }
}