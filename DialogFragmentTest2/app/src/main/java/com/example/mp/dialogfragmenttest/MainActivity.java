package com.example.mp.dialogfragmenttest;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDialog(View v){
        FragmentManager fm = getFragmentManager();
        MyDialog myDialog = new MyDialog();
        myDialog.show(fm, "MyDialog");

    }
}
