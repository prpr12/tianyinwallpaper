package com.zeaze.tianyinwallpaper.ui.about;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.zeaze.tianyinwallpaper.R;
import com.zeaze.tianyinwallpaper.base.BaseFragment;

public class AboutFragment extends BaseFragment {
    private TextView about;
    @Override
    protected void init() {
        about=view.findViewById(R.id.about);
        about.setMovementMethod(LinkMovementMethod.getInstance());
        String verName = "获取失败";
        try {
            verName = getActivity().getPackageManager().
                    getPackageInfo(getActivity().getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        about.setText(getClickableHtml("天音壁纸是一个用来设置壁纸的软件>_< <br>\n" +
                "点击“增加壁纸”，可以增加当前壁纸组的壁纸<br>\n" +
                "点击“应用本组”，会把当前壁纸组设置为手机壁纸，每次进入桌面，都会更新显示壁纸组里的下一张壁纸<br>\n" +
                "点击右上角的齿轮，可以保存当前壁纸组<br>\n" +
                "齿轮里的“壁纸通用设置”，可以设置通用的壁纸切换方式<br>\n" +
                "目前支持顺序切换和随机切换，和最小切换时间<br>\n" +
                "最小切换时间的意思是在切换壁纸后，未达这个时间间隔的话是不会二次切换壁纸的<br>\n" +
                "齿轮里的“清空当前壁纸组”，可以方便的一键清空壁纸组来设置新的壁纸<br>\n" +
                "点击壁纸缩略图，可以选择删除壁纸或者设置壁纸显示的条件，长按可以调整顺序<br>\n" +
                "当满足条件时，会优先显示满足条件的壁纸，借此，可以设置早安壁纸，下班壁纸<br>\n" +
                "目前仅支持按时间设置条件，开始时间为闭区间，结束时间为开区间<br>\n" +
                "欢迎加入天音壁纸qq群,BUG和意见都可以提：<a href=\"https://jq.qq.com/?_wv=1027&k=vjcrjY7L\">722673402</a><br>\n" +
                "------<br>\n" +
                "项目开源地址：<a href=\"https://github.com/prpr12/tianyinwallpaper.git\">https://github.com/prpr12/tianyinwallpaper.git</a><br>\n" +
                "软件下载地址：<a href=\"https://www.pgyer.com/eEna\">https://www.pgyer.com/eEna</a><br>\n" +
                "------<br>\n" +
                "当前版本号："+verName+"\n"));
    }

    @Override
    protected int getLayout() {
        return R.layout.about_fragment;
    }

    private CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spannedHtml = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            spannedHtml = Html.fromHtml(html);
        }
        SpannableStringBuilder clickableBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        if (urls.length == 0){
            return html.replace("\\n", "\n").replace("\\r", "\r");
        }
        for (final URLSpan span : urls) {
            setLinkClickable(clickableBuilder, span);
        }
        return clickableBuilder;
    }

    private void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder, final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {
            public void onClick(View view) {

            }
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.RED);
            }

        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }
}
