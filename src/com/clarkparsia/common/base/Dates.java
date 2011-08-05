package com.clarkparsia.common.base;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <p>Utility methods for working with the Date object</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 */
public class Dates {

	/**
	  * Formats the given string as a java.util.Date object.
	  * @param theDate the date string
	  * @return the string as a java.util.Date object
	  */
	 public static Date asDate(String theDate) {
		try {
			return new SimpleDateFormat("MM/dd/yy hh:mm").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("MM/dd/yy").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("MM-dd-yyyy").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("yyyy/MM/dd").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("dd-MMM-yy").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new SimpleDateFormat("MM dd yyyy HH:mm:ss").parse(theDate);
		}
		catch (ParseException pe) {
		}

		try {
			return new Date(Long.parseLong(theDate));
		}
		catch (Exception pe) {
		}

		throw new IllegalArgumentException("Invalid date format supplied: " + theDate);
	}

	 public static String date(Date theDate) {
		 return new SimpleDateFormat("yyyy-MM-dd").format(theDate);
	 }

	 public static String datetime(Date theDate) {
		 return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(theDate);
	 }

}
