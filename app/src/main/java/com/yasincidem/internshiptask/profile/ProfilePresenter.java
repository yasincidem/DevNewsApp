package com.yasincidem.internshiptask.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View mProfileView;

    @Nullable
    private String mProfileId;

    public ProfilePresenter(@NonNull ProfileContract.View profileView, @Nullable String profileId) {
        this.mProfileView = checkNotNull(profileView, "mProfileView cannot be null!");
        this.mProfileId = profileId;

        mProfileView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getDataFromFirebase(Context context) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String userId = account.getId();
            DatabaseReference myRef = database.getReference().child("users").child(userId);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    mProfileView.fillImageView(dataSnapshot.child("img_url").getValue(String.class));
                    mProfileView.fillNameField(dataSnapshot.child("name").getValue(String.class));
                    mProfileView.fillEmailField(dataSnapshot.child("email").getValue(String.class));
                    mProfileView.fillProfessionField(dataSnapshot.child("profession").getValue(String.class));
                    mProfileView.fillBirthdateField(dataSnapshot.child("birthdate").getValue(String.class));
                    mProfileView.fillPhoneField(dataSnapshot.child("phone").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("ProfilePresenter.java", "Failed to read value.", error.toException());
                }
            });

    }

    @Override
    public void logout(Context context) {
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN);
        googleSignInClient.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       mProfileView.showToast("You logged out successfully");
                       mProfileView.startLoginActivity();
                    }
                });

    }
}
