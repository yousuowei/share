package org.yousuowei.share.task;

/**
 * 
 * @author ellison
 * 
 */
public interface TaskCallbackIfc {
    public abstract void call(final AbstractTask task);

    // public abstract void fileDownComplete(int fileType, int id, String
    // filename);
    //
    // public abstract void fileDownProgress(int fileType, int id, int
    // progress);
}
