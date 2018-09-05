package com.yasincidem.internshiptask.login;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.yasincidem.internshiptask.R;
import com.yasincidem.internshiptask.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.login_fragment);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.login_fragment);
        }

        new LoginPresenter(
                loginFragment,
                "yasincidem"
                );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // can be filled later
    }
}
