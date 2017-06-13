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

public class EditPostActivity extends AppCompatActivity {


    private EditText etTitle, etMessage;
    private Button btnDate, btnImage, btnSave;
    private TextView tvDate;

    private Long id;
    private String title, date, message;

    private DBSource mDBSource;

    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSimpleDateFormater;

    private Diary mDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        getSupportActionBar().setTitle("Edit Post");

        etTitle = (EditText) findViewById(R.id.et_title);
        etMessage = (EditText) findViewById(R.id.et_message);
        tvDate = (TextView) findViewById(R.id.tv_date);
        btnDate = (Button) findViewById(R.id.btn_date);
        btnImage = (Button) findViewById(R.id.btn_image);
        btnSave = (Button) findViewById(R.id.btn_save);


        mSimpleDateFormater = new SimpleDateFormat("EEE, dd MMM yyyy");

        mDBSource = new DBSource(EditPostActivity.this);
        mDBSource.open();

        Bundle mBundle = EditPostActivity.this.getIntent().getExtras();
        id = mBundle.getLong("id");
        title = mBundle.getString("title");
        date = mBundle.getString("date");
        message = mBundle.getString("message");

        etTitle.setText(title);
        tvDate.setText(date);
        etMessage.setText(message);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiary = null;

                title = etTitle.getText().toString();
                date = tvDate.getText().toString();
                message = etMessage.getText().toString();

                try {

                    Toast.makeText(EditPostActivity.this, "" + id , Toast.LENGTH_LONG).show();

                    mDiary = mDBSource.updatePost(String.valueOf(id), title, date, message);

                    Intent mIntent = new Intent(EditPostActivity.this, ListActivity.class);
                    startActivity(mIntent);

                    EditPostActivity.this.finish();

                    mDBSource.close();


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showDatePicker() {
        Calendar mCalendar = Calendar.getInstance();

        mDatePickerDialog = new DatePickerDialog(EditPostActivity.this, new DatePickerDialog.OnDateSetListener() {

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
