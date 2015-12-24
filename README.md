# Android-SpringView
#Demo
先看一下SpringView的效果图：
[SpringView效果图](http://b.picphotos.baidu.com/album/s%3D900%3Bq%3D90/sign=7a2063ff12ce36d3a6048f300ac84bba/d53f8794a4c27d1ef38ea4e11cd5ad6eddc4386e.jpg)

1.拖动灰色部分可拖动下方视图，点击jump按钮可让下方视图自行滑动。

#使用方法

android studio:

    > compile 'com.zql.android:springview:1.0'

布局文件：
```xml
    <com.zql.android.springview.SpringView
        android:id="@+id/spring"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:background="#ff5533ff">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@android:color/white"
                android:textSize="30sp"
                android:text="First View"/>
        </LinearLayout>
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:orientation="horizontal"
            android:background="#ffeeeeee">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:text="I am move holder"
                android:gravity="center"
                android:layout_centerInParent="true"
                android:layout_gravity="left"/>
            <Button
                android:id="@+id/jump"
                android:layout_width="wrap_content"
                android:layout_height="40dp"
                android:layout_alignParentRight="true"
                android:text="jump"/>
        </RelativeLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:background="#ff553300">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@android:color/white"
                android:textSize="30sp"
                android:text="Second View"/>
        </LinearLayout>
    </com.zql.android.springview.SpringView>
```
SpringView内部可以容纳两个或三个子view，当只有两个的时候，SringView会自动生成一个供拖拽的视图，当有三个的时候，会把第二个子view作为拖拽视图使用。建议使用三个子view，这样可以自定义拖拽的视图。
> 注意点：当使用三个子view的时候，第二个作为拖拽视图的布局要明确layout_height,不能使用match_parent或wrap_content。

SpringView的事件响应
```java
    public interface OnSpringListener{
        //达到顶部时回调
        void touchTop();
        //达到底部时回调
        void touchBottom();
    }
```

SpringView自动滑动：

```java
public void SpringView.jumpUp();
public void SpringView.jumpDown()
```
