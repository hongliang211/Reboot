package Been;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/28.
 */

public class Friend_Circle_Info implements Serializable{
  //  private String path;
    private String name;
    private String text;
    private List<String> imglist = new ArrayList<>();

    public Friend_Circle_Info(/*String path , */ String name , String text , List<String>imglist){
        this.imglist = imglist;
        this.name = name;
     //   this.path = path;
        this.text = text;
    }

    public Friend_Circle_Info(){

    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }
}
