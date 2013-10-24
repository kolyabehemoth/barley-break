package com.app_name.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class AbstarctOwnView extends View implements OnClickListener{

	public AbstarctOwnView(Context context) {
		super(context);
	}
	
	public AbstarctOwnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	public AbstarctOwnView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}	 
	public abstract Point getLocationRel();
	public abstract int getDirectionToMove();
	public abstract void setLocationRel(Point p);
	public abstract void draw();
}
