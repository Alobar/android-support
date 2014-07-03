/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Alobar Productions
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.alobarproductions.support;

import android.annotation.TargetApi;
import android.database.ContentObservable;
import android.os.Build;

/**
 * <p>Helper class for accessing features in {@link android.database.ContentObservable} introduced in
 * Android API level 16 in a backwards compatible fashion.</p>
 * <p>API level 16 deprecates methods:
 * <ul>
 * <li><code>dispatchChange(boolean selfChange)</code></li>
 * <li><code>notifyChange(boolean selfChange)</code>,</li>
 * </ul>
 * and introduces:
 * <ul><li><code>dispatchChange(boolean selfChange, Uri uri)</code>.</li></ul>
 * This leaves you you with nothing to call if you need to support lower API levels.
 * Class ObservableContentCompat provides the necessary compatibility functionality</p>
 */
public final class ContentObservableCompat {

    private static final ContentObservableImpl IMPL;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            IMPL = new ContentObservableJellyBean();
        else
            IMPL = new ContentObservableBase();
    }

    /** Do not instantiate; only static methods are provided. */
    private ContentObservableCompat() {}

    /**
     * Invokes {@link android.database.ContentObserver#dispatchChange(boolean)} on each observer.
     * <p>
     * If <code>selfChange</code> is true, only delivers the notification
     * to the observer if it has indicated that it wants to receive self-change
     * notifications by implementing {@link android.database.ContentObserver#deliverSelfNotifications}
     * to return true.
     * </p>
     *
     * @param selfChange True if this is a self-change notification.
     */
    public static void dispatchChange(ContentObservable observable, boolean selfChange) {
        IMPL.dispatchChange(observable, selfChange);
    }

    private interface ContentObservableImpl {
        public void dispatchChange(ContentObservable observable, boolean selfChange);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static class ContentObservableJellyBean implements ContentObservableImpl {
        @Override
        public void dispatchChange(ContentObservable observable, boolean selfChange) {
            observable.dispatchChange(selfChange, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.BASE)
    private static class ContentObservableBase implements ContentObservableImpl {
        @Override
        public void dispatchChange(ContentObservable observable, boolean selfChange) {
            observable.dispatchChange(selfChange);
        }
    }
}
