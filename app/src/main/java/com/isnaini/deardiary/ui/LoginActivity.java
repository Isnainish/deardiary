package com.isnaini.deardiary.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.isnaini.deardiary.R;
import com.isnaini.deardiary.model.DBSource;
import com.isnaini.deardiary.pojo.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnReset;

    private String username;
    private String password;

    private DBSource mDBSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                mDBSource = new DBSource(LoginActivity.this);
                mDBSource.open();


                Cursor mCursor = mDBSource.checkTBUser();


                if (username.equals("") || password.equals("")){

                    Toast.makeText(getApplicationContext(), "Mohon Diisi Lengkap",
                            Toast.LENGTH_SHORT).show();

                } else if (!mCursor.moveToNext()){

                    User mUser = null;
                    mUser = mDBSource.insertUser(username,password);
                    Toast.makeText(getApplicationContext(),"Registrasi Berhasil, Username = "
                            + mUser.getUsername() + ", Silahkan Login",
                            Toast.LENGTH_SHORT).show();

                } else {

                    User mUser = null;
                    mUser = mDBSource.getOneUser();

                    String mUsername = mUser.getUsername();
                    String mPassword = mUser.getPassword();


                    if (username.equals(mUsername) && password.equals(mPassword)){

                        Toast.makeText(getApplicationContext(), "Login Berhasil, Username "
                        + mUsername, Toast.LENGTH_SHORT).show();

                        Intent mIntent = new Intent(LoginActivity.this, ListActivity.class);
                        startActivity(mIntent);

                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etUsername.setText("");
                etPassword.setText("");

            }
        });
    }
}
