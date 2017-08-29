package kaizone.songmaya.woo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import kaizone.songmaya.woo.R;


public class DelEditText extends EditText {

    public DelEditText(Context context) {
        super(context);
    }

    public DelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DelEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        addTextChangedListener(new IconTextWatcher());
        setOnFocusChangeListener(new IconFocusChange());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[2];
            if (drawable != null && event.getX() > getWidth()
                    - getPaddingRight()
                    - drawable.getIntrinsicWidth()) {
                if (listener == null) {
                    setText("");
                } else {
                    listener.onClick(drawable);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void addIcon() {
        if (getText().length() <= 0)
            return;
        Drawable rightDrawable = getCompoundDrawables()[2];
        if (rightDrawable == null) {
            rightDrawable = getResources().getDrawable(android.R.drawable.ic_delete);
            setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
        }
    }

    public void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    class IconTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() > 0) {
                addIcon();
            } else {
                removeIcon();
            }
        }
    }

    class IconFocusChange implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                removeIcon();
            } else {
                addIcon();
            }
        }
    }

    private OnClickDrawableListener listener;

    public void setOnDrawableClickedListener(OnClickDrawableListener listener) {
        this.listener = listener;
    }

    public interface OnClickDrawableListener {
        void onClick(Drawable drawable);
    }
}
