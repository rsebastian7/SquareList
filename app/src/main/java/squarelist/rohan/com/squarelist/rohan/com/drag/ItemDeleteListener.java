package squarelist.rohan.com.squarelist.rohan.com.drag;

import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import squarelist.rohan.com.squarelist.ItemActionListener;

/**
 * Created by Rohan on 11/21/2015.
 */
public class ItemDeleteListener implements View.OnDragListener {

    private ItemActionListener mListener;

    private static final String TAG = ItemDeleteListener.class.getSimpleName();

    public ItemDeleteListener(ItemActionListener listener){
        mListener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        LinearLayout lloDelete = (LinearLayout) v;

        int action = event.getAction();
        switch (action){
            case DragEvent.ACTION_DRAG_STARTED:

                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                enteredBackground(lloDelete);
                return true;
            case DragEvent.ACTION_DROP:
                long itemId = (long) ((View) event.getLocalState()).getTag();
                mListener.showDeleteDialog(itemId);
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                restoreBackground(lloDelete);
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                restoreBackground(lloDelete);
                return true;
        }

        return false;
    }


    private void enteredBackground(LinearLayout llo){
        llo.setBackgroundColor(Color.CYAN);
    }

    private void restoreBackground(LinearLayout llo) {
        llo.setBackgroundColor(Color.RED);
    }

}
