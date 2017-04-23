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
    public TextView editionView;
    public TextView authornameView;
    public ImageView imageView;
    public TextView userNameView;
    public TextView addressView;
    public TextView cityView;
    public TextView stateView;
    public TextView phonenoView;
    public TextView phoneno2View;
    public View item;

    public BookViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.book_name);
        costView = (TextView) itemView.findViewById(R.id.cost);
        imageView = (ImageView) itemView.findViewById(R.id.book_image);
        editionView= (TextView) itemView.findViewById(R.id.edition);
        authornameView= (TextView) itemView.findViewById(R.id.authorname);
        userNameView = (TextView) itemView.findViewById(R.id.userName);
        addressView= (TextView) itemView.findViewById(R.id.address);
        cityView= (TextView) itemView.findViewById(R.id.city);
        stateView= (TextView) itemView.findViewById(R.id.state);
        phonenoView= (TextView) itemView.findViewById(R.id.phoneno);
        phoneno2View= (TextView) itemView.findViewById(R.id.phoneno2);
        item = itemView;
    }
}
