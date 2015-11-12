package squarelist.rohan.com.squarelist.rohan.com.drag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Rohan on 11/2/2015.
 */
public class ItemDragShadow extends View.DragShadowBuilder {

    private static Drawable shadow;

    public ItemDragShadow(View v){
        super(v);
        shadow = new ColorDrawable(Color.GRAY);
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
        super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        super.onDrawShadow(canvas);
    }
}
