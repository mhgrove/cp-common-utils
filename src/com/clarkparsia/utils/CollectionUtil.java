package com.clarkparsia.utils;

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Title: <br/>
 * Description: <br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Apr 21, 2009 10:15:38 AM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class CollectionUtil {

    public static <T> Set<T> set(Iterator<T> theIter) {
        Set<T> aSet = new LinkedHashSet<T>();

        while (theIter.hasNext())
            aSet.add(theIter.next());

        return aSet;
    }


    public static <T> List<T> list(Iterator<T> theIter) {
        List<T> aSet = new ArrayList<T>();

        while (theIter.hasNext())
            aSet.add(theIter.next());

        return aSet;
    }

    /**
     * Checks to see if any elements of one set are present in another.
     * First parameter is the list of elements to search for, the second
     * parameter is the list to search in.  Basically tests to see if the two sets intersect
     * @param theList Set the elements to look for
     * @param toSearch Set the search set
     * @return boolean true if any element in the first set is present in the second
     */
    public static <T> boolean containsAny(Set<T> theList, Set<T> toSearch) {

        if (toSearch.isEmpty()) {
            return false;
        }
        else
        {
            for (T aObj : theList) {
                if (toSearch.contains(aObj)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static <T> boolean containsAny(List<T> theList, List<T> theSearch) {
        return containsAny(new HashSet<T>(theList), new HashSet<T>(theSearch));
    }
}
