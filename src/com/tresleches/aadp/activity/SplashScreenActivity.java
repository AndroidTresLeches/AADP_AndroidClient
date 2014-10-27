package com.tresleches.aadp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.ParallelAnimator;
import com.easyandroidanimations.library.PathAnimation;
import com.tresleches.aadp.R;
import com.tresleches.aadp.R.anim;
import com.tresleches.aadp.R.id;
import com.tresleches.aadp.R.layout;



/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SplashScreenActivity extends Activity {
	
	private ImageView ivSplashScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		getActionBar().hide();
		
		ivSplashScreen =  (ImageView)findViewById(R.id.ivSplashScreen);

		FadeInAnimation fadeIn = new FadeInAnimation(ivSplashScreen);
		fadeIn.setDuration(1000);
		fadeIn.animate();
        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         * This method will be executed once the timer is over
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your app main activity
            	startAnimation();
            }
        }, 1000);		
		
	}
	
	private void startAnimation() {
		ArrayList<Point> points = new ArrayList<Point>();
//		points.add(new Point(50, 50));
		points.add(new Point(8, 4));

		PathAnimation path = new PathAnimation(ivSplashScreen);
		path = path.setPoints(points).setDuration(2000).setListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation arg0) {
//				Start Activity
				Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
				
			}
		});
		

		
		new ParallelAnimator().add(path).animate();
		
	}

	
}