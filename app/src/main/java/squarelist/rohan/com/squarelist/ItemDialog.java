package squarelist.rohan.com.squarelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import squarelist.rohan.com.database.DataProvider;
import squarelist.rohan.com.database.ItemContract;

/**
 * Created by Rohan on 11/15/2015.
 */
public class ItemDialog extends DialogFragment {

    private long mItemId = -5;

    private EditText mEtName;
    private RadioButton mRdBtnChecked;
    private RadioButton mRdBtnUnchecked;

    private DialogActionListenr mListener;

    private static final String TAG = ItemDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dlg_item, null);
        mEtName = (EditText) v.findViewById(R.id.ItemDialog_etItemName);
        mRdBtnChecked = (RadioButton) v.findViewById(R.id.ItemDialog_rdBtnChecked);
        mRdBtnUnchecked = (RadioButton) v.findViewById(R.id.ItemDialog_rdBtnUnchecked);
        if (mItemId != -5){
            loadItem();
        }
        builder.setView(v);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isItemValid()){
                    String itemName = mEtName.getText().toString();
                    int itemStatus = findItemStatus();
                    Bundle b = new Bundle();
                    b.putLong(ItemContract.ItemEntry._ID, mItemId);
                    b.putString(ItemContract.ItemEntry.ITEM_NAME, itemName);
                    b.putInt(ItemContract.ItemEntry.ITEM_STATUS, itemStatus);
                    mListener.onOKSelected(b);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dlg = builder.create();
        return dlg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        if (getArguments() != null){
            mItemId = getArguments().getLong(ItemContract.ItemEntry._ID);
        }
        super.onCreate(savedInstanceState);
    }

    private void loadItem(){
        Uri uri = Uri.parse(DataProvider.CONTENT + DataProvider.AUTHORITY + DataProvider.SEPARATOR + ItemContract.ItemEntry.ITEM_TABLE);
        Cursor c = getActivity().getContentResolver().query(uri,
                new String[]{ItemContract.ItemEntry._ID, ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                ItemContract.ItemEntry._ID + " = ? ",
                new String[]{Long.toString(mItemId)}, null);
        c.moveToFirst();
        String itemName = c.getString(c.getColumnIndex(ItemContract.ItemEntry.ITEM_NAME));
        int itemStatus = c.getInt(c.getColumnIndex(ItemContract.ItemEntry.ITEM_STATUS));
        c.close();
        populateDialog(itemName, itemStatus);
    }

    private void populateDialog(String itemName, int itemStatus){
        mEtName.setText(itemName);
        switch (itemStatus){
            case ItemContract.CHECKED:
                mRdBtnChecked.setChecked(true);
                break;
            case ItemContract.UNCHECKED:
                mRdBtnUnchecked.setChecked(true);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogActionListenr) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " did not implement DialogActionListenr.");
        }
    }

    private boolean isItemValid(){
        if (mEtName.getText().length() == 0){
            return false;
        }
        if (!mRdBtnChecked.isChecked() && !mRdBtnUnchecked.isChecked()){
            return false;
        }
        return true;
    }

    private int findItemStatus(){
        if (mRdBtnUnchecked.isChecked()){
            return ItemContract.UNCHECKED;
        } else if (mRdBtnChecked.isChecked()){
            return ItemContract.CHECKED;
        } else {
            return -5;
        }
    }

}
