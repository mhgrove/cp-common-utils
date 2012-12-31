/*
 * Copyright (c) 2005-2011 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.common.net;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;

/**
 * <p>Network, URI/URL, and IP related utility functions</p>
 *
 * @author Michael Grove
 * @author Blazej Bulka
 * @since 1.0
 * @version 2.0
 */
public final class NetUtils {

	private NetUtils() {
	}

	/**
     * Quotes special characters in path fragment of a URL.  This is different from URL encoding, of for example form submission results.  This will replace
	 * characters in the URI, such as space (' ') with '+' rather than "%2F" as you would get from URL encoding.
     *
     * @param unquotedPath the unquoted path
     * @return quoted path
     */
    public static String quoteURIPath(String unquotedPath) {
        // NOTE: do NOT URLEncoder here because its intended purpose is encoding HTML forms. In particular
        // it will encode all slashes '/' with %2F, which is not intended

        // the code below assumes that the unquoted path is an absolute path, then converts it into a file: URI.
        // next, it uses Java's toURI() method which properly quotes characters in the path, then converts it into a string
        // and strips file: prefix
        String result = new File(unquotedPath).toURI().toString().substring("file:".length());

        // preserve trailing slashes
        if (unquotedPath.endsWith("/")) {
            return result + "/";
        }
        else {
            return result;
        }
    }

	/**
	 * Return whether or not the string is a valid URL
	 * @param theURL the string to test
	 * @return true if the string is a valid URL, false otherwise
	 */
	public static boolean isURL(String theURL) {
		try {
			new URL(theURL);
			return true;
		}
		catch (MalformedURLException e) {
			return false;
		}
	}

	/**
	 * Return whether or not the string is a valid URI
	 * @param theURI the string to test
	 * @return true if it is a valid URI, false otherwise
	 */
	public static boolean isURI(String theURI) {
		try {
			new URI(theURI);
			return true;
		}
		catch (URISyntaxException e) {
			return false;
		}
	}

    /**
     * Lookup the IP for a domain name
     * @param theDN the domain name
     * @return the IP
     * @throws java.net.UnknownHostException if a machine with the specified domain name does not exist
     */
    public static InetAddress lookupIP(String theDN) throws UnknownHostException {
        return InetAddress.getByName(theDN);
    }

    /**
     * Attempt a reverse look-up with an IP to get a domain name
     * @param theIP the IP to lookup
     * @return the domain name corresponding to the IP
     * @throws UnknownHostException if a machine with the specified IP does not exist, or a domain name cannot be found
     */
    public static String reverseLookup(String theIP) throws UnknownHostException {
        String aName = InetAddress.getByName(theIP).getCanonicalHostName();

        if (aName.equals(theIP)) {
            throw new UnknownHostException("Cannot find DN for IP: " + theIP);
        }
        else {
            if (isIP(aName)) {
                throw new UnknownHostException("Invalid DN (" + aName + ") obtained via reverse lookup of: " + theIP);
            }

            return aName;
        }
    }

    ///////////////////////////////////////////////////////////
    //////////// IP and net related Utils
    //////////// much of this is gleaned from answers in the java forms
    //////////// and from this helpful page:
    //////////// http://www.cisco.com/web/about/ac123/ac147/archived_issues/ipj_9-1/ip_addresses.html
    ///////////////////////////////////////////////////////////

	/**
	 * Return the masked IP of the given network IP
	 * @param theNetIP the network IP
	 * @return the masked IP
	 */
    public static byte[] mask(String theNetIP) {
        if (theNetIP.indexOf("/") == -1) {
            throw new IllegalArgumentException("Invalid Net IP");
        }

        // prefix length of the mask, i think this will be somewhere between 8 and 32
        // based on what I'm reading
        int aPrefixLength = 32 - Integer.parseInt(theNetIP.substring(theNetIP.indexOf("/") + 1));

        // figure out the mask based on the prefix length
        int aMask = (aPrefixLength == 32) ? 0 : 0xFFFFFFFF - ((1 << aPrefixLength) - 1);

        // get our byte array which will correspond to the subnet mask, this can be passed to an InetAddress
        return new byte[] {
                (byte) (aMask >> 24 & 0xFF),
                (byte) (aMask >> 16 & 0xFF),
                (byte) (aMask >>  8 & 0xFF),
                (byte) (aMask >>  0 & 0xFF)
        };
    }

	/**
	 * Return whether or not the string represents a possible value IP.  This will return true if the IP is syntactically
	 * valid, it does not determine whether or not it's the IP of anything actually addressable.
	 * @param theString the possible IP
	 * @return true if its a syntactically valid IP, false otherwise.
	 */
    public static boolean isIP(String theString) {
        return theString.matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
    }

	/**
	 * Return true if the provided IP is on the given network using some IP math
	 * @param theNetIP the Network's IP
	 * @param theIP the IP to check
	 * @return true of theIP is on the specified network
	 * @throws UnknownHostException thrown if there is an issue getting the IP
	 */
    public static boolean contains(String theNetIP, String theIP) throws UnknownHostException {
        byte[] ip = InetAddress.getByName(theIP).getAddress();

        byte[] mask = mask(theNetIP);

        byte[] masked = new byte[]
            {
                (byte) (mask[0] & ip[0]),
                (byte) (mask[1] & ip[1]),
                (byte) (mask[2] & ip[2]),
                (byte) (mask[3] & ip[3])
            };

        return InetAddress.getByName(theNetIP.substring(0, theNetIP.indexOf("/"))).equals(InetAddress.getByAddress(masked));
    }

	/**
	 * Enumeration representing the class of an IP
	 */
	public enum IPClass {
		A, B, C, D, E
	}

	/**
	 * Given an IP, return the IP class it fits into
	 * @param theAddress the IP
	 * @return the IP class for the given IP
	 */
    public static IPClass getIpClass(InetAddress theAddress) {
        byte b = theAddress.getAddress()[0];

        if (b > 0 && b < 127) {
            return IPClass.A;
        }
        else if (b > 127 && b <= 191) {
            return IPClass.B;
        }
        else if (b >= 192 && b <= 223) {
            return IPClass.C;
        }
        else if (b >= 224 && b <= 239) {
            return IPClass.D;
        }
        else {
            return IPClass.E;
        }
    }

	/**
	 * Return the subnet mask for the IP
	 * @param theAddress the IP
	 * @return the subnet mask as an array of 4 bytes for each part of the mask
	 */
    public static byte[] getSubnetMask(InetAddress theAddress) {
        IPClass aIPClass = getIpClass(theAddress);

        switch (aIPClass) {
            case A:
                return new byte[] { (byte) 255, 0, 0, 0 };
            case B:
                return new byte[] { (byte) 255, (byte) 255, 0, 0 };
            case C:
                return new byte[] { (byte) 255, (byte) 255, (byte) 255, 0 };
            default:
                throw new IllegalArgumentException("Class D & E IP's not supported!");
        }
    }
}
