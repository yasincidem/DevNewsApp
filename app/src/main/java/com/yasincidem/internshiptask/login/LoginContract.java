package com.yasincidem.internshiptask.login;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.yasincidem.internshiptask.BasePresenter;
import com.yasincidem.internshiptask.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void hideLoginOptions();
        void startEditActivity(GoogleSignInAccount account);
        void showToast(String msg);
        Context getAppContext();
    }


    interface Presenter extends BasePresenter {
        void createClientGoogle (LoginFragment loginView);
        void onStartGoogle(LoginFragment loginView);
        void signInGoogle(LoginFragment loginView);
        void onActivityResultGoogle (LoginFragment loginView,int requestCode, int resultCode, Intent data);
        void onStopGoogle ();
        void onDestroyGoogle();
        boolean isSignedInWithGoogle(Context context);

    }
}
