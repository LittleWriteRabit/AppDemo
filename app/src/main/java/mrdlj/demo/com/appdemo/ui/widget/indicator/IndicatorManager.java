package mrdlj.demo.com.appdemo.ui.widget.indicator;

import android.support.annotation.Nullable;
import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.AnimationManager;
import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.controller.ValueController;
import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.data.Value;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.DrawManager;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
