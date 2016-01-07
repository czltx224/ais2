function fireInnerText(){
   var ua=navigator.userAgent;
   var ie=navigator.appName=="Microsoft Internet Explorer"?true:false;
   if(!ie){
      HTMLElement.prototype.__defineGetter__("innerText", 
        function(){ 
          var  anyString  =  ""; 
          var  childS  =  this.childNodes; 
          for(var  i=0;  i <childS.length;  i++)  { 
            if(childS[i].nodeType==1) 
              anyString  +=  childS[i].tagName=="BR" ? '\n'  :  childS[i].innerText; 
            else  if(childS[i].nodeType==3) 
              anyString  +=  childS[i].nodeValue; 
          } 
          return  anyString; 
        } 
      ); 
      HTMLElement.prototype.__defineSetter__("innerText", 
        function(sText){ 
          this.textContent=sText; 
        } 
      ); 
   }
}


//选择
function getSelectedData(input){
    fireInnerText();
	var _id = input.cells[0].innerText;
	var _name = input.cells[1].innerText;
    var resultArray = new Array();
    resultArray[0]= _id; 
    resultArray[1]= _name;
    window.parent.returnValue=resultArray;
    dialogClose();
}

//清除
function btnclear_onclick(){
    var resultArray = new Array();
    resultArray[0]= "";
    resultArray[1]= "";
    window.parent.returnValue=resultArray;
    dialogClose(); 
}
//关闭模式窗口
function dialogClose(){
   if(window.parent){
       window.parent.close();
    }else{
      window.close();
    }
}

function node_ondbclick(key,text){
    var resultArray = new Array();
    resultArray[0]= key;
    resultArray[1]= text;
    window.parent.returnValue=resultArray;
    window.close();
}

function getrefobj(inputname,inputspan,refid,viewurl,isneed){
	idsin = document.all(inputname).value;
	var result = window.showModalDialog("/vbase/popupmain.jsp?url=/vbase/refobj/baseobjbrowser.jsp?id="+refid+"&idsin="+idsin);
	if(!result){return;}
	var _id = result[0];
	var _name = result[1];
	if(_id==""){
		document.all(inputname).value = "";
		if(isneed == "1"){
			document.all(inputspan).innerHTML = "<img src=/vimgs/checkinput.gif>";
		}else{
			document.all(inputspan).innerHTML = "";
		}
		
	}else{
		document.all(inputname).value = _id;
		document.all(inputspan).innerHTML = _name;	
	}
}

//如果存在同名函数,则后定义的会覆盖先定义的
function getrefobjwf(inputname,inputspan,refid,param,viewurl,isneed){
	if(param != ""){
		param = parserRefParam(inputname,param);
	}
	idsin = document.all(inputname).value;
	var result = window.showModalDialog("/vbase/popupmain.jsp?url=/vbase/refobj/baseobjbrowser.jsp?id="+refid+"&"+param+"&idsin="+idsin);
	if(!result){return;}
	var _id = result[0];
	var _name = result[1];
	if(_id==""){
		document.all(inputname).value = "";
		if(isneed == "1"){
			document.all(inputspan).innerHTML = "<img src=/vimgs/checkinput.gif>";
		}else{
			document.all(inputspan).innerHTML = "";
		}
	}else{
		document.all(inputname).value = _id;
		document.all(inputspan).innerHTML = _name;	
	}
	if(param != ""){
		onCal();
	}
}

function getBrowser(url,inputname,inputspan,isneed){
	var idsin = document.all(inputname).value;
	var result = window.showModalDialog("/vbase/popupmain.jsp?url="+url+"&idsin=" + idsin);
	if(!result){return;}
	var _id = result[0];
	var _name = result[1];
	if(_id==""){
		document.all(inputname).value = "";
		if(isneed == "1"){
			document.all(inputspan).innerHTML = "<img src=/vimgs/checkinput.gif>";
		}else{
			document.all(inputspan).innerHTML = "";
		}
	}else{
		document.all(inputname).value = _id;
		document.all(inputspan).innerHTML = _name;	
	}
}
function getBrowseriscludesub(url,inputname,inputspan,isneed){	
	var iscludesub=document.all("iscludesub").value;
	var idsin = document.all(inputname).value;
	var result = window.showModalDialog("/vbase/popupmain.jsp?url="+url+"&idsin=" + idsin+"&iscludesub="+iscludesub);
	if(!result){return;}
	var _id = result[0];
	var _name = result[1];
	if(_id==""){
		document.all(inputname).value = "";
		document.all("iscludesub").value="";
		if(isneed == "1"){
			document.all(inputspan).innerHTML = "<img src=/vimgs/checkinput.gif>";
		}else{
			document.all(inputspan).innerHTML = "";
		}
	}else{
		document.all(inputname).value = _id;
		document.all("iscludesub").value=result[2];
		document.all(inputspan).innerHTML = _name;	
	}
}