package com.example.kasayonetimi;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;

public class AnimationDrawableWithCallback extends AnimationDrawable {

    interface IAnimationFinishListener {
        void onAnimationChanged(int index, boolean finished);
    }

    private IAnimationFinishListener animationFinishListener;

    public IAnimationFinishListener getAnimationFinishListener() {
        return animationFinishListener;
    }

    public void setAnimationFinishListener(IAnimationFinishListener animationFinishListener) {
        this.animationFinishListener = animationFinishListener;
    }
    @Override
    public boolean selectDrawable(int index) {
        boolean drawableChanged = super.selectDrawable(index);
        if (drawableChanged && animationFinishListener != null) {
            boolean animationFinished = (index == getNumberOfFrames() - 1);
            animationFinishListener.onAnimationChanged(index, animationFinished);
        }
        return drawableChanged;
    }
}
