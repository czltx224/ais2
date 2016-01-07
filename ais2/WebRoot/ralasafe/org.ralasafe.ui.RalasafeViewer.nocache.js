function org_ralasafe_ui_RalasafeViewer(){
  var $intern_0 = '', $intern_27 = '" for "gwt:onLoadErrorFn"', $intern_25 = '" for "gwt:onPropertyErrorFn"', $intern_10 = '"><\/script>', $intern_12 = '#', $intern_33 = '&', $intern_14 = '/', $intern_59 = '29D13FBEE28D71CC2A2A133B938A3DE4.cache.html', $intern_57 = '46BFB70AD0ED4F7888E7972492165DB5.cache.html', $intern_52 = '4A62C3CF284A9D3AE123F443E81A5CDA.cache.html', $intern_51 = '4ECD177D856421691751C43CCF7E8025.cache.html', $intern_58 = '52C0AC0F191A02C27CB80C41AD5CA8BE.cache.html', $intern_53 = '757815D742A699ADDB8209BEE79886FF.cache.html', $intern_49 = '9E892F5E35D283D7FC1729E2BD0F3617.cache.html', $intern_100 = '<script defer="defer">org_ralasafe_ui_RalasafeViewer.onInjectionDone(\'org.ralasafe.ui.RalasafeViewer\')<\/script>', $intern_9 = '<script id="', $intern_70 = '<script language="javascript" src="', $intern_22 = '=', $intern_13 = '?', $intern_50 = 'A424F52D70B68A2302D5952705EA7B2B.cache.html', $intern_55 = 'BCFC331B4CDBECF6FAB1CB36A7F76F16.cache.html', $intern_24 = 'Bad handler "', $intern_68 = 'DOMContentLoaded', $intern_56 = 'F959F020668E17A969743B6CA4DF0D6A.cache.html', $intern_60 = 'GwtExt.css', $intern_11 = 'SCRIPT', $intern_36 = 'Unexpected exception in locale detection, using default: ', $intern_35 = '_', $intern_8 = '__gwt_marker_org.ralasafe.ui.RalasafeViewer', $intern_15 = 'base', $intern_4 = 'begin', $intern_3 = 'bootstrap', $intern_17 = 'clear.cache.gif', $intern_21 = 'content', $intern_34 = 'default', $intern_7 = 'end', $intern_43 = 'gecko', $intern_44 = 'gecko1_8', $intern_5 = 'gwt.hybrid', $intern_26 = 'gwt:onLoadErrorFn', $intern_23 = 'gwt:onPropertyErrorFn', $intern_20 = 'gwt:property', $intern_65 = 'head', $intern_48 = 'hosted.html?org_ralasafe_ui_RalasafeViewer', $intern_64 = 'href', $intern_42 = 'ie6', $intern_28 = 'iframe', $intern_16 = 'img', $intern_29 = "javascript:''", $intern_66 = 'js/gridcellactions/ext-ux-grid-cellactions.css', $intern_69 = 'js/gridcellactions/ext-ux-grid-cellactions.js', $intern_71 = 'js/gridcellactions/ext-ux-grid-cellactions.js"><\/script>', $intern_72 = 'js/gridsearch/ext-ux-grid-search.js', $intern_73 = 'js/gridsearch/ext-ux-grid-search.js"><\/script>', $intern_67 = 'js/sh/SyntaxHighlighter.css', $intern_78 = 'js/sh/shBrushCSharp.js', $intern_79 = 'js/sh/shBrushCSharp.js"><\/script>', $intern_76 = 'js/sh/shBrushCpp.js', $intern_77 = 'js/sh/shBrushCpp.js"><\/script>', $intern_80 = 'js/sh/shBrushCss.js', $intern_81 = 'js/sh/shBrushCss.js"><\/script>', $intern_82 = 'js/sh/shBrushDelphi.js', $intern_83 = 'js/sh/shBrushDelphi.js"><\/script>', $intern_86 = 'js/sh/shBrushJScript.js', $intern_87 = 'js/sh/shBrushJScript.js"><\/script>', $intern_84 = 'js/sh/shBrushJava.js', $intern_85 = 'js/sh/shBrushJava.js"><\/script>', $intern_88 = 'js/sh/shBrushPhp.js', $intern_89 = 'js/sh/shBrushPhp.js"><\/script>', $intern_90 = 'js/sh/shBrushPython.js', $intern_91 = 'js/sh/shBrushPython.js"><\/script>', $intern_92 = 'js/sh/shBrushRuby.js', $intern_93 = 'js/sh/shBrushRuby.js"><\/script>', $intern_94 = 'js/sh/shBrushSql.js', $intern_95 = 'js/sh/shBrushSql.js"><\/script>', $intern_96 = 'js/sh/shBrushVb.js', $intern_97 = 'js/sh/shBrushVb.js"><\/script>', $intern_98 = 'js/sh/shBrushXml.js', $intern_99 = 'js/sh/shBrushXml.js"><\/script>', $intern_74 = 'js/sh/shCore.js', $intern_75 = 'js/sh/shCore.js"><\/script>', $intern_61 = 'link', $intern_46 = 'loadExternalRefs', $intern_32 = 'locale', $intern_18 = 'meta', $intern_31 = 'moduleRequested', $intern_6 = 'moduleStartup', $intern_41 = 'msie', $intern_19 = 'name', $intern_38 = 'opera', $intern_1 = 'org.ralasafe.ui.RalasafeViewer', $intern_30 = 'position:absolute;width:0;height:0;border:none', $intern_62 = 'rel', $intern_40 = 'safari', $intern_47 = 'selectingPermutation', $intern_2 = 'startup', $intern_63 = 'stylesheet', $intern_45 = 'unknown', $intern_37 = 'user.agent', $intern_39 = 'webkit', $intern_54 = 'zh';
  var $wnd = window, $doc = document, $stats = $wnd.__gwtStatsEvent?function(a){
    return $wnd.__gwtStatsEvent(a);
  }
  :null, scriptsDone, loadDone, bodyDone, base = $intern_0, metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc;
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_4});
  if (!$wnd.__gwt_stylesLoaded) {
    $wnd.__gwt_stylesLoaded = {};
  }
  if (!$wnd.__gwt_scriptsLoaded) {
    $wnd.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    try {
      return $wnd.external && ($wnd.external.gwtOnLoad && $wnd.location.search.indexOf($intern_5) == -1);
    }
     catch (e) {
      return false;
    }
  }

  function maybeStartModule(){
    if (scriptsDone && loadDone) {
      var iframe = $doc.getElementById($intern_1);
      var frameWnd = iframe.contentWindow;
      frameWnd.__gwt_initHandlers = org_ralasafe_ui_RalasafeViewer.__gwt_initHandlers;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name){
          return computePropValue(name);
        }
        ;
      }
      org_ralasafe_ui_RalasafeViewer = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, $intern_1, base);
      $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_6, millis:(new Date()).getTime(), type:$intern_7});
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = $intern_8, markerScript;
    $doc.write($intern_9 + markerId + $intern_10);
    markerScript = $doc.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != $intern_11) {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf($intern_12);
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf($intern_13);
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf($intern_14, Math.min(queryIndex, hashIndex));
      return slashIndex >= 0?path.substring(0, slashIndex + 1):$intern_0;
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == $intern_0) {
      var baseElements = $doc.getElementsByTagName($intern_15);
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        base = getDirectoryOfFile($doc.location.href);
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc.createElement($intern_16);
      img.src = base + $intern_17;
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName($intern_18);
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name = meta.getAttribute($intern_19), content;
      if (name) {
        if (name == $intern_20) {
          content = meta.getAttribute($intern_21);
          if (content) {
            var value, eq = content.indexOf($intern_22);
            if (eq >= 0) {
              name = content.substring(0, eq);
              value = content.substring(eq + 1);
            }
             else {
              name = content;
              value = $intern_0;
            }
            metaProps[name] = value;
          }
        }
         else if (name == $intern_23) {
          content = meta.getAttribute($intern_21);
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_24 + content + $intern_25);
            }
          }
        }
         else if (name == $intern_26) {
          content = meta.getAttribute($intern_21);
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_24 + content + $intern_27);
            }
          }
        }
      }
    }
  }

  function __gwt_isKnownPropertyValue(propName, propValue){
    return propValue in values[propName];
  }

  function __gwt_getMetaProperty(name){
    var value = metaProps[name];
    return value == null?null:value;
  }

  function unflattenKeylistIntoAnswers(propValArray, value){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  var frameInjected;
  function maybeInjectFrame(){
    if (!frameInjected) {
      frameInjected = true;
      var iframe = $doc.createElement($intern_28);
      iframe.src = $intern_29;
      iframe.id = $intern_1;
      iframe.style.cssText = $intern_30;
      iframe.tabIndex = -1;
      $doc.body.appendChild(iframe);
      $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_6, millis:(new Date()).getTime(), type:$intern_31});
      iframe.contentWindow.location.replace(base + strongName);
    }
  }

  providers[$intern_32] = function(){
    try {
      var locale;
      if (locale == null) {
        var args = location.search;
        var startLang = args.indexOf($intern_32);
        if (startLang >= 0) {
          var language = args.substring(startLang);
          var begin = language.indexOf($intern_22) + 1;
          var end = language.indexOf($intern_33);
          if (end == -1) {
            end = language.length;
          }
          locale = language.substring(begin, end);
        }
      }
      if (locale == null) {
        locale = __gwt_getMetaProperty($intern_32);
      }
      if (locale == null) {
        return $intern_34;
      }
      while (!__gwt_isKnownPropertyValue($intern_32, locale)) {
        var lastIndex = locale.lastIndexOf($intern_35);
        if (lastIndex == -1) {
          locale = $intern_34;
          break;
        }
         else {
          locale = locale.substring(0, lastIndex);
        }
      }
      return locale;
    }
     catch (e) {
      alert($intern_36 + e);
      return $intern_34;
    }
  }
  ;
  values[$intern_32] = {'default':0, zh:1};
  providers[$intern_37] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf($intern_38) != -1) {
      return $intern_38;
    }
     else if (ua.indexOf($intern_39) != -1) {
      return $intern_40;
    }
     else if (ua.indexOf($intern_41) != -1) {
      var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 6000) {
          return $intern_42;
        }
      }
    }
     else if (ua.indexOf($intern_43) != -1) {
      var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 1008)
          return $intern_44;
      }
      return $intern_43;
    }
    return $intern_45;
  }
  ;
  values[$intern_37] = {gecko:0, gecko1_8:1, ie6:2, opera:3, safari:4};
  org_ralasafe_ui_RalasafeViewer.onScriptLoad = function(){
    if (frameInjected) {
      loadDone = true;
      maybeStartModule();
    }
  }
  ;
  org_ralasafe_ui_RalasafeViewer.onInjectionDone = function(){
    scriptsDone = true;
    $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_46, millis:(new Date()).getTime(), type:$intern_7});
    maybeStartModule();
  }
  ;
  computeScriptBase();
  processMetas();
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_47});
  var strongName;
  if (isHostedMode()) {
    strongName = $intern_48;
  }
   else {
    try {
      unflattenKeylistIntoAnswers([$intern_34, $intern_38], $intern_49);
      unflattenKeylistIntoAnswers([$intern_34, $intern_44], $intern_50);
      unflattenKeylistIntoAnswers([$intern_34, $intern_43], $intern_51);
      unflattenKeylistIntoAnswers([$intern_34, $intern_40], $intern_52);
      unflattenKeylistIntoAnswers([$intern_34, $intern_42], $intern_53);
      unflattenKeylistIntoAnswers([$intern_54, $intern_38], $intern_55);
      unflattenKeylistIntoAnswers([$intern_54, $intern_43], $intern_56);
      unflattenKeylistIntoAnswers([$intern_54, $intern_44], $intern_57);
      unflattenKeylistIntoAnswers([$intern_54, $intern_40], $intern_58);
      unflattenKeylistIntoAnswers([$intern_54, $intern_42], $intern_59);
      strongName = answers[computePropValue($intern_32)][computePropValue($intern_37)];
    }
     catch (e) {
      return;
    }
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      if (!__gwt_stylesLoaded[$intern_60]) {
        var l = $doc.createElement($intern_61);
        __gwt_stylesLoaded[$intern_60] = l;
        l.setAttribute($intern_62, $intern_63);
        l.setAttribute($intern_64, base + $intern_60);
        $doc.getElementsByTagName($intern_65)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_66]) {
        var l = $doc.createElement($intern_61);
        __gwt_stylesLoaded[$intern_66] = l;
        l.setAttribute($intern_62, $intern_63);
        l.setAttribute($intern_64, base + $intern_66);
        $doc.getElementsByTagName($intern_65)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_67]) {
        var l = $doc.createElement($intern_61);
        __gwt_stylesLoaded[$intern_67] = l;
        l.setAttribute($intern_62, $intern_63);
        l.setAttribute($intern_64, base + $intern_67);
        $doc.getElementsByTagName($intern_65)[0].appendChild(l);
      }
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener($intern_68, onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener($intern_68, function(){
      maybeInjectFrame();
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc.readyState)) {
      maybeInjectFrame();
      onBodyDone();
    }
  }
  , 50);
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_7});
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_46, millis:(new Date()).getTime(), type:$intern_4});
  if (!__gwt_scriptsLoaded[$intern_69]) {
    __gwt_scriptsLoaded[$intern_69] = true;
    document.write($intern_70 + base + $intern_71);
  }
  if (!__gwt_scriptsLoaded[$intern_72]) {
    __gwt_scriptsLoaded[$intern_72] = true;
    document.write($intern_70 + base + $intern_73);
  }
  if (!__gwt_scriptsLoaded[$intern_74]) {
    __gwt_scriptsLoaded[$intern_74] = true;
    document.write($intern_70 + base + $intern_75);
  }
  if (!__gwt_scriptsLoaded[$intern_76]) {
    __gwt_scriptsLoaded[$intern_76] = true;
    document.write($intern_70 + base + $intern_77);
  }
  if (!__gwt_scriptsLoaded[$intern_78]) {
    __gwt_scriptsLoaded[$intern_78] = true;
    document.write($intern_70 + base + $intern_79);
  }
  if (!__gwt_scriptsLoaded[$intern_80]) {
    __gwt_scriptsLoaded[$intern_80] = true;
    document.write($intern_70 + base + $intern_81);
  }
  if (!__gwt_scriptsLoaded[$intern_82]) {
    __gwt_scriptsLoaded[$intern_82] = true;
    document.write($intern_70 + base + $intern_83);
  }
  if (!__gwt_scriptsLoaded[$intern_84]) {
    __gwt_scriptsLoaded[$intern_84] = true;
    document.write($intern_70 + base + $intern_85);
  }
  if (!__gwt_scriptsLoaded[$intern_86]) {
    __gwt_scriptsLoaded[$intern_86] = true;
    document.write($intern_70 + base + $intern_87);
  }
  if (!__gwt_scriptsLoaded[$intern_88]) {
    __gwt_scriptsLoaded[$intern_88] = true;
    document.write($intern_70 + base + $intern_89);
  }
  if (!__gwt_scriptsLoaded[$intern_90]) {
    __gwt_scriptsLoaded[$intern_90] = true;
    document.write($intern_70 + base + $intern_91);
  }
  if (!__gwt_scriptsLoaded[$intern_92]) {
    __gwt_scriptsLoaded[$intern_92] = true;
    document.write($intern_70 + base + $intern_93);
  }
  if (!__gwt_scriptsLoaded[$intern_94]) {
    __gwt_scriptsLoaded[$intern_94] = true;
    document.write($intern_70 + base + $intern_95);
  }
  if (!__gwt_scriptsLoaded[$intern_96]) {
    __gwt_scriptsLoaded[$intern_96] = true;
    document.write($intern_70 + base + $intern_97);
  }
  if (!__gwt_scriptsLoaded[$intern_98]) {
    __gwt_scriptsLoaded[$intern_98] = true;
    document.write($intern_70 + base + $intern_99);
  }
  $doc.write($intern_100);
}

org_ralasafe_ui_RalasafeViewer.__gwt_initHandlers = function(resize, beforeunload, unload){
  var $wnd = window, oldOnResize = $wnd.onresize, oldOnBeforeUnload = $wnd.onbeforeunload, oldOnUnload = $wnd.onunload;
  $wnd.onresize = function(evt){
    try {
      resize();
    }
     finally {
      oldOnResize && oldOnResize(evt);
    }
  }
  ;
  $wnd.onbeforeunload = function(evt){
    var ret, oldRet;
    try {
      ret = beforeunload();
    }
     finally {
      oldRet = oldOnBeforeUnload && oldOnBeforeUnload(evt);
    }
    if (ret != null) {
      return ret;
    }
    if (oldRet != null) {
      return oldRet;
    }
  }
  ;
  $wnd.onunload = function(evt){
    try {
      unload();
    }
     finally {
      oldOnUnload && oldOnUnload(evt);
      $wnd.onresize = null;
      $wnd.onbeforeunload = null;
      $wnd.onunload = null;
    }
  }
  ;
}
;
org_ralasafe_ui_RalasafeViewer();
