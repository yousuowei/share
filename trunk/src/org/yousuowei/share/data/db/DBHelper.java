package org.yousuowei.share.data.db;
//package org.om.bracelet.data.db;
//
//import org.om.bracelet.data.entity.TaskInfo;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DBHelper extends SQLiteOpenHelper {
//
//    public final static String DB_NAME = "om_wear.db";
//    public final static int DB_VERSION = 2;
//
//    public DBHelper(Context context) {
//	super(context, DB_NAME, null, DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//	createTables(db);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
//	dropTables(db);
//	createTables(db);
//    }
//
//    private void dropTables(SQLiteDatabase db) {
//	db.execSQL(TaskInfo.getDropTbStr());
//    }
//
//    private void createTables(SQLiteDatabase db) {
//	db.execSQL(TaskInfo.getCreateTbStr());
//    }
// }
