package com.indira.usedbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Manish on 23-04-2017.
 */

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    TextView usernameView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetails);
        usernameView= (TextView) findViewById(R.id.userName);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString("string");
            usernameView.setText(s);

            //The key argument here must match that used in the other activity
        }

    }




    @Override
    public void onClick(View view) {

    }
}
