package com.coppermobile.mylocationmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.iv_fruit);

        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        observeImages(myViewModel);
    }

    private void observeImages(MyViewModel myViewModel) {
        myViewModel.getFruits().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                image.setImageResource(integer);
            }
        });
    }
}
