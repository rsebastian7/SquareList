package squarelist.rohan.com.database;

import android.provider.BaseColumns;

/**
 * Created by Rohan on 11/1/2015.
 */
public class ItemContract {

    public static final String TEXT_DATA_TYPE = " TEXT";
    public static final String COMMA_DELIMITER = ", ";

    /**
     * Not meant to actually be instantiated.
     */
    public ItemContract(){

    }

    public static abstract class ItemEntry implements BaseColumns {
        public static final String ITEM_TABLE = "item";
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_STATUS = "item_status";
    }

    public static final int UNCHECKED = 0;
    public static final int CHECKED = 1;

    public static final String CREATE_ITEM_STMT = "create table " + ItemEntry.ITEM_TABLE + " (" +
            ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + COMMA_DELIMITER +
            ItemEntry.ITEM_NAME + TEXT_DATA_TYPE + COMMA_DELIMITER +
            ItemEntry.ITEM_STATUS + " INTEGER) ";

    public static final String DELETE_ITEM_STMT = "delete table if exists " + ItemEntry.ITEM_TABLE;

}
