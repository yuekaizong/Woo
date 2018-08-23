package kaizone.songmaya.datamanager.retrofit.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private SubscriberOnErrorListener mSubscriberOnErrorListener;
    private ProgressCancelListener mProgressCancelListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        if (this.context != null) {
            mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
        }
    }

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, SubscriberOnErrorListener subscriberOnErrorListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mSubscriberOnErrorListener = subscriberOnErrorListener;
        this.context = context;
    }

    //设置是否显示进度框
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context,
                              boolean isShowProgressDialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;

        if (!isShowProgressDialog || context == null) return;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    //设置进度框取消的回调处理
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener,
                              ProgressCancelListener progressCancelListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;

        mProgressCancelListener = progressCancelListener;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }

        log("start");
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        log("end");
    }

    @Override
    public void onError(Throwable e) {
        log(e.getMessage());
        if (e instanceof SocketTimeoutException) {
            tips("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            tips("网络中断，请检查您的网络状态");
        } else {
            tips("error:" + e.getMessage());
        }

        if (mSubscriberOnErrorListener != null) {
            mSubscriberOnErrorListener.onError(e);
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.next(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }

        if (mProgressCancelListener != null) {
            mProgressCancelListener.onCancelProgress();
        }
    }

    public void tips(String text) {
        if (context == null) return;
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void log(String text){
        System.out.println(text);
    }

    class ProgressDialogHandler extends Handler {

        public static final int SHOW_PROGRESS_DIALOG = 1;
        public static final int DISMISS_PROGRESS_DIALOG = 2;

        private ProgressDialog pd;

        private Context context;
        private boolean cancelable;
        private ProgressCancelListener mProgressCancelListener;

        public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                     boolean cancelable) {
            super();
            this.context = context;
            this.mProgressCancelListener = mProgressCancelListener;
            this.cancelable = cancelable;
        }

        private void initProgressDialog() {
            if (pd == null) {
                pd = new ProgressDialog(context);

                pd.setCancelable(cancelable);

                if (cancelable) {
                    pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            if (mProgressCancelListener != null)
                                mProgressCancelListener.onCancelProgress();
                        }
                    });
                }

                if (!pd.isShowing()) {
                    pd.show();
                }
            }
        }

        private void dismissProgressDialog() {
            if (pd != null) {
                pd.dismiss();
                pd = null;
            }
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS_DIALOG:
                    initProgressDialog();
                    break;
                case DISMISS_PROGRESS_DIALOG:
                    dismissProgressDialog();
                    break;
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }
}
