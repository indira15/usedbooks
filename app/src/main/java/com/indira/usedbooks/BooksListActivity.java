package com.indira.usedbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.indira.usedbooks.entity.Book;

import java.util.ArrayList;

/**
 * Created by Manish on 08-04-2017.
 */

public class BooksListActivity extends AppCompatActivity {
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
        recyclerView.setAdapter(booksAdapter);
        prepareBookData();
    }

    private void prepareBookData()
    {
        booksAdapter.notifyDataSetChanged();
    }

}
