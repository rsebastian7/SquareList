package squarelist.rohan.com.squarelist;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import squarelist.rohan.com.database.DatabaseHelper;
import squarelist.rohan.com.database.ItemContract;
import squarelist.rohan.com.squarelist.rohan.com.drag.ItemAdapter;
import squarelist.rohan.com.squarelist.rohan.com.drag.ItemDragListener;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private GridView gvChecked;
    private GridView gvUnchecked;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initializeControls();
        //addTestItems();
        initializeCheckedItems();
        initializeUncheckedItems();
    }

    private void initializeControls(){
        gvChecked = (GridView) findViewById(R.id.gvChecked);
        gvUnchecked = (GridView) findViewById(R.id.gvUnchecked);

        gvChecked.setOnDragListener(new ItemDragListener());
        gvUnchecked.setOnDragListener(new ItemDragListener());
    }

    private void startLoader(){
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //CursorLoader cursorLoader = new CursorLoader();

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void addTestItems(){
        DatabaseHelper.getInstance(getBaseContext()).addItem(getBaseContext(), "Item #1", 0);
        DatabaseHelper.getInstance(getBaseContext()).addItem(getBaseContext(), "Item #2", 0);

        DatabaseHelper.getInstance(getBaseContext()).addItem(getBaseContext(), "Item #3", 1);
        DatabaseHelper.getInstance(getBaseContext()).addItem(getBaseContext(), "Item #4", 1);
    }

    private void initializeCheckedItems(){
        Cursor c = DatabaseHelper.getInstance(getBaseContext()).selectItemsCursor(getBaseContext(), ItemContract.CHECKED);
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_lo, c, new String[]{ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
//                new int[]{R.id.item_lo_tvName, R.id.item_lo_tvStatus}, 0);
//
        ItemAdapter adapter = new ItemAdapter(getBaseContext(), c, R.layout.item_lo, new String[]{ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                new int[]{R.id.item_lo_tvName, R.id.item_lo_tvStatus});


        gvChecked.setAdapter(adapter);
    }

    private Bundle checkedBundle(){
        Bundle b = new Bundle();

        return null;
    }

    private void initializeUncheckedItems(){
        Cursor c = DatabaseHelper.getInstance(getBaseContext()).selectItemsCursor(getBaseContext(), ItemContract.UNCHECKED);
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_lo, c, new String[]{ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
//                new int[]{R.id.item_lo_tvName, R.id.item_lo_tvStatus}, 0);

        ItemAdapter adapter = new ItemAdapter(getBaseContext(), c, R.layout.item_lo, new String[]{ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                new int[]{R.id.item_lo_tvName, R.id.item_lo_tvStatus});

        gvUnchecked.setAdapter(adapter);
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
