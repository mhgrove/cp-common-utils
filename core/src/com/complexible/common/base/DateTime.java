// Copyright (c) 2006 - 2010, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// This source code is available under the terms of the Affero General Public License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package com.complexible.common.base;

import java.sql.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;

/**
 * Immutable representation of date time.
 * 
 * @author Evren Sirin
 */
public final class DateTime {
	public static DateTime now() {
		return new DateTime(System.currentTimeMillis());
	}

	private final long dateTime;

	public DateTime(long time) {
		this.dateTime = time;
	}
    
	public long getTime() {
	    return dateTime;
    }
    
	public Date toDate() {
		return new Date(dateTime);
	}

	public GregorianCalendar toCalendar() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(toDate());
		return calendar;
	}

    /**
     * @inheritDoc
     */
	@Override
    public int hashCode() {
	    return (int) (dateTime ^ (dateTime >>> 32));
    }

    /**
     * @inheritDoc
     */
	@Override
    public boolean equals(Object obj) {
	    return (obj instanceof DateTime) && this.dateTime == ((DateTime) obj).dateTime;
    }

	/**
	 * String representation of date time in XML schema format.
	 */
    @Override
	public String toString() {
		return DatatypeConverter.printDateTime(toCalendar());
	}
	
	/**
	 * Creates a DateTime instance from XML schema serialization of xsd:dateTime.
	 */
	public static DateTime valueOf(String str) {
		return new DateTime(DatatypeConverter.parseDateTime(str).getTime().getTime());
	}
}
