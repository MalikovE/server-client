/**
 * Created by m.lapaev on 10.07.19.
 */
public class TPPUtilities {
    public static String bytesToStringRaw(byte[] array) {
        return bytesToStringRaw(array, null == array ? 0 : array.length);
    }

    public static String bytesToStringRaw(byte[] array, int count) {
        return bytesToStringRaw(array, 0, count);
    }

    public static String bytesToStringRaw(byte[] array, int offset, int count) {
        if ((null == array) || (offset == count)) {
            return "";
        }

        byte[] buf = new byte[(count) * 2];
        for (int i = offset; i < array.length && i < offset + count; i++) {
            halfByteToChar(buf, 2 * (i - offset), (array[i] & 0xf0) >> 4);
            halfByteToChar(buf, 2 * (i - offset) + 1, array[i] & 0xf);
        }

        return new String(buf);
    }

    public static void halfByteToChar(byte[] buf, int index, int num) {
        buf[index] = num < 0x0A ? (byte) ('0' + num) : (byte) ('A' + num - 0x0A);
    }

    public static long byteArrayToBigEndianLong(byte[] data, int base) {
        return ((long) (data[base] & 0xFF)) << 56
                | ((long) (data[base + 1] & 0xFF)) << 48
                | ((long) (data[base + 2] & 0xFF)) << 40
                | ((long) (data[base + 3] & 0xFF)) << 32
                | ((long) (data[base + 4] & 0xFF)) << 24
                | ((long) (data[base + 5] & 0xFF)) << 16
                | ((long) (data[base + 6] & 0xFF)) << 8
                | ((long) (data[base + 7] & 0xFF));
    }

    public static long byteArrayToLittleEndianLong(byte[] data, int base) {
        return ((long) (data[base] & 0xFF)
                | (long) (data[base + 1] & 0xFF) << 8
                | (long) (data[base + 2] & 0xFF) << 16
                | (long) (data[base + 3] & 0xFF) << 24
                | (long) (data[base + 4] & 0xFF) << 32
                | (long) (data[base + 5] & 0xFF) << 40
                | (long) (data[base + 6] & 0xFF) << 48
                | (long) (data[base + 7] & 0xFF) << 56);
    }

    public static short byteArrayToLittleEndianShort(byte[] data, int base) {
        return (short) ((data[base] & 0xFF) | (data[base + 1] & 0xff) << 8);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
