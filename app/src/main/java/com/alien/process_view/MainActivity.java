package com.alien.process_view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alien.process_view.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private int value;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initData();

        initEvent();
    }

    private void initData() {
        value = binding.progress.getProgress();
    }

    private void initEvent() {
        binding.add.setOnClickListener(v ->
                binding.progress.setProgress(++value));

        binding.reduce.setOnClickListener(v ->
                binding.progress.setProgress(--value));
    }


}