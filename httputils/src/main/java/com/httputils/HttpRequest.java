package com.httputils;

public interface HttpRequest {

	boolean cancel();

	boolean isCancel();

	boolean recycle();

	boolean isRecycled();

}
