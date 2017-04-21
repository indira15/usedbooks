package com.indira.usedbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.Books;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manish on 08-04-2017.
 */

public class BooksListActivity extends AppCompatActivity implements Callback<Books>  {
    private ArrayList<Book> bookList = new ArrayList();
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private ProgressBar mProgressBar;
    private FloatingActionButton fab;
    public static String RESTART_ACTION = "restart_action";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);
        recyclerView = (RecyclerView) findViewById(R.id.booklistview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        booksAdapter = new BooksAdapter(bookList ,this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(booksAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        prepareBookData();
        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                Intent intent = new Intent(BooksListActivity.this,BookPostActivity.class);
                startActivity(intent);
            }

        });
    }

    private void prepareBookData()
    {   GetBooksInterface booksInterface = UsedbooksApplication.getInstance().getRetrofit().
            create(GetBooksInterface.class);
        Call<Books> callBooks = booksInterface.getBooks();
        callBooks.enqueue(BooksListActivity.this);
    }

    @Override
    public void onResponse(Call<Books> call, Response<Books> response) {
        if (Utils.isActivityAlive(BooksListActivity.this)) {
            if (response.isSuccessful()) {
                Books booksPresent = response.body();
                bookList.clear();
                bookList.addAll(booksPresent.books);
                mProgressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                booksAdapter.notifyDataSetChanged();
            } else {
                Utils.showToast(BooksListActivity.this, "Something went wrong");
            }
        }
    }

    @Override
    public void onFailure(Call<Books> call, Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        Utils.showToast(BooksListActivity.this, "Something went wrong");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if(RESTART_ACTION.equals(intent.getAction())) {
                recyclerView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                prepareBookData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_books_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
