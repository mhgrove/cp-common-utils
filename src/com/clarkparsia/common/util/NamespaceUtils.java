/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.common.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.net.URISyntaxException;
import java.net.URI;

/**
 * <p>Utility methods for working with namespaces</p>
 *
 * @author Michael Grove
 * @since 0.1
 * @version 1.1
 */
public class NamespaceUtils {
    private static final Map<String, String> mNamespaces = new HashMap<String, String>();
//
//	static {
//		addNamespace("xsd", "http://www.w3.org/2001/XMLSchema#");
//		addNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
//		addNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
//	}

    public static void clear() {
        mNamespaces.clear();
    }

	public static Collection<String> prefixes() {
		return mNamespaces.keySet();
	}

	public static String namespace(String thePrefix) {
		return mNamespaces.get(thePrefix);
	}

    public static void addNamespace(String theAbbrev, String theURI) {
		if (theAbbrev == null || theURI == null) {
			return;
		}

        if (!theURI.endsWith("#") && !theURI.endsWith("/")) {
            theURI += "#";
		}

        mNamespaces.put(theAbbrev, theURI);
    }

    public static void removeNamespace(String theAbbrev) {
        mNamespaces.remove(theAbbrev);
    }

    public static String getLocalName(String theId) {
		if (theId == null) {
			return null;
		}

        int aIndex = theId.lastIndexOf("#");

        if (aIndex == -1) {
            aIndex = theId.lastIndexOf("/");
		}

        if (aIndex == -1) {
            return theId;
		}

        return theId.substring(aIndex+1);
    }

    public static String getPrefix(String theQName) {
		if (theQName == null) {
			return null;
		}

		if (theQName.indexOf(":") != -1) {
        	return theQName.substring(0, theQName.indexOf(":"));
		}
		else {
			return null;
		}
    }
    
    public static String qname(String theId) {
		if (theId == null) {
			return null;
		}

        int aIndex = theId.lastIndexOf("#");

        if (aIndex == -1) {
            aIndex = theId.lastIndexOf("/");
		}

        if (aIndex == -1) {
            return theId;
		}

        String aURI = theId.substring(0, aIndex+1);
        String aLocalName = theId.substring(aIndex+1);
        String aAbbrev = abbrevForURI(aURI);

        if (aAbbrev != null) {
            return aAbbrev + (aAbbrev.length() > 0 ? ":" : "") + aLocalName;
		}
        else {
			return theId;
		}
    }

    public static String uri(String theQName) {
		if (theQName == null) {
			return null;
		}

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

        if (aURI == null) {
            return theQName;
        }
        else {
            return aURI + aLocalName;
        }
    }

    private static String uriForAbbrev(String theAbbrev) {
        for (String aKey : mNamespaces.keySet()) {
            if (aKey.equals(theAbbrev)) {
                return mNamespaces.get(aKey);
            }
        }

        return null;
    }

    private static String abbrevForURI(String theURI) {
        for (String aKey : mNamespaces.keySet()) {
            if (mNamespaces.get(aKey).equals(theURI)) {
                return aKey;
            }
        }

        return null;
    }
}
