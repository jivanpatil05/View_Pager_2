package com.example.view_pager_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     private ViewPager2 viewPager2;
     private Handler sliderHandler =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2=findViewById(R.id.viewPagerImageSlider);

        List<SliderItem>sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.ip1));
        sliderItems.add(new SliderItem(R.drawable.ip2));
        sliderItems.add(new SliderItem(R.drawable.ip3));
        sliderItems.add(new SliderItem(R.drawable.ip4));
        sliderItems.add(new SliderItem(R.drawable.ip5));
        sliderItems.add(new SliderItem(R.drawable.ip6));
        sliderItems.add(new SliderItem(R.drawable.ip7));
        sliderItems.add(new SliderItem(R.drawable.ip8));
        sliderItems.add(new SliderItem(R.drawable.ip9));
        sliderItems.add(new SliderItem(R.drawable.ip10));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren (false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f + r *0.15f);
            }
        });
            viewPager2.setPageTransformer(compositePageTransformer);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable,3000);
                }
            });
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
    protected void onPause(){
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
    protected void onResume(){
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,300);
    }
}