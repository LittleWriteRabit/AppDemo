package mrdlj.demo.com.appdemo.ui.widget.indicator.draw.drawer.type;

import android.graphics.Paint;
import android.support.annotation.NonNull;

import mrdlj.demo.com.appdemo.ui.widget.indicator.draw.data.Indicator;

class BaseDrawer {

    Paint paint;
    Indicator indicator;

    BaseDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        this.paint = paint;
        this.indicator = indicator;
    }
}
