var cordova = require('cordova'),
	exec = require('cordova/exec');

var DEVICE_PIXEL_RATIO = window.devicePixelRatio;
	
var NativeEvents = function() {
	exec(onEvent, function(){console.log(arguments);}, "NativeEvents", "register", []);
};

function onEvent() {
	var event = arguments[0];
	triggerEvent('native.'+ event.name, event.x/DEVICE_PIXEL_RATIO, event.y/DEVICE_PIXEL_RATIO);
};

function triggerEvent(event, x, y){
	cordova.fireDocumentEvent(event, {detail: {x: x, y: y}})
}

module.exports = new NativeEvents();

