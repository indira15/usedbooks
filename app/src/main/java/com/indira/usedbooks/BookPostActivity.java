package com.indira.usedbooks;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.indira.usedbooks.entity.Response;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Manish on 09-04-2017.
 */

public class BookPostActivity extends AppCompatActivity implements OnClickListener,
        Callback<Response> {

    private EditText bookname;
 private EditText authorname;
    private EditText mEdition;
    private EditText mCost;
    private Button submit;
    private Button capturepicture;
    private ImageView preview;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookspost);
        bookname = (EditText) findViewById(R.id.book_name);
        authorname = (EditText) findViewById(R.id.author_name);
        mEdition = (EditText) findViewById(R.id.edition);
        mCost = (EditText) findViewById(R.id.cost);
        submit = (Button) findViewById(R.id.submit);
        capturepicture = (Button) findViewById(R.id.btnCapturePicture);
        preview = (ImageView) findViewById(R.id.imgPreview);
        submit.setOnClickListener(this);
        capturepicture.setOnClickListener(this);
    }

    private void validateData() {
        String name = bookname .getText().toString().trim();
        String author= authorname.getText().toString().trim();
        String edition = mEdition.getText().toString().trim();
        String cost = mCost.getText().toString().trim();
        boolean error = false;
        if(TextUtils.isEmpty(name))
        {
            error = true;
            bookname.setError("Please enter the book name");
        }

        if (TextUtils.isEmpty(author))
        {
            error = true;
            authorname.setError("Please enter the author name");
        }

        if (TextUtils.isEmpty(edition))
        {
            error = true;
            authorname.setError("Please enter the mEdition");
        }

        if (TextUtils.isEmpty(cost))
        {
            error = true;
            authorname.setError("Please enter the mCost");
        }
        File imageFile = null;
        if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
            imageFile = new File(mCurrentPhotoPath);
            if (imageFile == null || !imageFile.exists() || !(imageFile.length() > 0)) {
                error = true;
                Utils.showToast(this, "Please capture the image");
            }
        } else {
            error = true;
            Utils.showToast(this, "Please capture the image");
        }


        if (error) {
            Utils.showToast(this, "Please fill the required details");
            submit.setEnabled(true);
            return;
        }


        GetBooksInterface service = UsedbooksApplication.getInstance().getRetrofit().
                create(GetBooksInterface.class);


       // create part for file (photo)
        MultipartBody.Part body = RetrofitUtils.prepareFilePart("image", Uri.fromFile(imageFile),
                this);

       // create a map of data to pass along
        RequestBody nameBody = RetrofitUtils.createPartFromString(name);
        RequestBody authorBody = RetrofitUtils.createPartFromString(author);
        RequestBody costBody = RetrofitUtils.createPartFromString(cost);
        RequestBody editionBody = RetrofitUtils.createPartFromString(edition);
        RequestBody userIdBody = RetrofitUtils.createPartFromString(String.valueOf(PreferenceUtils.
            getIntegerPrefs(this, PreferenceUtils.SAVED_USER_ID)));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", nameBody);
        map.put("cost", costBody);
        map.put("authorname", authorBody);
        map.put("edition", editionBody);
        map.put("user_id", userIdBody);

        Call<Response> call = service.addBook(map, body);
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

            case R.id.btnCapturePicture:
                if(hasPermissions(this,  new String[]{Manifest.permission.CAMERA,
                    permission.READ_EXTERNAL_STORAGE,
                    permission.WRITE_EXTERNAL_STORAGE})) {
                    dispatchTakePictureIntent();
                } else {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                        checkMultiplePermissions(REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS,this);
                    }
                }
                break;
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    boolean showRationale1 = shouldShowRequestPermissionRationale(
                        permission.CAMERA);
                    boolean showRationale2 = shouldShowRequestPermissionRationale(
                        permission.READ_EXTERNAL_STORAGE
                    );
                    boolean showRationale3 = shouldShowRequestPermissionRationale(
                        permission.WRITE_EXTERNAL_STORAGE
                    );
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //check for camera and storage access permissions
    @TargetApi(Build.VERSION_CODES.M)
    private void checkMultiplePermissions(int permissionCode, Context context) {
        String[] PERMISSIONS = {Manifest.permission.CAMERA,
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, permissionCode);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("Fail", "Failed");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.indira.usedbooks.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                    File imageFile = new File(mCurrentPhotoPath);
                    if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
                        Picasso.with(this).load(imageFile).into(preview);
                        preview.setVisibility(View.VISIBLE);
                        capturepicture.setText("Retake");
                    } else {
                        Utils.showToast(this, "Try again");
                    }
                } else {
                    Utils.showToast(this, "Failed");
                }
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled
                Utils.showToast(this, "Try again");
            } else {
                // failed to click
                Utils.showToast(this, "Sorry! Failed to click");
            }
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
                    Utils.showToast(BookPostActivity.this, "Success!" + responseBody.getMessage());
                    Intent listIntent = new Intent(this, BooksListActivity.class);
                    listIntent.setAction(BooksListActivity.RESTART_ACTION);
                    startActivity(listIntent);
                    finish();
                } else {
                    submit.setText("Submit");
                    Utils.showToast(BookPostActivity.this, "Failed! " + responseBody.getMessage());
                }
            } else {
                submit.setText("Submit");
                Utils.showToast(BookPostActivity.this, "Something went wrong");
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
