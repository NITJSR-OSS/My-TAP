package com.nitjsr.tapcell.Statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjsr.tapcell.PastRecruiters.Recruiters;
import com.nitjsr.tapcell.R;

import java.util.ArrayList;

public class PastStatisticsActivity extends Activity {

    DatabaseReference mDatabaseReference;
    ImageView statsImage;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar=findViewById(R.id.progressBar);
        statsImage = (ImageView)findViewById(R.id.imageView);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("PastStats");

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Recruiters statsUrl = snapshot.getValue(Recruiters.class);

                        Glide.with(PastStatisticsActivity.this)
                                .load(statsUrl.getImages())
                                .apply(new RequestOptions()
                                        .fitCenter())
                                .into(statsImage);

                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
