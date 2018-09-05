package com.yasincidem.internshiptask.profile;

import android.content.Context;

import com.yasincidem.internshiptask.BasePresenter;
import com.yasincidem.internshiptask.BaseView;

public interface ProfileContract {
    interface View extends BaseView<ProfileContract.Presenter> {
        void fillImageView(String url);
        void fillNameField(String name);
        void fillEmailField(String email);
        void fillProfessionField(String profession);
        void fillBirthdateField(String birthdate);
        void fillPhoneField(String phone);
        void startEditActivity();
        void startLoginActivity();
        void showToast(String message);
    }

    interface Presenter extends BasePresenter {
        void getDataFromFirebase(Context context);
        void logout(Context Context);
    }
}
