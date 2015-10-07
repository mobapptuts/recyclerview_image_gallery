package nigelhenshaw.com.cameraintenttutorial;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by nigelhenshaw on 25/08/2015.
 */
public class GalleryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Fresco.initialize(this);
    }
}
