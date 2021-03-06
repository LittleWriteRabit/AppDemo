package mrdlj.demo.com.appdemo.ui.widget.indicator.draw.controller;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.data.Value;
import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.type.AnimationType;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.data.Indicator;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.drawer.Drawer;
import mrdlj.demo.com.appdemo.ui.widget.indicator.utils.CoordinatesUtils;

public class DrawController {

    private Value value;
    private Drawer drawer;
    private Indicator indicator;

    public DrawController(@NonNull Indicator indicator) {
        this.indicator = indicator;
        this.drawer = new Drawer(indicator);
    }

    public void updateValue(@Nullable Value value) {
        this.value = value;
    }

    public void draw(@NonNull Canvas canvas) {
        int count = indicator.getCount();

        for (int position = 0; position < count; position++) {
            int coordinateX = CoordinatesUtils.getXCoordinate(indicator, position);
            int coordinateY = CoordinatesUtils.getYCoordinate(indicator, position);
            drawIndicator(canvas, position, coordinateX, coordinateY);
        }
    }

    private void drawIndicator(
            @NonNull Canvas canvas,
            int position,
            int coordinateX,
            int coordinateY) {

        boolean interactiveAnimation = indicator.isInteractiveAnimation();
        int selectedPosition = indicator.getSelectedPosition();
        int selectingPosition = indicator.getSelectingPosition();
        int lastSelectedPosition = indicator.getLastSelectedPosition();

        boolean selectedItem = !interactiveAnimation && (position == selectedPosition || position == lastSelectedPosition);
        boolean selectingItem = interactiveAnimation && (position == selectedPosition || position == selectingPosition);
        boolean isSelectedItem = selectedItem | selectingItem;
        drawer.setup(position, coordinateX, coordinateY);

        if (value != null && isSelectedItem) {
            drawWithAnimation(canvas);
        } else {
            drawer.drawBasic(canvas, isSelectedItem);
        }
    }

    private void drawWithAnimation(@NonNull Canvas canvas) {
        AnimationType animationType = indicator.getAnimationType();
        switch (animationType) {
            case NONE:
                drawer.drawBasic(canvas, true);
                break;

            case COLOR:
                drawer.drawColor(canvas, value);
                break;

            case SCALE:
                drawer.drawScale(canvas, value);
                break;

            case WORM:
                drawer.drawWorm(canvas, value);
                break;

            case SLIDE:
                drawer.drawSlide(canvas, value);
                break;

            case FILL:
                drawer.drawFill(canvas, value);
                break;

            case THIN_WORM:
                drawer.drawThinWorm(canvas, value);
                break;

            case DROP:
                drawer.drawDrop(canvas, value);
                break;

            case SWAP:
                drawer.drawSwap(canvas, value);
                break;
        }
    }
}
