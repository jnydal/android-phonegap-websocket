/*
 * Copyright (c) 2012 Jørund Nydal
 *  
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *  
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *  
 */

package com.feliz.support;

import java.net.URI;
import java.util.Random;

import android.util.Log;
import android.webkit.WebView;

/**
 * Overhead class for WebSocket.
 * 
 * @author Jørund Nydal
 *
 */
public class WebSocketFactory {

	WebView appView;

	public WebSocketFactory(WebView appView) {
		this.appView = appView;
	}

	public WebSocket getInstance(String url) {
		WebSocket socket = null;
		try {
			socket = new WebSocket(appView, new URI(url), getRandonUniqueId());
		} catch (Exception e) {
			Log.v("websocket error", e.toString());
		} 
		return socket;
	}

	private String getRandonUniqueId() {
		return "WEBSOCKET." + new Random().nextInt(100);
	}
}