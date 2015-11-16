package squarelist.rohan.com.squarelist;

/**
 * Created by Rohan on 11/15/2015.
 */
public interface ItemActionListener {
    public int updateItem(long itemId, int status, String itemName);
    public int updateItemStatus(long itemId, int status);
    public int deleteItem(long itemId);
    public int addItem();
}
