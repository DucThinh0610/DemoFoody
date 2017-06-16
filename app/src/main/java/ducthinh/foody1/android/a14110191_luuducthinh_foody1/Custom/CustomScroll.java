package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Test on 4/2/2017.
 */

public class CustomScroll extends ScrollView { //tạo 1 custom scroll có tác dụng để scroll 2 gridview cùng lúc
    private ScrollViewListener scrollViewListener = null;
    public CustomScroll(Context context) {
        super(context);
    }
    public CustomScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}

