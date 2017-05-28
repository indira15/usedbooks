
package com.indira.usedbooks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Manish on 09-05-2017. */



public class FeedbackViewHolder extends RecyclerView.ViewHolder {
    public TextView userNameView;
    public TextView messageView;
    public TextView dateView;
    public View item;

    public FeedbackViewHolder(View itemView) {
        super(itemView);
        userNameView = (TextView) itemView.findViewById(R.id.userName);
        messageView = (TextView) itemView.findViewById(R.id.message);
        dateView = (TextView) itemView.findViewById(R.id.date);
        item = itemView;
    }
}

