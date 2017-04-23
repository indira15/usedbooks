package com.indira.usedbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.User;
import com.squareup.picasso.Picasso;

/**
 * Created by Manish on 23-04-2017.
 */

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Book book = (Book) extras.getSerializable("book");
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
        }

    }




    @Override
    public void onClick(View view) {

    }
}
