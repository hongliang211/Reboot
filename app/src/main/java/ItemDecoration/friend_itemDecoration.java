package ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import Been.Friend_Info;

/**
 * Created by admin on 2018/5/24.
 */

public class friend_itemDecoration extends RecyclerView.ItemDecoration {
   private List<Friend_Info>friend_infoList;
    private Paint paint;
    private Rect rect;
    private int textheight;
    private int textsize;
    private int background = Color.parseColor("#fffdfdfd");
    private int textcolor = Color.parseColor("#ff000000");

    public friend_itemDecoration(Context context , List<Friend_Info>friend_infoList){
        super();
        this.friend_infoList = friend_infoList;
        paint = new Paint();
        rect = new Rect();
        //转化成dip和sp形式
        textheight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        textsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        paint.setTextSize(textsize);
        paint.setAntiAlias(true);//抗锯齿

    }

    @Override
    public void onDraw(Canvas canvas , RecyclerView parent , RecyclerView.State state){
        super.onDraw(canvas , parent , state);

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(canvas, left, right, child, params, position);

                } else {//其他的通过判断
                    if (null != friend_infoList.get(position).getTag() && !friend_infoList.get(position).getTag().equals(friend_infoList.get(position - 1).getTag())) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(canvas, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }

    }

    @Override
    public void onDrawOver(Canvas canvas , RecyclerView parent , RecyclerView.State state){
        super.onDrawOver(canvas , parent , state);
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();

        String tag = friend_infoList.get(pos).getTag();
        //View child = parent.getChildAt(pos);
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;//出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView

        boolean flag = false;//定义一个flag，Canvas是否位移过的标志
        if ((pos + 1) < friend_infoList.size()) {//防止数组越界（一般情况不会出现）
            if (null != tag && !tag.equals(friend_infoList.get(pos + 1).getTag())) {//当前第一个可见的Item的tag，不等于其后一个item的tag，说明悬浮的View要切换了
                Log.d("zxt", "onDrawOver() called with: c = [" + child.getTop());//当getTop开始变负，它的绝对值，是第一个可见的Item移出屏幕的距离，
                if (child.getHeight() + child.getTop() < textheight) {//当第一个可见的item在屏幕中还剩的高度小于title区域的高度时，我们也该开始做悬浮Title的“交换动画”
                    canvas.save();//每次绘制前 保存当前Canvas状态，
                    flag = true;

                    //一种头部折叠起来的视效，个人觉得也还不错~
                    //可与123行 c.drawRect 比较，只有bottom参数不一样，由于 child.getHeight() + child.getTop() < mTitleHeight，所以绘制区域是在不断的减小，有种折叠起来的感觉
                    //c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + child.getHeight() + child.getTop());

                    //类似饿了么点餐时,商品列表的悬停头部切换“动画效果”
                    //上滑时，将canvas上移 （y为负数） ,所以后面canvas 画出来的Rect和Text都上移了，有种切换的“动画”感觉
                    canvas.translate(0, child.getHeight() + child.getTop() - textheight);
                }
            }
        }
        paint.setColor(background);
        canvas.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + textheight, paint);
        paint.setColor(textcolor);
        paint.getTextBounds(tag, 0, tag.length(), rect);
        canvas.drawText(tag, child.getPaddingLeft(),
                parent.getPaddingTop() + textheight - (textheight / 2 - rect.height() / 2),
                paint);
        if (flag)
            canvas.restore();//恢复画布到之前保存的状态

    }

    @Override
    public void getItemOffsets(Rect rect , View view , RecyclerView parent , RecyclerView.State state){
        super.getItemOffsets(rect , view , parent , state);

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if(position > -1){
            if (position == 0){
                rect.set(0,textheight,0,0);
            }else if(friend_infoList.get(position).getTag()!=null && friend_infoList.get(position).getTag()!= friend_infoList.get(position - 1).getTag())
            {
                rect.set(0,textheight,0,0);
            }
            else{
                rect.set(0,0,0,0);
            }
        }

    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        paint.setColor(background);
        c.drawRect(left, child.getTop() - params.topMargin - textheight, right, child.getTop() - params.topMargin, paint);
        paint.setColor(textcolor);
/*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        paint.getTextBounds(friend_infoList.get(position).getTag(), 0, friend_infoList.get(position).getTag().length(), rect);
        c.drawText(friend_infoList.get(position).getTag(), child.getPaddingLeft(), child.getTop() - params.topMargin - (textheight / 2 - rect.height() / 2), paint);
    }

}
