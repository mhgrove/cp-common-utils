package com.clarkparsia.common.net;

import java.net.InetAddress;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

import java.text.ParseException;

import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/** <p>
 * Try to determine MAC address of local network card; this is done
 *  using a shell to run ifconfig (linux) or ipconfig (windows). The
 *  output of the processes will be parsed.
 *  </p>
 *
 *  <p>
 *
 *  Adapted from code found in the Java forums: http://forum.java.sun.com/thread.jspa?threadID=245711&start=15&tstart=0
 *
 *  <p>
 *
 *  Current restrictions:
 *
 *  <ul>
 *     <li>Will probably not run in applets
 *     <li>Tested Windows / Linux / Mac OSX only
 *     <li>Tested J2SDK 1.4 only
 *     <li>If a computer has more than one network adapters, only one MAC address will be returned
 *     <li>will not run if user does not have permissions to run ifconfig/ipconfig (e.g. under linux this is typically only permitted for root)
 *  </ul>
 */
public final class NetworkInfo {

	private NetworkInfo() {
	}

	public static String getMacAddress() {
		String mac = "";
        String os = System.getProperty("os.name");

        try {
            if(os.startsWith("Windows")) {
                mac = windowsParseMacAddress(runCommand("ipconfig /all"));
            }
            else if(os.startsWith("Linux")) {
                mac = linuxParseMacAddress(runCommand("ifconfig"));
            }
            else if(os.startsWith("Mac OS X")) {
                mac = osxParseMacAddress(runCommand("ifconfig"));
            }
        }
        catch(Exception ex) {
            return null;
        }

        return mac;
    }


    /*
	 * Linux stuff
	 */
    private static String linuxParseMacAddress(String ipConfigResponse) throws ParseException {
        String aLocalHost = null;
        try {
            aLocalHost = InetAddress.getLocalHost().getHostAddress();
        }
        catch(java.net.UnknownHostException ex) {
            throw new ParseException(ex.getMessage(), 0);
        }

        StringTokenizer aTokenizer = new StringTokenizer(ipConfigResponse, "\n");
        String aLastMacAddress = null;

        while(aTokenizer.hasMoreTokens()) {
            String aLine = aTokenizer.nextToken().trim();
            boolean aContainsLocalHost = aLine.indexOf(aLocalHost) >= 0;

            // see if aLine contains IP address
            if(aContainsLocalHost && aLastMacAddress != null) {
                return aLastMacAddress;
            }

            // see if aLine contains MAC address
            int macAddressPosition = aLine.indexOf("HWaddr");
            if(macAddressPosition <= 0)
                continue;

            String macAddressCandidate = aLine.substring(macAddressPosition + 6).trim();
            if(isMacAddress(macAddressCandidate)) {
                aLastMacAddress = macAddressCandidate;
                continue;
            }
        }

        throw new ParseException("cannot read MAC address for " + aLocalHost + " from [" + ipConfigResponse + "]", 0);
    }


	/*
	 * Windows stuff
	 */
	private static String windowsParseMacAddress(String ipConfigResponse) throws ParseException {
		String aLocalHost = null;
		try {
			aLocalHost = InetAddress.getLocalHost().getHostAddress();
		}
        catch(java.net.UnknownHostException ex) {
			throw new ParseException(ex.getMessage(), 0);
		}

		StringTokenizer aTokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String aLastMacAddress = null;

		while(aTokenizer.hasMoreTokens()) {
			String aLine = aTokenizer.nextToken().trim();

			// see if aLine contains IP address
			if(aLine.endsWith(aLocalHost) && aLastMacAddress != null) {
				return aLastMacAddress;
			}

			// see if aLine contains MAC address
			int aMacAddressPosition = aLine.indexOf(":");
			if(aMacAddressPosition <= 0)
                continue;

			String macAddressCandidate = aLine.substring(aMacAddressPosition + 1).trim();
			if(isMacAddress(macAddressCandidate)) {
				aLastMacAddress = macAddressCandidate;
				continue;
			}
		}

		throw new ParseException("cannot read MAC address from [" + ipConfigResponse + "]", 0);
	}

	/*
	 * Mac OS X Stuff
	 */
	 private static String osxParseMacAddress(String ipConfigResponse) throws ParseException {
		String aLocalHost = null;

		try {
			aLocalHost = InetAddress.getLocalHost().getHostAddress();
		}
        catch(java.net.UnknownHostException ex) {
			throw new ParseException(ex.getMessage(), 0);
		}

		StringTokenizer aTokenizer = new StringTokenizer(ipConfigResponse, "\n");

		while(aTokenizer.hasMoreTokens()) {
			String aLine = aTokenizer.nextToken().trim();
			//boolean containsLocalHost = aLine.indexOf(aLocalHost) >= 0;

            // see if aLine contains MAC address
            int aMacAddressPosition = aLine.indexOf("ether");
            if(aMacAddressPosition != 0)
                continue;

            String aMacAddressCandidate = aLine.substring(aMacAddressPosition + 6).trim();
            if(isMacAddress(aMacAddressCandidate)) {
                return aMacAddressCandidate;
            }
        }

        throw new ParseException("cannot read MAC address for " + aLocalHost + " from [" + ipConfigResponse + "]", 0);
    }

    /*
     * Util code
     */
    private static boolean isMacAddress(String macAddressCandidate) {
        Pattern macPattern = Pattern.compile("[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0 -9a-fA-F]{2}[-:][0-9a-fA-F]{2}");
        Matcher m = macPattern.matcher(macAddressCandidate);
		return m.matches();
	}

	private static String runCommand(String theCommand) throws IOException {
		Process p = Runtime.getRuntime().exec(theCommand);
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

		StringBuffer buffer= new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) break;
			buffer.append((char)c);
		}
		String outputText = buffer.toString();

		stdoutStream.close();

		return outputText;
	}

	/*
	 * Main
	 */
	public static void main(String[] args) {
		try {
			System.err.println("Network info:");

			System.err.println("  Operating System: " + System.getProperty("os.name"));
			System.err.println("  IP/Localhost: " + InetAddress.getLocalHost().getHostAddress());
			System.err.println("  MAC Address: " + getMacAddress());
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
}