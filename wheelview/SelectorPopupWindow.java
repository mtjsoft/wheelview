package com.huahan.finance.wheelview;

import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huahan.benefitfinance.R;
import com.huahan.hhbaseutils.HHScreenUtils;
import com.huahan.hhbaseutils.HHViewHelper;

/**
 * 选择器
 * 
 * @author Sai
 * 
 */
public class SelectorPopupWindow extends PopupWindow implements OnClickListener
{

	private View rootView; // 总的布局
	WheelSelector wheelSelector;
	private View btnSubmit, btnCancel;
	private TextView useTextView;// 控件用途
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";
	private OnSelectListener timeSelectListener;
	private LinearLayout contentLayout;

	
	public SelectorPopupWindow(Context context)
	{
		
	}

	/**
	 * 显示弹出框
	 * @param context 
	 * @param hint  显示提示所语如：选择性别
	 * @param wheelCount 需要轮子数量
	 * @param listMap    轮子显示数据长度与wheelCount 对应  key 为0  1  2  
	 * @param selectionMap  初始化每个轮子停留位置  轮子显示数据长度与wheelCount 对应  key 为0  1  2  
	 */
	public SelectorPopupWindow(Context context, String hint,int wheelCount,Map<Integer , String []> listMap,Map<Integer , Integer> selectionMap)
	{
		super(context);
		this.setWidth(LayoutParams.MATCH_PARENT);
		int width=HHScreenUtils.getScreenWidth(context);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.timepopwindow_anim_style);
		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		rootView = mLayoutInflater.inflate(R.layout.pw_selector, null);
		contentLayout=HHViewHelper.getViewByID(rootView, R.id.ll_pop);
		RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width*3/5);
		rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		contentLayout.setLayoutParams(rl);
		// -----确定和取消按钮
		btnSubmit = rootView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = rootView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		useTextView = (TextView) rootView.findViewById(R.id.tv_use);
		useTextView.setText(hint);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		// ----选择器
		final View selectorview = rootView.findViewById(R.id.selector);
		wheelSelector = new WheelSelector(selectorview, wheelCount);
		wheelSelector.setPicker(listMap,selectionMap);
		setContentView(rootView);
		rootView.setOnTouchListener(new View.OnTouchListener()
		{

			public boolean onTouch(View v, MotionEvent event)
			{

				int height = rootView.findViewById(R.id.ll_pop).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					if (y < height)
					{
						dismiss();
					}
				}
				return true;
			}
		});
	}

	/**
	 * 置顶 Popuwindos 显示位置
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 * @param listMap
	 * @param labMap
	 */
	public void showAtLocation(View parent, int gravity, int x, int y)
	{
		update();
		super.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 设置是否循环滚动
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic)
	{
		wheelSelector.setCyclic(cyclic);
	}

	@Override
	public void onClick(View v)
	{
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL))
		{
			dismiss();
			return;
		} else
		{
			if (timeSelectListener != null)
			{
				timeSelectListener.onSelect(wheelSelector.getSelecor());
			}
			dismiss();
			return;
		}
	}

	public interface OnSelectListener
	{
		/**
		 * 被选中位置 数组
		 * @param selectorPosi
		 */
		public void onSelect(int [] selectorPosi);
	}

	public void setOnSelectListener(OnSelectListener timeSelectListener)
	{
		this.timeSelectListener = timeSelectListener;
	}

}
