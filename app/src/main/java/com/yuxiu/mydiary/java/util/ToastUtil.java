package com.yuxiu.mydiary.java.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;


public class ToastUtil {
    private static Toast mToast = null;

    public static void showToast(Context context, int Stringid) {
        showToast(context, context.getString(Stringid));
    }

    public static void showToast(Context context, String string) {
        if (TextUtils.isEmpty(string))
            return;
        try {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
//            LayoutToastBinding binding= LayoutToastBinding.inflate(LayoutInflater.from(context));
//            binding.content.setText(string);
//            mToast.setView(binding.getRoot());
//            mToast.setGravity(Gravity.BOTTOM, 0, DisplayUtils.dip2px(context, 64));
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToastTxt(Context context, String txt) {

        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }
    public void change(int a){
        a=20;
    }

}