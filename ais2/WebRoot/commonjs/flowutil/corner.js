function CornerPoint(x,y,id){
	//alert(y);
	this.id = id;
	this.X=x;
	this.Y=y;
	this.moveable = true;
	this.onEdge = null;
	this.onNode = null;
	this.onNodeDirection = null;
}
CornerPoint.prototype.drawSelf =function(edgeDiv,readonly){
	if(readonly){
		return;
	}
	var oDiv = document.createElement("DIV");
	oDiv.className="cornerDiv";
	oDiv.style.left=this.X-2;
	oDiv.style.top=this.Y-2;
	if(this.moveable){
		oDiv.style.backgroundImage="url('"+sysPath+"/commonjs/flowutil/img/movenode.gif')";
	}
	oDiv.attachEvent("ondblclick",doubleClickHandleCreater(this,oDiv,edgeDiv));
	oDiv.attachEvent("onmousedown",mouseDownHandleCreater(null,this,oDiv));
	edgeDiv.appendChild(oDiv);
}
CornerPoint.prototype.drawTemp =function(){
	tempDiv.innerHTML="";
	var oDiv = document.createElement("DIV");
	oDiv.className="cornerDiv";
	oDiv.style.width=7;
	oDiv.style.height=7;
	oDiv.style.left=this.X-3;
	oDiv.style.top=this.Y-3;
	oDiv.style.backgroundImage="url('"+sysPath+"/commonjs/flowutil/img/tempnode.gif')";
	tempDiv.appendChild(oDiv);
}
/**
 * 求当前点与另一点的距离平方
 * @param {Object} oPoint
 */
CornerPoint.prototype.getDistance =function(oPoint){
	return Math.pow((oPoint.X-this.X),2)+Math.pow((oPoint.Y-this.Y),2);
}
CornerPoint.prototype.toString =function(){
	return this.id+",X:"+this.X+",Y:"+this.Y+"moveable:"+this.moveable;
}
