package nigelhenshaw.com.cameraintenttutorial;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by nigelhenshaw on 25/06/2015.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Bitmap placeHolderBitmap;
    private File imagesFile;

    public static class AsyncDrawable extends BitmapDrawable {
        final WeakReference<BitmapWorkerTask> taskReference;

        public AsyncDrawable(Resources resources,
                             Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(resources, bitmap);
            taskReference = new WeakReference(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return taskReference.get();
        }
    }

    public ImageAdapter(File folderFile) {
        imagesFile = folderFile;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_images_relative_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File imageFile = imagesFile.listFiles()[position];
        // Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        // holder.getImageView().setImageBitmap(imageBitmap);
        // BitmapWorkerTask workerTask = new BitmapWorkerTask(holder.getImageView());
        // workerTask.execute(imageFile);
        Bitmap bitmap = CamaraIntentActivity.getBitmapFromMemoryCache(imageFile.getName());
        if(bitmap != null) {
            holder.getImageView().setImageBitmap(bitmap);
        }
        else if(checkBitmapWorkerTask(imageFile, holder.getImageView())) {
            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(holder.getImageView());
            AsyncDrawable asyncDrawable = new AsyncDrawable(holder.getImageView().getResources(),
                    placeHolderBitmap,
                    bitmapWorkerTask);
            holder.getImageView().setImageDrawable(asyncDrawable);
            bitmapWorkerTask.execute(imageFile);
        }
    }

    @Override
    public int getItemCount() {
        return imagesFile.listFiles().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.imageGalleryView);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public static boolean checkBitmapWorkerTask(File imageFile, ImageView imageView) {
        BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if(bitmapWorkerTask != null) {
            final File workerFile = bitmapWorkerTask.getImageFile();
            if(workerFile != null) {
                if(workerFile != imageFile) {
                    bitmapWorkerTask.cancel(true);
                } else {
                    // bitmap worker task file is the same as the imageview is expecting
                    // so do nothing
                    return false;
                }
            }
        }
        return true;
    }

    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof AsyncDrawable) {
            AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            return asyncDrawable.getBitmapWorkerTask();
        }
        return null;
    }
}
