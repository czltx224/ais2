package com.xbwl.common.bean;

/**
 * 封装前台CheckBoxGroup选项
 * @author yab
 *
 */
public class CheckBoxGroup {
	private String boxLabel;
	
	private String name;
	
	private String inputValue;
	
	private boolean checked;

	public String getBoxLabel() {
		return boxLabel;
	}

	public void setBoxLabel(String boxLabel) {
		this.boxLabel = boxLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
