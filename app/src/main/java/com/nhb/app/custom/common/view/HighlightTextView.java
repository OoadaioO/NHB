package com.nhb.app.custom.common.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nhb.app.custom.R;

/**
 * 关键字高亮 TextView
 */
public class HighlightTextView extends TextView {

    private static final String DEFAULT_TAG_LEFT = "<ems>";
    private static final String DEFAULT_TAG_RIGHTL = "</ems>";

    public static int mHighlightColor;// 高亮颜色
    private String mTagLeft;// 左标记
    private String mTagRight;// 右标记

    public HighlightTextView(Context context) {
        this(context, null);
    }

    public HighlightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHighlightColor = getResources().getColor(R.color.c_main);
        mTagLeft = DEFAULT_TAG_LEFT;
        mTagRight = DEFAULT_TAG_RIGHTL;
    }

    /**
     * 设置高亮标记
     *
     * @param tagLeft  左标记
     * @param tagRight 右标记
     */
    public void setTag(String tagLeft, String tagRight) {
        if (!TextUtils.isEmpty(tagLeft)) {
            mTagLeft = tagLeft;
        }
        if (!TextUtils.isEmpty(tagRight)) {
            mTagRight = tagRight;
        }
    }

    public void setHighlightColor(int highlightColor) {
        mHighlightColor = highlightColor;
    }

    /**
     * @param text 文本内容
     */
    public void setText(String text) {
        setText(text, mHighlightColor);
    }

    /**
     * @param text      文本内容
     * @param highLight 是否高亮？true 时使用默认高亮颜色
     */
    public void setText(String text, boolean highLight) {
        if (highLight) {
            setText(text, mHighlightColor);
        } else {
            setText(text);
        }
    }

    /**
     * @param text           文本内容
     * @param highlightColor 高亮颜色
     */
    public void setText(String text, int highlightColor) {
        if (text == null) {
            text = "";
        }
        // 左右标记的长度
        int tagLeftLen = mTagLeft.length();
        int tagRightLen = mTagRight.length();

        // 关键字开始和结束
        int offsetStart = text.indexOf(mTagLeft, 0);
        int offsetEnd = text.indexOf(mTagRight, offsetStart + tagLeftLen);

        //文字中没有高亮标签，直接返回
        if (offsetStart == -1 || offsetEnd == -1) {
            super.setText(text);
            return;
        }

        // 遍历原始text，截取并拼接出最终的串
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int start = 0; start < text.length() && offsetStart != -1 && offsetEnd != -1; start = offsetEnd + tagRightLen) {

            offsetStart = text.indexOf(mTagLeft, start);
            offsetEnd = text.indexOf(mTagRight, offsetStart + tagLeftLen);

            // 若找不到标记则跳出
            if (offsetStart == -1 || offsetEnd == -1) {
                ssb.append(text.subSequence(start, text.length()));
                break;
            } else {
                // [之前的串
                CharSequence csBefore = text.subSequence(start, offsetStart);
                ssb.append(csBefore);
                // []之间的串
                CharSequence csBetween = text.subSequence(offsetStart + tagLeftLen, offsetEnd);
                ssb.append(csBetween);
                // LogUtil.d("csBefore = " + csBefore);
                // LogUtil.d("csBetween = " + csBetween);

                int spanEnd = ssb.length();
                int spanStart = spanEnd - csBetween.length();
                // LogUtil.d("spanEnd = " + spanEnd);
                // LogUtil.d("spanStart = " + spanStart);

                ssb.setSpan(new ForegroundColorSpan(highlightColor), spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        super.setText(ssb);
    }

    /**
     * 使用自定义高亮关键字设置文本内容
     *
     * @param tv      TextView
     * @param text    文本内容
     * @param keyword 自定义关键字
     */
    public static void setTextWithKeyword(TextView tv, String text, String keyword) {
        if (text == null) {
            text = "";
        }
        if (TextUtils.isEmpty(keyword)) {
            tv.setText(text);
        } else {
            int offset = text.indexOf(keyword, 0);
            Spannable word2Span = new SpannableString(text);
            for (int start = 0; start < text.length() && offset != -1; start = offset + 1) {
                offset = text.indexOf(keyword, start);
                if (offset == -1) {
                    break;
                } else {
                    word2Span.setSpan(new ForegroundColorSpan(mHighlightColor), offset, offset + keyword.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setText(word2Span, BufferType.SPANNABLE);
        }
    }

    /**
     * 将高亮词转换为普通词
     *
     * @param highlightText
     * @return
     */
    public static String highlight2Normal(String highlightText) {
        return highlightText.replaceAll(DEFAULT_TAG_LEFT, "").replaceAll(DEFAULT_TAG_RIGHTL, "");
    }

}
