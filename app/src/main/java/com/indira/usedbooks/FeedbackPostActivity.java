
package com.indira.usedbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.indira.usedbooks.entity.Response;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Manish on 09-05-2017.
 */


public class FeedbackPostActivity extends AppCompatActivity implements View.OnClickListener, Callback<Response> {

    private EditText comment;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackpost);
        comment = (EditText) findViewById(R.id.post_message);
        submit.setOnClickListener(this);
    }

    private void validateData() {
        String data = comment.getText().toString().trim();
        boolean error = false;
        if (TextUtils.isEmpty(data)) {
            error = true;
            comment.setError("Please enter the bookname");
        }
        if (error) {
            Utils.showToast(this, "Please fill the required details");
            submit.setEnabled(true);
            return;
        }


        GetFeedbacksInterface service = UsedbooksApplication.getInstance().getRetrofit().
                create(GetFeedbacksInterface.class);

        RequestBody messageBody = RetrofitUtils.createPartFromString(data);
        RequestBody userIdBody = RetrofitUtils.createPartFromString(String.valueOf(PreferenceUtils.
                getIntegerPrefs(this, PreferenceUtils.SAVED_USER_ID)));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("comment", messageBody);
        map.put("user_id", userIdBody);

        Call<Response> call = service.addFeedback(map);
        call.enqueue(this);
        submit.setText("Submitting.....");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                view.setEnabled(false);
                validateData();
                break;
        }
    }


    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (Utils.isActivityAlive(this)) {
            submit.setText("Success");
            submit.setEnabled(true);
            if (response.isSuccessful()) {
                Response responseBody = response.body();
                if (responseBody.getSuccess() == 1) {
                    submit.setText("Success");
                    Utils.showToast(FeedbackPostActivity.this, "Success!" + responseBody.getMessage());
                    Intent listIntent = new Intent(this, FeedbacksListActivity.class);
                    listIntent.setAction(BooksListActivity.RESTART_ACTION);
                    startActivity(listIntent);
                    finish();
                } else {
                    submit.setText("Submit");
                    Utils.showToast(FeedbackPostActivity.this, "Failed! " + responseBody.getMessage());
                }
            } else {
                submit.setText("Submit");
                Utils.showToast(FeedbackPostActivity.this, "Something went wrong");
            }
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        if (Utils.isActivityAlive(this)) {
            Utils.showToast(this, "Something went wrong while posting data");
            submit.setEnabled(true);
            submit.setText("Submit");
        }
    }
}




