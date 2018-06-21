package Been;

/**
 * Created by admin on 2018/5/21.
 */

public class Msg_Info {
    private int msgtype;


    private String text_msg;
    private String img_msg;
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    public Msg_Info(int msgtype,String text_msg,String img_msg){
        this.msgtype = msgtype;
        this.text_msg = text_msg;
        this.img_msg = img_msg;
    }
    public Msg_Info(){

    }

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }


    public String getText_msg() {
        return text_msg;
    }

    public void setText_msg(String text_msg) {
        this.text_msg = text_msg;
    }

    public String getImg_msg() {
        return img_msg;
    }

    public void setImg_msg(String img_msg) {
        this.img_msg = img_msg;
    }


}
