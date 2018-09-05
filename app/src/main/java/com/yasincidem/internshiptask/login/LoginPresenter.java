package com.yasincidem.internshiptask.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View  mLoginView;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN_G = 2016;
    private GoogleSignInAccount account;

    @Nullable
    private String mLoginId;

    public LoginPresenter(@NonNull LoginFragment loginView, @Nullable String loginId) {
        this.mLoginView = checkNotNull(loginView, "tLoginView cannot be null!");
        this.mLoginId = loginId;

        mLoginView.setPresenter(this);

        this.createClientGoogle(loginView); // should be added here not to get NPE
    }

    //Google Sign in
    @Override
    public void createClientGoogle(LoginFragment loginView) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(loginView.getActivity()), gso);
    }

    @Override
    public void onStartGoogle(LoginFragment loginView) {
        account = GoogleSignIn.getLastSignedInAccount(Objects.requireNonNull(loginView.getContext()));
    }

    @Override
    public void signInGoogle(LoginFragment loginView) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        loginView.startActivityForResult(signInIntent, RC_SIGN_IN_G);
    }

    @Override
    public void onActivityResultGoogle(LoginFragment loginView, int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_G) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task, loginView);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask, LoginFragment loginFragment) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            mLoginView.startEditActivity(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LoginPresenter.java", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public boolean isSignedInWithGoogle(Context context) {
        return GoogleSignIn.getLastSignedInAccount(context) != null;

    }

    @Override
    public void onStopGoogle() {
        mGoogleSignInClient = null;
    }

    @Override
    public void onDestroyGoogle() {
        mLoginView = null;
    }

    //Facebook Log in

    @Override
    public void start() {

    }
}
