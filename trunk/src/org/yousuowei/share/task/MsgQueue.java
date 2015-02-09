package org.yousuowei.share.task;

import java.util.Vector;

/**
 * A message queue implementation
 * 
 * <p>
 * Thread that call get to get message from the queue will be block until there
 * are messages available.
 * 
 * 
 * @author <a href="mailto:ellison.lv@borqs.com">Ellison.lv</a>
 * @version $Id$
 */
public class MsgQueue {

    /**
     * the message cnt
     */
    protected int m_nItemCnt = 0;
    /**
     * the vector that hold the messages
     */
    @SuppressWarnings("rawtypes")
    protected Vector m_list = null;

    /**
     * Method MsgQueue Constructor of message queue, create member variables.
     * 
     * @param name
     *            Name of the message queue
     */
    @SuppressWarnings("rawtypes")
    public MsgQueue() {
	m_nItemCnt = 0;
	m_list = new Vector(100);
    }

    /**
     * Method putTail Put a message into the message queue tail, and notify the
     * waiting thread.
     * 
     * @param obj
     *            the new message
     * 
     * @return boolean
     * 
     */
    @SuppressWarnings("unchecked")
    public synchronized boolean putTail(Object obj) {

	// add the message
	m_list.addElement(obj);

	// increase the item count
	m_nItemCnt++;

	wakeUpThread();

	return true;
    }

    /**
     * Method putHead Put a message into the message queue tail, and notify the
     * waiting thread.
     * 
     * @param obj
     *            the new message
     * 
     * @return boolean
     * 
     */
    @SuppressWarnings("unchecked")
    public synchronized boolean putHead(Object obj) {

	// add the message
	m_list.insertElementAt(obj, 0);

	// increase the item count
	m_nItemCnt++;

	wakeUpThread();

	return true;
    }

    /**
     * Method peek Peek next message in message queue, don't remove it, if
     * there're no message it will not wait.
     * 
     * @return Object next message
     * 
     */
    public synchronized Object peek() {

	// wait for message
	if (m_nItemCnt < 1) {
	    return null;
	}

	// get one message from head
	return m_list.firstElement();
    }

    /**
     * Method get Get next message from message queue, if there're no message it
     * will wait until any message is available.
     * 
     * @return Object next message
     * 
     */
    public synchronized Object get() {

	// wait for message
	while (m_nItemCnt < 1) {
	    try {
		wait();
		// Thread.sleep(200);
	    } catch (Exception e) {
		// #debug
		e.printStackTrace();
		return null;
	    }
	}

	// remove one message from head
	Object obj = m_list.firstElement();
	m_list.removeElementAt(0);

	// decrease item count
	if (m_nItemCnt > 0) {
	    m_nItemCnt--;
	}

	return obj;
    }

    public synchronized Object getNoWait() {

	// wait for message
	if (m_nItemCnt < 1) {
	    return null;
	}

	// remove one message from head
	Object obj = m_list.firstElement();
	m_list.removeElementAt(0);

	// decrease item count
	if (m_nItemCnt > 0) {
	    m_nItemCnt--;
	}

	return obj;
    }

    /**
     * Method hasItem Check if we have message items in the queue
     * 
     * @return boolean
     * 
     */
    public synchronized boolean hasItem() {
	return m_nItemCnt > 0;
    }

    protected void wakeUpThread() {
	// wake up one waiting thread
	try {
	    notify();
	} catch (Exception ex) {
	    // #debug
	    ex.printStackTrace();
	}
    }
}
