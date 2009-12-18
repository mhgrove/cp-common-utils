package com.clarkparsia.utils;

import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.IOException;

/**
 * Title: EnhancedProperties<br>
 * Description: Extends the java.util.Properties stuff to provide typed accessors to get property values as boolean, int, etc.
 * Also provides a way to get a property value as a list, or as a map of values.  And it does variable substitution on
 * property values.<br/><br/>
 * Given the following property file
 * <code>
 * some_boolean_property = true
 * some_integer_property = 42
 * some_property = some_value
 * some_list = one, two, three, four
 * some_other_property = ${some_property}/foo
 * some_map = key_a, key_b, key_c
 * key_a = a
 * key_b = b
 * key_c = c
 * </code>
 * {@link #getPropertyAsBoolean(String) getPropertyAsBoolean("some_boolean_property")} yields the boolean value "true".
 * {@link #getPropertyAsInt(String) getPropertyAsInt("some_integer_property")} yields the integer 42.
 * {@link #getPropertyAsList(String) getPropertyAsList("some_list")} yields a List<String> with the values "one", "two", "three" and "four".
 * {@link #getPropertyAsMap(String) getPropertyAsMap("some_map")} yields a Map<String, String> with the key value pairs:
 * key_a => a, key_b => b, key_c => c.  Lastly, getting the property "some_other_property" yields the value "some_value/foo" via
 * variable substitution.
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Jan 25, 2008 8:40:09 AM
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class EnhancedProperties extends Properties {

	/**
	 * Create a new EnhancedProperties
	 */
	public EnhancedProperties() {
		super();
	}

	/**
	 * Create a new EnhancedProperties
	 * @param theInput the inputstream to load property data from
	 * @throws IOException thrown if there is an error reading properties data
	 */
	public EnhancedProperties(InputStream theInput) throws IOException {
		super();

		load(theInput);
	}

    /**
     * Return the value of the property as a boolean
     * @param theProp the property to retrieve
     * @return the value of the property as a boolean, or false if the property does not exist
     */
    public boolean getPropertyAsBoolean(String theProp) {
        return super.getProperty(theProp) != null && Boolean.valueOf(super.getProperty(theProp));
    }

    /**
     * Returns the value of the given property
     * @param theProp the property to retrieve
     * @return the value of the property, or null if one is not found
     */
    @Override
    public String getProperty(String theProp) {
        String aValue = super.getProperty(theProp);
        if (aValue != null) {
            aValue = replaceVariables(aValue);
        }

        return aValue;
    }

	/**
	 * @inheritDoc
	 */
	@Override
	public Object put(Object theKey, Object theValue) {
		if (theKey != null && theValue != null) {
			return super.put(theKey.toString(), theValue.toString());
		}
		else {
			return null;
		}
	}

    /**
     * Return the value of the property as an int
     * @param theProp the property to retrieve
     * @return the value of the property as an int
     * @throws NumberFormatException thrown if the value is not a valid integer value
     */
    public int getPropertyAsInt(String theProp) throws NumberFormatException {
        return Integer.parseInt(getProperty(theProp));
    }

    /**
     * Returns the value of a property as a list.  The value of the property must be comma separated:
     * <pre>
     * mylist = one, two, three, four
     * </pre>
     * Would yield a list of four elements, "one", "two", "three" and "four".
     * @param theProp the property key
     * @return the value as a list, or null if the key is not in the properties. 
     */
    public List<String> getPropertyAsList(String theProp) {
        String aValue = getProperty(theProp);

        if (aValue == null) {
            return null;
        }

        List<String> aList = new ArrayList<String>();

        String[] aElems = aValue.split(",");
        for (String aElem : aElems) {
            aList.add(aElem.trim());
        }

        return aList;
    }

    /**
     * Returns the value of the property as a map.  The way this works is if you have some properties like this:
     * <pre>
     * map = foo, baz, boz
     * foo = bar
     * baz = biz
     * boz = buzz
     * </pre>
     * Getting the key "map" as a map will yield a map with three keys "foo", "baz", and "boz" with the values "bar",
     * "biz" and "buzz" respectively.  The keys of the map MUST be comma separated.  If a key does not have a corresponding
     * value, it is not added to the result map.
     * @param theProp the property key which has the key values of the map as its value
     * @return the property value as a map.
     */
    public Map<String, String> getPropertyAsMap(String theProp) {
        List<String> aList = getPropertyAsList(theProp);

        Map<String, String> aMap = new HashMap<String, String>();

        for (String aKey : aList) {
            String aValue = getProperty(aKey);

            if (aValue != null) {
                aMap.put(aKey, aValue);
            }
        }

        return aMap;
    }

    /**
     * Given a property value, resolve any variable references in the value
     * @param theValue the value to resolve
     * @return the value, with any valid variable references resolved.
     */
    private String replaceVariables(String theValue) {
        StringBuffer aNewValue = new StringBuffer(theValue);
        int aIndex = 0;
        while (aNewValue.indexOf("${", aIndex) != -1) {

            String aVar = aNewValue.substring(aNewValue.indexOf("${", aIndex) + 2, aNewValue.indexOf("}", aIndex));

            if (super.getProperty(aVar) != null) {
                aNewValue.replace(aNewValue.indexOf("${", aIndex), aNewValue.indexOf("}", aIndex)+1, replaceVariables(super.getProperty(aVar)));
            }

            aIndex = aNewValue.indexOf("}", aIndex);
        }
        return aNewValue.toString();
    }
}

