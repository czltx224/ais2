/**
 * www.velcro.soft.com.cn
 * 2008.6.13 Ricky.zhou
 * 画线工具，主要方法： 
 * 1.划直线 drawLine(x1,y1,x2,y2);
 * 2.画带箭头的直线 drawArrow(x1,y1,x2,y2);
 * 用法：
 * var jg = new jsGraphics(id);
 * jp.drawLine(10,10,10,400);
 * jp.paint();
 */

/**
 * jsGraphics构造函数
 * @param {Object||[String]||null} container
 * 参数为String或者空,为空时将创建默认画布tempCanvas对象
 * 为String时 在document对象上创建一个id等于container的Div，图像画在Div对象上
 * 若对象已存在，在用已存在的对象
 */
function jsGraphics(container) {
	this.bw = new checkbrowser();
	this.color = "#4a7ebb";
	this.backbuffer = new Array();
	this.linewidth = 1;
	this.arrowwidth = 6;
	this.arrowangle = 15 / 180 * Math.PI;//弧度
	if (!container || typeof (container) != "string" || container == "") {
		container = "tempCanvas";//默认画布tempCanvas
	}
	if ($(container)) {
		this.canvas = $(container);
	} else {
		var canvas =  document.getElementsByTagName("body")[0];
		var containerDiv = document.createElement("div");
		containerDiv.id = container;
		canvas.appendChild(containerDiv);
		this.canvas = containerDiv;
	}
}
jsGraphics.prototype.paint = function () {
	if (this.bw.ie) {
		this.canvas.insertAdjacentHTML("BeforeEnd", this.backbuffer.join(""));
	} else {
		var r = document.createRange();
		r.setStartBefore(this.canvas);
		this.canvas.appendChild(r.createContextualFragment(this.backbuffer.join("")));
	}
	this.backbuffer = new Array();
}
jsGraphics.prototype.clear = function () {
	this.canvas.innerHTML = "";
	this.backbuffer = new Array();
}
jsGraphics.prototype.setColor = function (color) {
	this.color = color;
}

jsGraphics.prototype.setPixel = function (x, y, w, h) {
   	this.backbuffer.push("<div style=\"position:absolute;left:");
	this.backbuffer.push(x);
	this.backbuffer.push("px;top:");
	this.backbuffer.push(y);
	this.backbuffer.push("px;width:");
	this.backbuffer.push(w);
	this.backbuffer.push("px;height:");
	this.backbuffer.push(h);
	this.backbuffer.push("px;background-color:");
	this.backbuffer.push(this.color);
	this.backbuffer.push(";overflow:hidden;\"></div>");
}
jsGraphics.prototype.drawTerm = function (x, y, w, h,term) {
   	this.backbuffer.push("<div style=\"font-size:12px;position:absolute;left:");
	this.backbuffer.push(x);
	this.backbuffer.push("px;top:");
	this.backbuffer.push(y);
	this.backbuffer.push("px;width:");
	this.backbuffer.push(w);
	this.backbuffer.push("px;height:");
	this.backbuffer.push(h);
	this.backbuffer.push("px;");
	this.backbuffer.push(";overflow:show;\">");
	this.backbuffer.push(term);
	this.backbuffer.push("</div>");
}
jsGraphics.prototype.fillPolygon = function (array_x, array_y) {
	var i;
	var y;
	var miny, maxy;
	var x1, y1;
	var x2, y2;
	var ind1, ind2;
	var ints;
	var n = array_x.length;
	if (!n) {
		return;
	}
	miny = array_y[0];
	maxy = array_y[0];
	for (i = 1; i < n; i++) {
		if (array_y[i] < miny) {
			miny = array_y[i];
		}
		if (array_y[i] > maxy) {
			maxy = array_y[i];
		}
	}
	for (y = miny; y <= maxy; y++) {
		var polyInts = new Array();
		ints = 0;
		for (i = 0; i < n; i++) {
			if (!i) {
				ind1 = n - 1;
				ind2 = 0;
			} else {
				ind1 = i - 1;
				ind2 = i;
			}
			y1 = array_y[ind1];
			y2 = array_y[ind2];
			if (y1 < y2) {
				x1 = array_x[ind1];
				x2 = array_x[ind2];
			} else {
				if (y1 > y2) {
					y2 = array_y[ind1];
					y1 = array_y[ind2];
					x2 = array_x[ind1];
					x1 = array_x[ind2];
				} else {
					continue;
				}
			}
			if ((y >= y1) && (y < y2)) {
				polyInts[ints++] = Math.round((y - y1) * (x2 - x1) / (y2 - y1) + x1);
			} else {
				if ((y == maxy) && (y > y1) && (y <= y2)) {
					polyInts[ints++] = Math.round((y - y1) * (x2 - x1) / (y2 - y1) + x1);
				}
			}
		}
		polyInts.sort(compInt);
		for (i = 0; i < ints; i += 2) {
			this.setPixel(polyInts[i], y, polyInts[i + 1] - polyInts[i] + 1, 1);
		}
	}
}
jsGraphics.prototype.drawLine = function (x1, y1, x2, y2) {
	if (x1 > x2) {
		var tmpx = x1;
		var tmpy = y1;
		x1 = x2;
		y1 = y2;
		x2 = tmpx;
		y2 = tmpy;
	}
	var dx = x2 - x1;
	var dy = y2 - y1;
	var sy = 1;
	if (dy < 0) {
		sy = -1;
		dy = -dy;
	}
	dx = dx << 1;
	dy = dy << 1;
	if (dy <= dx) {
		var fraction = dy - (dx >> 1);
		var mx = x1;
		while (x1 != x2) {
			x1++;
			if (fraction >= 0) {
				this.setPixel(mx, y1, x1 - mx, 1);
				y1 += sy;
				mx = x1;
				fraction -= dx;
			}
			fraction += dy;
		}
		this.setPixel(mx, y1, x1 - mx, 1);
	} else {
		var fraction = dx - (dy >> 1);
		var my = y1;
		if (sy > 0) {
			while (y1 != y2) {
				y1++;
				if (fraction >= 0) {
					this.setPixel(x1++, my, 1, y1 - my);
					my = y1;
					fraction -= dy;
				}
				fraction += dx;
			}
			this.setPixel(x1, my, 1, y1 - my);
		} else {
			while (y1 != y2) {
				y1--;
				if (fraction >= 0) {
					this.setPixel(x1++, y1, 1, my - y1);
					my = y1;
					fraction -= dy;
				}
				fraction += dx;
			}
			this.setPixel(x1, y1, 1, my - y1);
		}
	}
}
/**
 * 画带箭头的线
 * @param {Object} beginX
 * @param {Object} beginY
 * @param {Object} endX
 * @param {Object} endY
 */
jsGraphics.prototype.drawArrow = function (beginX, beginY, endX, endY) {
	var linewidth = this.linewidth;
	var arrowwidth = this.arrowwidth;
	var arrowangle = this.arrowangle;
	var dx = (beginX - endX) * 1;
	var dy = (beginY - endY) * 1;
	var LineSlope = Math.atan(dx / dy); //直线斜率(弧度)   
	var flag = 1;
	if (dy < 0) {
		flag = -1;
	}
  	//求出中距每点的坐标
	var tmpLine = (linewidth * 1 + arrowwidth * 1) / (2 * Math.sin(arrowangle));
	var EndX1, EndY1;
	var EndX2, EndY2;
	var EndX3, EndY3;
	EndX1 = endX;
	EndY1 = endY;
	EndX2 = endX * 1 + flag * tmpLine * Math.sin(arrowangle * 1 + LineSlope * 1);
	EndY2 = endY * 1 + flag * tmpLine * Math.cos(arrowangle * 1 + LineSlope * 1);
	EndX3 = endX * 1 - flag * tmpLine * Math.sin(arrowangle - LineSlope);
	EndY3 = endY * 1 + flag * tmpLine * Math.cos(arrowangle - LineSlope);
	var XEndpoints = new Array(EndX1, EndX2, EndX3);
	var YEndpoints = new Array(EndY1, EndY2, EndY3);
	this.fillPolygon(XEndpoints, YEndpoints);
	this.drawLine(beginX, beginY, endX, endY);
}
/*浏览器检测*/
function checkbrowser() {
	this.b = document.body;
	this.dom = $ ? 1 : 0;
	this.ie = this.b && typeof this.b.insertAdjacentHTML != "undefined";
	this.mozilla = typeof (document.createRange) != "undefined" && typeof ((document.createRange()).setStartBefore) != "undefined";
}
function compInt(x, y) {
	return (x - y);
}