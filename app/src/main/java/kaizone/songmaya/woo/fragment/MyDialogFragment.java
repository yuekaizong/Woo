package kaizone.songmaya.woo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.IllegalFormatCodePointException;

import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.fragment.a.GoFragment2;

/**
 * Created by Kaizo on 2017/11/22.
 */

public class MyDialogFragment extends DialogFragment {

    private final static String FLAG_MESSAGE = "message";

    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(FLAG_MESSAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_dialog, container);
        TextView messageView = view.findViewById(R.id.message);
        messageView.setText(message);
        return view;
    }

    public static MyDialogFragment newInstance(Bundle bd) {
        final MyDialogFragment f = new MyDialogFragment();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

    public static MyDialogFragment newInstance(String message) {
        final MyDialogFragment f = new MyDialogFragment();
        Bundle bd = new Bundle();
        bd.putString(FLAG_MESSAGE, message);
        f.setArguments(bd);
        return f;
    }



}
