package com.k4ench.scanme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity1 extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener{
    @BindView(R.id.lblHowHappy)
    TextView lblHowHappy;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.lblWeHearFeedback) TextView lblWeHearFeedback;
    @BindView(R.id.txtComments)
    EditText txtComments;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.layoutForm)
    LinearLayout layoutForm;
    @BindView(R.id.lblThanksFeedback) TextView txtThanks;

    boolean isAnimated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upIntent = NavUtils.getParentActivityIntent(MessageActivity1.this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(MessageActivity1.this, upIntent);
            }
        });
        setTitle("FeedBack");
        ButterKnife.bind(this);

        initializeUI();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void initializeUI()
    {
        // Setting Initial Settings for UIs
        ratingBar.setRating(0);
        layoutForm.setVisibility(View.INVISIBLE);

        // Setting listeners
        ratingBar.setOnRatingBarChangeListener(this);

    }

    public void onBackPressed() {

        finish();


    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
        if (isAnimated == false)
        {
            // show hidden views
            layoutForm.setVisibility(View.VISIBLE);

            // Work out animations
            ViewAnimator
                    // Initial Label
                    .animate(lblHowHappy)
                    .dp().translationY(0, -100)
                    .alpha(1,0)
                    .duration(350)
                    .interpolator(new LinearOutSlowInInterpolator())
                    // Rating bar
                    .andAnimate(ratingBar)
                    .dp().translationY(0, -100)
                    .duration(450)
                    .interpolator(new LinearOutSlowInInterpolator())
                    // Layout Form
                    .andAnimate(layoutForm)
                    .dp().translationY(0, -100)
                    .singleInterpolator(new LinearOutSlowInInterpolator())
                    .duration(450)
                    .alpha(0,1)
                    .interpolator(new FastOutSlowInInterpolator())
                    // Label feedback of form
                    .andAnimate(lblWeHearFeedback)
                    .dp().translationY(0,-20)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(300)
                    .alpha(0,1)
                    // Commects edittext
                    .andAnimate(txtComments)
                    .dp().translationY(30,-30)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(550)
                    .alpha(0,1)
                    // Submit button
                    .andAnimate(btnSubmit)
                    .dp().translationY(60,-35)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(800)
                    .alpha(0,1)
                    .onStop(new AnimationListener.Stop() {
                        @Override
                        public void onStop() {
                            isAnimated = true;
                        }
                    })
                    .start();
        }
    }


    @OnClick(R.id.btnSubmit)
    public void onSubmitClick()
    {
        String inp=txtComments.getText().toString();
        if(inp.isEmpty()){
            TastyToast.makeText(getApplicationContext(), "Please Enter Comments ", TastyToast.LENGTH_LONG,
                    TastyToast.INFO);
        }
        else
        {
            // Feedback has been written
            txtThanks.setVisibility(View.VISIBLE);

            // Perfrom Animations
            ViewAnimator
                    .animate(ratingBar)
                    .dp().translationY(-100, -130)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(200)
                    .alpha(1,0)
                    .andAnimate(lblWeHearFeedback)
                    .dp().translationY(-20, -90)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(250)
                    .alpha(1,0)
                    .andAnimate(txtComments)
                    .dp().translationY(-30, -120)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(300)
                    .alpha(1,0)
                    .andAnimate(btnSubmit)
                    .dp().translationY(-35, -200)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(340)
                    .alpha(1,0)
                    .andAnimate(txtThanks)
                    .dp().translationY(0, -200)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .duration(600)
                    .start();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
