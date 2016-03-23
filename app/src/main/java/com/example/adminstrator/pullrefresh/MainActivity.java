package com.example.adminstrator.pullrefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rootView = (RelativeLayout)this.findViewById(R.id.root);

        ListView listView = new ListView(this);
        listView.setAdapter(new Adapter());
       final  SwipeRefreshLayout srl = new SwipeRefreshLayout(this);
        srl.addView(listView);
        srl.setMode(SwipeRefreshLayout.Mode.BOTH);
        srl.setOnRefreshListener(new SwipeRefreshLayout.SwipeOnRefreshListener() {
            @Override
            public void onRefresh(int mode, boolean isDrag) {
                if (mode == 1) {
                    Toast.makeText(MainActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
                } else if (mode == 2) {
                    Toast.makeText(MainActivity.this, "上拉加载更多", Toast.LENGTH_SHORT).show();
                }
                //set the footer progress bar attribute
                //RefreshProgressBar footerBar = srl.getBottomBar();
                //footerBar.setAnimationImage(path);
                srl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl.refreshComplete();
                    }
                }, 2000);
            }
        });


        rootView.addView(srl, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView v = null;
            if(null == convertView){
                v = new TextView(MainActivity.this);
                v.setHeight(200);
            }else{
                v = (TextView)convertView;
            }

            v.setText("item " + position);
            return v;
        }
    }

}
