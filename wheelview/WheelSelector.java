package com.huahan.finance.wheelview;

import java.util.Map;

import android.view.View;

import com.huahan.benefitfinance.R;

public class WheelSelector {
	private View view;
	private SelectorWheelView first;
	private SelectorWheelView second;
	private SelectorWheelView third;
	private  int wheelCount=3;
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}


	public WheelSelector(View view) {
		super();
		this.view = view;
		setView(view);
	}
	public WheelSelector(View view,int wheelCount) {
		super();
		this.view = view;
		this.wheelCount=wheelCount;
		setView(view);
	}
	
	/**
	 * 数组选择器
	 * @param listMap   
	 * @param labMap 需要显示的数组 map 的key 为 0 1 2 ，map 的长度对应 所需选择器个数
	 * @param selectionMap     设置与徐安中位置
	 */
	public void setPicker(Map<Integer , String []> listMap,Map<Integer , Integer> selectionMap) {


		first = (SelectorWheelView) view.findViewById(R.id.first);
		second = (SelectorWheelView) view.findViewById(R.id.second);
		third = (SelectorWheelView) view.findViewById(R.id.third);
		switch (wheelCount)
		{
		case 3:
			first.setVisibility(View.VISIBLE);
			second.setVisibility(View.VISIBLE);
			third.setVisibility(View.VISIBLE);
			for (int i = 0; i <listMap.get(0).length; i++)
			{
				first.addData(listMap.get(0)[i]);
			}
			for (int i = 0; i <listMap.get(1).length; i++)
			{
				second.addData(listMap.get(1)[i]);
			}
			for (int i = 0; i <listMap.get(2).length; i++)
			{
				third.addData(listMap.get(2)[i]);
			}
			first.setCenterItem(selectionMap.get(0));
			second.setCenterItem(selectionMap.get(1));
			third.setCenterItem(selectionMap.get(2));
			break;
		case 2:
			first.setVisibility(View.VISIBLE);
			second.setVisibility(View.VISIBLE);
			for (int i = 0; i <listMap.get(0).length; i++)
			{
				first.addData(listMap.get(0)[i]);
			}
			for (int i = 0; i <listMap.get(1).length; i++)
			{
				second.addData(listMap.get(1)[i]);
			}
			first.setCenterItem(selectionMap.get(0));
			second.setCenterItem(selectionMap.get(1));
			break;
		case 1:
			first.setVisibility(View.VISIBLE);
			for (int i = 0; i <listMap.get(0).length; i++)
			{
				first.addData(listMap.get(0)[i]);
			}
			first.setCenterItem(selectionMap.get(0));
			break;

		default:
			break;
		}

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		switch(wheelCount){
		case 3:
			textSize = 16;
			break;
		case 2:
			textSize = 18;
			third.setVisibility(View.GONE);
			break;
		case 1:
			textSize = 20;
			second.setVisibility(View.GONE);
			third.setVisibility(View.GONE);
			break;
		}
			
		first.setTextSize(textSize);
		second.setTextSize(textSize);
		second.setTextSize(textSize);

	}

	/**
	 * 设置是否循环滚动
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic){
		first.setCircle(cyclic);
		second.setCircle(cyclic);
		third.setCircle(cyclic);
	}
	public int[] getSelecor() 
	{
		int [] selectPosi=new int [wheelCount];
		
		switch (wheelCount)
		{
		case 1:
			selectPosi[0]=first.getCenterPosi();
			break;
		case 2:
			selectPosi[0]=first.getCenterPosi();
			selectPosi[1]=second.getCenterPosi();
			break;
		case 3:
			selectPosi[0]=first.getCenterPosi();
			selectPosi[1]=second.getCenterPosi();
			selectPosi[2]=third.getCenterPosi();
			break;

		default:
			break;
		}
		
		return selectPosi;
	}
}
