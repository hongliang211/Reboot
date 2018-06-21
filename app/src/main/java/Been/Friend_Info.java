package Been;

/**
 * Created by admin on 2018/5/24.
 */

public class Friend_Info {
    private String tag;//朋友的首字母
    private String name;

    public Friend_Info(String tag , String name){
        this.tag = tag;
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
