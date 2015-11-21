package squarelist.rohan.com.squarelist;

/**
 * Created by Rohan on 11/15/2015.
 */
public interface ItemActionListener {
    public int updateItem(long itemId, int status, String itemName);
    public int updateItemStatus(long itemId, int status);
    public void showEditDialog(long itemId);
    public int showDeleteDialog(long itemId);
    public int addItem(String itemName, int itemStatus);
}
