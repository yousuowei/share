package org.yousuowei.share.task;

import android.os.Process;
import android.util.Log;

/**
 * 
 * @author ellison
 */
public class TaskEngine implements Runnable {
    private static final String TAG = "TaskEngine";

    public static final int DEFAULT_TASKGROUPID = -99;

    /**
     * 
     * priority is valid when calling putTaskTail. highest: ui other high: ui
     * sync one comp normal: sync all low: push lowest: auto upgrade.
     */
    static final int PRIORITY_SMALLEST = 0;
    public static final int PRIORITY_REALTIME = 0;
    public static final int PRIORITY_HIGHEST = 1;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_NORMAL = 3;
    public static final int PRIORITY_LOW = 4;
    public static final int PRIORITY_LOWEST = 5;
    static final int PRIORITY_CNT = 6;
    PriorityMsgQueue m_qTask = new PriorityMsgQueue();

    TaskItem m_curTask = null;

    /**
     * use to identify a task group, so we can cancel all tasks in the same
     * group
     */
    private int m_nTaskGroupId = 0;
    private int m_nTaskId = 0;
    private static byte[] locker = new byte[0];
    private static TaskEngine m_instance = null;

    TaskItem m_tiEndThreadFlag = new TaskItem();

    private TaskEngine() {
	Thread t = new Thread(this);
	// t.setPriority(Thread.MIN_PRIORITY);
	t.start();
    }

    public static TaskEngine getInstance() {
	synchronized (locker) {
	    if (null == m_instance) {
		m_instance = new TaskEngine();
	    }
	}

	return m_instance;
    }

    public static void clearInstance() {
	synchronized (locker) {
	    if (null != m_instance) {
		m_instance.endThread();
	    }
	    m_instance = null;
	}
    }

    private void endThread() {
	m_tiEndThreadFlag.m_nGroupId = -1;
	m_tiEndThreadFlag.m_nTaskId = -1;
	m_tiEndThreadFlag.priority = PRIORITY_REALTIME;
	m_tiEndThreadFlag.m_oTask = null;

	// System.out.println("[TaskEngine.addTaskTail] 1!");
	m_qTask.putTail(m_tiEndThreadFlag, PRIORITY_REALTIME);
    }

    public void run() {
	Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

	Log.d(TAG, "TaskEngine thread started...");
	while (true) {
	    try {
		setCurTask(null);

		// System.out.println("[TaskEngine.run] Task get. free: "+Runtime.getRuntime().freeMemory()+", total: "+Runtime.getRuntime().totalMemory());
		TaskItem ti = (TaskItem) m_qTask.get();

		if (m_tiEndThreadFlag == ti) {
		    break;
		}
		// System.out.println("[TaskEngine.run] Task got! ti: "+ti);
		if (null != ti && null != ti.m_oTask) {

		    setCurTask(ti);

		    if (ti.m_oTask.isCanceled()) {
			continue;
		    }

		    // execute task
		    try {
			ti.m_oTask.execute();
		    } catch (Throwable ex) {
			Log.e(TAG, "1", ex);
		    }

		    if (ti.m_oTask.isCanceled()) {
			continue;
		    }

		    if (ti.m_oTask.isCanceled()) {
			continue;
		    }

		    try {
			ti.m_oTask.callbackFinish();
		    } catch (Throwable t) {

			Log.e(TAG, "callBackFinish ", t);
		    }
		    ti = null;
		    setCurTask(null);
		}
		// Thread.yield();
		// Thread.sleep(500);
	    } catch (Exception ex) {
		Log.e(TAG, ex.toString());
	    }
	}

	Log.d(TAG, "TaskEngine thread ended...");
    }

    private synchronized void setCurTask(TaskItem ti) {
	m_curTask = ti;
    }

    protected synchronized void checkCancelCurTask(int grpId) {
	if (null != m_curTask) {
	    if (m_curTask.m_nGroupId == grpId) {
		if (null != m_curTask.m_oTask) {
		    m_curTask.m_oTask.cancel();
		}
	    }
	}
    }

    public int addTaskTail(AbstractTask tsk) {
	return addTaskTail(tsk, TaskEngine.PRIORITY_REALTIME);
    }

    public int addTaskTail(AbstractTask tsk, int priority) {
	int grpId = allocGroupId();
	if (!addTaskTail(tsk, grpId, priority)) {
	    return -1;
	}
	return grpId;
    }

    public synchronized boolean addTaskTail(AbstractTask tsk, int grpId,
	    int priority) {

	if (priority < PRIORITY_SMALLEST || priority >= PRIORITY_CNT) {

	    Log.e(TAG, "Invalid priority: " + priority);
	    return false;
	}

	TaskItem ti = new TaskItem();
	ti.m_nGroupId = grpId;
	ti.m_nTaskId = m_nTaskId++;
	ti.priority = priority;
	ti.m_oTask = tsk;
	if (null != tsk) {
	    tsk.setTaskGroupId(grpId);
	    tsk.setPriority(priority);
	}
	// System.out.println("[TaskEngine.addTaskTail] 1!");
	m_qTask.putTail(ti, priority);
	ti = null;
	// System.out.println("[TaskEngine.addTaskTail] 2!");
	return true;
    }

    /*
     * public synchronized boolean addTaskHead(TaskIfc tsk, int grpId) {
     * TaskItem ti = new TaskItem(); ti.m_nGroupId = grpId; ti.m_nTaskId =
     * m_nTaskId++; ti.priority = PRIORITY_HIGHEST; ti.m_oTask = tsk;
     * m_qTask.putHead(ti); return true; }
     */

    public synchronized void cancelTasks(int grpId) {
	m_qTask.cancelTasks(grpId);
	checkCancelCurTask(grpId);
    }

    public synchronized void cancelAllTasks() {
	m_qTask.cancelAllTasks();
	if (null != m_curTask) {
	    if (null != m_curTask.m_oTask) {
		m_curTask.m_oTask.cancel();
	    }
	}
    }

    public synchronized int allocGroupId() {
	m_nTaskGroupId++;

	return m_nTaskGroupId;
    }
}

class TaskItem {

    int m_nGroupId = -1; // task group id
    int m_nTaskId = 0;
    int priority; // task priority
    AbstractTask m_oTask = null; // the task

    public String toString() {
	return "[" + m_nGroupId + "," + m_nTaskId + "," + priority + ","
		+ m_oTask + "]";
    }
}

class PriorityMsgQueue extends MsgQueue {
    private static final String TAG = "PriorityMsgQueue";

    int[] m_vPCnt = new int[TaskEngine.PRIORITY_CNT];

    public PriorityMsgQueue() {
	super();
    }

    private int getPIndex(int priority) {
	int ret = 0;
	for (int i = 0; i <= priority; i++) {
	    ret += m_vPCnt[i];
	}
	return ret;
    }

    @SuppressWarnings("unchecked")
    public synchronized boolean putTail(TaskItem obj, int priority) {

	if (null == obj) {

	    Log.e(TAG, "obj is null");
	    return false;
	}

	int i = getPIndex(priority);

	// add the message
	m_list.insertElementAt(obj, i);

	m_vPCnt[priority]++;

	// increase the item count
	m_nItemCnt++;

	wakeUpThread();

	return true;
    }

    public synchronized void cancelAllTasks() {
	int i = -1;
	// if there's few items, or binaty search failed, iterate throw it.
	for (i = m_nItemCnt - 1; i >= 0; i--) {
	    TaskItem ti = (TaskItem) m_list.elementAt(i);
	    if (null == ti) {
		m_list.removeElementAt(i);
		m_nItemCnt--;
		continue;
	    }

	    m_list.removeElementAt(i);
	    if (m_vPCnt[ti.priority] > 0) {
		m_vPCnt[ti.priority]--;
	    }
	    m_nItemCnt--;
	}
    }

    public synchronized void cancelTasks(int grpId) {
	int i = -1;
	// if there's few items, or binaty search failed, iterate throw it.
	for (i = m_nItemCnt - 1; i >= 0; i--) {
	    TaskItem ti = (TaskItem) m_list.elementAt(i);
	    if (null == ti) {
		m_list.removeElementAt(i);
		m_nItemCnt--;
		continue;
	    }

	    if (ti.m_nGroupId == grpId) {
		m_list.removeElementAt(i);
		if (m_vPCnt[ti.priority] > 0) {
		    m_vPCnt[ti.priority]--;
		}
		m_nItemCnt--;
	    }
	}
    }

    public synchronized Object get() {
	TaskItem ti = (TaskItem) super.get();
	if (null != ti) {
	    if (m_vPCnt[ti.priority] > 0) {
		m_vPCnt[ti.priority]--;
	    }
	}

	return ti;
    }

}
