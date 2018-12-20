package com.example.signin;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class main_Sign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__sign);//签到
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //back
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button btn_sign=findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
            @Override
            public void onClick(View v)
            {
                //计算点击次数,单数为发起签到,双数为结束签到
                Toast.makeText(getApplicationContext(),"发起/结束签到",Toast.LENGTH_LONG).show();

            }
        });
    }
    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu)
      {
          getMenuInflater().inflate(R.menu.action,menu);
          return true;
      }
  */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                this.finish();
                return super.onOptionsItemSelected(item);
            default:
        }
        return true;

    }

}
