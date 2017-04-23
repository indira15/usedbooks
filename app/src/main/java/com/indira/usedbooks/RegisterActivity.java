package com.indira.usedbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import com.indira.usedbooks.entity.Response;

import retrofit2.Callback;
import retrofit2.Call;


/**
 * Created by Manish on 18-04-2017.
 */

public class RegisterActivity extends AppCompatActivity implements OnClickListener, Callback<Response>
{
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPhoneno;
    private EditText inputPhoneno2;
    private EditText inputAddress;
    private EditText inputCity;
    private EditText inputState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPhoneno = (EditText) findViewById(R.id.phoneno);
        inputPhoneno2 = (EditText) findViewById(R.id.phoneno2);
        inputAddress = (EditText) findViewById(R.id.address);
        inputCity = (EditText) findViewById(R.id.city);
        inputState =(EditText) findViewById(R.id.state);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);}

        private void validatedata() {
            String name = inputFullName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String phoneno = inputPhoneno.getText().toString().trim();
            String phoneno2 = inputPhoneno2.getText().toString().trim();
            String address = inputAddress.getText().toString().trim();
            String city = inputCity.getText().toString().trim();
            String state = inputState.getText().toString().trim();

            boolean error = false;
            if (TextUtils.isEmpty(name)) {
                error = true;
                inputFullName.setError("Please enter the Full Name");
            }

            if (TextUtils.isEmpty(password)) {
                error = true;
                inputPassword.setError("Pleases enter the password");
            }

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error = true;
                inputEmail.setError("Please enter the valid Email Id");
            }

            if (TextUtils.isEmpty(phoneno) || !Patterns.PHONE.matcher(phoneno).matches()) {
                error = true;
                inputPhoneno.setError("Please enter the Phone number");
            }

            if (TextUtils.isEmpty(phoneno2)) {
                error = true;
                inputPhoneno2.setError("Please enter the Contact number");
            }

            if (TextUtils.isEmpty(address)) {
                error = true;
                inputAddress.setError("Please enter the address");
            }

            if (TextUtils.isEmpty(city)) {
                error = true;
                inputCity.setError("Please enter the City");
            }

            if (TextUtils.isEmpty(state)) {
                error = true;
                inputState.setError("Please enter the state");
            }

            if (error) {
                Utils.showToast(this, "Please fill the required details");
                btnRegister.setEnabled(true);
                return;
            }


            GetUserInterface service = UsedbooksApplication.getInstance().getRetrofit().
                    create(GetUserInterface.class);



            Call<Response> cn = service.addUser(name,email,password,phoneno,phoneno2,address,city,state);
            cn.enqueue(this);
            btnRegister.setText("Submitting.....");

}
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                validatedata();
                break;
            case R.id.btnLogin:
                startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (Utils.isActivityAlive(this)) {
            btnRegister.setText("Success");
            btnRegister.setEnabled(true);
            if (response.isSuccessful()) {
                Response responseBody = response.body();
                if (responseBody.getSuccess() == 1) {
                    btnRegister.setText("Success");
                    PreferenceUtils.set(getApplicationContext(), PreferenceUtils.SAVED_USER_NAME,
                        responseBody.getUser().getName());
                    PreferenceUtils.set(getApplicationContext(), PreferenceUtils.SAVED_USER_ID,
                        responseBody.getUser().getId());
                    Utils.showToast(RegisterActivity.this, "Success!" + responseBody.getMessage());
                    Intent listIntent = new Intent(this, BooksListActivity.class);
                    listIntent.setAction(BooksListActivity.RESTART_ACTION);
                    startActivity(listIntent);
                    finish();
                } else {
                    btnRegister.setText("Submit");
                    Utils.showToast(RegisterActivity.this, "Failed! " + responseBody.getMessage());
                }
            } else {
                btnRegister.setText("Submit");
                Utils.showToast(RegisterActivity.this, "Something went wrong");
            }
        }
    }
    @Override
    public void onFailure(Call<Response> call, Throwable t) {
              if (Utils.isActivityAlive(this)) {
                  Utils.showToast(this, "Something went wrong while posting data");
                  btnRegister.setEnabled(true);
                  btnRegister.setText("Submit");
          }

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sign_in, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_in:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
