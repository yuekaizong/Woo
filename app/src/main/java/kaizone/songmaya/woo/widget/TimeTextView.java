package kaizone.songmaya.woo.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.Log;

import java.util.HashMap;

import kaizone.songmaya.woo.util.SystemUtils;

/**
 * 项目名称：倒计时button
 * 项目作者：胡玉君
 * 创建日期：2016/4/15 20:19.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class TimeTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "TimeTextView";
    private Status status = Status.UnClick;//未点击状态
    private CharSequence pre = "发送验证码";
    private int time = 60;
    private CharSequence s = "后重发";
    private boolean flag = true;
    private int count = 60;
    private long startTimestamp;
    private String stampForKey;
    private final static HashMap<String, Long> map = new HashMap<>();

    private int defaultTextColor = 0xff666666;

    public enum Status {
        UnClick, Timer, TimerOver
    }

    public TimeTextView(Context context) {
        super(context);
        initView();
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        stampForKey = getContext().getClass().getName();
        setPreText(pre);
        setTextColor(0xff41aefa);
        startTimestamp = getStartTimestamp();
        long diff = (SystemUtils.currentTimestamp() - startTimestamp) / 1000;
        if (diff < 60) {
            time -= diff;
            status = Status.Timer;
            hd.sendEmptyMessage(0);
        }

    }

    public void setPreText(CharSequence s) {
        if (s != null) {
            this.pre = s;
        }
        setText(s);
    }

    /**
     * 倒计时文字
     *
     * @param s    文字，不要带符号
     * @param time 时间
     * @param flag 是否带()
     */
    public void setTimerText(CharSequence s, int time, boolean flag) {
        if (time != 0) {
            this.time = time;
            if (status == Status.UnClick) {
                this.count = time;
            }
        }
        if (s != null) {
            this.s = s;
        }
        this.flag = flag;
        String str;
        if (flag) {
            str = time + "s" + s.toString();
        } else {
            str = time + "s" + s.toString();
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        TextAppearanceSpan textAppearanceSpan1 = new TextAppearanceSpan(getContext(), 0xff666666);
        builder.setSpan(textAppearanceSpan1, 0, str.trim().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(builder);
        setTextColor(0xff666666);
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        super.setTextColor(color);
        defaultTextColor = color;
    }

    private SendMsgListener listener;
    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (time > 1) {
                time -= 1;
                setTimerText(s, time, flag);
                status = Status.Timer;//计时状态
                hd.sendEmptyMessageDelayed(0, 1000);
            } else {
                startTimestamp = 0;
                setText(pre);
                setTextColor(0xff41aefa);
                time = count;
                status = Status.UnClick;//未点击状态
                if (listener != null) {
                    listener.countFinished();
                }
            }
        }
    };

    /**
     * 设置剩余时间
     */
    public void setLastTime(SendMsgListener listener) {
        startTimestamp = SystemUtils.currentTimestamp();
        map.put(stampForKey, startTimestamp);
        this.listener = listener;
        if (status == Status.UnClick) {
            hd.removeMessages(0);
            setTimerText(s, time, flag);
            status = Status.Timer;//计时状态
            if (listener != null) {
                listener.countStart();
            }
            hd.sendEmptyMessageDelayed(0, 1000);
        } else if (status == Status.Timer) {
            hd.removeMessages(0);
            time = count;
            setTimerText(s, time, flag);
            if (listener != null) {
                listener.countStart();
            }
            hd.sendEmptyMessageDelayed(0, 1000);
        }
    }

    public interface SendMsgListener {
        void countStart();

        void countFinished();
    }

    public void release() {
        if (hd != null) {
            hd.removeCallbacksAndMessages(null);
        }
        hd = null;
        listener = null;
        status = Status.UnClick;
        setPreText(pre);
    }

    public void resetTime() {
        startTimestamp = 0;
    }

    public long getStartTimestamp() {
        Long stamp = map.get(stampForKey);
        if (stamp == null)
            return 0;
        return stamp;
    }

    private void init() {
        hd = null;
        listener = null;
        status = Status.UnClick;
        setPreText(pre);
        startTimestamp = 0;
        time = 60;
        count = 60;
        flag = true;
    }

}
