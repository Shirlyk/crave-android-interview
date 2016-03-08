package vodiolabs.androidinterview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import vodiolabs.androidinterview.application.App;

/**
 * Created by Shirly on 3/8/16.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> mImageView;
    private ProgressBar mProgressBar;

    public ImageLoader(ImageView imageView) {
        mImageView = new WeakReference<>(imageView);
    }

    public ImageLoader(ImageView imageView, ProgressBar progressBar) {
        mImageView = new WeakReference<>(imageView);
        mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            String urlString = params[0];

            // first check memory cache
            Bitmap bitmapFromMemCache = App.getBitmapFromMemCache(urlString);
            if (bitmapFromMemCache != null)
                return bitmapFromMemCache;

            // if no memory cache download the image
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() != HttpStatus.SC_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap result = BitmapFactory.decodeStream(inputStream);
                App.addBitmapToMemoryCache(urlString, result);
                return result;
            }

        } catch (Exception e) {
            App.logE("Image loading exception: " + e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mImageView != null) {
            ImageView imageView = mImageView.get();
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }

            if (mProgressBar != null)
                mProgressBar.setVisibility(View.GONE);


        }
    }
}
