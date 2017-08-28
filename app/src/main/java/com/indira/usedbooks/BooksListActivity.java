package com.indira.usedbooks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;
import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.Books;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manish on 08-04-2017.
 */

public class BooksListActivity extends AppCompatActivity  implements Callback<Books>   {
    private ArrayList<Book> bookList = new ArrayList();
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private ProgressBar mProgressBar;
    private FloatingActionButton fab;
    public static String RESTART_ACTION = "restart_action";
    private SearchView mSearchView;
    private Disposable mDisposable;
    private ArrayList<Book> originalList = new ArrayList<>();
    private String mQuery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);
        recyclerView = (RecyclerView) findViewById(R.id.booklistview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        booksAdapter = new BooksAdapter(bookList ,this);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("Search by Book Name");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(booksAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        prepareBookData();

        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                if (PreferenceUtils.isLoggedIn(BooksListActivity.this)) {
                    Intent intent = new Intent(BooksListActivity.this, BookPostActivity.class);
                    startActivity(intent);
                } else {
                    Utils.showToast(BooksListActivity.this, "Please register to add a book");
                }
            }

        });
        mDisposable = RxSearchView.queryTextChangeEvents(mSearchView)
                .debounce(400, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(_getSearchObserver());
    }

    private DisposableObserver<SearchViewQueryTextEvent> _getSearchObserver() {
        return new DisposableObserver<SearchViewQueryTextEvent>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(SearchViewQueryTextEvent onTextChangeEvent) {
                String query = onTextChangeEvent.queryText().toString();
                searchBooksByNameAndAuthor(query);
            }
        };
    }

    private void searchBooksByNameAndAuthor(String query) {
        if(!TextUtils.isEmpty(query) && originalList.size() > 0) {
            query = query.toLowerCase();
            mQuery = query;
            bookList.clear();
            for (Book book : originalList) {
                if (book.getName().toLowerCase().contains(query)) {
                    bookList.add(book);
                }
            }
        } else {
            mQuery = null;
            bookList.clear();
            bookList.addAll(originalList);
        }
        booksAdapter.notifyDataChanged(mQuery);
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
                originalList.clear();
                originalList.addAll(booksPresent.books);
                bookList.addAll(booksPresent.books);
                mProgressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                booksAdapter.notifyDataChanged(mQuery);
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
        if (PreferenceUtils.isLoggedIn(this)) {
            MenuItem registerItem  = menu.findItem(R.id.register);
            registerItem.setTitle(PreferenceUtils.getStringPrefs(this, PreferenceUtils
                .SAVED_USER_NAME));
            FirebaseMessaging.getInstance().subscribeToTopic("usedbooks_" +
                PreferenceUtils.getIntegerPrefs(this, PreferenceUtils
                .SAVED_USER_ID));
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
        if (mDisposable != null){
            mDisposable.dispose();
        }
    }
}
