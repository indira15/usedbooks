package com.indira.usedbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class BooksListActivity extends AppCompatActivity implements Callback<Books> {
    private ArrayList<Book> bookList = new ArrayList();
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);
        recyclerView = (RecyclerView) findViewById(R.id.booklistview);
        booksAdapter = new BooksAdapter(bookList ,this);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration( new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(booksAdapter);
        prepareBookData();
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
                bookList = booksPresent.books;
                booksAdapter.notifyDataSetChanged();
            } else {
                Utils.showToast(BooksListActivity.this, "Something went wrong");
            }
        }
    }

    @Override
    public void onFailure(Call<Books> call, Throwable t) {
        Utils.showToast(BooksListActivity.this, "Something went wrong");
    }
}
