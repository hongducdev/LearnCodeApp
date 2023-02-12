package com.example.learncodeapp;

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

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.List;

public class CourseGridAdapter extends BaseAdapter {
//    private List<CourseModel> courseList;
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
                Splash.selected_course_index = position;
                Intent intent = new Intent(parent.getContext(), IntroductionCourse.class);
                intent.putExtra("course", courseList.get(position));
                intent.putExtra("courseIntroduct", courseIntroductList.get(position));
                intent.putExtra("course_id", position + 1);
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
