package org.yousuowei.share.data.db;
//package org.om.bracelet.data.db;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.om.bracelet.common.CommonLog;
//import org.om.bracelet.data.entity.TaskInfo;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//public class Dao {
//    private final static String TAG = Dao.class.getName();
//
//    private DBHelper helper;
//
//    public Dao(Context ctx) {
//	helper = new DBHelper(ctx);
//    }
//
//    public boolean addOrUpdateTask(TaskInfo taskInfo) {
//	boolean result = true;
//	SQLiteDatabase dataBase = helper.getWritableDatabase();
//	if (null != dataBase) {
//	    try {
//		if (0 == taskInfo.id) {// 新增
//		    dataBase.insert(TaskInfo.getTbName(), null,
//			    taskInfo.toInsertValues());
//		} else {// 修改
//		    dataBase.update(TaskInfo.getTbName(),
//			    taskInfo.toInsertValues(), "id=?",
//			    new String[] { String.valueOf(taskInfo.id) });
//		}
//	    } catch (Exception e) {
//		CommonLog.e(TAG, e);
//		result = false;
//	    } finally {
//		if (null != dataBase) {
//		    dataBase.close();
//		}
//	    }
//	}
//	return result;
//    }
//
//    public boolean addTaskList(List<TaskInfo> taskList) {
//	boolean result = true;
//	SQLiteDatabase dataBase = helper.getWritableDatabase();
//	if (null != dataBase) {
//	    dataBase.beginTransaction(); // 手动设置开始事务
//	    dataBase.execSQL(TaskInfo.getDeleteDataStr());
//	    try {
//		for (TaskInfo info : taskList) {
//		    dataBase.insert(TaskInfo.getTbName(), null,
//			    info.toInsertValues());
//		}
//		dataBase.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
//	    } catch (Exception e) {
//		CommonLog.e(TAG, e);
//		result = false;
//	    } finally {
//		dataBase.endTransaction(); // 处理完成
//		if (null != dataBase) {
//		    dataBase.close();
//		}
//	    }
//	}
//	return result;
//    }
//
//    public boolean delTaskById(int id) {
//	boolean result = true;
//	SQLiteDatabase dataBase = helper.getWritableDatabase();
//	if (null != dataBase) {
//	    try {
//		dataBase.delete(TaskInfo.getTbName(), "id=?",
//			new String[] { String.valueOf(id) });
//	    } catch (Exception e) {
//		CommonLog.e(TAG, e);
//		result = false;
//	    } finally {
//		if (null != dataBase) {
//		    dataBase.close();
//		}
//	    }
//	}
//	return result;
//    }
//
//    public List<TaskInfo> getTaskList() {
//	SQLiteDatabase dataBase = helper.getReadableDatabase();
//	List<TaskInfo> list = new ArrayList<TaskInfo>();
//	String[] params = null;
//	StringBuilder sql = new StringBuilder("SELECT * FROM ");
//	sql.append(TaskInfo.getTbName());
//	sql.append(" c");
//
//	try {
//	    Cursor cursor = dataBase.rawQuery(sql.toString(), params);
//
//	    TaskInfo task;
//	    while (cursor.moveToNext()) {
//		task = fillTask(cursor);
//		list.add(task);
//	    }
//	    cursor.close();
//	} catch (Exception e) {
//	    CommonLog.e(TAG, e);
//	} finally {
//	    if (null != dataBase) {
//		dataBase.close();
//	    }
//	}
//	return list;
//    }
//
//    public TaskInfo getTaskById(String taskId) {
//	SQLiteDatabase dataBase = helper.getReadableDatabase();
//	TaskInfo task = null;
//	try {
//	    Cursor cursor = dataBase.query(TaskInfo.getTbName(), null,
//		    "taskId=?", new String[] { taskId }, null, null, null);
//
//	    while (cursor.moveToNext()) {
//		task = fillTask(cursor);
//	    }
//	    cursor.close();
//	} catch (Exception e) {
//	    CommonLog.e(TAG, e);
//	} finally {
//	    if (null != dataBase) {
//		dataBase.close();
//	    }
//	}
//	return task;
//    }
//
//    private TaskInfo fillTask(Cursor cursor) {
//	TaskInfo task = new TaskInfo();
//	task.id = cursor.getInt(cursor.getColumnIndex("id"));
//	task.type = cursor.getInt(cursor.getColumnIndex("type"));
//	task.wearImei = cursor.getString(cursor.getColumnIndex("wearImei"));
//	task.name = cursor.getString(cursor.getColumnIndex("name"));
//	task.taskId = cursor.getString(cursor.getColumnIndex("taskId"));
//	task.time = cursor.getString(cursor.getColumnIndex("time"));
//	task.voiceFileId = cursor.getString(cursor
//		.getColumnIndex("voiceFileId"));
//	task.weekinfo = cursor.getString(cursor.getColumnIndex("weekinfo"));
//	task.isActive = cursor.getInt(cursor.getColumnIndex("isActive"));
//	task.setVoiceFilePath(cursor.getString(cursor
//		.getColumnIndex("voiceFilePath")));
//	return task;
//    }
//
//}
