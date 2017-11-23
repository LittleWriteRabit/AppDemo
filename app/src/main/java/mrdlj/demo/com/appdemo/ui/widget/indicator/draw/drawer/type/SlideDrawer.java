package mrdlj.demo.com.appdemo.ui.widget.indicator.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.data.Value;
import mrdlj.demo.com.appdemo.ui.widget.indicator.animation.data.type.SlideAnimationValue;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.data.Indicator;
import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.data.Orientation;

public class SlideDrawer extends BaseDrawer {

    public SlideDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(
            @NonNull Canvas canvas,
            @NonNull Value value,
            int coordinateX,
            int coordinateY) {

        if (!(value instanceof SlideAnimationValue)) {
            return;
        }

        SlideAnimationValue v = (SlideAnimationValue) value;
        int coordinate = v.getCoordinate();
        int unselectedColor = indicator.getUnselectedColor();
        int selectedColor = indicator.getSelectedColor();
        int radius = indicator.getRadius();

        paint.setColor(unselectedColor);
        canvas.drawCircle(coordinateX, coordinateY, radius, paint);

        paint.setColor(selectedColor);
        if (indicator.getOrientation() == Orientation.HORIZONTAL) {
            canvas.drawCircle(coordinate, coordinateY, radius, paint);
        } else {
            canvas.drawCircle(coordinateX, coordinate, radius, paint);
        }
    }
}
