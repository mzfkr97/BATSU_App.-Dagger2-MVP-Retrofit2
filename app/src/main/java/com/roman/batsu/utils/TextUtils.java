package com.roman.batsu.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextUtils {

    /**
     * Set text of textView with html format of html parameter
     *
     * @param textView instance {@link TextView}
     * @param html     String
     */
    @SuppressLint("ClickableViewAccessibility")
    public static void setTextWithLinks(TextView textView, CharSequence html) {
        textView.setText(html);
        textView.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                TextView widget = (TextView) v;
                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = Spannable.Factory.getInstance()
                        .newSpannable(widget.getText())
                        .getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                    }
                    return true;
                }
            }
            return false;
        });
    }


    /**
     * Change string to html format
     *
     * @param htmlText String text
     * @return String text
     */
    public static CharSequence fromHtml(String htmlText) {
        if (android.text.TextUtils.isEmpty(htmlText)) {
            return null;
        }
        CharSequence spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(htmlText);
        }
        return trim(spanned);
    }


    /**
     * Trim string text
     *
     * @param charSequence String text
     * @return String text
     */

    private static CharSequence trim(CharSequence charSequence) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        int end = charSequence.length() - 1;
        while (Character.isWhitespace(charSequence.charAt(end))) {
            end--;
        }
        return charSequence.subSequence(0, end + 1);
    }
}
