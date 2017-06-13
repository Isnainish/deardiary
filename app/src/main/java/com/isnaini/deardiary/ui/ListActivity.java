package com.isnaini.deardiary.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.isnaini.deardiary.R;
import com.isnaini.deardiary.adapter.PostAdapter;
import com.isnaini.deardiary.model.DBSource;
import com.isnaini.deardiary.pojo.Diary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private DBSource mDBSource;
    private ArrayList<Diary> dataDiary;
    private PostAdapter mPostAdapter;
    private SimpleDateFormat mFormat;

    private Button btnEdit, btnDelete;
    private ListView lvPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        lvPost = (ListView) findViewById(R.id.lv_post);

        mDBSource = new DBSource(ListActivity.this);
        mDBSource.open();


        try {
            dataDiary = mDBSource.getAllDiary();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mPostAdapter = new PostAdapter(ListActivity.this, dataDiary);
        lvPost.setAdapter(mPostAdapter);




        lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Diary d = (Diary) lvPost.getAdapter().getItem(position);

                Toast.makeText(ListActivity.this, "CLick : " + d.getTitle(), Toast.LENGTH_LONG).show();

                mFormat = new SimpleDateFormat("EEE, dd MMM yyyy");


                Intent mIntent = new Intent(ListActivity.this, DetailPostActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putLong("id", d.getId());
                mBundle.putString("title", d.getTitle());
                mBundle.putString("date", String.valueOf(mFormat.format(d.getDate())));
                mBundle.putString("message", d.getMessage());

                mIntent.putExtras(mBundle);
                mDBSource.close();

                startActivity(mIntent);
            }
        });

        lvPost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog mDialog = new Dialog(ListActivity.this);
                mDialog.setContentView(R.layout.dialog_view);
                mDialog.setTitle("Action?");
                mDialog.show();

                btnEdit = (Button) mDialog.findViewById(R.id.btn_edit);
                btnDelete = (Button) mDialog.findViewById(R.id.btn_delete);

                final Diary mDiary = (Diary) lvPost.getAdapter().getItem(position);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(ListActivity.this, "Edit", Toast.LENGTH_SHORT).show();

                        try {
                            editData(mDiary.getId());
                            mDialog.dismiss();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDBSource.deletePost(mDiary.getId());
                        mDialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });

                return true;
            }
        });

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(ListActivity.this, AddPostActivity.class);
                startActivity(mIntent);
            }
        });
    }

    private void editData(long id) throws ParseException {
        mFormat = new SimpleDateFormat("EEE, dd MMM yyyy");


        Diary mDiary = mDBSource.getOneDiary(id);
        Intent mIntent = new Intent(ListActivity.this, EditPostActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putLong("id", mDiary.getId());
        mBundle.putString("title", mDiary.getTitle());
        mBundle.putString("date", String.valueOf(mFormat.format(mDiary.getDate())));
        mBundle.putString("message", mDiary.getMessage());

        mIntent.putExtras(mBundle);
        mDBSource.close();

        startActivity(mIntent);

    }



    @Override
    protected void onResume() {
        mDBSource.open();

        super.onResume();
    }

    @Override
    protected void onPause() {
        mDBSource.close();

        super.onPause();
    }
}
