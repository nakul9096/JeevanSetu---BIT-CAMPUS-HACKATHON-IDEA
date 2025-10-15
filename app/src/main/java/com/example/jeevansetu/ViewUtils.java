package com.example.jeevansetu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class ViewUtils {

    /**
     * Applies a press-and-release animation effect to a view.
     * @param view The view to apply the effect to.
     */
    public static void applyPressEffect(View view) {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Animate the view down when pressed
                        v.animate()
                                .scaleX(0.95f)
                                .scaleY(0.95f)
                                .setDuration(100)
                                .setInterpolator(new DecelerateInterpolator())
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Animate the view back up when released or canceled
                        v.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(150)
                                .setInterpolator(new AccelerateInterpolator())
                                .start();
                        break;
                }
                // Return false to allow other listeners (like OnClickListener) to run.
                return false;
            }
        });
    }
}