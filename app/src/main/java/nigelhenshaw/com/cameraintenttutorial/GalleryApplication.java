package nigelhenshaw.com.cameraintenttutorial;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by nigelhenshaw on 25/08/2015.
 */
public class GalleryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Fresco.initialize(this);
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true
                ).build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
}
