package ischool.dsa.client.async;

import ischool.dsa.client.ContractConnection;
import ischool.dsa.exception.DSAServerException;
import ischool.dsa.exception.DSAServiceException;
import ischool.dsa.utility.DSADelayInfo;
import ischool.dsa.utility.DSRequest;
import ischool.dsa.utility.DSResponse;
import ischool.dsa.utility.XmlUtil;
import ischool.dsa.utility.http.Cancelable;

import org.w3c.dom.Element;

public class DelayRequestAsyncWorker implements Runnable {

	public ContractConnection _connection;
	public DSRequest _request;
	public String _targetService;
	public IResponseListener _listener;

	public DelayRequestAsyncWorker(ContractConnection connection, String targetService, DSRequest request, IResponseListener listener) {
		_connection = connection;
		_targetService = targetService;
		_listener = listener;
		_request = request;
	}

	@Override
	public void run() {
		try {
			_request.setParameter(DSADelayInfo.ATTR_DELAY, "true");

			String delayID = _connection.sendDelayRequest(_targetService,
					_request, new Cancelable());

			boolean first = true;
			while (true) {
				DSResponse rsp = _connection.getDelayResponse(delayID, new Cancelable());
				Element content = rsp.getContent();
				String status = XmlUtil.getElementText(content, "Status");

				if (status.equals(DSADelayInfo.PROC_EXECUTING)) {
					if (!first) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {

						}
					}
				} else {
					_listener.onServiceResponse(rsp);
					break;
				}
				first = false;
			}

		} catch (Exception ex) {
			if (ex instanceof DSAServiceException) {
				_listener.onServiceError((DSAServiceException) ex);
			} else if (ex instanceof DSAServerException) {
				_listener.onServerError((DSAServerException) ex);
			}

			_listener.onError(ex);
		} finally {
			_listener.onCompleted();
		}
	}
}
