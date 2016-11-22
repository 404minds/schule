package com.a404minds.rinki.schule;

/**
 * Created by rinki on 17/11/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;

import android.support.v7.widget.RecyclerView.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentAdapter extends Adapter<StudentAdapter.ViewHolder> {
    private JSONArray mDataset;
    public View v;

    // Provide a suitable constructor (depends on the kind of dataset)
    public StudentAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;

        public ViewHolder(TextView v) {
            super(v);
            studentName = v;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.student_text_view, parent, false);

        View view = v.findViewById(R.id.studentNameTextView);
        v.removeView(view);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder studentViewHolder = new ViewHolder((TextView) view);
        return studentViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            JSONObject student = mDataset.getJSONObject(position);
            holder.studentName.setText(student.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}

