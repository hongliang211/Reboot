package DB;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import Been.Friend_Circle_Info;

/**
 * Created by admin on 2018/6/12.
 */

public class db_friend_circle extends DataSupport {
    private int id_;
    private String name;
    private String text;
    private List<String> path= new ArrayList<>();

    public int getId_() {
        return id_;
    }

    public void setId_(int id) {
        this.id_ = id;
    }

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

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<String> getPath() {
        return path;
    }
}
