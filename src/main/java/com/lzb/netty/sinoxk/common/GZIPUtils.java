package com.lzb.netty.sinoxk.common;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtils {
    public static int BUFFER = 1024;

    public static byte[] compress(byte[] data) throws IOException {
        try (ByteArrayInputStream is = new ByteArrayInputStream(data);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            compress(is, os);
            byte[] output = os.toByteArray();
            os.flush();
            return output;
        }
    }

    private static void compress(InputStream is, OutputStream os)
            throws IOException {
        try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = is.read(data, 0, BUFFER)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            gos.flush();
        }
    }

    public static byte[] decompress(byte[] data) throws Exception {
        try (ByteArrayInputStream input = new ByteArrayInputStream(data);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            decompress(input, out);
            data = out.toByteArray();
            out.flush();
            return data;
        }
    }

    private static void decompress(InputStream is, OutputStream os)
            throws Exception {
        try (GZIPInputStream gis = new GZIPInputStream(is)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, count);
            }
        }
    }
}
