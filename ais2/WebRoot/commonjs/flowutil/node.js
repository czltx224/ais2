function Node(id,oName){
	this.id=id;
	this.workflowName=oName;
	this.inEdges=new Array();
	this.outEdges=new Array();
	this.isVisible=true;
	this.moveable=true;
	this.viewX=0;
	this.viewY=0;
}
Node.prototype.addInEdge=function(inEdge){
	this.inEdges.push(inEdge);
}
Node.prototype.addOutEdge=function(outEdge){
	this.outEdges.push(outEdge);
}
Node.prototype.removeInEdge=function(inEdge){
	var length = this.inEdges.length;
	for(var i=0;i<length;i++){
		var edge = inEdges[i];
		if(edge.id==inEdge.id){
			this.inEdges.splice(i,1);
		}
	}
}
Node.prototype.removeOutEdge=function(outEdge){
	var length = this.outEdges.length;
	for(var i=0;i<length;i++){
		var edge = outEdges[i];
		if(edge.id==outEdge.id){
			this.outEdges.splice(i,1);
		}
	}
}
Node.prototype.draw=function(readonly){
	var oDiv = document.createElement("DIV");
	oDiv.id=this.id;
	oDiv.className="workflowNode";
	oDiv.style.left=this.viewX+"px";
	oDiv.style.top=this.viewY+"px";
	if(readonly){//显示视图
		var type = this.nodeType;
		if(type==2){// 2表示已流转节点; 
			this.makeFlowedNode(oDiv);			
		}else	if(type==1){//1表示当前节点;
			this.makeCurrentNode(oDiv);
		}else{//0表示末流转节点
			this.makeUnFlowNode(oDiv);
		}		
	}else{//编辑视图
		this.makeEditNode(oDiv);
	}
	nodesDiv.appendChild(oDiv);
}
/**
 * 生成已流转节点
 * @param {Object} oDiv
 */
Node.prototype.makeFlowedNode=function(oDiv){
	oDiv.style.backgroundImage="url("+sysPath+"/commonjs/flowutil/img/node_green.gif)";
	var nameDiv = document.createElement("DIV");
	nameDiv.className="workflowName";
	var tagName = document.createElement("span");
	tagName.title=this.workflowName;
	//tagName.href="/vworkflow/workflow/workflow.jsp?nodeid="+this.id+"&workflowid="+graph.flowid;
	//tagName.target="_blank";
	tagName.appendChild(document.createTextNode(this.workflowName));
	nameDiv.appendChild(tagName);
	oDiv.appendChild(nameDiv);
	var usersDiv = document.createElement("div");
	usersDiv.className="workflowUser";	
	var imgGreen = document.createElement("img");
	imgGreen.src=sysPath+"/commonjs/flowutil/img/users_green.gif";
	imgGreen.align="absmiddle";
	imgGreen.title="已操作者:";
	imgGreen.height=15;
	imgGreen.width=9;
	usersDiv.appendChild(imgGreen);
	var len = this.doUsers.length;
	for(var i=0;i<len;i++){
		var user = this.doUsers[i];
		imgGreen.title+=" "+user.name;
		var userTagA = document.createElement("span");
		userTagA.setAttribute("style","font-size:12px");
		//userTagA.href="/vhumres/base/humresview.jsp?id="+user.id;
		//userTagA.target="_blank";
		userTagA.appendChild(document.createTextNode(user.name));
		usersDiv.appendChild(userTagA);
	}
	oDiv.appendChild(usersDiv);
}
/**
 * 生成当前节点
 * @param {Object} oDiv
 */
Node.prototype.makeCurrentNode=function(oDiv){
	oDiv.style.backgroundImage="url("+sysPath+"/commonjs/flowutil/img/node_red.gif)";
	var nameDiv = document.createElement("DIV");
	nameDiv.className="workflowName";
	var tagName = document.createElement("span");
	tagName.title=this.workflowName;
	//tagName.href="/vworkflow/workflow/workflow.jsp?nodeid="+this.id+"&workflowid="+graph.flowid;
	tagName.appendChild(document.createTextNode(this.workflowName));
	nameDiv.appendChild(tagName);
	oDiv.appendChild(nameDiv);
	var imgRed = document.createElement("img");
	imgRed.src=sysPath+"/commonjs/flowutil/img/users_red.gif";
	imgRed.align="bottom";
	imgRed.title="未操作者:";
	var imgGreen = document.createElement("img");
	imgGreen.src=sysPath+"/commonjs/flowutil/img/users_green.gif";
	imgGreen.align="absmiddle";
	imgGreen.title="已操作者:";
	var imgBlue = document.createElement("img");
	imgBlue.src=sysPath+"/commonjs/flowutil/img/users_blue.gif";
	imgBlue.align="absmiddle";
	imgBlue.title="已查看者:";	
	
	var undoUserDiv = document.createElement("div");
	var doUserDiv = document.createElement("div");
	var viewUserDiv = document.createElement("div");
	undoUserDiv.className="workflowUser";	
	doUserDiv.className="workflowUser";	
	viewUserDiv.className="workflowUser";	
	undoUserDiv.appendChild(imgRed);
	doUserDiv.appendChild(imgGreen);
	viewUserDiv.appendChild(imgBlue);
	
	var undoUsersLen = this.undoUsers.length;
	var doUsersLen = this.doUsers.length;
	var viewUsersLen = this.viewUsers.length;
	
	for(var i=0;i<undoUsersLen;i++){
		var undoUser = this.undoUsers[i];
		imgRed.title+=" "+undoUser.name;
		var userTagA = document.createElement("span");
		userTagA.setAttribute("style","font-size:12px");
		//userTagA.href="/vhumres/base/humresview.jsp?id="+undoUser.id;
		//userTagA.target="_blank";
		userTagA.appendChild(document.createTextNode(undoUser.name));
		undoUserDiv.appendChild(userTagA);
	}
	for(var i=0;i<doUsersLen;i++){
		var doUser = this.doUsers[i];
		imgGreen.title+=" "+doUser.name;
		var userTagA = document.createElement("span");
		userTagA.setAttribute("style","font-size:12px");
		//userTagA.href="/vhumres/base/humresview.jsp?id="+doUser.id;
		//userTagA.target="_blank";
		userTagA.appendChild(document.createTextNode(doUser.name));
		doUserDiv.appendChild(userTagA);
	}
	for(var i=0;i<viewUsersLen;i++){
		var viewUser = this.viewUsers[i];
		imgBlue.title+=" "+viewUser.name;
		var userTagA = document.createElement("span");
		userTagA.setAttribute("style","font-size:12px");
		//userTagA.href="/vhumres/base/humresview.jsp?id="+viewUser.id;
		//userTagA.target="_blank";
		userTagA.appendChild(document.createTextNode(viewUser.name));
		viewUserDiv.appendChild(userTagA);
	}
	
	oDiv.appendChild(undoUserDiv);
	oDiv.appendChild(doUserDiv);
	oDiv.appendChild(viewUserDiv);
}
/**
 * 生成未流转节点
 * @param {Object} oDiv
 */
Node.prototype.makeUnFlowNode=function(oDiv){
	oDiv.style.backgroundImage="url("+sysPath+"/commonjs/flowutil/img/node_blue.gif)";
	var nameDiv = document.createElement("DIV");
	nameDiv.className="workflowName";	
	nameDiv.title=this.workflowName;	
	nameDiv.appendChild(document.createTextNode(this.workflowName));
	var optionDiv = document.createElement("DIV");
	optionDiv.className="workflowOption";
	var img = document.createElement("img");
	img.src=sysPath+"/commonjs/flowutil/img/options.gif";
	img.align="absmiddle";
	optionDiv.appendChild(img);	
	optionDiv.appendChild(document.createTextNode("操作者"));	
	var id = this.id;
	optionDiv.onclick=function(){showRule(id,oDiv);};
	oDiv.appendChild(nameDiv);
	oDiv.appendChild(optionDiv);
}
/**
 * 生成编辑节点
 * @param {Object} oDiv
 */
Node.prototype.makeEditNode=function(oDiv){
	oDiv.attachEvent("onmousedown",mouseDownHandleCreater(this,null,oDiv));
	var nameDiv = document.createElement("div");
	nameDiv.className="workflowName";
	var tagName = document.createElement("a");
	tagName.title=this.workflowName;
	tagName.href="javascript:openNode('"+pipeId+"','"+pipeName+"','"+this.id+"');";
	tagName.appendChild(document.createTextNode(this.workflowName));
	nameDiv.appendChild(tagName);
	var usersDiv = document.createElement("div");
	usersDiv.className="workflowUser";

	var img = document.createElement("img");
	img.src=sysPath+"/commonjs/flowutil/img/users.gif";
	img.border=0;
	img.title="设置节点操作者";
	img.style.cursor="hand";
	var nodeid=this.id;
	img.onclick =function(){editUser(pipeId,nodeid);};
	usersDiv.appendChild(img);
	oDiv.appendChild(nameDiv);
	oDiv.appendChild(usersDiv);
}
Node.prototype.reDraw=function(readOnly){
	this.clear();
	this.draw(readOnly);
}
Node.prototype.clear=function(){
	var oDiv = document.getElementById(this.id);
	if(oDiv){
		oDiv.removeNode(true);
	}
}
/**
 * 获取节点上iDirection方向上的X坐标
 * @param {Number} iDirection
 */
Node.prototype.getPointX = function(iDirection){
	var flag = 0;
	if(iDirection==0||(iDirection>=12&&iDirection<=15)){
		flag = 0;
	}else if(iDirection>=4&&iDirection<=8){
		flag = 4;
	}else if(iDirection==1||iDirection==11){
		flag = 1;
	}else if(iDirection==2||iDirection==10){
		flag = 2;
	}else if(iDirection==4||iDirection==9){
		flag = 3;
	}
	return this.viewX*1+flag*24;
}
/**
 * 获取节点上iDirection方向上的Y坐标
 * @param {Number} iDirection
 */
Node.prototype.getPointY = function(iDirection){
	var flag = 0;
	if(iDirection>=0&&iDirection<=4){
		flag = 0;
	}else if(iDirection>=8&&iDirection<=12){
		flag = 4;
	}else if(iDirection==5||iDirection==15){
		flag = 1;
	}else if(iDirection==6||iDirection==14){
		flag = 2;
	}else if(iDirection==7||iDirection==13){
		flag = 3;
	}
	return this.viewY*1+flag*18;
}
/**
 * 获取节点上指定方向的连接点坐标
 * @param {Object} iDirection
 */
Node.prototype.getPoint = function(iDirection){
	var X=this.getPointX(iDirection);
	var Y=this.getPointY(iDirection);
	return new CornerPoint(X,Y);
}
/**
 * 获取之地的临拐点和节点上知道的位置间的距离
 * @param {Object} iDirection
 * @param {Object} tmpPoint
 */
Node.prototype.getDirectionDistance = function(iDirection,tmpPoint){
	var point = this.getPoint(iDirection);
	return point.getDistance(tmpPoint);
}
/**
 * 获取节点上的16个拐点中与指定拐点距离最近的那个点的位置
 * @param {Object} tmpPoint
 */
Node.prototype.getMinDistanceDirection = function(tmpPoint){
	var minDistance = this.getDirectionDistance(0,tmpPoint);
	var minDirection = 0;
	for(var i=1;i<16;i++){
		var pointDistance = this.getDirectionDistance(i,tmpPoint);
		if(pointDistance<minDistance){
			minDistance = pointDistance;
			minDirection =i;
		}
	}	
	return minDirection;
}
Node.prototype.toString=function(){
	return this.id+","+this.workflowName;
}