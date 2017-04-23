package com.indira.usedbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.User;

/**
 * Created by Manish on 23-04-2017.
 */

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    TextView booknameView;
    TextView authornameView;
    TextView costView;
    TextView editionView;
    TextView usernameView;
    TextView addressView;
    TextView cityView;
    TextView stateView;
    TextView phonenoView;
    TextView phoneno2View;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetails);
        booknameView= (TextView) findViewById(R.id.book_name);
        authornameView= (TextView) findViewById(R.id.authorname);
        costView= (TextView) findViewById(R.id.cost);
        editionView= (TextView) findViewById(R.id.edition);
        usernameView= (TextView) findViewById(R.id.userName);
        addressView= (TextView) findViewById(R.id.address);
        cityView= (TextView) findViewById(R.id.city);
        stateView= (TextView) findViewById(R.id.state);
        phonenoView= (TextView) findViewById(R.id.phoneno);
        phoneno2View= (TextView) findViewById(R.id.phoneno2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Book book = (Book) extras.getSerializable("book");
            booknameView.setText(book.getName());
            authornameView.setText(book.getAuthorName());
            costView.setText(String.valueOf(book.getCost()));
            editionView.setText(book.getEdition());

            User user = (User) extras.getSerializable("user");
            usernameView.setText(user.getName());
            addressView.setText(user.getAddress());
            cityView.setText(user.getCity());
            stateView.setText(user.getState());
            phonenoView.setText(user.getPhoneno());
            phoneno2View.setText(user.getPhoneno2());




            //The key argument here must match that used in the other activity
        }

    }




    @Override
    public void onClick(View view) {

    }
}
