package demo.alexan.com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import demo.alexan.com.myapplication.R;

/**
 * TODO: document your custom view class.
 */
public class SlideCutListView extends ListView {
    
    private int slidePosition;
    private int downY;
    private int downX;
    private int screenWidth;
    private View itemView;
    private Scroller scroller;
    private static final int SNAP_VELOCITY = 600;
    //todo: what's this?
    private VelocityTracker velocityTracker;
    private boolean isSlide = false;
    private int mTouchSlop;
    private RemoveListener mRemoveListener;
    private RemoveDirection removeDirection;
    
    public enum RemoveDirection {
        RIGHT, LEFT;
    }

    public SlideCutListView(Context context) {
        this(context, null);
    }

    public SlideCutListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCutListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //todo: deprecated?
        screenWidth = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context);
        //todo: what's this?
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
    
    public void setRemoveListener(RemoveListener removeListener) {
        this.mRemoveListener = removeListener;
    }
    
    private void addVelocityTracker(MotionEvent event) {
        if(velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }
    
    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return velocity;
    }
    
    private void recycleVelocityTracker() {
        if(velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                addVelocityTracker(ev);
                if(!scroller.isFinished()) {
                    return super.dispatchTouchEvent(ev);
                }
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                //todo: what's this?
                slidePosition = pointToPosition(downX, downY);
                //todo: what's this?
                if(slidePosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(ev);
                }
                //todo: what's this?
                itemView = getChildAt(slidePosition - getFirstVisiblePosition());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(Math.abs(getScrollVelocity()) > SNAP_VELOCITY ||
                        (Math.abs(ev.getX() - downX) > mTouchSlop && Math.abs(ev.getY() - downY) < mTouchSlop)) {
                    isSlide = true;
                }
                break;
            }
            case MotionEvent.ACTION_UP: 
                recycleVelocityTracker();
                break;
                
        }
        return super.dispatchTouchEvent(ev);
    }
    
    private void scrollRight() {
        removeDirection = RemoveDirection.RIGHT;
        final int delta = (screenWidth + itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }
    
    private void scrollLeft() {
        removeDirection = RemoveDirection.LEFT;
        final int delta = (screenWidth - itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(), 0, delta, 0, Math.abs(delta));
        postInvalidate();
    }
    
    private void scrollByDistanceX() {
        if(itemView.getScrollX() >= screenWidth / 2) {
            scrollLeft();
        } else if(itemView.getScrollX() <= -screenWidth / 2) {
            scrollRight();
        } else {
            itemView.scrollTo(0, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //todo: adapterview
        if(isSlide && slidePosition != AdapterView.INVALID_POSITION) {
            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(ev);
            final int action = ev.getAction();
            int x = (int) ev.getX();
            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    //靠杯啊这里改成FALSE也啥问题都没有
                    //return false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    //todo: shenmegui
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL | (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                    int deltaX = downX - x;
                    downX = x;
                    itemView.scrollBy(deltaX, 0);
                    //靠杯我把这里改成FALSE也没有影响啊。
                    return true;
                case MotionEvent.ACTION_UP:
                    int velocityX = getScrollVelocity();
                    //todo: why positive right?
                    if(velocityX > SNAP_VELOCITY) {
                        scrollRight();
                    } else if(velocityX < -SNAP_VELOCITY) {
                        scrollLeft();
                    } else {
                        scrollByDistanceX();
                    }
                    recycleVelocityTracker();
                    isSlide = false;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }
    
    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()) {
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
            if(scroller.isFinished()) {
                if(mRemoveListener == null) {
                    throw new NullPointerException("removelistener is null, we should called setremovelistener()");
                }
                itemView.scrollTo(0, 0);
                mRemoveListener.removeItem(removeDirection, slidePosition);
            }
        }
    }
    
    public interface RemoveListener {
        public void removeItem(RemoveDirection direction, int position);
    }
}
