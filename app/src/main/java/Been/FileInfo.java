package Been;

import android.graphics.Bitmap;

/**
 * Created by admin on 2018/5/20.
 */

public class FileInfo {
    private Bitmap bitmap;
    private String path;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
