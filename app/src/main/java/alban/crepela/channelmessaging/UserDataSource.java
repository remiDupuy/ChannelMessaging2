package alban.crepela.channelmessaging;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class UserDataSource {
    // Database fields
    private SQLiteDatabase database;
    private FriendsDB dbHelper;
    private String[] allColumns = { FriendsDB.KEY_ID,FriendsDB.KEY_NAME,
            FriendsDB.KEY_IMG };


    public UserDataSource(Context context) {
        dbHelper = new FriendsDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public Friend cursorToFriend(Cursor cursor) {
        Friend friend = new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return friend;
    }

    public Friend createFriend(int userid, String nom, String imgUrl) {
        ContentValues values = new ContentValues();
        values.put(FriendsDB.KEY_NAME, nom);
        values.put(FriendsDB.KEY_IMG, imgUrl);
        values.put(FriendsDB.KEY_ID, userid);
        database.insert(FriendsDB.USER_TABLE_NAME, null,
                values);
        Cursor cursor = database.query(FriendsDB.USER_TABLE_NAME,
                allColumns, FriendsDB.KEY_ID + " = \"" +userid+"\"", null,
                null, null, null);
        cursor.moveToFirst();
        Friend newFriend = cursorToFriend(cursor);
        cursor.close();
        return newFriend;
    }

    public List<Friend> getAllHommes() {
        List<Friend> lesFriends = new ArrayList<Friend>();
        Cursor cursor = database.query(FriendsDB.USER_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Friend unFriend = cursorToFriend(cursor);
            lesFriends.add(unFriend);
            cursor.moveToNext();
        }
// make sure to close the cursor
        cursor.close();
        return lesFriends;
    }
}
