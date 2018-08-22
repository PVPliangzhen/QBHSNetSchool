package com.qbhsnetschool.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;

import java.lang.ref.WeakReference;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

public class TabGroupLayout extends LinearLayout {

    private static final boolean DEBUG = true;
    private static final String TAG = TabGroupLayout.class.getSimpleName();

    public static final int TAB_LAYOUT_FORM_CHILD = 0;
    public static final int TAB_LAYOUT_FROM_PARENT = 1;

    public static final int CHAIN_STYLE_SPREAD = 0;

    private static final int NO_POSITION = -1;
    private static final int ANIMATION_DURATION = 300;
    static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();

    protected int mTabCount;
    protected Tab[] mTabs;

    private int mGravity = -1;

    private int mIndicatorLeft = NO_POSITION;
    private int mIndicatorRight = NO_POSITION;

    private Paint mSelectedIndicatorPaint = new Paint();

    private int mSelectedIndicatorHeight;
    private int mSelectedIndicatorWidth;
    private RectF tempDrawRect = new RectF();

    private int mTabLayout;
    private int mChainStyle = CHAIN_STYLE_SPREAD;


    private TextRender unSelectTextRender, selectedTextRender;

    private ValueAnimator mIndicatorAnimator;

    public interface OnHideSoftInputListener{
        void hideSoftInput();
    }

    public static OnHideSoftInputListener onHideSoftInputListener;

    public void setOnHideSoftInputListener(OnHideSoftInputListener onHideSoftInputListener) {
        this.onHideSoftInputListener = onHideSoftInputListener;
    }

    public static class TabSelector implements OnClickListener {

        protected int mSelectedPosition = NO_POSITION;
        private final TabGroupLayout mParent;
        private ViewPager mViewPager;

        public TabSelector(TabGroupLayout parent) {
            this.mParent = parent;
        }

        @Override
        public void onClick(View v) {
            final int toPosition = indexOfTab(v);
            if (toPosition != mSelectedPosition) {
                animateToTab(toPosition);
            }
            if (mViewPager != null)
                mViewPager.setCurrentItem(toPosition);
            if (onHideSoftInputListener != null){
                onHideSoftInputListener.hideSoftInput();
            }
        }

        private void animateToTab(int newPosition) {
            if (mParent != null)
                mParent.animateIndicatorToPosition(newPosition, ANIMATION_DURATION);
        }

        private int indexOfTab(View view) {
            if (mParent == null || mParent.mTabCount == 0) return NO_POSITION;
            for (int i = 0; i < mParent.mTabCount; i++)
                if (mParent.mTabs[i].mView == view) return i;
            return NO_POSITION;
        }

        public void setSelected(int position) {
            if (mSelectedPosition != position) {
                if (mSelectedPosition != NO_POSITION)
                    mParent.mTabs[mSelectedPosition].setSelected(false);
                mParent.mTabs[position].setSelected(true);
                mSelectedPosition = position;
            }
        }

        public int getSelected() {
            return mSelectedPosition;
        }

    }

    private static final class TextRender {
        float textSize;
        ColorStateList textColor;
        boolean textStyleBold;
    }

    public TabSelector getmTabSelector() {
        return mTabSelector;
    }

    public final TabSelector mTabSelector = new TabSelector(this);

    public TabGroupLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TabGroupLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabGroupLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOrientation(HORIZONTAL);
        setGravity(mGravity = Gravity.CENTER);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.TabGroupLayout, defStyleAttr, defStyleRes);
        mSelectedIndicatorWidth = a.getDimensionPixelSize(
                R.styleable.TabGroupLayout_indicatorWidth, dpToPx(30)
        );
        mSelectedIndicatorHeight = a.getDimensionPixelSize(
                R.styleable.TabGroupLayout_indicatorHeight, dpToPx(4)
        );
        final int indicatorPaintColor = a.getColor(
                R.styleable.TabGroupLayout_indicatorColor, Color.WHITE
        );
        int tabLayoutStyle = TAB_LAYOUT_FORM_CHILD;
        if (a.hasValue(R.styleable.TabGroupLayout_tabLayout)) {
            tabLayoutStyle = a.getInt(R.styleable.TabGroupLayout_tabLayout, TAB_LAYOUT_FORM_CHILD);
        }
        if (tabLayoutStyle == TAB_LAYOUT_FROM_PARENT) {
            mChainStyle = a.getInt(R.styleable.TabGroupLayout_chainStyle, CHAIN_STYLE_SPREAD);
        }
        this.mTabLayout = tabLayoutStyle;
        //未选中选项文本样式
        if (a.hasValue(R.styleable.TabGroupLayout_tabTextAppearance)) {
            final int tabTextAppearance = a.getResourceId(R.styleable.TabGroupLayout_tabTextAppearance,
                    0);
            if (tabTextAppearance != 0) {
                unSelectTextRender = new TextRender();
                final TypedArray ta = context.obtainStyledAttributes(tabTextAppearance,
                        android.support.v7.appcompat.R.styleable.TextAppearance);
                try {
                    unSelectTextRender.textSize = getTextSizeFromAttr(ta);
                    unSelectTextRender.textColor = getTextColorFromAttr(ta);
                    unSelectTextRender.textStyleBold = getTextStyleBoldFromAttr(ta);
                } finally {
                    ta.recycle();
                }
            }
        }
        //选中选项文本样式
        if (a.hasValue(R.styleable.TabGroupLayout_tabSelectedTextAppearance)) {
            final int tabTextAppearance = a.getResourceId(R.styleable.TabGroupLayout_tabSelectedTextAppearance,
                    0);
            if (tabTextAppearance != 0) {
                selectedTextRender = new TextRender();
                final TypedArray ta = context.obtainStyledAttributes(tabTextAppearance,
                        android.support.v7.appcompat.R.styleable.TextAppearance);
                try {
                    selectedTextRender.textSize = getTextSizeFromAttr(ta);
                    selectedTextRender.textColor = getTextColorFromAttr(ta);
                    selectedTextRender.textStyleBold = getTextStyleBoldFromAttr(ta);
                } finally {
                    ta.recycle();
                }
            }
        }
        a.recycle();
        if (selectedTextRender == null)
            selectedTextRender = unSelectTextRender;
        mSelectedIndicatorPaint.setColor(indicatorPaintColor);
    }

    private float getTextSizeFromAttr(TypedArray t) {
        return t.getDimensionPixelSize(
                android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, 0);
    }

    private ColorStateList getTextColorFromAttr(TypedArray t) {
        return t.getColorStateList(
                android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
    }

    private boolean getTextStyleBoldFromAttr(TypedArray t) {
        final int styleIndex = t.getInt(
                android.support.v7.appcompat.R.styleable.TextAppearance_android_textStyle, -1);
        if (styleIndex == -1)
            return false;
        return ((styleIndex & Typeface.BOLD) == Typeface.BOLD);
    }


    public void setupViewPager(ViewPager viewPager) {
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(new OnPageChangeListener(this));
            mTabSelector.mViewPager = viewPager;
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (mTabLayout == TAB_LAYOUT_FROM_PARENT && childCount > 0 && mTabCount > 0) {
            if (getOrientation() == HORIZONTAL) {
                doLayoutHorizontal(l, t, r, b, childCount, mTabCount, mChainStyle);
            }
            return;
        }

        super.onLayout(changed, l, t, r, b);

    }

    void doLayoutHorizontal(int l, int t, int r, int b, int childCount, int tabCount, int chainStyle) {
        final int minorGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;

        int parentWidth = (r - l);
        int parentHeight = (b - t);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int usedWidth = paddingLeft + paddingRight;
        View child;
        LayoutParams layoutParams;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            layoutParams = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() == GONE)
                continue;
            usedWidth += child.getMeasuredWidth();
            usedWidth += (layoutParams.leftMargin + layoutParams.rightMargin);
        }

        int spaceWidth = 0;
        int spaceCount = tabCount;
        if ((chainStyle == CHAIN_STYLE_SPREAD))
            spaceCount += 1;
        if (usedWidth < parentWidth) {
            spaceWidth = (parentWidth - usedWidth) / spaceCount;
        }

        int childTop;
        int childLeft = paddingLeft;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            layoutParams = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() == GONE)
                continue;
            if (layoutParams.tab == LayoutParams.TRUE)
                childLeft += spaceWidth;

            int gravity = layoutParams.gravity;
            if (gravity < 0) {
                gravity = minorGravity;
            }


            switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
                case Gravity.TOP:
                    childTop = paddingTop + layoutParams.topMargin;
                    break;

                case Gravity.CENTER_VERTICAL:
                    childTop = paddingTop + ((parentHeight - paddingTop - paddingBottom - child.getMeasuredHeight()) / 2)
                            + layoutParams.topMargin - layoutParams.bottomMargin;
                    break;

                case Gravity.BOTTOM:
                    childTop = parentHeight - paddingBottom - child.getMeasuredHeight() - layoutParams.bottomMargin;
                    break;
                default:
                    childTop = paddingTop;
                    break;
            }


            childLeft += layoutParams.leftMargin;
            setChildFrame(child, childLeft, childTop + layoutParams.topMargin, child.getMeasuredWidth(), child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth();
            childLeft += layoutParams.rightMargin;
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int selected = mTabSelector.getSelected();
        if (mIndicatorRight - mIndicatorLeft <= 0) {
            if (selected > NO_POSITION) {
                mIndicatorLeft = getIndicatorPositionLeft(mTabs[selected], mSelectedIndicatorWidth);
                mIndicatorRight = getIndicatorPositionRight(mTabs[selected], mSelectedIndicatorWidth);
            }
        }

        if (mIndicatorRight > mIndicatorLeft) {
            tempDrawRect.set(mIndicatorLeft, getHeight() - mSelectedIndicatorHeight,
                    mIndicatorRight, getHeight());
            float radius = mSelectedIndicatorHeight / 2;
            canvas.drawRoundRect(tempDrawRect, radius, radius, mSelectedIndicatorPaint);
//            canvas.drawRect(mIndicatorLeft, getHeight() - mSelectedIndicatorHeight,
//                    mIndicatorRight, getHeight(), mSelectedIndicatorPaint);
        }
    }

    boolean animateIndicatorToPosition(final int position, int duration) {
        if (mIndicatorAnimator != null && mIndicatorAnimator.isRunning()) {
            mIndicatorAnimator.cancel();
        }
        if (position < 0 || position >= mTabCount)
            return false;
        final Tab target = mTabs[position];
        if (target == null)
            return false;
        final int startLeft = mIndicatorLeft;
        final int startRight = mIndicatorRight;
        if (startRight - startLeft == 0) {
            return false;
        }
        final int targetLeft = getIndicatorPositionLeft(target, mSelectedIndicatorWidth);
        final int targetRight = getIndicatorPositionRight(target, mSelectedIndicatorWidth);
        if (startLeft != targetLeft || startRight != targetRight) {
            ValueAnimator animator = mIndicatorAnimator = new ValueAnimator();
            animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            animator.setDuration(duration);
            animator.setFloatValues(0, 1);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    final float fraction = animator.getAnimatedFraction();
                    setIndicatorPosition(
                            lerp(startLeft, targetLeft, fraction),
                            lerp(startRight, targetRight, fraction));
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animator) {
                    selectTab(position, true);
                }
            });
            animator.start();
        }
        return true;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childCount = getChildCount();
        if (childCount > 0) {
            View child;
            LayoutParams lps;
            int tab = 0;
            /*统计选项（TAB）标记视图数*/
            for (int i = 0; i < childCount; i++) {
                child = getChildAt(i);
                if (child != null) {
                    lps = (LayoutParams) child.getLayoutParams();
                    if (lps != null && lps.tab == LayoutParams.TRUE) tab++;
                }
            }
            /*初始化标签*/
            final int tabCount;
            mTabCount = tabCount = tab;
            tab = 0;
            mTabs = new Tab[tabCount];
            Tab ta;
            for (int i = 0; i < childCount; i++) {
                child = getChildAt(i);
                if (child != null) {
                    lps = (LayoutParams) child.getLayoutParams();
                    if (lps != null && lps.tab == LayoutParams.TRUE) {
                        child.setOnClickListener(mTabSelector);
                        ta = mTabs[tab] = new Tab(child, this, tab++);
                        if (lps.selected == LayoutParams.TRUE) {
                            mTabSelector.setSelected(ta.mPosition);
                        }
                        ta.renderView(ta.mPosition == mTabSelector.getSelected());

                    }

                }
            }

            if (tabCount > 0 && mTabSelector.getSelected() == NO_POSITION) {
                mTabSelector.setSelected(0);
                mTabs[0].renderView(true);
            }

        }

        if (DEBUG) Log.i(TAG, "onFinishInflate selected=" + mTabSelector.getSelected());

    }


    void setIndicatorPosition(int left, int right) {
        if (left != mIndicatorLeft || right != mIndicatorRight) {
            // If the indicator's left/right has changed, invalidate
            mIndicatorLeft = left;
            mIndicatorRight = right;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    void selectTab(int nextPosition, boolean updateIndicator) {
        final int position = mTabSelector.getSelected();
        if (position != nextPosition || updateIndicator) {
            if (position != NO_POSITION)
                mTabs[position].renderView(false);
            mTabs[nextPosition].renderView(true);
        }
        mTabSelector.setSelected(nextPosition);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    private static int getIndicatorPositionLeft(Tab tab, int indicatorWidth) {
        return (tab.mView.getLeft() + tab.mView.getRight() - indicatorWidth) / 2;
    }

    private static int getIndicatorPositionRight(Tab tab, int indicatorWidth) {
        return (tab.mView.getLeft() + tab.mView.getRight() + indicatorWidth) / 2;
    }

    void setScrollPosition(int position, float positionOffset, boolean updateSelectedText,
                           boolean updateIndicatorPosition) {
        final int nextPosition = positionOffset < 0 ? position - 1 : position + 1;
        if (nextPosition < 0 || nextPosition >= mTabCount)
            return;
        slideIndicatorPosition(
                position, nextPosition
                , positionOffset < 0 ? 1 + positionOffset : positionOffset
        );

    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    private void slideIndicatorPosition(int selectedPosition, int nextPosition, float selectedOffset) {
        if (selectedPosition != nextPosition) {
            final int indicatorWidth = mSelectedIndicatorWidth;
            final int curIndicatorOffset = getIndicatorPositionLeft(mTabs[selectedPosition], indicatorWidth);
            final int overOffset = getIndicatorPositionLeft(mTabs[nextPosition], indicatorWidth) - curIndicatorOffset;
            final int intDiff = Math.round(selectedOffset * overOffset);
            setIndicatorPosition(
                    curIndicatorOffset + intDiff
                    , getIndicatorPositionRight(mTabs[selectedPosition], indicatorWidth) + intDiff
            );
        }
    }


    static int lerp(int startValue, int endValue, float fraction) {
        return startValue + Math.round(fraction * (endValue - startValue));
    }

    int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    protected int getSelectedTabPosition() {
        return mTabSelector.getSelected();
    }

    public int getTabCount() {
        return mTabCount;
    }


    public static class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final WeakReference<TabGroupLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public OnPageChangeListener(TabGroupLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            final TabGroupLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null) {
                // Only update the text selection if we're not settling, or we are settling after
                // being dragged
                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
                        mPreviousScrollState == SCROLL_STATE_DRAGGING;
                // Update the indicator if we're not settling after being idle. This is caused
                // from a setCurrentItem() call and will be handled by an animation from
                // onPageSelected() instead.
                final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING
                        && mPreviousScrollState == SCROLL_STATE_IDLE);
                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        }

        @Override
        public void onPageSelected(int position) {
            final TabGroupLayout tabLayout = mTabLayoutRef.get();

            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position
                    && position < tabLayout.getTabCount()) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
                final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE
                        || (mScrollState == SCROLL_STATE_SETTLING
                        && mPreviousScrollState == SCROLL_STATE_IDLE);
                tabLayout.selectTab(position, !updateIndicator);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }
    }


    public static class LayoutParams extends LinearLayout.LayoutParams {
        public static final int FALSE = 0;
        public static final int TRUE = 1;

        public int tab = FALSE;
        public int selected = FALSE;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a =
                    c.obtainStyledAttributes(attrs, R.styleable.TabGroupLayout_Layout);
            tab = a.getInt(R.styleable.TabGroupLayout_Layout_tabView, FALSE);
            selected = a.getInt(R.styleable.TabGroupLayout_Layout_tabSelected, FALSE);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    private final static class Tab {
        final View mView;
        final TabGroupLayout mParent;
        final int mPosition;

        public Tab(View view, TabGroupLayout parent, int position) {
            this.mView = view;
            this.mParent = parent;
            this.mPosition = position;
        }

        void renderView(boolean selected) {
            if (null != mView && null != mParent) {
                dispatchViewRender(selected);
                mView.postInvalidateOnAnimation();
            }
        }

        final void setSelected(boolean selected) {
            if (mView != null) {
                ((LayoutParams) mView.getLayoutParams()).selected =
                        (selected ? LayoutParams.TRUE : LayoutParams.FALSE);
            }
        }

        private void dispatchViewRender(boolean selected) {
            if (mView instanceof TextView) {
                onTextViewRender(
                        selected
                        , (TextView) mView,
                        mParent.selectedTextRender == null ? mParent.unSelectTextRender : mParent.selectedTextRender
                        , mParent.unSelectTextRender
                );
            }
        }


        private void onTextViewRender(boolean selected, TextView target, TextRender
                selectedTextRender, TextRender unSelectTextRender) {
            final ColorStateList defaultTextColor = ColorStateList.valueOf(Color.WHITE);
            target.setTextColor(
                    selected ? (selectedTextRender == null ? defaultTextColor : selectedTextRender.textColor)
                            : (unSelectTextRender == null ? defaultTextColor : unSelectTextRender.textColor)
            );
            target.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    selected ? (selectedTextRender == null ? target.getTextSize() : selectedTextRender.textSize)
                            : (unSelectTextRender == null ? target.getTextSize() : unSelectTextRender.textSize)
            );

            final boolean textStyleBold = (
                    selected && selectedTextRender != null && selectedTextRender.textStyleBold)
                    || (!selected && unSelectTextRender != null && unSelectTextRender.textStyleBold
            );
            target.setTypeface(Typeface.defaultFromStyle(textStyleBold ? Typeface.BOLD : Typeface.NORMAL));

//            target.getPaint().setFakeBoldText(
//                    (selected && selectedTextRender != null && selectedTextRender.textBold)
//                            || (!selected && unSelectTextRender != null && unSelectTextRender.textBold)
//            );


        }

    }
}
