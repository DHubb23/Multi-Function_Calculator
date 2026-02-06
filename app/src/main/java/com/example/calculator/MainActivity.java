package com.example.calculator;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.calculator.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initLayout();
    }
    private void initLayout() {
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout = binding.main;

        final int KEYS_WIDTH = 5;
        final int KEYS_HEIGHT = 4;

        int[][] horizontals = new int[KEYS_HEIGHT][KEYS_WIDTH];
        int[][] verticals = new int[KEYS_WIDTH][KEYS_HEIGHT];

        String[] text = getResources().getStringArray(R.array.btnText);
        String[] tag = getResources().getStringArray(R.array.btnTag);

        TextView display = new TextView(this);
        display.setId(View.generateViewId());
        display.setText("0");
        display.setTextSize(48);
        display.setGravity(Gravity.END | Gravity.CLIP_VERTICAL);
        layout.addView(display);

        //Create Buttons
        int count = 0;
        for (int i = 0; i < KEYS_HEIGHT; i++) {
            for (int c = 0; c < KEYS_WIDTH; c++) {
                Button btn = new Button(this);
                btn.setId(View.generateViewId());
                btn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                btn.setText(text[count]);
                btn.setTextSize(28);
                btn.setTag(tag[count]);

                layout.addView(btn);

                horizontals[i][c] = btn.getId();
                verticals[c][i] = btn.getId();
                count++;
            }
        }
        set.clone(layout);

        //Button Constraints
        for (int i = 0; i < KEYS_HEIGHT; i++) {
            for (int c = 0; c < KEYS_WIDTH; c++) {
                int id = horizontals[i][c];
                set.constrainWidth(id, ConstraintSet.MATCH_CONSTRAINT);
                set.constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT);
            }
        }
        //Display Constraints
        set.constrainWidth(display.getId(), ConstraintSet.MATCH_CONSTRAINT);
        set.connect(display.getId(), ConstraintSet.TOP, R.id.guideNorth, ConstraintSet.TOP, 32);
        set.connect(display.getId(), ConstraintSet.START, R.id.guideWest, ConstraintSet.START, 16);
        set.connect(display.getId(), ConstraintSet.END, R.id.guideEast, ConstraintSet.END, 16);

        //Chains
        for (int r = 0; r < KEYS_HEIGHT; r++) {
            set.createHorizontalChain(
                    R.id.guideWest, ConstraintSet.RIGHT,
                    R.id.guideEast, ConstraintSet.LEFT,
                    horizontals[r], null, ConstraintSet.CHAIN_SPREAD
            );
        }
        for (int c = 0; c < KEYS_WIDTH; c++) {
            set.createVerticalChain(
                    display.getId(), ConstraintSet.BOTTOM,
                    R.id.guideSouth, ConstraintSet.TOP,
                    verticals[c], null, ConstraintSet.CHAIN_SPREAD
            );
        }
        set.applyTo(layout);
    }

}