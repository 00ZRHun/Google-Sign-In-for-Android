package com.example.demologin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private static final int RC_SIGN_IN = 1;  // OPTION: 120
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

//        findViewById(R.id.sign_in_button).setOnClickListener(this);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override  // ***
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });



    }

    // [START main_menu]
//    NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
//        logout();
//        return true;
//    });
//    @SuppressWarnings()
    // [END main_menu]


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_button:
//                signIn();
//                break;
//            // ...
//        }
//    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        Log.d("onStart: ", account.toString());
//
////        if (account != null) {
////        }
//            updateUI(account);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());

            Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }


    private void updateUI(GoogleSignInAccount account) {
        Log.d("TAG", "updateUI... ");

//        Intent startProfileActivity = new Intent(MainActivity.this, ProfileActivity.class);
//
//        startActivity();
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        finish();  // don't want come back to the main activity, just close the app
    }
}