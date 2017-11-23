package mrdlj.demo.com.appdemo.ui.widget.indicator.animation;

import android.support.annotation.NonNull;

import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.controller.AnimationController;
import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.controller.ValueController;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.data.Indicator;

public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicator, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
