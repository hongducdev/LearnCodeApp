package com.example.learncodeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    Context context;
    ArrayList<CommentModel> commentList;

    public CommentAdapter(Context context, ArrayList<CommentModel> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_comment_item, viewGroup, false);

        TextView tvUserName = view.findViewById(R.id.tvUserName);
        TextView tvComment = view.findViewById(R.id.tvComment);
        TextView tvTime = view.findViewById(R.id.tvTime);

        tvUserName.setText(commentList.get(i).getName());
        tvComment.setText(commentList.get(i).getComment());
        tvTime.setText(commentList.get(i).getTimestamp());

        return view;
    }
}
