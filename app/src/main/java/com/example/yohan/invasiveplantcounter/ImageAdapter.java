package com.example.yohan.invasiveplantcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.net.ContentHandler;

public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mImageIDS = new int[] {R.mipmap.blue1_,R.mipmap.blue2_};

    ImageAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageIDS.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageview = new ImageView(mContext);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.setImageResource(mImageIDS[position]);
        container.addView(imageview,0);
        return imageview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
