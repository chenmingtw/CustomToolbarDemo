package org.chenming.customtoolbardemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean isToolbarExpanded = false;
    private int toolbarHeight = 0;

    private ArcBarView arcBarView;
    private Toolbar toolbar;
    private TextView textViewTitle;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        // init UI
        arcBarView = findViewById(R.id.arcBarView);
        toolbar = findViewById(R.id.toolbar);
        textViewTitle = findViewById(R.id.textTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        arcBarView.setBarWidthAndHeight(metrics.widthPixels, toolbarHeight);
        arcBarView.setBarColor(getResources().getColor(R.color.colorPrimaryDark));

        button = findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ObjectAnimator animator = ObjectAnimator.ofArgb(arcBarView, "barColor", getResources().getColor(R.color.colorPrimaryDark));
            animator.addListener(collapseAnimatorListener);
            animator.start();
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isToolbarExpanded) {
                ObjectAnimator animator = ObjectAnimator.ofInt(arcBarView, "expandHeight", toolbarHeight);
                animator.addListener(expandAnimatorListener);
                animator.setInterpolator(new OvershootInterpolator());
                animator.setDuration(500)
                        .start();
            }
        }
    };

    ObjectAnimator.AnimatorListener expandAnimatorListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isToolbarExpanded = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(arcBarView, "barColor", getResources().getColor(R.color.colorPrimary));
            objectAnimator.start();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setBackgroundResource(R.color.colorPrimaryDark);
            textViewTitle.setText("Expand");
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ObjectAnimator.AnimatorListener collapseAnimatorListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ObjectAnimator animator = ObjectAnimator.ofInt(arcBarView, "expandHeight", 0);
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(500)
                    .start();
            toolbar.setBackgroundResource(android.R.color.transparent);
            textViewTitle.setText("Collapse");
            isToolbarExpanded = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
}
