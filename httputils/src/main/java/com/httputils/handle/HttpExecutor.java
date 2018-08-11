package com.httputils.handle;


import com.httputils.HttpRequest;
import com.httputils.body.HttpPost;

public interface HttpExecutor {
	void processRequest(HttpPost post, String httpMethod);
	void cancelRequest(HttpRequest request);

}
