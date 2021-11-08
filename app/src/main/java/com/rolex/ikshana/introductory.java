package com.rolex.ikshana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import org.jetbrains.annotations.NotNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;




public class introductory extends AppCompatActivity {
    ImageView bg,logo,name,skip;

    private static final int NUM_PAGES = 3;
    private ViewPager viewpager;
    private ScreenSlidePagerAdapter pagerAdapter;
    Animation anim;
    private static int SPLASH_TIME_OUT=5000;
    SharedPreferences mSharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducto);
        logo = findViewById(R.id.logo);
        bg = findViewById(R.id.bg);

        name = findViewById(R.id.name);
        viewpager = findViewById(R.id.pager);
        pagerAdapter =new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        anim= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewpager.startAnimation(anim);
        bg.animate().translationY(-2900).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        name.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedpref=getSharedPreferences("SharedPref",MODE_PRIVATE);
                boolean isFirstTime= mSharedpref.getBoolean("firsttime",true);
                if(isFirstTime){
                    SharedPreferences.Editor editor=mSharedpref.edit();

                    editor.putBoolean("firsttime",false);
                    editor.commit();
                }
                else{
                    Intent intent = new Intent(introductory.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);

    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;

                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;

                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}