package com.getz.setthegoal.ui.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class ExpandableTextView extends TextView {

    public static final int STANDARD_LINE_COUNT = Integer.MAX_VALUE;

    private final List<OnExpandListener> onExpandListeners;
    private TimeInterpolator expandInterpolator;
    private TimeInterpolator collapseInterpolator;

    private final int originalMaxLines;
    private long animationDuration = 500;
    private boolean animating;
    private int lastCollapsedValue;

    public ExpandableTextView(final Context context) {
        this(context, null);
    }

    public ExpandableTextView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        // keep the original value of maxLines
        this.originalMaxLines = this.getMaxLines();

        // create bucket of OnExpandListener instances
        this.onExpandListeners = new ArrayList<>();

        // create default interpolators
        this.expandInterpolator = new AccelerateDecelerateInterpolator();
        this.collapseInterpolator = new AccelerateDecelerateInterpolator();
    }

    public void expand() {
        if (!this.animating && this.originalMaxLines >= 0) {
            this.notifyOnStartExpand();
            this.measure(
                    MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            );
            final int collapsedHeight = this.getMeasuredHeight();
            this.animating = true;
            this.setMaxLines(STANDARD_LINE_COUNT);
            this.setEllipsize(null);
            this.measure(
                    MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            );
            final int expandedHeight = this.getMeasuredHeight();
            final ValueAnimator valueAnimator = ValueAnimator.ofInt(collapsedHeight, expandedHeight);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    ExpandableTextView.this.setHeight((int) animation.getAnimatedValue());
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(final Animator animation) {
                    ExpandableTextView.this.setMaxHeight(Integer.MAX_VALUE);
                    ExpandableTextView.this.setMinHeight(0);
                    ExpandableTextView.this.animating = false;
                    ExpandableTextView.this.notifyOnEndExpand();
                }
            });
            valueAnimator.setInterpolator(this.expandInterpolator);
            valueAnimator
                    .setDuration(this.animationDuration)
                    .start();
        }
    }

    public void collapse() {
        if (!this.animating && this.originalMaxLines >= 0) {
            this.notifyOnStartCollapse();
            ExpandableTextView.this.measure(
                    MeasureSpec.makeMeasureSpec(ExpandableTextView.this.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            );
            final int expandedH = ExpandableTextView.this.getMeasuredHeight();
            ExpandableTextView.this.setMaxLines(ExpandableTextView.this.originalMaxLines);
            ExpandableTextView.this.measure(
                    MeasureSpec.makeMeasureSpec(ExpandableTextView.this.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            );
            final int collapsedH = ExpandableTextView.this.getMeasuredHeight();
            this.animating = true;
            final ValueAnimator valueAnimator = ValueAnimator.ofInt(expandedH, collapsedH);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    lastCollapsedValue = (int) animation.getAnimatedValue();
                    ExpandableTextView.this.setHeight(lastCollapsedValue);
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(final Animator animation) {
                    ExpandableTextView.this.animating = false;
                    ExpandableTextView.this.setMinHeight(0);
                    ExpandableTextView.this.setMaxHeight(lastCollapsedValue);
                    ExpandableTextView.this.setEllipsize(TextUtils.TruncateAt.END);
                    ExpandableTextView.this.setMaxLines(ExpandableTextView.this.originalMaxLines);
                    ExpandableTextView.this.notifyOnEndCollapse();
                }
            });
            valueAnimator.setInterpolator(this.collapseInterpolator);
            valueAnimator
                    .setDuration(this.animationDuration)
                    .start();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final boolean isEllipsize = !((ExpandableTextView.this.getLayout().getText().toString()).equalsIgnoreCase(ExpandableTextView.this.getText().toString()));
        final int realLineCount = ExpandableTextView.this.getLineCount();

        notifyOnDrewInLayout(isEllipsize, realLineCount);
    }

    public void setAnimationDuration(final long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void addOnExpandListener(final OnExpandListener onExpandListener) {
        this.onExpandListeners.add(onExpandListener);
    }

    public void removeOnExpandListener(final OnExpandListener onExpandListener) {
        this.onExpandListeners.remove(onExpandListener);
    }

    public void setExpandInterpolator(final TimeInterpolator expandInterpolator) {
        this.expandInterpolator = expandInterpolator;
    }

    public TimeInterpolator getExpandInterpolator() {
        return this.expandInterpolator;
    }

    public void setCollapseInterpolator(final TimeInterpolator collapseInterpolator) {
        this.collapseInterpolator = collapseInterpolator;
    }

    public TimeInterpolator getCollapseInterpolator() {
        return this.collapseInterpolator;
    }

    private void notifyOnStartCollapse() {
        for (final OnExpandListener onExpandListener : this.onExpandListeners) {
            onExpandListener.onStartCollapse(this);
        }
    }

    private void notifyOnStartExpand() {
        for (final OnExpandListener onExpandListener : this.onExpandListeners) {
            onExpandListener.onStartExpand(this);
        }
    }

    private void notifyOnEndExpand() {
        for (final OnExpandListener onExpandListener : this.onExpandListeners) {
            onExpandListener.onEndExpand(this);
        }
    }

    private void notifyOnEndCollapse() {
        for (final OnExpandListener onExpandListener : this.onExpandListeners) {
            onExpandListener.onEndCollapse(this);
        }
    }

    private void notifyOnDrewInLayout(boolean isEllipsize, int realLineCount) {
        for (final OnExpandListener onExpandListener : this.onExpandListeners) {
            onExpandListener.onDrewInLayout(isEllipsize, realLineCount);
        }
    }

    public interface OnExpandListener {

        void onStartExpand(@NonNull ExpandableTextView view);

        void onEndExpand(@NonNull ExpandableTextView view);

        void onStartCollapse(@NonNull ExpandableTextView view);

        void onEndCollapse(@NonNull ExpandableTextView view);

        void onDrewInLayout(boolean isEllipsize, int realLineCount);
    }

    public int getOriginalMaxLines() {
        return originalMaxLines;
    }

    public void toggle() {
        if (ExpandableTextView.this.getMaxLines() == originalMaxLines) {
            expand();
        } else {
            collapse();
        }
    }
}