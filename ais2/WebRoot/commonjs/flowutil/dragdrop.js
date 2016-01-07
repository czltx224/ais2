var iDiffX = 0;
var iDiffY = 0;
var oNode = null;
var oPoint = null;
var tmpPoint = null;
function handleMouseMove() {
  var oEvent = EventUtil.getEvent();
	if(oNode){
		oNode.viewX = oEvent.clientX - iDiffX;
	  oNode.viewY = oEvent.clientY - iDiffY;
		graph.reDrawNode(oNode);
	}
	if(oPoint&&oPoint.moveable){
		if(oPoint.onNodeDirection==null){
			oPoint.X = oEvent.clientX - iDiffX;
		  oPoint.Y = oEvent.clientY - iDiffY;
			graph.reDrawEdge(oPoint);
		}else{			
			tmpPoint = new CornerPoint(oEvent.clientX-iDiffX,oEvent.clientY-iDiffY);
			tmpPoint.drawTemp();
		}		
	}
}
/**
 * 添加鼠标按下时的事件 
 * @param {Object} node
 * @param {Object} oDiv
 */
function mouseDownHandleCreater(node,point,oDiv){
	return function(){
		handleMouseDown(node,point,oDiv);
	}
}
/**
 * 鼠标按下时的事件函数
 * @param {Nde} node
 * @param {Object[DIV]} oDiv
 */  
function handleMouseDown(node,point,oDiv) {
  var oEvent = EventUtil.getEvent();
	oNode = node;
	oPoint = point;
  iDiffX = oEvent.clientX - oDiv.offsetLeft;
  iDiffY = oEvent.clientY - oDiv.offsetTop;
  EventUtil.addEventHandler(document.body, "mousemove", handleMouseMove);
  EventUtil.addEventHandler(document.body, "mouseup", handleMouseUp);
}
function handleMouseUp() {
  EventUtil.removeEventHandler(document.body, "mousemove", handleMouseMove);
  EventUtil.removeEventHandler(document.body, "mouseup", handleMouseUp);
	if(tmpPoint){
		var node = oPoint.onNode;
		var edge = oPoint.onEdge;
		var direction = node.getMinDistanceDirection(tmpPoint);
		if(node.id==edge.fromNode.id){
			edge.fromDirection=direction;
			graph.reDrawEdge(edge.getStartPoint());
		}else{
			edge.toDirection=direction;
			graph.reDrawEdge(edge.getEndPoint());
		}		
	}
	oNode = null;
	oPoint = null;
	tmpPoint = null;	
	tempDiv.innerHTML="";
}
/**
 * 添加拐点鼠标双击事件
 * @param {Object} point
 * @param {Object[DIV]} oDiv
 * @param {Object[DIV]} edgeDiv
 */
function doubleClickHandleCreater(point,oDiv,edgeDiv){
	return function(){
		handleDoubleClick(point,oDiv,edgeDiv);
	}
}
/**
 * 拐点上鼠标双击时的事件函数
 * @param {CornerPoint} point
 * @param {Object[DIV]} oDiv
 * @param {Object[DIV]} edgeDiv
 */
function handleDoubleClick(point,oDiv,edgeDiv){
	if(!point.onNode){
		oDiv.removeNode(true);
		point.moveable=!point.moveable;
		if(!point.moveable){
			point.onEdge.reSetCornerPoint(point);
		}		
		point.onEdge.draw();
	}	
}
