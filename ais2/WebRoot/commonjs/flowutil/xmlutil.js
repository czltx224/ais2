function $(id){
  return document.getElementById(id);
}
/**
 * 生成XMLDOM对象
 */
function createXMLDOM(){
  var arrSignatures = ["MSXML2.DOMDocument.5.0", "MSXML2.DOMDocument.4.0", "MSXML2.DOMDocument.3.0", "MSXML2.DOMDocument", "Microsoft.XmlDom"];
  for (var i = 0; i < arrSignatures.length; i++) {
    try {
      var oXmlDom = new ActiveXObject(arrSignatures[i]);
      return oXmlDom;
    } 
    catch (oError) {
    }
  }
  throw new Error("MSXML is not installed on your system.");
}
/**
 * XML操作类(写)
 */
function XMLWriter(){
    this.XML = ['<?xml version="1.0" encoding="UTF-8"?> '];
    this.Nodes = [];
    this.State = "";
    this.FormatXML = function(Str){
        if (Str||Str==0) 
            return  Str;
        return ""
    }
    this.BeginNode = function(Name){
        if (!Name) 
            return;
        if (this.State == "beg") 
            this.XML.push(">");
        this.State = "beg";
        this.Nodes.push(Name);
        this.XML.push("<" + Name);
    }
    this.EndNode = function(){
        if (this.State == "beg") {
            this.XML.push("/>");
            this.Nodes.pop();
        }
        else 
            if (this.Nodes.length > 0) 
                this.XML.push("</" + this.Nodes.pop() + ">");
        this.State = "";
    }
    this.Attrib = function(Name, Value){
        if (this.State != "beg" || !Name) 
            return;
        this.XML.push(" " + Name + "=\"" + this.FormatXML(Value) + "\"");
    }
    this.WriteString = function(Value){
        if (this.State == "beg") 
            this.XML.push(">");
        this.XML.push(this.FormatXML(Value));
        this.State = "";
    }
    this.Node = function(Name, Value){
        if (!Name) 
            return;
        if (this.State == "beg") 
            this.XML.push(">");
        this.XML.push((Value == "" || !Value) ? "<" + Name + "/>" : "<" + Name + ">" + this.FormatXML(Value) + "</" + Name + ">");
        this.State = "";
    }
    this.Close = function(){
        while (this.Nodes.length > 0) 
            this.EndNode();
        this.State = "closed";
    }
    this.ToString = function(){
        return this.XML.join("");
    }
}