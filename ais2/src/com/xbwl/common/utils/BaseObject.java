package com.xbwl.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 重新OBJECT的equals和hashcode方法，并深度克隆
 * @author Administrator
 *
 */
public abstract class BaseObject {

		public String toString() {
	       return ReflectionToStringBuilder.reflectionToString(this,
	              ToStringStyle.MULTI_LINE_STYLE);

	    }


	    /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 * 
		 */

	    public boolean equals(Object obj) {
	       return EqualsBuilder.reflectionEquals(this, obj);

	    }


	    /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 * 
		 */

	    public int hashCode() {
	       return HashCodeBuilder.reflectionHashCode(this);

	    }


	    /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#clone()
		 * 
		 * 利用对象序列化和反序列化巧妙实现通用对象clone
		 * 
		 */

	    public Object clone() {

	       try {

	           ByteArrayOutputStream bout = new ByteArrayOutputStream();

	           ObjectOutputStream out = new ObjectOutputStream(bout);

	           out.writeObject(this);

	           out.close();


	           ByteArrayInputStream bin = new ByteArrayInputStream(bout

	                  .toByteArray());

	           ObjectInputStream in = new ObjectInputStream(bin);

	           Object ret = in.readObject();

	           in.close();


	           return ret;

	       } catch (Exception e) {

	           return null;

	       }

	    }

}
