function Resource(locale){
	this.locale = locale;
	this.getLanguageResource=function(){
		switch(this.locale){
			case "en_US":
				return Language_en();
			case "zh_CN":
				return Language_zh();
			default:
				return Language_zh();
		}
	};
	return this;
}
function getFCKLanguage(locale){
	switch(locale){
		case "en_US":
			return "en";
		case "zh_CN":
			return "zh-cn";
		default:
			return "zh-cn";
	}
}
/*英语*/
function Language_en(){
	this.SHORT_WEEK_SUN = "S";
	this.SHORT_WEEK_MON = "M"; 
	this.SHORT_WEEK_TUE = "T"; 
	this.SHORT_WEEK_WED = "W"; 
	this.SHORT_WEEK_THU = "T"; 
	this.SHORT_WEEK_FRI = "F"; 
	this.SHORT_WEEK_SAT = "S";
	this.FULL_WEEK_SUN = "Sunday";
	this.FULL_WEEK_MON = "Monday"; 
	this.FULL_WEEK_TUE = "Tuesday"; 
	this.FULL_WEEK_WED = "Wednesday"; 
	this.FULL_WEEK_THU = "Thursday"; 
	this.FULL_WEEK_FRI = "Friday"; 
	this.FULL_WEEK_SAT = "Saturday";
	this.HELP = "Help";
	this.TODAY = "Today";
	this.MONTH = ["January","February","March","April","May","June","July","August","September","October","November","December"];
	this.HOUR = "Hour";
	this.MINUTE = "Minute";
	this.AM = "AM";
	this.PM = "PM";
	this.SHOW = "Show";
	this.HIDE = "Hide";
	this.DELETE = "Delete";
	this.AUTHORIZE_NOW = "Now";
	this.AUTHORIZE_SELECT = "Select";
	this.AUTHORIZE_NEVER = "Never";
	this.AUTHORIZE_NOMAL = "Nomal";
	this.AUTHORIZE_FULL = "Full";
	this.CONFIRM_DELETE = "Are you sure you want to delete this?";
	this.CONFIRM_FULL_AUTHORIZE = "In full mode you will lost your permission in the node, are you sure you want to do this?";
	this.CANCEL = "Cancel";
	this.CLOSE = "Close";
	this.PREYEAR = "Prev. year";
	this.NEXTYEAR = "Next year";
	this.PREMONTH = "Prev. month";
	this.NEXTMONTH = "Next month";
	this.PRETENYEAR = "Prev. 10 year";
	this.NEXTTENYEAR = "Next 10 year";
	this.PRETHREEMONTH = "Prev. 3 month";
	this.NEXTTHREEMONTH = "Next 3 month";
	this.TATEPICKERHELP = "Date selection:\n - Use the \xab, \xbb buttons to select year\n - Use the " 
	+ String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) 
	+ " buttons to select month\n - Use the bottom buttons for faster selection.";
	this.TIMEPICKERHELP = "Time selection:\n- Click left to select hour,Click right to select minute\n- Direct click right for faster selection.";
	return this;
	return this;
}
/*简体中文*/
function Language_zh(){
	this.SHORT_WEEK_SUN = "日";
	this.SHORT_WEEK_MON = "一"; 
	this.SHORT_WEEK_TUE = "二"; 
	this.SHORT_WEEK_WED = "三"; 
	this.SHORT_WEEK_THU = "四"; 
	this.SHORT_WEEK_FRI = "五"; 
	this.SHORT_WEEK_SAT = "六";
	this.FULL_WEEK_SUN = "星期日";
	this.FULL_WEEK_MON = "星期一"; 
	this.FULL_WEEK_TUE = "星期二"; 
	this.FULL_WEEK_WED = "星期三"; 
	this.FULL_WEEK_THU = "星期四"; 
	this.FULL_WEEK_FRI = "星期五"; 
	this.FULL_WEEK_SAT = "星期六";
	this.HELP = "帮助";
	this.TODAY = "今天";
	this.MONTH = ["1月"	,"2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"];
	this.HOUR = "小时";
	this.MINUTE = "分钟";
	this.AM = "上午";
	this.PM = "下午";
	this.SHOW = "显示";
	this.HIDE = "隐藏";
	this.DELETE = "删除";
	this.AUTHORIZE_NOW = "立即";
	this.AUTHORIZE_SELECT = "指定";
	this.AUTHORIZE_NEVER = "永不";
	this.AUTHORIZE_NOMAL = "一般授权";
	this.AUTHORIZE_FULL = "完全授权";
	this.CONFIRM_DELETE = "确定删除吗？";
	this.CONFIRM_FULL_AUTHORIZE = "完全授权后授权人将不具有操作权限，确定吗？";
	this.CANCEL = "取消";
	this.CLOSE = "关闭";
	this.PREYEAR = "上一年";
	this.NEXTYEAR = "下一年";
	this.PREMONTH = "上一月";
	this.NEXTMONTH = "下一月";
	this.PRETENYEAR = "前10年";
	this.NEXTTENYEAR = "后10年";
	this.PRETHREEMONTH = "前3月";
	this.NEXTTHREEMONTH = "后3月";
	this.TATEPICKERHELP = "选择日期:\n- 点击 \xab, \xbb 按钮选择年份\n- 点击 " 
	+ String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) 
	+ " 按钮选择月份\n- 点击底部按钮可选择更多年份或月份" 
	+ "\n- 点击今天选择当天日期";
	this.TIMEPICKERHELP = "选择时间:\n- 点击左侧选小时，右侧选分钟\n- 直接点击右侧将使用默认小时可快速选择";
	return this;
}
