package com.yasincidem.internshiptask.profile;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yasincidem.internshiptask.R;
import com.yasincidem.internshiptask.util.ActivityUtils;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.profile_fragment);

        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    profileFragment, R.id.profile_fragment);
        }

        new ProfilePresenter(
                profileFragment,
                "profile"
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // can be filled later
    }
}
