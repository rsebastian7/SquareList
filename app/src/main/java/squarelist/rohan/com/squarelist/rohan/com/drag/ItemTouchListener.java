package squarelist.rohan.com.squarelist.rohan.com.drag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Rohan on 11/1/2015.
 */
public class ItemTouchListener implements View.OnTouchListener {

    private static final String TAG = ItemTouchListener.class.getSimpleName();

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ClipData.Item itemContent = new ClipData.Item(v.getTag().toString());
        ClipData clip = new ClipData(v.getTag().toString(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, itemContent);
        ItemDragShadow shadow = new ItemDragShadow(v);
        v.startDrag(clip, shadow, v, 0);

        return false;
    }



}
