package com.example.asmdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

//import com.example.mytestmd.TimeManager;
//import com.zjy.cost.TimeTotal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button2).setOnClickListener(this);

//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("this is testdfafasfafasfafa"+v);
//
//            }
//        });
//        test();
    }


//    @TimeTotal
//    public void test()
//    {
//        System.out.println("this is test");
//    }
public void reClick(View view) {
    Intent intent = new Intent(MainActivity.this,TestWebViewActivity.class);
    MainActivity.this.startActivity(intent);
    System.out.println("mainactivity->onClick() -> reClick !");

}

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(MainActivity.this,TestWebViewActivity.class);
//        MainActivity.this.startActivity(intent);
//        System.out.println("mainactivity->onClick() -> view !");
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.line1);

        Button button = new Button(MainActivity.this);
        button.setText("xxxxxxxxxxx");
        linearLayout.addView(button,200,200);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        System.out.println("好好mainactivity->onClick() -> view dafafaafd!");

            }
        });

    }
}
