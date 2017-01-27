package alban.crepela.channelmessaging;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class FriendsDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FriendsDB.db";
    public static final String USER_TABLE_NAME = "User";
    public static final String KEY_ID = "userid";
    public static final String KEY_NAME = "username";
    public static final String KEY_IMG = "imageurl";
    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" + KEY_ID + " STRING, " +
            KEY_NAME + " TEXT, " + KEY_IMG + " TEXT);";


    public FriendsDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
