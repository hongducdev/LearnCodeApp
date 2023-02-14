package com.example.learncodeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {

    Context context;
    ArrayList<RankModel> rankList;

    public RankAdapter(Context context, ArrayList<RankModel> rankList) {
        this.context = context;
        this.rankList = rankList;
    }

    @Override
    public int getCount() {
        return rankList.size();
    }

    @Override
    public Object getItem(int position) {
        return rankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.rank_item, parent, false);

        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        TextView tvNameUser = convertView.findViewById(R.id.tvNameUser);
        TextView tvScoreUser = convertView.findViewById(R.id.tvScoreUser);

        return convertView;
    }
}
