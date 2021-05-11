package com.alien.process_view;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alien.process_view.databinding.ActivityMainBinding;
import com.alien.process_view.process_view.path.ArrowTypeManager;

public class MainActivity extends AppCompatActivity {

    private int type = 1;

    private int value, count;
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
        count = binding.progress.getCount();
    }

    private void initEvent() {
        binding.add.setOnClickListener(v ->
                binding.progress.setProgress(++value));

        binding.reduce.setOnClickListener(v ->
                binding.progress.setProgress(--value));

        binding.addCount.setOnClickListener(v ->
                binding.progress.setCount(++count));

        binding.reduceCount.setOnClickListener(v ->
                binding.progress.setCount(--count));

        binding.changeType.setOnClickListener(v -> {
            int result = 0;
            if(type > 3) {
                --type;
            } else {
                ++type;
            }

            switch (type) {
                case 0:
                    result = ArrowTypeManager.FULL_ARROW;
                    break;

                case 1:
                    result = ArrowTypeManager.FULL_ARROW_END;
                    break;

                case 2:
                    result = ArrowTypeManager.UPPER_ARROW_CENTER;
                    break;

                case 3:
                    result = ArrowTypeManager.UPPER_ARROW_SIDE;
                    break;
            }

            binding.progress.setArrowType(result);
        });

        binding.angleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.progress.setAngle(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}