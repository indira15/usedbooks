package com.indira.usedbooks;

//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.indira.usedbooks.entity.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import okhttp3.internal.Util;

/**
 * Created by Manish on 08-04-2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private ArrayList<Book> mBookList;
    private Context mContext;
    private String mSearchString;

    public BooksAdapter(ArrayList<Book> bookList, Context context) {
        this.mBookList = bookList;
        this.mContext = context;
    }

    public void notifyDataChanged(String query) {
        mSearchString = query;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        final Book book = mBookList.get(position);
        if(!TextUtils.isEmpty(mSearchString)) {
            holder.nameView.setText(Utils.highlight(mSearchString, book.getName()));
        }else {
            holder.nameView.setText(book.getName());
        }
        holder.costView.setText(String.valueOf("Rs: "+ book.getCost()));
        holder.userNameView.setText(" Posted by " + book.getUser().getName());

        if (!TextUtils.isEmpty(book.getImageUrl())) {
            Picasso.with(mContext).load(book.getImageUrl())
                    .placeholder(R.drawable.ic_launcher)
                    .resize(80, 80)
                    .into(holder.imageView);
        }

        holder.item.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(final View view) {
                if (PreferenceUtils.isLoggedIn(mContext))
                {
                    Intent contactIntent = new Intent(mContext, ContactActivity.class);
                contactIntent.putExtra("book", book);
                mContext.startActivity(contactIntent);
            }
                else
                {
                    Utils.showToast(mContext,"Please login to view details");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}