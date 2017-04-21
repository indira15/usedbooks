package com.indira.usedbooks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Manish on 08-04-2017.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView costView;
    public TextView userNameView;
    public ImageView imageView;

    public BookViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.book_name);
        costView = (TextView) itemView.findViewById(R.id.cost);
        imageView = (ImageView) itemView.findViewById(R.id.book_image);
        userNameView = (TextView) itemView.findViewById(R.id.userName);

    }
}
