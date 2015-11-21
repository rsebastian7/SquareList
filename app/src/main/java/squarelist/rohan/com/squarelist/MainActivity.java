package squarelist.rohan.com.squarelist;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;

import squarelist.rohan.com.database.DataProvider;
import squarelist.rohan.com.database.ItemContract;
import squarelist.rohan.com.squarelist.rohan.com.drag.ItemAdapter;
import squarelist.rohan.com.squarelist.rohan.com.drag.ItemClickListener;
import squarelist.rohan.com.squarelist.rohan.com.drag.ItemDeleteListener;
import squarelist.rohan.com.squarelist.rohan.com.drag.ItemDragListener;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ItemActionListener, DialogActionListener {

    private GridView gvChecked;
    private GridView gvUnchecked;

    private ItemAdapter mCheckedAdapter;
    private ItemAdapter mUncheckedAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeControls();

        initializeCheckedItems();
        initializeUncheckedItems();

        loadItems();
    }

    private void initializeControls(){
        gvChecked = (GridView) findViewById(R.id.gvChecked);
        gvUnchecked = (GridView) findViewById(R.id.gvUnchecked);

        gvChecked.setOnDragListener(new ItemDragListener(this));
        gvUnchecked.setOnDragListener(new ItemDragListener(this));

        gvChecked.setOnItemClickListener(new ItemClickListener(this));
        gvUnchecked.setOnItemClickListener(new ItemClickListener(this));

        setupFloatingAdd();

        LinearLayout lloDelete = (LinearLayout) findViewById(R.id.MainActivity_lloDelete);
        lloDelete.setOnDragListener(new ItemDeleteListener(this));
    }

    private void setupFloatingAdd(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                ItemDialog dialog = new ItemDialog();
                dialog.show(getFragmentManager(), "ItemDialog");
            }
        });
    }

    @Override
    public void showEditDialog(long itemId) {
        ItemDialog dialog = new ItemDialog();
        Bundle b = new Bundle();
        b.putLong(ItemContract.ItemEntry._ID, itemId);
        dialog.setArguments(b);
        dialog.show(getFragmentManager(), "ItemDialog");
    }

    private void loadItems(){
        getLoaderManager().initLoader(ItemContract.CHECKED, generateBundle(ItemContract.CHECKED), this);
        getLoaderManager().initLoader(ItemContract.UNCHECKED, generateBundle(ItemContract.UNCHECKED), this);
    }

    private void reloadItems(){
        getLoaderManager().restartLoader(ItemContract.CHECKED, generateBundle(ItemContract.CHECKED), this);
        getLoaderManager().restartLoader(ItemContract.UNCHECKED, generateBundle(ItemContract.UNCHECKED), this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse(DataProvider.CONTENT + DataProvider.AUTHORITY + DataProvider.SEPARATOR + ItemContract.ItemEntry.ITEM_TABLE);
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                uri,
                new String[]{ItemContract.ItemEntry._ID, ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                ItemContract.ItemEntry.ITEM_STATUS + " = ? ",
                new String[]{Integer.toString(args.getInt(ItemContract.ItemEntry.ITEM_STATUS))},
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       switch(loader.getId()){
           case ItemContract.CHECKED:
               mCheckedAdapter.swapCursor(data);
               break;
           case ItemContract.UNCHECKED:
               mUncheckedAdapter.swapCursor(data);
               break;
       }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch(loader.getId()){
            case ItemContract.CHECKED:
                mCheckedAdapter.swapCursor(null);
                break;
            case ItemContract.UNCHECKED:
                mUncheckedAdapter.swapCursor(null);
                break;
        }
    }

    @Override
    public int updateItem(long itemId, int status, String itemName) {
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.ITEM_STATUS, status);
        values.put(ItemContract.ItemEntry.ITEM_NAME, itemName);
        Uri uri = Uri.parse(DataProvider.CONTENT + DataProvider.AUTHORITY + DataProvider.SEPARATOR + ItemContract.ItemEntry.ITEM_TABLE + DataProvider.SEPARATOR + Long.toString(itemId));
        int returnNumber = getContentResolver().update(uri, values, ItemContract.ItemEntry._ID + " = ?", new String[]{Long.toString(itemId)});
        reloadItems();
        return returnNumber;
    }

    @Override
    public int updateItemStatus(long itemId, int status) {
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.ITEM_STATUS, status);
        Uri uri = Uri.parse(DataProvider.CONTENT + DataProvider.AUTHORITY + DataProvider.SEPARATOR + ItemContract.ItemEntry.ITEM_TABLE + DataProvider.SEPARATOR + Long.toString(itemId));
        int returnNumber = getContentResolver().update(uri, values, ItemContract.ItemEntry._ID + " = ?", new String[]{Long.toString(itemId)});
        reloadItems();
        return returnNumber;
    }

    @Override
    public int showDeleteDialog(long itemId) {
        final long fitemId = itemId;
        final int returnNumber = -5;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirm_msg);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(fitemId);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dlg = builder.create();
        dlg.show();
        return returnNumber;
    }

    private void deleteItem(long itemId){
        Uri uri = Uri.parse(DataProvider.CONTENT + DataProvider.AUTHORITY + DataProvider.SEPARATOR + ItemContract.ItemEntry.ITEM_TABLE + DataProvider.SEPARATOR + Long.toString(itemId));
        int returnNumber = getContentResolver().delete(uri, ItemContract.ItemEntry._ID + " = ?", new String[]{Long.toString(itemId)});
        if (returnNumber > 0){
            reloadItems();
        }
    }

    @Override
    public int addItem(String itemName, int itemStatus) {
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.ITEM_NAME, itemName);
        values.put(ItemContract.ItemEntry.ITEM_STATUS, itemStatus);
        Uri uri = Uri.parse(DataProvider.CONTENT + DataProvider.AUTHORITY + DataProvider.SEPARATOR + ItemContract.ItemEntry.ITEM_TABLE);
        Uri addUri = getContentResolver().insert(uri, values);
        Log.i(TAG, "addUri: " + addUri);
        int addNumber = Integer.parseInt(addUri.getLastPathSegment());
        Log.i(TAG, "addNumber: " + addNumber);
        return addNumber;
    }

    @Override
    public void onOKSelected(Bundle b) {
        long itemId = b.getLong(ItemContract.ItemEntry._ID);
        if (itemId != -5){
            updateItem(itemId, b.getInt(ItemContract.ItemEntry.ITEM_STATUS), b.getString(ItemContract.ItemEntry.ITEM_NAME));
        } else {
            addItem(b.getString(ItemContract.ItemEntry.ITEM_NAME), b.getInt(ItemContract.ItemEntry.ITEM_STATUS));
        }
        reloadItems();
    }

    @Override
    public void onCancelSelected() {

    }

    private void initializeCheckedItems(){
        mCheckedAdapter = new ItemAdapter(getBaseContext(), null, R.layout.item_lo, new String[]{ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                new int[]{R.id.item_lo_tvName, R.id.item_lo_tvStatus});
        gvChecked.setAdapter(mCheckedAdapter);
    }

    private Bundle generateBundle(int status){
        Bundle b = new Bundle();
        b.putInt(ItemContract.ItemEntry.ITEM_STATUS, status);
        return b;
    }

    private void initializeUncheckedItems(){
        mUncheckedAdapter = new ItemAdapter(getBaseContext(), null, R.layout.item_lo, new String[]{ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                new int[]{R.id.item_lo_tvName, R.id.item_lo_tvStatus});
        gvUnchecked.setAdapter(mUncheckedAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
