package com.example.present_funding;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends Activity {

    Button goJoin, goMain;
    //private Object FirebaseAuth;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goJoin = findViewById(R.id.btn_join);
        goJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), JoinActivity.class));

            }
        });

        goMain = findViewById(R.id.btn_login);
        goMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), MainActivity.class));

            }
        });

        /*
        // [START declare_auth]
        private lateinit var auth: FirebaseAuth;
        // [END declare_auth]
        private lateinit var googleSignInClient: GoogleSignInClient;
        private lateinit var authResultLauncher: ActivityResultLauncher<Intent>;

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        authResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()); {
            result -> val data: Intent? = result.data;
            val task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java);

                Log.d("signin", "success");
                firebaseAuthWithGoogle(account);
            } catch (e:ApiException) {
                Log.d("signin", "failure");
                Log.e("task", "error", e);

            }

        }
        */

    }

    /*
    @Override
    fun onStart() {
        super.onStart();

        auth = FirebaseAuth.getInstance();
        val currentUser = auth.currentUser;
        updateUI(currentUser);
    }

    private fun firebaseAuthWithGoogle(account:GoogleSignInAccount?){

        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null);

        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                Log.d("signin", "success2");

                accessToken = account.idToken.toString();
                Log.d("TOKEN",accessToken);

                val user = FirebaseAuth.getInstance().currentUser;
                updateUI(user);
                AuthService.googleLogin(this,accessToken);

            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show();
            }
        }
    }
    *?
     */
}
