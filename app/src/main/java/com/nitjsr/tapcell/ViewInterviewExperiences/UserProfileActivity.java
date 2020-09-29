package com.nitjsr.tapcell.ViewInterviewExperiences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjsr.tapcell.Modals.InterviewExperience;
import com.nitjsr.tapcell.R;

public class UserProfileActivity extends Activity {

    TextView mStudName,mBranch,mCompanyName,mJobTitle,mInternCompany;
    TextView mProjectDesc,mOnlineRound,mTechRound,mHrRound,mInterviewMode,mInterviewDifficulty,mWordsToJr;
    private ImageView mImageView;
    private Button mStudentLinkedIn,mStudentPhone,mStudentWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mImageView=(ImageView)findViewById(R.id.photoUrl);
        mStudName=(TextView)findViewById(R.id.studName);
        mStudentLinkedIn=(Button) findViewById(R.id.linkedInId);
        mStudentPhone=(Button) findViewById(R.id.phoneNum);
        mBranch=(TextView)findViewById(R.id.branch);
        mCompanyName=(TextView)findViewById(R.id.companyName);
        mJobTitle=(TextView)findViewById(R.id.jobTitle);
        mInternCompany=(TextView)findViewById(R.id.internCompany);
        mProjectDesc=(TextView)findViewById(R.id.projectDesc);
        mOnlineRound=(TextView)findViewById(R.id.onlineRound);
        mTechRound=(TextView)findViewById(R.id.techRound);
        mHrRound=(TextView)findViewById(R.id.hrRound);
        mInterviewDifficulty=(TextView)findViewById(R.id.interviewDifficulty);
        mInterviewMode=(TextView)findViewById(R.id.interviewMode);
        mWordsToJr=(TextView)findViewById(R.id.wordsToJr);
        mStudentWhatsapp=(Button)findViewById(R.id.whatsappNum);

        InterviewExperience interviewExperience = (InterviewExperience)getIntent().getSerializableExtra("StudentDetails");

        Glide.with(getApplicationContext())
                .load(interviewExperience.getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.user_avtar)
                        .centerCrop()
                        .fitCenter())
                .into(mImageView);
        mStudName.setText(interviewExperience.getStudName());
        mStudentLinkedIn.setText(interviewExperience.getLinkedInId());
        mStudentPhone.setText(interviewExperience.getWhatsappNum());
        mBranch.setText(interviewExperience.getBranch());
        mCompanyName.setText(interviewExperience.getCompanyName());
        mJobTitle.setText(interviewExperience.getJobTitle());
        mInternCompany.setText(interviewExperience.getInternCompany());
        mProjectDesc.setText(interviewExperience.getProjectDesc());
        mOnlineRound.setText(interviewExperience.getOnlineRound());
        mTechRound.setText(interviewExperience.getTechRound());
        mHrRound.setText(interviewExperience.getHrRound());
        mInterviewDifficulty.setText(interviewExperience.getInterviewDifficulty());
        mInterviewMode.setText(interviewExperience.getInterviewMode());
        mWordsToJr.setText(interviewExperience.getWordsToJr());

        String whatsappUrl = "https://api.whatsapp.com/send?phone=" + "+91" + interviewExperience.getWhatsappNum();
        mStudentWhatsapp.setText(whatsappUrl);

    }
}