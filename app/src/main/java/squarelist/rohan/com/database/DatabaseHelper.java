package squarelist.rohan.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rohan on 11/1/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "squareDb";
    private static final int DATABASE_VERSION = 1;

   private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static synchronized DatabaseHelper getInstance(Context context){
        // Use application context to prevent activity leakage.
        if (sInstance  == null){
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context){
        //Log.i(TAG, "DatabaseHelper constructor");
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        db.execSQL(ItemContract.CREATE_ITEM_STMT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(TAG, "onOpen");
        super.onOpen(db);
    }

    public Cursor selectItemsCursor(Context context, int status){
        Cursor c = getReadableDatabase(context).query(ItemContract.ItemEntry.ITEM_TABLE, new String[]{ItemContract.ItemEntry._ID, ItemContract.ItemEntry.ITEM_NAME, ItemContract.ItemEntry.ITEM_STATUS},
                ItemContract.ItemEntry.ITEM_STATUS + " = ? ", new String[]{Integer.toString(status)}, null, null, null);
        return c;
    }



    public long addItem(Context context, String name, int status){
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.ITEM_NAME, name);
        values.put(ItemContract.ItemEntry.ITEM_STATUS, status);

        long addedItemRowId;
        addedItemRowId = getWritableDatabase(context).insert(ItemContract.ItemEntry.ITEM_TABLE, null, values);
        Log.i(TAG, "addItem addedItemRowId: " + addedItemRowId);
        return addedItemRowId;
    }

    private SQLiteDatabase getWritableDatabase(Context context){
        return getInstance(context).getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase(Context context){
        return getInstance(context).getReadableDatabase();
    }

    public int updateItem(Context context, int _id, String name, int status){
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.ITEM_NAME, name);
        values.put(ItemContract.ItemEntry.ITEM_STATUS, status);

        int updateId = getWritableDatabase(context).update(ItemContract.ItemEntry.ITEM_TABLE, values, ItemContract.ItemEntry._ID + " = ? ", new String[]{Integer.toString(_id)});
        Log.i(TAG, "updateItem updateId: " + updateId);
        return updateId;
    }

    public int deleteItem(Context context, int _id){
        int affectedRows = getWritableDatabase(context).delete(ItemContract.ItemEntry.ITEM_TABLE, ItemContract.ItemEntry._ID + " = ? ", new String[]{Integer.toString(_id)});
        Log.i(TAG, "deleteItem affectedRows: " + affectedRows);
        return affectedRows;
    }

    public int deleteAllItems(Context context){
        int affectedRows = getWritableDatabase(context).delete(ItemContract.ItemEntry.ITEM_TABLE, "1", null);
        Log.i(TAG, "deleteAllItems affectedRows: " + affectedRows);
        return affectedRows;
    }

}
