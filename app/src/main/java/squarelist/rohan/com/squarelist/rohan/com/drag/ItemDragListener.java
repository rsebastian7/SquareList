package squarelist.rohan.com.squarelist.rohan.com.drag;

import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;

/**
 * Created by Rohan on 11/4/2015.
 */
public class ItemDragListener implements View.OnDragListener {

    private static final String TAG = ItemDragListener.class.getSimpleName();

    @Override
    public boolean onDrag(View v, DragEvent event) {

        View draggedView = (View) event.getLocalState();
        if (draggedView != null){
            GridView gvOrigin = (GridView) draggedView.getParent();
            GridView gvDest = (GridView) v;
            if (gvOrigin != gvDest){

                switch(event.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i(TAG, "onDrag ACTION_DRAG_STARTED");

                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i(TAG, "onDrag ACTION_DRAG_ENTERED");
                        enteredBackground(gvDest);

                        return true;
                    case DragEvent.ACTION_DROP:
                        Log.i(TAG, "onDrag ACTION_DROP");



                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i(TAG, "onDrag ACTION_DRAG_EXITED");
                        restoreBackground(gvDest);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i(TAG, "onDrag ACTION_DRAG_ENDED");
                        restoreBackground(gvDest);

                        return true;
                    default:
                        return false;

                }

            }

        }

        return false;
    }

    private void enteredBackground(GridView gv){
        gv.setBackgroundColor(Color.CYAN);
    }

    private void restoreBackground(GridView gv) {
        gv.setBackgroundColor(Color.WHITE);
    }



}
