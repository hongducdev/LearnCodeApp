package com.example.learncodeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {

    Context context;
    ArrayList<RankModel> rankList;
    String nameCheck = "";

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

        tvNameUser.setText(rankList.get(position).getName());
        tvScoreUser.setText(String.valueOf(rankList.get(position).getScore()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        db.collection("users").whereEqualTo("username", sharedPreferences.getString("username", ""))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            nameCheck = document.getString("name");
                            return;
                        }
                    }
                });
        tvStatus.setText(String.valueOf(position + 1));
        if (rankList.get(position).getName().equals(nameCheck)) {
            tvNameUser.setText("Bạn");
            tvStatus.setTextColor(context.getResources().getColor(R.color.primary));
            tvScoreUser.setTextColor(context.getResources().getColor(R.color.primary));
            tvNameUser.setTextColor(context.getResources().getColor(R.color.primary));
        }

        return convertView;
    }
}
