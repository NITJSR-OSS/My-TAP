package com.nitjsr.tapcell.ViewInterviewExperiences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjsr.tapcell.Modals.InterviewExperience;
import com.nitjsr.tapcell.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends Activity {

    private final int REQ_CODE_SPEECH_INPUT = 2;
    private ImageView searchBack, searchMic;
    private EditText enterSearchText;
    private ArrayList<InterviewExperience> currentItemList;
    private boolean mIsKeyBoardOpen = false;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    List<InterviewExperience> interviewExperienceFullList;
    InterviewExperienceAdapter adapter;

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsKeyBoardOpen)
            closeKeyboard();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsKeyBoardOpen)
            closeKeyboard();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        currentItemList = new ArrayList<>();

        searchBack = findViewById(R.id.search_product_back);
        searchMic = findViewById(R.id.search_item_mic);
        enterSearchText = findViewById(R.id.edit_search_text);
        recyclerView = findViewById(R.id.recycler_view);

        interviewExperienceFullList = InterviewExperienceActivity.interviewExperienceList;

        layoutManager = new LinearLayoutManager(SearchActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new InterviewExperienceAdapter(getApplicationContext(), interviewExperienceFullList);
        recyclerView.setAdapter(adapter);

        receiveClicks();
    }

    private void receiveClicks() {
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        enterSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItemList();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        enterSearchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showKeyboard();
                else
                    closeKeyboard();
            }
        });

        enterSearchText.requestFocus();
    }

    private void filterItemList() {
        currentItemList.clear();

        String searchText = enterSearchText.getText().toString().trim();

        if (searchText.equals("")) {
            adapter = new InterviewExperienceAdapter(getApplicationContext(), interviewExperienceFullList);
            recyclerView.setAdapter(adapter);
            return;
        }

        for (int i = 0; i < interviewExperienceFullList.size(); i++)
                if( (interviewExperienceFullList.get(i).getStudName().toLowerCase().contains(searchText.toLowerCase()))
                    || (interviewExperienceFullList.get(i).getCompanyName().toLowerCase().contains(searchText.toLowerCase()))
                    || (interviewExperienceFullList.get(i).getBranch().toLowerCase().contains(searchText.toLowerCase())))
                    currentItemList.add(interviewExperienceFullList.get(i));


        adapter = new InterviewExperienceAdapter(getApplicationContext(), currentItemList);
        recyclerView.setAdapter(adapter);
    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Name, Branch or Company to Search");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    enterSearchText.setText(result.get(0));
                }
                break;
        }
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        mIsKeyBoardOpen = true;
    }

    public void closeKeyboard() {
        if (!mIsKeyBoardOpen) return;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        mIsKeyBoardOpen = false;
    }

}