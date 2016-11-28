package com.a404minds.rinki.schule;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private JSONArray mDataset;
    private Context context;
    Random rnd;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView icon;
        public RelativeLayout relayout;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.studentNameTextView);
            txtFooter = (TextView) v.findViewById(R.id.status);
            icon = (TextView) v.findViewById(R.id.icon);
            relayout = (RelativeLayout) v.findViewById(R.id.relative_layout);
            context = itemView.getContext();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StudentAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            Log.e("dataset", mDataset.toString());
            JSONObject classStudent = mDataset.getJSONObject(position);
            JSONObject student = classStudent.getJSONObject("student");
            Log.e("student name",student.getString("name"));
            holder.txtHeader.setText(student.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        holder.txtFooter.setText("Footer: " + mDataset.get(position));
        rnd = new Random();
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        holder.icon.setBackgroundResource(R.drawable.circle);

        GradientDrawable drawable = (GradientDrawable) holder.icon.getBackground();

        drawable.setColor(randomAndroidColor);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }

}
