package Been;

import android.graphics.Bitmap;

/**
 * Created by admin on 2018/5/20.
 */

public class Choose_Photo_Info {
    private Bitmap bitmap;


    public Choose_Photo_Info(Bitmap bitmap){
        this.bitmap = bitmap;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
