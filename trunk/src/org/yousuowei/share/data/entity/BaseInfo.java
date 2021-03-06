/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseEntity.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.entity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:35:54
 * @version: V1.0
 */

package org.yousuowei.share.data.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @ClassName: BaseEntity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:35:54
 */

public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    public int id;

    @Override
    public String toString() {
	StringBuilder str = new StringBuilder();
	str.append(this.getClass().getSimpleName());
	Field[] fields = this.getClass().getDeclaredFields();
	Object obj;
	for (Field f : fields) {
	    f.setAccessible(true);
	    str.append("-");
	    str.append(f.getName());
	    str.append(":");
	    try {
		obj = f.get(this);
		str.append(null == obj ? "null" : obj.toString());
	    } catch (IllegalArgumentException e) {
		str.append("");
	    } catch (IllegalAccessException e) {
		str.append("");
	    }
	}
	return str.toString();
    }
}
