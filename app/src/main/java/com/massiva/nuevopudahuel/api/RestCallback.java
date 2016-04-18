package com.massiva.nuevopudahuel.api;

import com.massiva.nuevopudahuel.base.BaseActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 02/02/2016.
 */
public abstract class RestCallback<T> implements Callback<T> {

    private static final String TAG = RestCallback.class.getSimpleName();

    String errorMessage;

    public RestCallback() {}

    @Override
    public void failure(RetrofitError error) {
//        try {
//            errorMessage = ((ErrorResponse) error.getBodyAs(ErrorResponse.class)).getCode();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void success(T t, Response response) {

    }

    public void showErrorMessage(BaseActivity context) {
        context.dismissProgressDialog();
//        context.showMessage(getErrorMessage(context));
    }

//    public String getErrorMessage(BaseActivity context) {
//        if (errorMessage == null)
//            errorMessage = context.getString(R.string.apiManagerErrorGenericKey);
//        else {
//            try {
//                errorMessage = context.getString(context.getResources().getIdentifier("apiManagerError" + errorMessage + "Key", "string", context.getPackageName()));
//            } catch (Exception e) {
//                errorMessage = context.getString(R.string.apiManagerErrorGenericKey);
//            }
//        }
//        return errorMessage;
//    }
}
