package ie.cr.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import ie.cr.R;

public class Splash extends Activity {
    // used to know if the back button was pressed in the splash screen activity 
    // and avoid opening the next activity
    private boolean 			mIsBackButtonPressed;
    private static final int 	SPLASH_DURATION = 3000; // 3 seconds

    @SuppressLint("WrongThread")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        ImageView im = findViewById(R.id.imageView);

        try {
            AnimatedImageDrawable barberAnimation;
            barberAnimation = (AnimatedImageDrawable) ImageDecoder.decodeDrawable(
                    ImageDecoder.createSource(getResources(), R.drawable.splash));
            im.setImageDrawable(barberAnimation);
            barberAnimation.start();
        }
        catch(Exception e)
        {

        }

        Handler handler = new Handler();
        // run a thread after 3 seconds to start the home screen
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // make sure we close the splash screen so the user
                // won't come back to it if back key pressed
                finish();

                if (!mIsBackButtonPressed) {
                    // start the home screen if the back button wasn't pressed already
                    Intent intent = new Intent(Splash.this, Home.class);
                    Splash.this.startActivity(intent);
                }
            }
        }, SPLASH_DURATION); // time in milliseconds to delay call to run()
    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
}