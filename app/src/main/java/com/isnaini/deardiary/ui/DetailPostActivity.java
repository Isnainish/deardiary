package com.isnaini.deardiary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.isnaini.deardiary.R;

public class DetailPostActivity extends AppCompatActivity {

    private TextView tvTitle, tvDate, tvMessage;
    private Long id;
    private String title, date, message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvDate = (TextView) findViewById(R.id.tv_date);

        Bundle mBundle = DetailPostActivity.this.getIntent().getExtras();
        id = mBundle.getLong("id");
        title = mBundle.getString("title");
        date = mBundle.getString("date");
        message = mBundle.getString("message");

        getSupportActionBar().setTitle(title);

        tvTitle.setText(title);
        tvDate.setText(date);
        tvMessage.setText(message);
    }
}
