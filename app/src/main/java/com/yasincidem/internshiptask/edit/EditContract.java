package com.yasincidem.internshiptask.edit;

import android.content.Context;

import com.yasincidem.internshiptask.BasePresenter;
import com.yasincidem.internshiptask.BaseView;

public interface EditContract {
    interface View extends BaseView<EditContract.Presenter> {
        void showToast(String message);
        void startProfileActivity();
        Context getAppContext();
        void invokeImageUrl(String url);
        void invokeName(String name);
        void invokeEmail(String email);
        void invokeProfession(String profession);
        void invokeBirthdate(String birthdate);
        void invokePhone(String phone);
        void setDataFromProvider();
    }

    interface Presenter extends BasePresenter {
        void addDataToFirebase(Context context, String... string);
        void getDataFromFirebase(Context context);
    }
}
