package com.example.learncodeapp;

import static android.service.controls.ControlsProviderService.TAG;
import static com.google.common.io.ByteStreams.copy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CourseGridAdapter extends BaseAdapter {
    private List<String> courseList, courseImageList, courseIntroductList;
    ImageView courseImage;

    public CourseGridAdapter(List<String> courseList, List<String> courseImageList, List<String> courseIntroductList){
        this.courseList = courseList;
        this.courseImageList = courseImageList;
        this.courseIntroductList = courseIntroductList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_layout, parent, false);
        } else {
            view = convertView;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), IntroductionCourse.class);
                intent.putExtra("course", courseList.get(position));
                intent.putExtra("courseIntroduct", courseIntroductList.get(position));
                parent.getContext().startActivity(intent);
            }
        });

        // load text, image ở HomePage
        ((TextView) view.findViewById(R.id.courseName)).setText(courseList.get(position));
        new DownloadImageTask((ImageView) view.findViewById(R.id.courseImage))
                .execute(courseImageList.get(position));

        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
