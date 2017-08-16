package kaizone.songmaya.woo.fragment.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import kaizone.songmaya.woo.R;

/**
 * Created by Administrator on 2017/7/13.
 */

public class GoWebFragment extends Fragment{

    TextView mTitle;
    WebView mContent;
    String html="<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='initial-scale=1, maximum-scale=1, user-scalable=no'></head><body><p><img src='http://10.164.194.115:8000/app/appmanage/file/viewImg?fileName=/testshare01/app/img/2017_07/ee3e61a852864a20a74d6bc630a20f3f.jpg'>1、借款提交成功后，资金一般会在一个工作日内到账。</p><p>2、若提示需要等待电话确认，则需要电话确认通过后到账。</p><p>温馨提示：具体到账时间以收款卡银行的到账通知为准。</p><p><br></p><p><br></p></body>";
//    String html2="<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1'/></head><body><p><img src='http://10.164.194.115:8000/app/appmanage/file/viewImg?fileName=/testshare01/app/img/2017_07/ee3e61a852864a20a74d6bc630a20f3f.jpg'>1、借款提交成功后，资金一般会在一个工作日内到账。</p><p>2、若提示需要等待电话确认，则需要电话确认通过后到账。</p><p>温馨提示：具体到账时间以收款卡银行的到账通知为准。</p><p><br></p><p><br></p></body>";
    String html3="<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1'/></head><body><p><img src='http://10.164.194.115:8000/app/appmanage/file/viewImg?fileName=/testshare01/app/img/2017_07/ee3e61a852864a20a74d6bc630a20f3f.jpg'>1、借款提交成功后，资金一般会在一个工作日内到账。</p><p>2、若提示需要等待电话确认，则需要电话确认通过后到账。</p><p>温馨提示：具体到账时间以收款卡银行的到账通知为准。</p><p><br></p><p><br></p></body>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_goweb, null);
        mTitle = (TextView) view.findViewById(R.id.help_title);
        mContent = (WebView) view.findViewById(R.id.help_content);

        mTitle.setText("hello");
//        mContent.setBackgroundColor(getContext().getResources().getColor(R.color.app_bg));
        WebSettings settings = mContent.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS );
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setSupportZoom(true); // 支持缩放

        html = html.replace("<img", "<img style=\"display:;max-width:100%;\"");
        mContent.loadData(html, "text/html; charset=UTF-8", null);

        return view;
    }

    public static final int ID = GoWebFragment.class.hashCode();
    public static final String NAME = GoWebFragment.class.getSimpleName();

    public static GoWebFragment newInstance(Bundle bd) {
        final GoWebFragment f = new GoWebFragment();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
