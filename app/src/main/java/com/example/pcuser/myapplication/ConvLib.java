package com.example.pcuser.myapplication;

/**
 * Created by PCuser on 09/11/2017.
 */

public class ConvLib {
    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("EbookConv");
    }

    // in fb2ToEpubNative() use empty strings "" for cssDir and fontsDir, if not needed
    // Both return 0 on success, negative value on error.
    public static native int fb2ToEpubNative(String fb2FileName, String epubFileName, String cssDir, String fontsDir);
    public static native int mobiToEpubNative(String mobiFileName, String epubFileName);
}
