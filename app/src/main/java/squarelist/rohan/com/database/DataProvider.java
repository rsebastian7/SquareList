package squarelist.rohan.com.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;

/**
 * Created by Rohan on 11/8/2015.
 */
public class DataProvider extends ContentProvider {

    public static final String AUTHORITY = "squarelist.rohan.com.provider";
    public static final String CONTENT = "content://";
    public static final String SEPARATOR = "/";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ITEMS_TABLE = 5;
    private static final int ITEM_ID = 6;

    static {
        sUriMatcher.addURI(AUTHORITY, ItemContract.ItemEntry.ITEM_TABLE, ITEMS_TABLE);
        sUriMatcher.addURI(AUTHORITY, ItemContract.ItemEntry.ITEM_TABLE + SEPARATOR + "#", ITEM_ID);

    }

    private static final String TAG = DataProvider.class.getSimpleName();

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    private String fetchTableName(Uri uri){
        int match = sUriMatcher.match(uri);
        switch (match){
            case ITEMS_TABLE:
                return ItemContract.ItemEntry.ITEM_TABLE;
            case ITEM_ID:
                return null;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        return DatabaseHelper.getInstance(getContext()).getWritableDatabase().query(fetchTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);

        //return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return DatabaseHelper.getInstance(getContext()).getWritableDatabase().query(fetchTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);

        //return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = DatabaseHelper.getInstance(getContext()).getWritableDatabase().insert(fetchTableName(uri), null, values);

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowCount = DatabaseHelper.getInstance(getContext()).getWritableDatabase().delete(fetchTableName(uri), selection, selectionArgs);

        return rowCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int id = DatabaseHelper.getInstance(getContext()).getWritableDatabase().update(fetchTableName(uri), values, selection, selectionArgs);
        return id;
    }

    @Override
    public boolean onCreate() {
        return true;
    }




}
