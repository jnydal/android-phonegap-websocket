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
(function() {
	var WebSocket = window.WebSocket = function(url) {
		this.socket = WebSocketFactory.getInstance(url);
		if (this.socket) {
			WebSocket.store[this.socket.getId()] = this;
		} else {
			throw new Error('Websocket instantiation failed!');
		}
	};

	WebSocket.store = {};

	WebSocket.onmessage = function (evt) {
		WebSocket.store[evt._target].onmessage.call(window, evt);
	}	

	WebSocket.onopen = function (evt) {
		WebSocket.store[evt._target].onopen.call(window, evt);
	}

	WebSocket.onclose = function (evt) {
		WebSocket.store[evt._target].onclose.call(window, evt);
	}

	WebSocket.onerror = function (evt) {
		WebSocket.store[evt._target].onerror.call(window, evt);
	}

	WebSocket.prototype.send = function(data) {
		this.socket.send(data);
	}

	WebSocket.prototype.close = function() {
		this.socket.close();
	}

	WebSocket.prototype.getReadyState = function() {
		this.socket.getReadyState();
	}
})();