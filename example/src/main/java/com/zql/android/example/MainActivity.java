package com.zql.android.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zql.android.springview.SpringView;

public class MainActivity extends AppCompatActivity {

    private Button mJumpBtn ;
    private SpringView mSpringView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJumpBtn = (Button) findViewById(R.id.jump);
        mSpringView = (SpringView) findViewById(R.id.spring);
        mJumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpringView.isTouchTop()){
                    mSpringView.jumpDown();
                }else{
                    mSpringView.jumpUp();
                }
            }
        });

    }
}
