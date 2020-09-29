package com.nitjsr.tapcell.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjsr.tapcell.Activity.AboutActivity;
import com.nitjsr.tapcell.AddInterviewExperience.AddInterviewExperienceActivity;
import com.nitjsr.tapcell.Carousel.SliderAdapter;
import com.nitjsr.tapcell.Modals.SliderImageAndText;
import com.nitjsr.tapcell.PastRecruiters.PastRecruitersActivity;
import com.nitjsr.tapcell.Utils.SharedPrefManager;
import com.nitjsr.tapcell.Statistics.PastStatisticsActivity;
import com.nitjsr.tapcell.Portfolio.PortfolioActivity;
import com.nitjsr.tapcell.ViewInterviewExperiences.InterviewExperienceActivity;
import com.nitjsr.tapcell.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    ViewPager viewPager;
    TabLayout indicator;
    List<String> sliderImages;
    List<String> sliderText;
    String sliderText1,sliderText2,sliderText3;
    String sliderImage1,sliderImage2,sliderImage3;
    DatabaseReference mPlacementYearReference; //to get current placement year
    String currPlacementYear;
    DatabaseReference mSliderReference;
    TextView mCurrPlacementYear;

    LinearLayout viewInterviewExperiences, shareInterviewExperience, internshipExperience;
    CardView pastStats, pastRecruiters, tapTeam, about;

    private SharedPrefManager prefManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        indicator = (TabLayout) root.findViewById(R.id.indicator);
        viewInterviewExperiences =(LinearLayout)root.findViewById(R.id.placementExperiences);
        shareInterviewExperience = (LinearLayout)root.findViewById(R.id.shareInterviewExperience);
        internshipExperience = (LinearLayout)root.findViewById(R.id.internshipExprience);
        mCurrPlacementYear = (TextView)root.findViewById(R.id.currPlacementYear);
        pastStats = (CardView)root.findViewById(R.id.pastStats);
        pastRecruiters = (CardView)root.findViewById(R.id.pastRecruiters);
        tapTeam = (CardView)root.findViewById(R.id.tapTeam);
        about = (CardView)root.findViewById(R.id.about);

        prefManager = new SharedPrefManager(getActivity());
        if(prefManager.isPopupDialogShown()==false){
            showReportedDialog();
        }

        getPlacementYear();
        setCarouselViewPager();

        viewInterviewExperiences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), InterviewExperienceActivity.class);
                i.putExtra("ExpType", "Placements");
                i.putExtra("Title", "Interview Experiences");
                startActivity(i);
            }
        });

        shareInterviewExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddInterviewExperienceActivity.class);
                startActivity(i);
            }
        });

        internshipExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), InterviewExperienceActivity.class);
                i.putExtra("ExpType", "Internships");
                i.putExtra("Title", "Internship Experiences");
                startActivity(i);
            }
        });

        pastStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PastStatisticsActivity.class);
                startActivity(i);
            }
        });

        pastRecruiters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PastRecruitersActivity.class);
                startActivity(i);
            }
        });

        tapTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PortfolioActivity.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AboutActivity.class);
                startActivity(i);
            }
        });


        return root;
    }

    public void setCarouselViewPager() {

        mSliderReference=FirebaseDatabase.getInstance().getReference().child("Carousel");
        mSliderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SliderImageAndText model=dataSnapshot.getValue(SliderImageAndText.class);
                sliderImage1=model.getImage1();
                sliderImage2=model.getImage2();
                sliderImage3=model.getImage3();
                sliderText1=model.getText1();
                sliderText2=model.getText2();
                sliderText3=model.getText3();

                sliderText = new ArrayList<>();
                sliderText.add(sliderText1);
                sliderText.add(sliderText2);
                sliderText.add(sliderText3);

                sliderImages=new ArrayList<>();
                sliderImages.add(sliderImage1);
                sliderImages.add(sliderImage2);
                sliderImages.add(sliderImage3);

                viewPager.setPageTransformer(false, new FadeOutTransformation());
                viewPager.setAdapter(new SliderAdapter(getActivity(), sliderImages, sliderText));
                indicator.setupWithViewPager(viewPager, true);

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new SliderTimer(), 5000, 6000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void getPlacementYear() {
        mPlacementYearReference= FirebaseDatabase.getInstance().getReference("PlacementYear");
        mPlacementYearReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currPlacementYear=dataSnapshot.getValue().toString();
                mCurrPlacementYear.setText(currPlacementYear);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void showReportedDialog() {
        prefManager.setPopupShown(true);

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.interview_exp_request_dialog, null, false);


        Button addButton = dialogView.findViewById(R.id.okBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    // Fade in fade out animation of carousel
    public class FadeOutTransformation implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {
            page.setTranslationX(-position*page.getWidth());

            page.setAlpha(1-Math.abs(position));
        }
    }

    //carousel image auto-slider
    public class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < sliderImages.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } else
                return;
        }
    }



}