package com.clarkparsia.utils;

import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Title: <br>
 * Description: <br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Jan 25, 2008 8:40:09 AM
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class EnhancedProperties extends Properties {

    public boolean getBooleanProperty(String theProp) {
        return super.getProperty(theProp) != null && Boolean.valueOf(super.getProperty(theProp));
    }

    public int getIntegerProperty(String theProp) {
        try {
            return Integer.valueOf(super.getProperty(theProp));
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public String getProperty(String theProp) {
        String aValue = super.getProperty(theProp);
        if (aValue != null) {
            aValue = replaceVariables(aValue);
        }

        return aValue;
    }

    public List<String> getPropertyAsList(String theProp) {
        String aValue = getProperty(theProp);

        List<String> aList = new ArrayList<String>();

        String[] aElems = aValue.split(",");
        for (String aElem : aElems) {
            aList.add(aElem.trim());
        }

        return aList;
    }

    public Map<String, String> getPropertyAsMap(String theProp) {
        List<String> aList = getPropertyAsList(theProp);

        Map<String, String> aMap = new HashMap<String, String>();

        for (String aKey : aList) {
            String aValue = getProperty(aKey);

            aMap.put(aKey, aValue);
        }

        return aMap;
    }

    private String replaceVariables(String theValue) {
        String aNewValue = theValue;
        while (aNewValue.indexOf("${") != -1) {
            String aVar = aNewValue.substring(aNewValue.indexOf("${") + 2, aNewValue.indexOf("}"));

            if (super.getProperty(aVar) != null) {
                aNewValue = aNewValue.substring(0, aNewValue.indexOf("${")) + replaceVariables(super.getProperty(aVar)) +
                            aNewValue.substring(aNewValue.indexOf("${") + 3 + aVar.length());
            }
        }

        return aNewValue;
    }
}

