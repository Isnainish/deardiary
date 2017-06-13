package com.isnaini.deardiary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isnaini.deardiary.R;
import com.isnaini.deardiary.pojo.Diary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by root on 29/05/17.
 */

public class PostAdapter extends BaseAdapter {

    ArrayList listPost;
    Activity mActivity;

    private SimpleDateFormat mFormat;

    public PostAdapter(Activity mActivity, ArrayList listPost){
        this.listPost = listPost;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return listPost.size();
    }

    @Override
    public Object getItem(int position) {
        return listPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;
        ViewHolder mVHolder = null;

        if(mVHolder == null){

            mVHolder = new ViewHolder();

            LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mView = mInflater.inflate(R.layout.list_post, null);
            mVHolder.tvTitle = (TextView) mView.findViewById(R.id.tv_title);
            mVHolder.tvDate = (TextView) mView.findViewById(R.id.tv_date);
            mVHolder.tvMessage = (TextView) mView.findViewById(R.id.tv_message);

            mView.setTag(mVHolder);
        } else {

            mVHolder = (ViewHolder) mView.getTag();
        }

        Diary mDiary = (Diary) getItem(position);

        mFormat = new SimpleDateFormat("EEE, dd MMM yyyy");

        mVHolder.tvTitle.setText(mDiary.getTitle());
        mVHolder.tvDate.setText(mFormat.format(mDiary.getDate()));
        mVHolder.tvMessage.setText(mDiary.getMessage());


        return mView;
    }

    static class ViewHolder{
        TextView tvTitle, tvDate, tvMessage;
    }
}
