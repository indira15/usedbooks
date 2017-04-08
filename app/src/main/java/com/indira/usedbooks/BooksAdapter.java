package com.indira.usedbooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Manish on 08-04-2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private ArrayList<Book> mBookList;
    private Context mContext;

    public BooksAdapter(ArrayList<Book> bookList, Context context) {
        this.mBookList = bookList;
        this.mContext = context;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.nameView.setText(book.getName());
        holder.costView.setText(String.valueOf(book.getCost()));
        ArrayList<Image> images = book.getImages();
        if (images != null && images.size() > 0) {
            Picasso.with(mContext).load(book.getImages().get(0).getUrl())
                    .placeholder(R.drawable.ic_launcher)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}