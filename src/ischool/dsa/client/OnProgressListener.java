package ischool.dsa.client;

public interface OnProgressListener<T> extends OnReceiveListener<T> {
	void onProgressUpdate(String message);
}