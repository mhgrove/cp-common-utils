/*
 * Copyright (c) 2005-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.complexible.common.protobuf.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;

/**
 * <p>Utility class for converting between {@link Core protobuf objects} and their Java equivalents, and vice versa.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public final class Cores {

    /**
     * No instances
     */
    private Cores() {
        throw new AssertionError();
    }

    /**
     * Create a new random UID
     *
     * @return a new random UID
     */
    public static Core.UID uid() {
        return uid(UUID.randomUUID());
    }

    /**
     * Create a new UID from the given UUID
     * @param theId	the UUID
     * @return		the UID corresponding to the UUID
     */
    public static Core.UID uid(final UUID theId) {
        return Core.UID.newBuilder()
            .setMostSigBits(theId.getMostSignificantBits())
            .setLeastSigBits(theId.getLeastSignificantBits())
            .build();
    }

    public static Core.StringMap setProperty(final Core.StringMap theMap, final String theKey, final String theValue) {
        Core.StringMapEntry aNewEntry = Core.StringMapEntry
                                         .newBuilder()
                                         .setKey(theKey)
                                         .setValue(theValue)
                                         .build();

        int i = 0;
        for (Core.StringMapEntry aEntry : theMap.getValuesList()) {
            if (aEntry.getKey().equals(theKey)) {
                return theMap.toBuilder()
                             .setValues(i, aNewEntry)
                             .build();
            }

            i++;
        }

        return theMap.toBuilder()
                     .addValues(aNewEntry)
                     .build();
    }

    public static Core.StringMap removeProperty(final Core.StringMap theMap, final String theKey) {
        int i = 0;
        for (Core.StringMapEntry aEntry : theMap.getValuesList()) {
            if (aEntry.getKey().equals(theKey)) {
                return theMap.toBuilder()
                             .removeValues(i)
                             .build();
            }

            i++;
        }

        return theMap;
    }

    public static String getProperty(final Core.StringMap theMap, final String theKey, final String theDefault) {
        for (Core.StringMapEntry aEntry : theMap.getValuesList()) {
            if (aEntry.getKey().equals(theKey)) {
                return aEntry.getValue();
            }
        }

        return theDefault;
    }

    public static boolean getProperty(final Core.StringMap theMap, final String theKey, final boolean theDefault) {
        for (Core.StringMapEntry aEntry : theMap.getValuesList()) {
            if (aEntry.getKey().equals(theKey)) {
                return Boolean.getBoolean(aEntry.getValue());
            }
        }

        return theDefault;
    }

    public static ByteString toByteString(final String theString) {
        return ByteString.copyFromUtf8(theString);
    }

    public static ByteString toByteString(final char[] theCharArray) {
        final ByteBuffer aEncode = Charsets.UTF_8.encode(CharBuffer.wrap(theCharArray));
        byte[] aBytes = new byte[aEncode.limit()];
        aEncode.get(aBytes);

        return ByteString.copyFrom(aBytes);
    }

    public static Core.StringMap toStringMap(final Properties theProps) {
        if (theProps == null) {
            return Core.StringMap.getDefaultInstance();
        }

        final Core.StringMap.Builder aBuilder = Core.StringMap.newBuilder();

        for (Map.Entry<Object, Object> aEntry : theProps.entrySet()) {
            aBuilder.addValues(Core.StringMapEntry.newBuilder()
                                   .setKey(aEntry.getKey().toString())
                                   .setValue(aEntry.getValue().toString())
                                   .build());
        }

        return aBuilder.build();
    }

    public static Core.StringMap toStringMap(final Map<String, String> theProps) {
        final Core.StringMap.Builder aBuilder = Core.StringMap.newBuilder();

        for (Map.Entry<String, String> aEntry : theProps.entrySet()) {
            aBuilder.addValues(Core.StringMapEntry.newBuilder()
                                   .setKey(aEntry.getKey())
                                   .setValue(aEntry.getValue())
                                   .build());
        }

        return aBuilder.build();
    }

    public static Map<String, String> toStringMap(final Core.StringMap theStringMap) {
        Map<String, String> aMap = Maps.newHashMap();
        for (Core.StringMapEntry aEntry : theStringMap.getValuesList()) {
            aMap.put(aEntry.getKey(), aEntry.getValue());
        }

        return aMap;
    }

    public static Core.IntStringMap toIntStringMap(final Map<Integer, String> theMap) {
        final List<Core.IntStringMapEntry> aEntries = Lists.newArrayList();

        for (Map.Entry<Integer, String> aEntry : theMap.entrySet()) {
            aEntries.add(Core.IntStringMapEntry
                             .newBuilder()
                             .setKey(aEntry.getKey())
                             .setValue(aEntry.getValue())
                             .build());
        }

        return Core.IntStringMap.newBuilder().addAllValues(aEntries).build();
    }

    /**
     * Create a UUID from the Protobuf UID
     * @param theId	the id
     * @return		the resulting UUID
     */
    public static UUID uuid(final Core.UID theId) {
        if (theId == null) {
            return null;
        }
        else {
            return new UUID(theId.getMostSigBits(), theId.getLeastSigBits());
        }
    }

    /**
     * Convert a Protobuf byte array into a char array
     * @param theBytes	the protobuf byte array
     * @return			the resulting char array
     */
    public static char[] toCharArray(final ByteString theBytes) {
        if (theBytes == null || theBytes.size() == 0) {
            return new char[0];
        }
        else {
            // afaict from PB docs, these ByteStrings are always UTF8

            final CharBuffer aEncode = Charsets.UTF_8.decode(theBytes.asReadOnlyByteBuffer());
            char[] aChars = new char[aEncode.limit()];
            aEncode.get(aChars);

            return aChars;
        }
    }

    public static Properties toProperties(final Core.StringMap theMap) {
        Properties aProperties = new Properties();

        if (theMap == null) {
            return aProperties;
        }

        for (Core.StringMapEntry aEntry : theMap.getValuesList()) {
            aProperties.put(aEntry.getKey(), aEntry.getValue());
        }

        return aProperties;
    }

    public static Map<Integer, String> toIntStringMap(final Core.IntStringMap theMap) {
        return toIntStringMap(theMap.getValuesList());
    }

    public static Map<Integer, String> toIntStringMap(final Iterable<Core.IntStringMapEntry> theEntries) {
        final Map<Integer, String> aMap = Maps.newHashMap();

        for (Core.IntStringMapEntry aEntry : theEntries) {
            aMap.put(aEntry.getKey(), aEntry.getValue());
        }

        return aMap;
    }

    public static Core.ListOfStrings toStringList(final Collection<String> theStrings) {
        Core.ListOfStrings.Builder aBuilder = Core.ListOfStrings.newBuilder();
        for (String aStr : theStrings) {
            aBuilder.addStrings(aStr);
        }
        return aBuilder.build();
    }
}
