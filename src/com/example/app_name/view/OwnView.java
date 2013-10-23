package com.example.app_name.view;

import com.example.app_name.AbstractSecondaryActivity;
import com.example.app_name.SecondActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

public class OwnView extends AbstarctOwnView {
	static int distanceToMove=60;
	AbstractSecondaryActivity sec;
	Point leftTopCorner = new Point(0,0);//left top corner of view
	Point locationRel = new Point();
	int size = 50;
	int deep = 4;
	String text="";
	int direction_to_move=-1;
	Paint paint = new Paint();
	SecondActivity activity;
	public OwnView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnClickListener(this);
	}
	public OwnView(Context context) {
        super(context, null);
        setOnClickListener(this);
    }

    public OwnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }
	
    @SuppressLint("DrawAllocation")
	@Override
    protected void onDraw(Canvas canvas) {
    	paint.setAntiAlias(true);
        paint.setDither(true);
    	paint.setColor(Color.rgb(99, 99, 99));
        paint.setStrokeWidth(3);
        RectF rf = new RectF(leftTopCorner.x, leftTopCorner.y, leftTopCorner.x+size, leftTopCorner.y+size);
        canvas.drawRoundRect(rf, 8, 8, paint);
        paint.setColor(Color.rgb(130, 130, 130));
        RectF rf2 = new RectF(leftTopCorner.x+deep, leftTopCorner.y+deep, leftTopCorner.x+size-deep-1, leftTopCorner.y+size-deep-1);
        canvas.drawRoundRect(rf2, 5, 5, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(size/2);
        if(text.length()<2){
        	canvas.drawText(text, leftTopCorner.x+size/2 -(int)size/10-size/40, leftTopCorner.y+size/2+size/4-size/12, paint);
        }else{
        	canvas.drawText(text, leftTopCorner.x+size/2 -(int)size/5-size/20, leftTopCorner.y+size/2+size/4-size/12, paint);
        }
    }
    public void setSecondActivity(AbstractSecondaryActivity s){
    	this.sec=s;
    }
    public void setPositionOfLeftTopCorner(Point p){
    	if(p.x>0&&p.y>0){
    		this.leftTopCorner=p;
    	}
    }
    @Override
    public void setLocationRel(Point p){
    	this.locationRel=p;
    }
    public void setSize(int size){
    	if(size>0){
    		this.size=size;
    		distanceToMove=size+10;
    	}
    }
    public void setDeep(int d){
    	if(d>0){
    		this.deep=d;
    	}
    }
    public void setText(String text){
    	this.text=text;
    }
    public void setDirectionToMove(int d){
    	direction_to_move=d;
    }
    
    @Override
    public int getDirectionToMove(){
    	return direction_to_move;
    }
    
    @Override
    public void draw(){
    	LayoutParams lp = (LayoutParams) this.getLayoutParams();
		switch(direction_to_move){
			case 0://down
				locationRel.x++;
				lp.setMargins(lp.leftMargin, lp.topMargin+distanceToMove, 0, 0);
				break;
			case 1://left
				locationRel.y--;
				lp.setMargins(lp.leftMargin-distanceToMove, lp.topMargin, 0, 0);
				break;
			case 2://up
				locationRel.x--;
				lp.setMargins(lp.leftMargin, lp.topMargin-distanceToMove, 0, 0);
				break;
			case 3://right
				locationRel.y++;
				lp.setMargins(lp.leftMargin+distanceToMove, lp.topMargin, 0, 0);
				break;
		}
		this.setLayoutParams(lp);
    }
	@Override
	public void onClick(View v) {
		sec.recalculate(this);
	}
	
	@Override
	public Point getLocationRel() {
		return locationRel;
	}
}
