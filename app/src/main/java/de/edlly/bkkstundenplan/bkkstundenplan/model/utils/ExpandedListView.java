package de.edlly.bkkstundenplan.bkkstundenplan.model.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExpandedListView extends ListView {


    public ExpandedListView(Context context) {
        super(context);
    }

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpc = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2 , MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpc);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();

    }
}
