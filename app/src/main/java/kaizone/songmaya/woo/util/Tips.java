
package kaizone.songmaya.woo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Toast;

public class Tips {

    public static void toToast(Context context, String text) {
        Toast.makeText(context, text, 300).show();
    }

    public static void toToast(Context context, String text, int gravity) {
        Toast toast = new Toast(context);
        toast.setGravity(gravity, 0, 0);
        toast.setText(text);
        toast.show();
    }

    public static void toDialog(Context context, String msg) {
        new AlertDialog.Builder(context)//
                .setMessage(msg)//
                .setPositiveButton("确定", null)//
                .show();
    }

    public static void toDialog(Context context, View content) {
        new AlertDialog.Builder(context)//
                .setView(content)//
                .setPositiveButton("确定", null)//
                .show();
    }

    public static void toDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context)//
                .setTitle(title)//
                .setMessage(msg)//
                .setPositiveButton("确定", null)//
                .show();
    }

    public static void toDialog(Context context, String title, String msg,
                                String posiName) {
        new AlertDialog.Builder(context)//
                .setTitle(title)//
                .setMessage(msg)//
                .setPositiveButton(posiName, null)//
                .show();
    }

    public static void toDialog(Context context, String msg,
                                String positive, final Runnable r) {
        new AlertDialog.Builder(context)//
                .setMessage(msg)//
                .setPositiveButton(positive, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (r != null) {
                            r.run();
                        }
                    }
                })//
                .show();
    }

    public static void toDialog(Context context, String title, String msg,
                                String positive, final Runnable r) {
        new AlertDialog.Builder(context)//
                .setTitle(title)//
                .setMessage(msg)//
                .setPositiveButton(positive, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (r != null) {
                            r.run();
                        }
                    }
                })//
                .show();
    }

    public static void toDialog(Context context, String title, String msg,
                                String positive, final Runnable r, String NegaName) {
        new AlertDialog.Builder(context)//
                .setTitle(title)//
                .setMessage(msg)//
                .setPositiveButton(positive, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (r != null) {
                            r.run();
                        }
                    }
                })//
                .setNegativeButton(NegaName, null)
                .show();
    }

    public static void toDialog(Context context, String title, String msg,
                                String positive, final Runnable posiRun, String NegaName, final Runnable negeRun) {
        new AlertDialog.Builder(context)//
                .setTitle(title)//
                .setMessage(msg)//
                .setPositiveButton(positive, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (posiRun != null) {
                            posiRun.run();
                        }
                    }
                })//
                .setNegativeButton(NegaName, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (negeRun != null) {
                            negeRun.run();
                        }
                    }
                })
                .show();
    }

    public static AlertDialog toDiaologList(Context context, String title, String[] items, OnClickListener listListener) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(items, listListener).show();
    }

    public static AlertDialog show(View view){
        return new AlertDialog.Builder(view.getContext())
                .setView(view)
                .setNegativeButton("确定", null)
                .show();
    }

}
