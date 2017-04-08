package com.indira.usedbooks;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 08-04-2017.
 */

public class BooksListActivity extends AppCompatActivity {
    private ArrayList<Book> bookList = new ArrayList();
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_bookslist);
        recyclerView = (RecyclerView) findViewById(R.id.booklistview);
        booksAdapter = new BooksAdapter(bookList ,this);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(booksAdapter);
        prepareBookData();
    }

    private void prepareBookData()
    {
        Book book = new Book();
        book.setName(" Andorid development");
        book.setCost(20);
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image("https://books.google.co.in/books/content?id=Up8QAwAAQBAJ&printsec=frontcover&img=1&zoom=1"));
        book.setImages(images);
        bookList.add(book);


        Book book1 = new Book();
        book1.setName(" iPphone  develop");
        book1.setCost(200);
        ArrayList<Image> images1 = new ArrayList<>();
        images1.add(new Image("http://www.hostiee.com/wp-content/uploads/500-Free-InstantFast-Approval-Directory-List-2015.png"));
        book.setImages(images1);
        bookList.add(book1);

        Book book2 = new Book();
        book2.setName(" Gaskdjlks11 development");
        book2.setCost(230);
        ArrayList<Image> images2 = new ArrayList<>();
        images2.add(new Image("http://t0.gstatic.com/images?q=tbn:ANd9GcTdzwGuLYHcE2f8ZP4M9zLvjzKHX4jetgbhJGZMj5ka_iCXGruT"));
        book2.setImages(images2);
        bookList.add(book2);

        Book book3 = new Book();
        book3.setName(" Googl development");
        book3.setCost(210);
        ArrayList<Image> images3 = new ArrayList<>();
        images3.add(new Image("https://pbs.twimg.com/profile_images/655066410087940096/QSUlrrlm.png"));
        book3.setImages(images3);
        bookList.add(book3);

        booksAdapter.notifyDataSetChanged();
    }

}
