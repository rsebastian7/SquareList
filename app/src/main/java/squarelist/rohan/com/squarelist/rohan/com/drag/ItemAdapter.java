package squarelist.rohan.com.squarelist.rohan.com.drag;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import squarelist.rohan.com.squarelist.ItemActionListener;

/**
 * Created by Rohan on 11/2/2015.
 */
public class ItemAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private Cursor mCursor;
    private int mLayoutId;
    private String[] mFrom;
    private int[] mTo;

    public ItemAdapter(Context context, Cursor c, int layoutId, String[] from, int[] to){
        super(context, layoutId, c, from, to, 0);
        mCursor = c;
        mLayoutId = layoutId;
        mFrom = from;
        mTo = to;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        long itemId = getItemId(position);
        v.setTag(itemId); // position
        v.setOnTouchListener(new ItemTouchListener());


        return v;
    }
}
