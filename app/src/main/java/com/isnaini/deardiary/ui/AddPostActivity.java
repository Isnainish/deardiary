package com.isnaini.deardiary.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isnaini.deardiary.R;
import com.isnaini.deardiary.model.DBSource;
import com.isnaini.deardiary.pojo.Diary;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {

    private EditText etTitle, etMessage;
    private Button btnDate, btnImage, btnSave;
    private TextView tvDate;

    private DBSource mDBSource;

    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSimpleDateFormater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        getSupportActionBar().setTitle("Add Post");

        mSimpleDateFormater = new SimpleDateFormat("EEE, dd MMM yyyy");

        etTitle = (EditText) findViewById(R.id.et_title);
        etMessage = (EditText) findViewById(R.id.et_message);
        tvDate = (TextView) findViewById(R.id.tv_date);
        btnDate = (Button) findViewById(R.id.btn_date);
        btnImage = (Button) findViewById(R.id.btn_image);
        btnSave = (Button) findViewById(R.id.btn_save);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                showDatePicker();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDBSource = new DBSource(AddPostActivity.this);
                mDBSource.open();

                String title = etTitle.getText().toString();
                String message = etMessage.getText().toString();
                String date = tvDate.getText().toString();

                Diary mDiary = null;

                try {
                    mDiary = mDBSource.insertPost(title,date,message);

                    Toast.makeText(AddPostActivity.this, "Data : "+ mDiary.getTitle() + mDiary.getMessage() + mDiary.getDate(),
                            Toast.LENGTH_SHORT).show();

                    Intent mIntent = new Intent(AddPostActivity.this, ListActivity.class);
                    startActivity(mIntent);

                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showDatePicker(){

        Calendar mCalendar = Calendar.getInstance();

        mDatePickerDialog = new DatePickerDialog(AddPostActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar mDate = Calendar.getInstance();
                mDate.set(year, monthOfYear, dayOfMonth);

                tvDate.setText(mSimpleDateFormater.format(mDate.getTime()));
            }

        },mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        mDatePickerDialog.show();

    }
}
