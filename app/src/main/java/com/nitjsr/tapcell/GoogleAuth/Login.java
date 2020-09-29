package com.nitjsr.tapcell.GoogleAuth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nitjsr.tapcell.Activity.MainActivity;
import com.nitjsr.tapcell.R;
import com.nitjsr.tapcell.Utils.SharedPrefManager;

import androidx.annotation.NonNull;

public class Login extends Activity {

    public static final int RequestSignInCode = 7;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button btnLoginGoogle;
    private ProgressDialog pd;
    private static final String TAG = "LoginActivity";
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPrefs = getSharedPreferences("First", MODE_PRIVATE);

        SharedPreferences.Editor edit = mSharedPrefs.edit();
        edit.putBoolean("FirstTime", false);
        edit.commit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();

        btnLoginGoogle = findViewById(R.id.btn_login_google);

        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setMessage("Logging in...");
        pd.setCancelable(false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            SharedPrefManager sm = new SharedPrefManager(Login.this);
                            sm.setIsLoggedIn(true);

                            pd.dismiss();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
//                            Log.d(TAG, "Google Authentication failed. Reason: " + task.getException());
                        }
                    }
                });

    }

    private void googleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RequestSignInCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode) {
            pd.show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, "Google Sign in failed!", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Google Sign in failed. Reason: " + e.getMessage());
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        }
    }

}
