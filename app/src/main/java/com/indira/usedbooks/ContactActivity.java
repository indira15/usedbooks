package com.indira.usedbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.Response;
import com.indira.usedbooks.entity.User;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Manish on 23-04-2017.
 */

public class ContactActivity extends AppCompatActivity implements Callback<Response> {

    TextView usernameView;

    TextView userContact;
    TextView userAddress;
    TextView userCity;
    TextView userState;

    TextView edition;
    TextView authorName;

    TextView bookCost;

    TextView bookName;
    ImageView bookImage;
    Button notifyOwner;
    View requesterView;
    TextView requestText;
    TextView requestContact;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetails);
        usernameView= (TextView) findViewById(R.id.userName);
        bookCost= (TextView) findViewById(R.id.cost);
        bookName= (TextView) findViewById(R.id.book_name);
        edition = (TextView) findViewById(R.id.edition);
        userContact = (TextView) findViewById(R.id.userContact);
        userAddress= (TextView) findViewById(R.id.address);
        userState = (TextView) findViewById(R.id.state);
        userCity = (TextView) findViewById(R.id.city);
        authorName = (TextView)findViewById(R.id.author_name);
        bookImage = (ImageView) findViewById(R.id.book_image);
        notifyOwner = (Button) findViewById(R.id.notifyOwner);
        requesterView = findViewById(R.id.requesterDetails);
        requestText = (TextView) findViewById(R.id.requestText);
        requestContact = (TextView) findViewById(R.id.contactText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final Book book = (Book) extras.getSerializable("book");
            User user = book.getUser();
            usernameView.setText(user.getName());
            bookName.setText(book.getName());
            authorName.setText(book.getAuthorName());
            bookCost.setText(String.valueOf(book.getCost()));
            edition.setText(book.getEdition());
            userState.setText(user.getState());
            userAddress.setText(user.getAddress());
            userCity.setText(user.getCity());
            userContact.setText(user.getPhoneno());
            Picasso.with(this).load(book.getImageUrl())
                .placeholder(R.drawable.ic_launcher)
                .resize(100, 100)
                .into(bookImage);
            notifyOwner.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View view) {
                    notifyOwner.setEnabled(false);
                    notifyOwner.setText("Notifying...");
                    GetUserInterface service = UsedbooksApplication.getInstance().getRetrofit().
                        create(GetUserInterface.class);
                    Call<Response> cn = service.notifyOwner(book.getId(),
                        PreferenceUtils.getIntegerPrefs(ContactActivity.this, PreferenceUtils.SAVED_USER_ID));
                    cn.enqueue(ContactActivity.this);
                }
            });
            if (extras.containsKey("requester")) {
                User requester = (User) extras.getSerializable("requester");
                requesterView.setVisibility(View.VISIBLE);
                requestText.setText("User - " + requester.getName() + " is interested in buying "
                    + "the book");
                requestContact.setText("Contact the user on " + requester.getPhoneno());
                notifyOwner.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void onResponse(final Call<Response> call, final retrofit2.Response<Response> response) {
        if (Utils.isActivityAlive(this)) {
            notifyOwner.setEnabled(true);
            if (response.isSuccessful()) {
                Response responseBody = response.body();
                if (responseBody.getSuccess() == 1) {
                    notifyOwner.setText("Notified");
                } else {
                    notifyOwner.setText("Notify Owner");
                    Utils.showToast(this, "Failed! " + responseBody.getMessage());
                }
            } else {
                notifyOwner.setText("Notify Owner");
                Utils.showToast(this, "Something went wrong");
            }
        }
    }

    @Override
    public void onFailure(final Call<Response> call, final Throwable t) {
        if (Utils.isActivityAlive(this)) {
            Utils.showToast(this, "Something went wrong while posting data");
            notifyOwner.setEnabled(true);
            notifyOwner.setText("Notify Owner");
        }
    }
}
