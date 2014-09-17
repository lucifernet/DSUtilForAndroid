package ischool.dsa.client.async;

import ischool.dsa.exception.DSAServerException;
import ischool.dsa.exception.DSAServiceException;
import ischool.dsa.utility.DSResponse;

public interface IResponseListener {
	void onServiceResponse(DSResponse response);

	void onServiceError(DSAServiceException ex);

	void onServerError(DSAServerException ex);

	void onError(Exception ex);

	void onCompleted();
}
