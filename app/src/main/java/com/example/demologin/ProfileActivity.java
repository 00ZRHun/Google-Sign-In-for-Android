package com.example.demologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profile_image;
    private TextView name;
    private TextView email;
    private TextView id;
    private Button sign_out_btn;

    private GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        profile_image = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        sign_out_btn = findViewById(R.id.sign_out_btn);

        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_out_btn:
                        Log.d("TAG", "signOut00: ");
                        signOut();
                        break;
                    // ...
                }
//                signOut();

            }
        });
    }

    // [START main_menu]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);  // pass menu as input
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                Log.d("TAG", "signOut00: ");
                signOut();
                return true;
            }
        }
//        return true;
        return super.onOptionsItemSelected(item);
    }

    // [END main_menu]

    @Override
    protected void onStart() {
        super.onStart();

        handleSignInResult();
    }



    private void signOut() {
        Log.d("TAG", "signOut01: ");
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("TAG", "signOut: ");
                        // ...
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    private void handleSignInResult() {
        //        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);  // *** -> ProfileActivity.this
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Picasso.get().load(personPhoto).placeholder(R.mipmap.ic_launcher).into(profile_image);  // profile_image
        }
    }
}