package com.indira.usedbooks;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Manish on 09-04-2017.
 */

public class BookPostActivity extends AppCompatActivity implements OnClickListener{

    private EditText bookname;
 private EditText authorname;
    private EditText edition;
    private EditText cost;
    private Button submit;
    private Button capturepicture;
    private ImageView preview;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private SQLiteHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookspost);

        bookname = (EditText) findViewById(R.id.book_name);
        authorname = (EditText) findViewById(R.id.author_name);
        edition = (EditText) findViewById(R.id.edition);
        cost = (EditText) findViewById(R.id.cost);
        submit = (Button) findViewById(R.id.submit);
        capturepicture = (Button) findViewById(R.id.btnCapturePicture);
        preview = (ImageView) findViewById(R.id.imgPreview);
        submit.setOnClickListener(this);
        capturepicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = bookname .getText().toString().trim();
        String author= authorname.getText().toString().trim();
        String edition = edition.getText().toString().trim();
        String cost = cost.getText().toString().trim();
        if(name.isEmpty() && author.isEmpty()&& edition.isEmpty() && cost.isEmpty())
        {
            registerBook(name,author,edition,cost);
        }

        else
        {
            Utils.showToast(this,"please enter your details");
        }


        switch (view.getId()) {
            case R.id.submit:
                // perform save book operation

                break;

            case R.id.btnCapturePicture:
                dispatchTakePictureIntent();

                break;
        }
    }
public void registerBook( final String name,final String author,final String edition,final String cost)
{
    String tag_string_req = "req_addbook";




    StringRequest strReq = new StringRequest(Method.POST,
            AppConfig.URL_, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(TAG, "Register Response: " + response.toString());


            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");
                if (!error) {
                    // User successfully stored in MySQL
                    // Now store the user in sqlite
                    String uid = jObj.getString("uid");

                    JSONObject books = jObj.getJSONObject("books");
                    String name = books.getString("name");
                    String author = books.getString("author");
                    String edition= books.getString("edition");
                    String cost = books.getString("cost");

                    String created_at = books
                            .getString("created_at");

                    // Inserting row in users table
                    db.addbooks(name, email, uid, created_at);


                } else {

                    // Error occurred in registration. Get the error
                    // message
                    String errorMsg = jObj.getString("error_msg");
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Registration Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();

        }
    }) {

        @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name);
            params.put("email", email);
            params.put("password", password);
            params.put("phoneno", phone);
            params.put("phoneno2",phone2);
            params.put("address",address);
            params.put("city",city);
            params.put("state",state);
            return params;
        }

    };

    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
}
