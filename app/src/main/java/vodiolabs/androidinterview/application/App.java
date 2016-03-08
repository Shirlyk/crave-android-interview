package vodiolabs.androidinterview.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by Shirly on 3/8/16.
 */
public class App extends Application {

    public static final String APP_NAME = "VodioLabsAndroid";

    /**
     * Use a memory cache for saving bitmaps.
     * A memory cache is useful in speeding up access to recently viewed bitmaps,
     * but in most cases it is better to use disk cache and not memory cache.
     * I chose here to use memory cache because fetching images from disk is slower than loading from memory
     * Of course using memory cache has its disadvantages.
     */
    private static LruCache<String, Bitmap> mMemoryCache;

    @Override
    public void onCreate() {
        super.onCreate();

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public static void logE(String text) {
        Log.e(APP_NAME, text);
    }
}
