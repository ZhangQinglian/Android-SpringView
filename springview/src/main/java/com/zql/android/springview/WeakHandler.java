package com.zql.android.springview;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by qingl on 2015/12/23.
 */
public abstract  class WeakHandler<T> extends Handler{
    private WeakReference<T> mHolder ;

    public WeakHandler(T holder){
        mHolder = new WeakReference<T>(holder);
    }

    public T getHolder(){
        return mHolder.get() ;
    }
}
