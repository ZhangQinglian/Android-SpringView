# Android-SpringView
# Demo
先看一下SpringView的效果图：
![SpringView效果图](http://7xprgn.com1.z0.glb.clouddn.com/springview1-1.gif)

>拖动灰色部分可拖动下方视图，点击jump按钮可让下方视图自行滑动。

# android studio
    compile 'com.zql.android:springview:1.1'

<!-- more -->
# 使用方法
布局文件：
```xml
    <com.zql.android.springview.SpringView
        android:id="@+id/spring"
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        custom:topMargin="56dp"
        custom:bottomMargin="56dp"
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
 - SpringView内部可以容纳两个或三个子view，当只有两个的时候，SringView会自动生成一个供拖拽的视图，当有三个的时候，会把第二个子view作为拖拽视图使用。建议使用三个子view，这样可以自定义拖拽的视图。
 - SringView有两个自定义属性`topMargin`和`bottomMargin`用于控制拖拽视图的上下边距，效果看上方截图。

> 注意点：当使用三个子view的时候，第二个作为拖拽视图的布局要明确layout_height,不能使用match_parent或wrap_content。

SpringView的事件响应
```java
mSpringView.setOnSpringListener(new SpringView.OnSpringListener() {
     @Override
     public void touchTop() {
         Toast.makeText(MainActivity.this,"Touch top!!!",Toast.LENGTH_SHORT).show();
     }

     @Override
     public void touchBottom() {
         Toast.makeText(MainActivity.this,"Touch Bottom!!!",Toast.LENGTH_SHORT).show();
     }
 });
```

SpringView自动滑动：

```java
public void SpringView.jumpUp();
public void SpringView.jumpDown()
```

# github
[Github](https://github.com/ZhangQinglian/Android-SpringView)