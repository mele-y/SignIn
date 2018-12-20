package com.example.signin.utility;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Last Update Time: 2018/12/20
 * Description: override original android.widget.Toast, featured with color
 */

public class chromToast {
    public static void showToast(@NonNull Context context, String content) {
        showToast(context, content, false);
    }

    /**
     * default color
     * @param context context
     * @param content textView content
     * @param longTime {@link Toast#LENGTH_LONG,Toast#LENGTH_SHORT}
     */
    public static void showToast(@NonNull Context context, String content, boolean longTime) {
        int color =0;
        try {
            String packageName = context.getPackageName();
            int colorId = context.getResources().getIdentifier("colorPrimaryDark", "color",
                    packageName);
            if (colorId !=0){
                color = context.getResources().getColor(colorId);
            }else {
                Log.e("test","not found....");
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        showToast(context, content, longTime, color,0);
    }

    /**
     * {@link #tintDrawable(Drawable, ColorStateList)}
     * @param content content to show
     * @param longTime short or long
     * @param context context
     * @param toastBackgroundColor toast background color
     * @param textColor toast text color
     */
    public static void showToast(@NonNull Context context, String content, boolean longTime,@ColorInt int toastBackgroundColor ,@ColorInt
            int textColor) {
        int type = longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, content, type);
        View toastView = toast.getView();
        TextView textView = (TextView) toastView.findViewById(android.R.id.message);
        if (toastBackgroundColor != 0){
            Drawable toastBackgroundDrawable = tintDrawable(toastView.getBackground(), ColorStateList.valueOf(toastBackgroundColor));
            toastView.setBackgroundDrawable(toastBackgroundDrawable);
        }
        if (textColor!=0){
            textView.setTextColor(textColor);
        }
        toast.setView(toastView);
        toast.setText(content);
        toast.show();
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
