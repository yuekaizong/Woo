package kaizone.songmaya.woo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import kaizone.songmaya.woo.R;


/**
 * 项目名称：goHuaAND
 * 项目作者：胡玉君
 * 创建日期：2017/7/3 16:21.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 *
 1. 右侧带图片的EditText输入框

 2. 若没有drawableRight，则就是一个纯粹的EditText

 3. 若设置drawableRigth，则默认点击右侧图片为删除输入框内的问题

 4. 若想有其他功能，可以设置图片的点击监听setOnDrawableClickedListener(OnClickDrawableListener listener)
 * ----------------------------------------------------------------------------------------------------
 */

public class DelEditText extends EditText{

    public DelEditText(Context context) {
        super(context);
    }

    public DelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(new IconTextWatcher());
    }

    public void addIcon(){
        Drawable rightDrawable = getCompoundDrawables()[2];
        if(rightDrawable == null){
            rightDrawable = getResources().getDrawable(android.R.drawable.ic_delete);
            setCompoundDrawablesWithIntrinsicBounds(null,null,rightDrawable,null);
        }
    }

    public void removeIcon(){
        setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
    }

    public DelEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            if(editable.toString().length() > 0){
                addIcon();
            }else{
                removeIcon();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            Drawable drawable = getCompoundDrawables()[2];
            if (drawable != null && event.getX() > getWidth()
                    - getPaddingRight()
                    - drawable.getIntrinsicWidth()){
                if(listener == null){
                    setText("");
                }else{
                    listener.onClick(drawable);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private OnClickDrawableListener listener;
    public void setOnDrawableClickedListener(OnClickDrawableListener listener){
        this.listener = listener;
    }
    public interface OnClickDrawableListener{
        void onClick(Drawable drawable);
    }
}
