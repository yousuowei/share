package org.yousuowei.share.data.entity;

public class JsonInfo extends BaseInfo {

    private static final long serialVersionUID = 1L;

    private int status;
    private String msg;
    private Object data;

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public Object getData() {
	return data;
    }

    public void setData(Object data) {
	this.data = data;
    }

}
