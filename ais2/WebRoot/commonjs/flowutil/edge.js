function Edge(id){
	this.id=id;
	this.fromNode=null;
	this.toNode=null;
	this.fromDirection=0;
	this.toDirection=0;
	this.cornerPoints=new Array;
	this.jg = new jsGraphics(id);
	this.jg.setColor("#4a7ebb");
	this.tmemo=null;
}
Edge.prototype.addMemo=function(tmemo){
	this.tmemo=tmemo
}
/**
 * 将边绘制到页面
 */
Edge.prototype.draw=function(readonly){
	//this.jg.addChild("125454545");
	if(readonly){
		this.jg.setColor("#cccccc");
	}
	var edgeDiv = document.getElementById(this.id);
	edgeDiv.innerHTML="";
	var hasFlow = this.hasFlow;
	if(hasFlow){
		this.jg.setColor("#4d8f3c");
	}
	var length = this.cornerPoints.length;
	var p0 = this.cornerPoints[0];
	var pn = this.cornerPoints[length-1];
	p0.drawSelf(edgeDiv,readonly);
	pn.drawSelf(edgeDiv,readonly);
	for(var i=1;i<length-1;i++){
		var pi = this.cornerPoints[i];
		//alert(pi);
		pi.drawSelf(edgeDiv,readonly);
		if(pi.moveable){	
			this.jg.drawLine(p0.X, p0.Y, pi.X, pi.Y);
			p0 = pi;
		}		
	}
	this.jg.drawArrow(p0.X, p0.Y, pn.X, pn.Y);
	//alert(this.tmemo+','+p0.X+','+pn.X+','+p0.Y+','+pn.Y);
	this.jg.drawTerm(p0.X+20, pn.Y, pn.X, pn.Y,this.tmemo);
	this.jg.paint();
}
/**
 * 添加起始节点
 * @param {Node} fNode
 * @param {Number} fDirection
 */
Edge.prototype.addFromNode = function(fNode,fDirection){
	this.fromNode = fNode;
	this.fromDirection = fDirection;
	fNode.addOutEdge(this);
}
/**
 * 添加终止节点 
 * @param {Node} tNode
 * @param {Number} tDirection
 */
Edge.prototype.addToNode = function(tNode,tDirection){
	this.toNode = tNode;
	this.toDirection = tDirection;
	tNode.addInEdge(this);
}
/**
 * 获取边的起点
 */
Edge.prototype.getStartPoint = function(){
	var x =this.fromNode.getPointX(this.fromDirection);
	var y =this.fromNode.getPointY(this.fromDirection);
	var point =new CornerPoint(x,y,"p0");
	point.moveable=true;
	point.onEdge=this;
	point.onNode=this.fromNode;
	point.onNodeDirection = this.fromDirection;
	return point;
}
/**
 * 获取边的终点
 */
Edge.prototype.getEndPoint = function(){
	var x =this.toNode.getPointX(this.toDirection);
	var y =this.toNode.getPointY(this.toDirection);
	var point =new CornerPoint(x,y,"p4");
	point.moveable=true;
	point.onEdge=this;
	point.onNode=this.toNode;
	point.onNodeDirection = this.toDirection;
	return point;
}
/**
 * 第一次设置拐点信息
 */
Edge.prototype.createCornerPoint = function(){
	var p1 =  this.getStartPoint();
	var p2 =  this.getEndPoint();
	this.cornerPoints=new Array();
	this.cornerPoints.push(p1);
	var dX = (p2.X-p1.X)/4;
	var dY = (p2.Y-p1.Y)/4;
	for(var i=1;i<4;i++){
		var point =new CornerPoint(p1.X+dX*i,p1.Y+dY*i,"p"+i);
		point.moveable=false;
		point.onEdge=this;
		this.cornerPoints.push(point);
	}
	this.cornerPoints.push(p2);
}
/**
 * 移动拐点后更新所有拐点信息 
 * @param {CornerPoint} oPoint
 */
Edge.prototype.updateCornerPoint = function(oPoint){
	var index = oPoint.id.substring(1)*1;
	switch(index){		
		case 0:
			this.cornerPoints[0] = oPoint;
			if(!this.cornerPoints[1].moveable){
				if(!this.cornerPoints[2].moveable) {					
					if(!this.cornerPoints[3].moveable){
						this.quarteringPoint();
					}else{
						this.trisectionPoint(this.cornerPoints[0],this.cornerPoints[3],1);
					}
				}else {
					this.halvePoint(this.cornerPoints[0],this.cornerPoints[2],1);
				}
			}
			break;
		case 1:	
			if(!this.cornerPoints[2].moveable){
				if(!this.cornerPoints[3].moveable) {
					this.trisectionPoint(oPoint,this.cornerPoints[4],2);
				}else {
					this.halvePoint(oPoint,this.cornerPoints[3],2);
				}
			}
			break;
		case 2:
			if(!this.cornerPoints[1].moveable){
				this.halvePoint(this.cornerPoints[0],oPoint,1);
			}
			if(!this.cornerPoints[3].moveable){
				this.halvePoint(oPoint,this.cornerPoints[4],3);
			}			
			break;
		case 3:			
			if(!this.cornerPoints[1].moveable){
				if(!this.cornerPoints[2].moveable){
					this.trisectionPoint(this.cornerPoints[0],oPoint,1);
				}
			}else if(!this.cornerPoints[2].moveable){
				this.halvePoint(this.cornerPoints[1],oPoint,2);
			}			
			break;
		case 4:	
			this.cornerPoints[4] = oPoint;		
			if(!this.cornerPoints[3].moveable){
				if(!this.cornerPoints[2].moveable) {					
					if(!this.cornerPoints[1].moveable){
						this.quarteringPoint();
					}else{
						this.trisectionPoint(this.cornerPoints[1],this.cornerPoints[4],2);
					}
				}else {
					this.halvePoint(this.cornerPoints[2],this.cornerPoints[4],3);
				}
			}
			break;
		default:			
			break;
	}
}
/**
 * 删除一个拐点后更新其他拐点信息
 * @param {Object} oPoint
 */
Edge.prototype.reSetCornerPoint = function(oPoint){
	var index = oPoint.id.substring(1)*1;
	switch(index){
		case 1:			
			if(!this.cornerPoints[2].moveable){
				if(!this.cornerPoints[3].moveable) {
					this.quarteringPoint();
				}else {
					this.trisectionPoint(this.cornerPoints[0],this.cornerPoints[3],index);
				}
			}else{
				this.halvePoint(this.cornerPoints[0],this.cornerPoints[2],index);
			}
			break;
		case 2:
			if(!this.cornerPoints[1].moveable){
				if (!this.cornerPoints[3].moveable) {
					this.quarteringPoint();
				}else{
					this.trisectionPoint(this.cornerPoints[0],this.cornerPoints[3],1);
				}
			}else{
				if (!this.cornerPoints[3].moveable) {
					this.trisectionPoint(this.cornerPoints[1],this.cornerPoints[4],2);
				}else{
					this.halvePoint(this.cornerPoints[1],this.cornerPoints[3],2);
				}
			}				
			break;
		case 3:			
			if(!this.cornerPoints[2].moveable){
				if(!this.cornerPoints[1].moveable){
					this.quarteringPoint();
				}else{
					this.trisectionPoint(this.cornerPoints[1],this.cornerPoints[4],2);
				}
			}else{
				this.halvePoint(this.cornerPoints[2],this.cornerPoints[4],3);
			}			
			break;
		default:
			break;
	}
}
/**
 * 载入节点时重新生成折点
 * @param {Object} iFlag
 */
Edge.prototype.updateUnmoveablePoint = function(iFlag){
	var len = this.cornerPoints.length;
	switch(iFlag){
		case 1:
			for(var i=1;i<len-1;i++){
				var point = this.cornerPoints[i];
				if(!point.moveable){
					this.halvePoint(this.cornerPoints[i-1],this.cornerPoints[i+1],i);
				}
			}			
			break;
		case 2:
			var pos = 0;
			for(var i=1;i<len-1;i++){
				var point = this.cornerPoints[i];				
				if(point.moveable){
					pos=i;
					break;
				}
			}
			switch(pos) {
				case 1:
					this.trisectionPoint(this.cornerPoints[1],this.cornerPoints[4],2);
					break;
				case 2:
					this.halvePoint(this.cornerPoints[0],this.cornerPoints[2],1);
					this.halvePoint(this.cornerPoints[2],this.cornerPoints[4],3);
					break;
				case 3:
					this.trisectionPoint(this.cornerPoints[0],this.cornerPoints[3],1);
					break;
				default:
					break;
			}		
			break;
		case 3:			
			this.quarteringPoint();
			break;
		default:
			break;
	}
}
/**
 * 四等分点
 */
Edge.prototype.quarteringPoint =function(){
	var p1 = this.cornerPoints[0];
	var p2 = this.cornerPoints[4];
	var dX = (p2.X-p1.X)/4;
	var dY = (p2.Y-p1.Y)/4;
	this.cornerPoints[1].X=Math.round((p1.X*1+dX*1));
	this.cornerPoints[1].Y=Math.round((p1.Y*1+dY*1));				
	this.cornerPoints[2].X=Math.round((p1.X*1+2*dX));
	this.cornerPoints[2].Y=Math.round((p1.Y*1+2*dY));				
	this.cornerPoints[3].X=Math.round((p1.X*1+3*dX));
	this.cornerPoints[3].Y=Math.round((p1.Y*1+3*dY));
}
/**
 * 二等分点
 * @param {Object} p1
 * @param {Object} p2
 * @param {Object} index
 */
Edge.prototype.halvePoint =function(p1,p2,index){
	this.cornerPoints[index].X=Math.round((p1.X*1+p2.X*1)/2);
	this.cornerPoints[index].Y=Math.round((p1.Y*1+p2.Y*1)/2);
}
/**
 * 三等分两点
 * @param {Object} p1
 * @param {Object} p2
 * @param {Object} index
 */
Edge.prototype.trisectionPoint =function(p1,p2,index){
	var dX = (p2.X-p1.X)/3;
	var dY = (p2.Y-p1.Y)/3;
	this.cornerPoints[index].X=Math.round(p1.X*1+dX*1);
	this.cornerPoints[index].Y=Math.round(p1.Y*1+dY*1);
	this.cornerPoints[index+1].X=Math.round(p1.X*1+2*dX);
	this.cornerPoints[index+1].Y=Math.round(p1.Y*1+2*dY);
}
Edge.prototype.toString = function(){
	return this.id;
}
Edge.prototype.printPoint = function(){
	var len = this.cornerPoints.length;
	for(var i=0;i<len;i++){
		var p = this.cornerPoints[i];
		alert(p);
	}
}