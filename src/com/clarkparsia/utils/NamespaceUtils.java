package com.clarkparsia.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 *
 * @author Michael Grove
 * @version 1.0
 */
public class NamespaceUtils
{
    private static final Map mNamespaces = new HashMap();

    public static void clear() {
        mNamespaces.clear();
    }

    public static void addNamespace(String theAbbrev, String theURI) {
        if (!theURI.endsWith("#") && !theURI.endsWith("/"))
            theURI += "#";

        mNamespaces.put(theAbbrev, theURI);
    }

    public static void removeNamespace(String theAbbrev) {
        mNamespaces.remove(theAbbrev);
    }

    public static String getLocalName(String theId) {
        int aIndex = theId.lastIndexOf("#");
        if (aIndex == -1)
            aIndex = theId.lastIndexOf("/");

        if (aIndex == -1)
            return theId;

        return theId.substring(aIndex+1);
    }
    
    public static String qname(String theId) {
        int aIndex = theId.lastIndexOf("#");
        if (aIndex == -1)
            aIndex = theId.lastIndexOf("/");

        if (aIndex == -1)
            return theId;

        String aURI = theId.substring(0,aIndex+1);
        String aLocalName = theId.substring(aIndex+1);
        String aAbbrev = abbrevForURI(aURI);

        if (aAbbrev != null)
            return aAbbrev + (aAbbrev.length() > 0 ? ":" : "") + aLocalName;
        else return theId;
    }

    public static String uri(String theQName) {
        String aURI = null;
        String aLocalName = null;

        if (theQName.indexOf(":") != -1) {
            aURI = uriForAbbrev(theQName.substring(0, theQName.indexOf(":")));
            aLocalName = theQName.substring(theQName.indexOf(":") + 1);
        }
        else {
            aURI = uriForAbbrev("");
            aLocalName = theQName;
        }

        if (aURI == null)
            return theQName;
        else return aURI + aLocalName;
    }

    private static String uriForAbbrev(String theAbbrev) {
        Iterator aIter = mNamespaces.keySet().iterator();
        while (aIter.hasNext()) {
            String aKey = (String)aIter.next();
            if (aKey.equals(theAbbrev))
                return (String)mNamespaces.get(aKey);
        }

        return null;
    }

    private static String abbrevForURI(String theURI) {
        Iterator aIter = mNamespaces.keySet().iterator();
        while (aIter.hasNext()) {
            String aKey = (String)aIter.next();
            if (mNamespaces.get(aKey).equals(theURI))
                return aKey;
        }

        return null;
    }
}
