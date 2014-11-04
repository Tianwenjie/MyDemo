package com.example.tangsong.mydemo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;

public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final int DURATION = 5000;
    private static final float BALL_SIZE = 100f;

    public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
    AnimatorSet animation = null;
    Animator bounceAnim = null;
    ShapeHolder ball = null;

    public MyAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        addBall(50, 0);
        addBall(150, 0);
        addBall(250, 0);
        addBall(350, 0);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void createAnimation() {
        if (bounceAnim == null) {
            ShapeHolder ball;
            ball = balls.get(0);
            ObjectAnimator yBouncer = ObjectAnimator.ofFloat(ball, "y",
                    ball.getY(), getHeight() - BALL_SIZE).setDuration(DURATION);
            yBouncer.setInterpolator(new BounceInterpolator());
            yBouncer.addUpdateListener(this);

            ball = balls.get(1);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                    getHeight() - BALL_SIZE);
            PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
            ObjectAnimator yAlphaBouncer = ObjectAnimator.ofPropertyValuesHolder(ball,
                    pvhY, pvhAlpha).setDuration(DURATION / 2);
            yAlphaBouncer.setInterpolator(new AccelerateInterpolator());
            yAlphaBouncer.setRepeatCount(1);
            yAlphaBouncer.setRepeatMode(ValueAnimator.REVERSE);


            ball = balls.get(2);
            PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width", ball.getWidth(),
                    ball.getWidth() * 2);
            PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height", ball.getHeight(),
                    ball.getHeight() * 2);
            PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", ball.getX(),
                    ball.getX() - BALL_SIZE / 2f);
            PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                    ball.getY() - BALL_SIZE / 2f);
            ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhW, pvhH,
                    pvTX, pvTY).setDuration(DURATION / 2);
            whxyBouncer.setRepeatCount(1);
            whxyBouncer.setRepeatMode(ValueAnimator.REVERSE);

            ball = balls.get(3);
            pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(), getHeight() - BALL_SIZE);
            float ballX = ball.getX();
            Keyframe kf0 = Keyframe.ofFloat(0f, ballX);
            Keyframe kf1 = Keyframe.ofFloat(.5f, ballX + 100f);
            Keyframe kf2 = Keyframe.ofFloat(1f, ballX + 50f);
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("x", kf0, kf1, kf2);
            ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhY,
                    pvhX).setDuration(DURATION / 2);
            yxBouncer.setRepeatCount(1);
            yxBouncer.setRepeatMode(ValueAnimator.REVERSE);


            bounceAnim = new AnimatorSet();
            ((AnimatorSet) bounceAnim).playTogether(yBouncer, yAlphaBouncer, whxyBouncer,
                    yxBouncer);
        }
    }

    public void startAnimation() {
        createAnimation();
        bounceAnim.start();
    }

    private ShapeHolder addBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(BALL_SIZE, BALL_SIZE);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        ShapeHolder shapeHolder = new ShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        int red = (int) (100 + Math.random() * 155);
        int green = (int) (100 + Math.random() * 155);
        int blue = (int) (100 + Math.random() * 155);
        int color = 0xff000000 | red << 16 | green << 8 | blue;
        Paint paint = drawable.getPaint();
        int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
        RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                50f, color, darkColor, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        shapeHolder.setPaint(paint);
        balls.add(shapeHolder);
        return shapeHolder;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (ShapeHolder ball : balls) {
            canvas.save();
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
//                canvas.translate(-ball.getX(), -ball.getY());
            canvas.restore();
        }
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

}