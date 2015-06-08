package net.minigab.nativeevents;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.*;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class NativeEvents extends CordovaPlugin implements OnTouchListener {
	private View view;
	private CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.view = this.webView.getView();
		this.view.setOnTouchListener(this);
		this.callbackContext = callbackContext;
		return true;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int eventAction = event.getAction();
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch(eventAction){
			case MotionEvent.ACTION_UP:
				sendEvent("touchup", x, y);
				break;
			case MotionEvent.ACTION_DOWN:
				sendEvent("touchdown", x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				sendEvent("touchmove", x, y);
				break;
			default:
		}
		return false;
	}

	private void sendEvent(String eventName, int x, int y){
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject.put("name", eventName);
			jsonObject.put("x", x);
			jsonObject.put("y", y);
		}catch (JSONException e){
		}

		PluginResult result = new PluginResult(PluginResult.Status.OK, jsonObject);
		result.setKeepCallback(true);
		this.callbackContext.sendPluginResult(result);
	}
}
