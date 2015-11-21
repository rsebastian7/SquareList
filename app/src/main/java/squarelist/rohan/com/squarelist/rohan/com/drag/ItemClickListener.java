package squarelist.rohan.com.squarelist.rohan.com.drag;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import squarelist.rohan.com.squarelist.ItemActionListener;

/**
 * Created by Rohan on 11/17/2015.
 */
public class ItemClickListener implements AdapterView.OnItemClickListener {

    private ItemActionListener mListener;

    private static final String TAG = ItemClickListener.class.getSimpleName();

    public ItemClickListener(ItemActionListener listener){
        mListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "position: " + position + " id: " + id);
        mListener.showEditDialog(id);
    }
}
