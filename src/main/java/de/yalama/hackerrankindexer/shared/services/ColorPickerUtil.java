package de.yalama.hackerrankindexer.shared.services;

/**
 * Generating colors by random works but hurts ergonomics
 */
public class ColorPickerUtil {

    private static int counter = 0;

    private static String[] colors = new String[] {"808080", "B22222", "228B22", "FF1493", "000080", "FF0000", "A52A2A",
            "00FFFF", "008080", "0000CD", "FFFF00", "F5F5DC", "DCDCDC", "1E90FF", "DAA520", "4B0082", "7CFC00",
            "FFB6C1", "DDA0DD", "32CD32", "F5DEB3", "800000", "006400", "F5FFFA", "F0FFFF", "800080", "708090",
            "7FFFD4", "000000", "9ACD32"};

    public static String getNextColor() {
        String next = String.format("#%s", colors[counter]);
        counter = (counter+1) % colors.length;
        return next;
    }

}
