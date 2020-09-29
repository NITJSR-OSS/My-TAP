package com.nitjsr.tapcell.AddInterviewExperience;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nitjsr.tapcell.Modals.InterviewExperience;
import com.nitjsr.tapcell.R;

public class AddInterviewExperienceActivity extends Activity implements  AdapterView.OnItemSelectedListener{

    String[] branches = {"CHOOSE","CSE", "ECE", "Mechanical", "Electrical", "Civil","Metallurgy","Production","MCA","M.Tech"};
    String[] difficulty={"CHOOSE","Very Easy","Easy","Average","Difficult","Very Difficult"};
    String[] interviewMode ={"CHOOSE","College","Applied Online","Referral"};

    private int mCnfStatus=0;
    private EditText mStudName,mWhatsappNum,mLinkedInId;
    private Switch mIsInternshipReview;
    private EditText mCompanyName,mJobTitle,mInternCompany,mProjectDesc,mOnlineRound,mTechRound,mHrRound,mWordsToJr;
    private Spinner mBranchSpin,mInterviewModeSpin,mInterviewDifficultySpin;
    private ImageView mPhotoPickerButton;
    private Button mSendButton;
    private DatabaseReference mMessagesDatabaseReference;
    String branchRes,diffRes,modeRes;
    boolean isInternshipReview;

    String imageUrlIfProfileNotUploaded = "";
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview_experience);

        mStudName = (EditText) findViewById(R.id.studName);
        mBranchSpin=(Spinner) findViewById(R.id.branch);
        mWhatsappNum=(EditText)findViewById(R.id.whatsappNum);
        mLinkedInId=(EditText)findViewById(R.id.linkedInId);
        mIsInternshipReview=(Switch)findViewById(R.id.isInternshipReview);
        mCompanyName = (EditText) findViewById(R.id.companyName);
        mJobTitle = (EditText) findViewById(R.id.jobTitle);
        mInternCompany=(EditText)findViewById(R.id.internCompany);
        mPhotoPickerButton = (ImageView) findViewById(R.id.photoPickerButton);
        mProjectDesc=(EditText)findViewById(R.id.projectDesc);
        mOnlineRound=(EditText)findViewById(R.id.onlineRound);
        mTechRound=(EditText)findViewById(R.id.techRound);
        mHrRound=(EditText)findViewById(R.id.hrRound);
        mInterviewModeSpin=(Spinner)findViewById(R.id.interviewMode);
        mInterviewDifficultySpin=(Spinner)findViewById(R.id.interviewDifficulty);
        mWordsToJr=(EditText)findViewById(R.id.wordsToJr);
        mSendButton = (Button) findViewById(R.id.sendButton);

        Toolbar toolbar = findViewById(R.id.toolbar);

        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UploadImage.class);
//                startActivity(intent);
                intent.putExtra("Value1", "1");
                startActivityForResult(intent, 100);
            }
        });

        mIsInternshipReview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isInternshipReview = true;
                    mJobTitle.setText("Intern");
                } else {
                    isInternshipReview = false;
                    mJobTitle.setText("");
                }
            }
        });

        // Send button sends a Review and clears all the input parameters
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,whatsappNum,linkedInId,company,jobTitle,internCompany,photoUrl;
                String projectDesc,onlinRound,techRound,hrRound,wordsToJr;

                name=mStudName.getText().toString();
                whatsappNum=mWhatsappNum.getText().toString();
                linkedInId=mLinkedInId.getText().toString();
                company=mCompanyName.getText().toString();
                jobTitle=mJobTitle.getText().toString();
                internCompany=mInternCompany.getText().toString();
                photoUrl=UploadImage.imageUrl;
                projectDesc=mProjectDesc.getText().toString();
                onlinRound=mOnlineRound.getText().toString();
                techRound=mTechRound.getText().toString();
                hrRound=mHrRound.getText().toString();
                wordsToJr=mWordsToJr.getText().toString();

                imageUrlIfProfileNotUploaded = photoUrl;

                // Validate all the required inputs
                int flag = 0;
                if(name.length()==0)
                    validateName();
                else if(branchRes.equals("CHOOSE"))
                    validateBranch();
                else if(company.length()==0)
                    validateCompanyName();
               else if(jobTitle.length()==0)
                    validateJobTitle();
               else if(onlinRound.length()==0)
                   validateOnline();
               else if(techRound.length()==0)
                   validateTech();
               else if(hrRound.length()==0)
                   validateHr();
                else if(modeRes.equals("CHOOSE"))
                    validateInterviewMode();
                else if(diffRes.equals("CHOOSE"))
                    validateInterviewDiff();
               else
                   flag=1;

                // Upload the data to the firebase
                if(flag==1) {
                    if (isInternshipReview == true)
                        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2020").child("Internships");
                    else
                        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2020").child("Placements");

                    String childKey = mMessagesDatabaseReference.push().getKey();

                    InterviewExperience interviewExperience = new InterviewExperience(mCnfStatus, name, branchRes, whatsappNum, linkedInId,
                            isInternshipReview, company, jobTitle, internCompany,photoUrl, projectDesc,
                            onlinRound, techRound, hrRound, modeRes, diffRes, wordsToJr, childKey);

                    mMessagesDatabaseReference.child(childKey).setValue(interviewExperience);

                    // Clear all the fields
                    mStudName.setText("");
                    mWhatsappNum.setText("");
                    mLinkedInId.setText("");
                    mCompanyName.setText("");
                    mJobTitle.setText("");
                    mInternCompany.setText("");
                    mProjectDesc.setText("");
                    mOnlineRound.setText("");
                    mTechRound.setText("");
                    mHrRound.setText("");
                    mWordsToJr.setText("");
                    UploadImage.imageUrl=null;
                    showSuccessDialog();
                }else {
                    submitForm();
                    Snackbar.make(view,"Please fill up all the mandatory details.", Snackbar.LENGTH_LONG).show();
                }

            }

        });

        // Dealing with the spinners input
        mBranchSpin.setOnItemSelectedListener(this);
        mInterviewDifficultySpin.setOnItemSelectedListener(this);
        mInterviewModeSpin.setOnItemSelectedListener(this);

        ArrayAdapter branchAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,branches);
        ArrayAdapter difficultyAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,difficulty);
        ArrayAdapter interviewModeAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,interviewMode);

        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        interviewModeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mBranchSpin.setAdapter(branchAdapter);
        mInterviewDifficultySpin.setAdapter(difficultyAdapter);
        mInterviewModeSpin.setAdapter(interviewModeAdapter);


        // on navigation up
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete the profile image if interview experience is not submitted
                if (imageUrlIfProfileNotUploaded!=null && !imageUrlIfProfileNotUploaded.equals("")) {
                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrlIfProfileNotUploaded);

                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully
//                            Toast.makeText(AddInterviewExperienceActivity.this, "Image deleted successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
//                            Toast.makeText(AddInterviewExperienceActivity.this, "Failed to delete the image!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                onBackPressed();
            }
        });
    }

    // Displaying profile image in the form
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            if(resultCode == Activity.RESULT_OK){
                String url =data.getStringExtra("result");

                imageUrlIfProfileNotUploaded = url;

                Glide.with(AddInterviewExperienceActivity.this)
                        .load(url)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.user_avtar)
                                .fitCenter())
                        .into(mPhotoPickerButton);
            }
        }
    }

    public void showSuccessDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.interview_exp_success_dialog, viewGroup, false);

        Button addButton = dialogView.findViewById(R.id.buttonOk);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                onBackPressed();
            }
        });
    }


    // Taking the inputs from spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        if(parent.getId()==R.id.branch)
            branchRes=branches[position];
        else if (parent.getId()==R.id.interviewDifficulty)
            diffRes=difficulty[position];
        else if(parent.getId()==R.id.interviewMode)
            modeRes=interviewMode[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if(!validateCompanyName()){
            return;
        }

        if(!validateJobTitle()){
            return;
        }

        if (!validateOnline()) {
            return;
        }

        if (!validateTech()) {
            return;
        }

        if(!validateHr()){
            return;
        }
        if(!validateBranch()){
            return;
        }
        if(!validateInterviewMode()){
            return;
        }
        if(!validateInterviewDiff()){
            return;
        }

    }

    // Methods to validate inputs
    private boolean validateName() {
        if (mStudName.getText().toString().trim().isEmpty()) {
            requestFocus(mStudName);
            return false;
        }
        return true;
    }

    private boolean validateOnline() {
        String online = mOnlineRound.getText().toString().trim();
        if (online.isEmpty()) {
            requestFocus(mOnlineRound);
            return false;
        }
        return true;
    }

    private boolean validateTech() {
        if (mTechRound.getText().toString().trim().isEmpty()) {
            requestFocus(mTechRound);
            return false;
        }
        return true;
    }

    private boolean validateCompanyName() {
        if (mCompanyName.getText().toString().trim().isEmpty()) {
            requestFocus(mCompanyName);
            return false;
        }
        return true;
    }

    private boolean validateJobTitle() {
        if (mJobTitle.getText().toString().trim().isEmpty()) {
            requestFocus(mJobTitle);
            return false;
        }
        return true;
    }

    private boolean validateHr() {
        if (mHrRound.getText().toString().trim().isEmpty()) {
            requestFocus(mHrRound);
            return false;
        }
        return true;
    }

    private boolean validateBranch() {
        if (branchRes.equals("CHOOSE")) {
            requestFocus(mBranchSpin);
            return false;
        }
        return true;
    }
    private boolean validateInterviewMode() {
        if (modeRes.equals("CHOOSE")) {
            requestFocus(mInterviewModeSpin);
            return false;
        }
        return true;
    }
    private boolean validateInterviewDiff() {
        if (diffRes.equals("CHOOSE")) {
            requestFocus(mInterviewDifficultySpin);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}