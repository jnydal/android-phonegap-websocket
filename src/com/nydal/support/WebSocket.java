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

package com.nydal.support;

import java.net.URI;

import org.jwebsocket.api.WebSocketClientEvent;
import org.jwebsocket.api.WebSocketClientListener;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.client.java.BaseWebSocketClient;
import org.jwebsocket.kit.WebSocketException;

import android.webkit.WebView;

/**
 * Wrapper class for jWebSocket client instance
 * 
 * @author Jørund Nydal
 *
 */
public class WebSocket implements WebSocketClientListener {
	
	private static String EVENT_ON_OPEN = "onopen";

	private static String EVENT_ON_MESSAGE = "onmessage";

	private static String EVENT_ON_CLOSE = "onclose";

	private static String EVENT_ON_ERROR = "onerror";
	
	private static String BLANK_MESSAGE = "";
	
	private final WebView appView;

	private final BaseWebSocketClient client;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected WebSocket(WebView appView, URI uri, String id) {
		this.appView = appView;
		this.id = id;
		client = new BaseWebSocketClient();
		client.addListener(this);
		try {
			client.open(uri.toString());
		} catch (WebSocketException e) {
			e.printStackTrace();
		}
	}

	public void onError(final Throwable t) {
		appView.post(new Runnable() {
			public void run() {
				appView.loadUrl(buildJavaScriptData(EVENT_ON_ERROR, t.getMessage()));
			}
		});
	}

	private String buildJavaScriptData(String event, String msg) {
		String _d = "javascript:WebSocket." + event + "(" + "{" + "\"_target\":\"" + id + "\"," + "\"data\":'" + msg
				+ "'" + "}" + ")";
		return _d;
	}

	public void processClosed(WebSocketClientEvent arg0) {
		appView.post(new Runnable() {
			public void run() {
				appView.loadUrl(buildJavaScriptData(EVENT_ON_CLOSE, BLANK_MESSAGE));
			}
		});
	}

	public void processOpened(WebSocketClientEvent arg0) {
		appView.post(new Runnable() {
			public void run() {
				appView.loadUrl(buildJavaScriptData(EVENT_ON_OPEN, BLANK_MESSAGE));
			}
		});
	}

	public void processOpening(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void processPacket(WebSocketClientEvent arg0, final WebSocketPacket arg1) {
		appView.post(new Runnable() {
			public void run() {
				appView.loadUrl(buildJavaScriptData(EVENT_ON_MESSAGE, arg1.getString()));
			}
		});
	}

	public void processReconnecting(WebSocketClientEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
