package com.zql.android.springview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by zhangqinglian on 2015/12/23.
 */
public class SpringView extends FrameLayout {


    /**
     * 用于摆放上拉的视图
     */
    private LinearLayout mSpringViewHolder;
    /**
     * 用于响应拖拽事件的视图
     */
    private View mMoveHolder;
    /**
     * mMoveHolder的默认高度
     */
    private int mMoveHolderH = 80;
    /**
     * mSpringViewHolder的最小上外边距
     */
    private int mMoveTop;
    /**
     * mSpringViewHolder的最大上外边距
     */
    private int mMoveBottom;
    /**
     * mSpringViewHolder的布局参数
     */
    private LayoutParams mFLP;

    private boolean mIsTouchTop = false ;
    /**
     * 用于回调
     */
    private OnSpringListener mCallback = new OnSpringListener() {
        @Override
        public void touchTop() {
        }

        @Override
        public void touchBottom() {
        }
    };

    private Context mContext;
    /**
     * 是否是第一次调用onLayout方法
     */
    private boolean isFirstLayout = true;

    public static final int PULL_UP_TOUCH_TOP = 0;

    public static final int PULL_UP_TOUCH_BOTTOM = 1 ;

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends WeakHandler<SpringView>{

        public MyHandler(SpringView holder) {
            super(holder);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PULL_UP_TOUCH_TOP:
                    getHolder().getOnSpringListener().touchTop();
                    break;
                case PULL_UP_TOUCH_BOTTOM:
                    getHolder().getOnSpringListener().touchBottom();
                    break;
                default:
            }
        }
    }
    public SpringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    /**
     * 设置
     * @param listener
     */
    public void setOnSpringListener(OnSpringListener listener){
        this.mCallback = listener;
    }
    public OnSpringListener getOnSpringListener(){
        return mCallback;
    }
    public boolean isTouchTop(){
        return mIsTouchTop ;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //初始化mPullUpViewHolder
        mSpringViewHolder = new LinearLayout(mContext);
        mSpringViewHolder.setBackgroundColor(Color.GREEN);
        mSpringViewHolder.setOrientation(LinearLayout.VERTICAL);
        mFLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mFLP.setMargins(0, 0, 0, 0);
        addView(mSpringViewHolder, 1, mFLP);

        //初始化mMoveHolder
        if(getChildCount() == 4){
            mMoveHolder = getChildAt(2);
            mMoveHolderH = mMoveHolder.getLayoutParams().height ;
            Log.d("scott","mMoveHolderH = " + mMoveHolderH);
            removeViewAt(2);
        }else{
            mMoveHolder = new View(mContext);
            mMoveHolder.setBackgroundColor(Color.GRAY);
        }
        LinearLayout.LayoutParams LLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mMoveHolderH);
        mMoveHolder.setLayoutParams(LLP);
        mSpringViewHolder.addView(mMoveHolder);

        //将需要上拉的视图从主视图移除并添加进mPullUpViewHolder
        View view = getChildAt(2);
        removeViewAt(2);
        mSpringViewHolder.addView(view);

        //给mMoveHolder添加拖拽事件
        mMoveHolder.setOnTouchListener(new OnTouchListener() {
            private float originY = 0;
            private float deltaY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        originY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        deltaY = event.getRawY() - originY;
                        originY = event.getRawY();
                        if (Math.abs(deltaY) > 0.0f) {
                            int top = (int) (mFLP.topMargin + deltaY + 0.5f);
                            if (top < mMoveTop) {
                                top = mMoveTop;
                            }
                            if (top > mMoveBottom) {
                                top = mMoveBottom;
                            }
                            mFLP.setMargins(0, top, 0, 0);
                            mSpringViewHolder.setLayoutParams(mFLP);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        int top = mFLP.topMargin ;
                        if(top == 0 || top == mMoveBottom){
                            return true;
                        }
                        if(top < (mMoveTop + mMoveBottom)/2){
                            doAnimation(250,top,0);
                        }else{
                            doAnimation(250,top,mMoveBottom);
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void jumpUp(){
        doAnimation(250,mMoveBottom,mMoveTop);
    }

    public void jumpDown(){
        doAnimation(250,mMoveTop,mMoveBottom);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void doAnimation(int during, int start, int end){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            ValueAnimator animator = ValueAnimator.ofInt(start,end);
            animator.setDuration(during);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mFLP.setMargins(0, (Integer) animation.getAnimatedValue(), 0, 0);
                    mSpringViewHolder.setLayoutParams(mFLP);
                }
            });
            animator.start();
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        View mainContent = getChildAt(0);
        mainContent.layout(l, t, r, b);


        mMoveTop = t;
        mMoveBottom = b - mMoveHolderH - 200;
        if (isFirstLayout) {
            mFLP.setMargins(0, mMoveBottom, 0, 0);
            isFirstLayout = false;
        }
        if(mFLP.topMargin == mMoveTop){
            mIsTouchTop = true ;
            mHandler.sendEmptyMessage(PULL_UP_TOUCH_TOP);
        }
        if(mFLP.topMargin == mMoveBottom){
            mIsTouchTop = false ;
            mHandler.sendEmptyMessage(PULL_UP_TOUCH_BOTTOM);
        }

        mSpringViewHolder.layout(l, t + mFLP.topMargin, r, b);
    }

    public interface OnSpringListener{
        void touchTop();
        void touchBottom();
    }
}
