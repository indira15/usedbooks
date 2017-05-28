
package com.indira.usedbooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indira.usedbooks.entity.Feedback;

import java.util.ArrayList;

/*
 * Created by Manish on 08-05-2017.
*/


public class FeedbacksAdapter extends RecyclerView.Adapter<FeedbackViewHolder>{

    private ArrayList<Feedback> mFeedbackList;
    private Context mContext;

    public FeedbacksAdapter(ArrayList<Feedback> feedbackList , Context context)
    {
        this.mFeedbackList = feedbackList;
        this.mContext = context;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback,parent,false);
        return new FeedbackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {
        final Feedback feedback = mFeedbackList.get(position);
        holder.messageView.setText("Feedback:" + feedback.getMessage());
        holder.dateView.setText("Posted On" + feedback.getDate());
        holder.userNameView.setText("Posted By" + feedback.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return mFeedbackList.size();
    }
}

