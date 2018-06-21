package Util;

import android.content.ContentQueryMap;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Been.FileInfo;

/**
 * Created by HL on 2018/5/19.
 * 遍历本地相册缩略图并提供接口处理
 */

public class Get_Bitmap_For_Phone {
    public interface GetBitmapListener{
        void onSucceed(List<FileInfo> fileInfoList);
        void onError(Exception e);
    }

    public static void get_bitmap(final Context context, final GetBitmapListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
                Cursor cursor = null;
                ContentResolver contentResolver = context.getContentResolver();
                try{
                    cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
                    if(cursor.moveToFirst()){
                        do{
                            FileInfo fileInfo = new FileInfo();
                                    fileInfo.setBitmap(MediaStore.Images.Thumbnails.getThumbnail
                                            (contentResolver,cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID)),
                                                    MediaStore.Images.Thumbnails.MICRO_KIND,null));
                            fileInfo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))); //文件路径);

                            fileInfoList.add(fileInfo);

                        }while(cursor.moveToNext());

                    }
                    listener.onSucceed(fileInfoList);


                }catch (Exception e){
                    e.printStackTrace();
                    listener.onError(e);
                }finally {
                    if(cursor != null){
                        cursor.close();
                    }
                }

            }
        }).start();
    }

}
