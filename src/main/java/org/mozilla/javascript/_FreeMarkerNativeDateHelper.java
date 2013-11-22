package org.mozilla.javascript;

/**
 * Don't use this; used internally by FreeMarker/ext/rhino, might changes without notice.
 *
 * @author Vakhurin Sergey
 */
public class _FreeMarkerNativeDateHelper {

    public static boolean instanceofNativeDate(Object object) {
        return object instanceof NativeDate;
    }

    public static long nativeDateToMillisUTC(Object object) {
        return (long) ((NativeDate) object).getJSTimeValue();
    }
}
