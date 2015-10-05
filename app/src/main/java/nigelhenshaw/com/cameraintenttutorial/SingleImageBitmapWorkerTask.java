package nigelhenshaw.com.cameraintenttutorial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by nigelhenshaw on 6/07/2015.
 */

public class SingleImageBitmapWorkerTask extends AsyncTask<File, Void, Bitmap> {

    WeakReference<ImageView> imageViewReferences;
    final int TARGET_IMAGE_VIEW_WIDTH;
    final int TARGET_IMAGE_VIEW_HEIGHT;
    private File mImageFile;
    public SingleImageBitmapWorkerTask(ImageView imageView, int width, int height) {
        TARGET_IMAGE_VIEW_WIDTH = width;
        TARGET_IMAGE_VIEW_HEIGHT = height;
        imageViewReferences = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(File... params) {
        // return BitmapFactory.decodeFile(params[0].getAbsolutePath());
        mImageFile = params[0];
        // return decodeBitmapFromFile(params[0]);
        Bitmap bitmap = decodeBitmapFromFile(mImageFile);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        /*
        if(bitmap != null && imageViewReferences != null) {
            ImageView viewImage = imageViewReferences.get();
            if(viewImage != null) {
                viewImage.setImageBitmap(bitmap);
            }
        }
        */
        if(bitmap != null && imageViewReferences != null) {
            ImageView imageView = imageViewReferences.get();
            if(imageView != null) {
                    imageView.setImageBitmap(bitmap);
            }
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options bmOptions) {
        final int photoWidth = bmOptions.outWidth;
        final int photoHeight = bmOptions.outHeight;
        int scaleFactor = 1;

        if(photoWidth > TARGET_IMAGE_VIEW_WIDTH || photoHeight > TARGET_IMAGE_VIEW_HEIGHT) {
            final int halfPhotoWidth = photoWidth/2;
            final int halfPhotoHeight = photoHeight/2;
            while(halfPhotoWidth/scaleFactor > TARGET_IMAGE_VIEW_WIDTH
                    || halfPhotoHeight/scaleFactor > TARGET_IMAGE_VIEW_HEIGHT) {
                scaleFactor *= 2;
            }
        }
        return scaleFactor;
    }

    private Bitmap decodeBitmapFromFile(File imageFile) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
        bmOptions.inSampleSize = calculateInSampleSize(bmOptions);
        bmOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
    }

    public File getImageFile() {
        return mImageFile;
    }
}
