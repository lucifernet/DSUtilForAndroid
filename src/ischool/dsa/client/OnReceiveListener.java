package ischool.dsa.client;

public interface OnReceiveListener<T> {
	void onReceive(T result);

	void onError(Exception ex);
}