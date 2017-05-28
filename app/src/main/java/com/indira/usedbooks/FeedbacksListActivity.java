package com.indira.usedbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.indira.usedbooks.entity.Feedback;
import com.indira.usedbooks.entity.Feedbacks;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manish on 08-05-2017.
 */

public class FeedbacksListActivity extends AppCompatActivity implements Callback<Feedbacks>{

    private ArrayList<Feedback> feedbackList = new ArrayList();
    private RecyclerView recyclerView;
    private FeedbacksAdapter feedbackAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    public static String RESTART_ACTION = "restart_action";
    //private String mQuery;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacklist);
        recyclerView= (RecyclerView) findViewById(R.id.feedbacklistview);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        feedbackAdapter= new FeedbacksAdapter(feedbackList,this);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(feedbackAdapter);
        progressBar.setVisibility(View.VISIBLE);
        prepareFeedbackData();
        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                if (PreferenceUtils.isLoggedIn(FeedbacksListActivity.this)) {
                    Intent intent = new Intent(FeedbacksListActivity.this, BookPostActivity.class);
                    startActivity(intent);
                } else {
                    Utils.showToast(FeedbacksListActivity.this, "Please register to add a feedback");
                }
            }



        });}

    private void prepareFeedbackData()
    {   GetFeedbacksInterface feedbackInterface = UsedbooksApplication.getInstance().getRetrofit().
            create(GetFeedbacksInterface.class);
        Call<Feedbacks> callFeedbacks = feedbackInterface.getFeedbacks();
        callFeedbacks.enqueue(FeedbacksListActivity.this);
    }

    @Override
    public void onResponse(Call<Feedbacks> call, Response<Feedbacks> response) {
        if (Utils.isActivityAlive(FeedbacksListActivity.this)) {
            if (response.isSuccessful()) {
                Feedbacks feedbackPresent = response.body();
                feedbackList.clear();
                feedbackList.addAll(feedbackPresent.feedbacks);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                feedbackAdapter.notifyDataSetChanged();
            } else {
                Utils.showToast(FeedbacksListActivity.this, "Something went wrong");
            }
        }
    }

    @Override
    public void onFailure(Call<Feedbacks> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        Utils.showToast(FeedbacksListActivity.this, "Something went wrong");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null)
        {
            if(RESTART_ACTION.equals(intent.getAction())) {
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                prepareFeedbackData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_books_list, menu);
        if (PreferenceUtils.isLoggedIn(this)) {
            MenuItem registerItem  = menu.findItem(R.id.register);
            registerItem.setTitle(PreferenceUtils.getStringPrefs(this, PreferenceUtils
                    .SAVED_USER_NAME));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                if (!PreferenceUtils.isLoggedIn(this)) {
                    startActivity(new Intent(this, RegisterActivity.class));
                } else {
                    Utils.showToast(this, "Hello "+ PreferenceUtils.getStringPrefs(this,
                            PreferenceUtils.SAVED_USER_NAME));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (mDisposable != null){
            mDisposable.dispose();
        }*/
    }

}






