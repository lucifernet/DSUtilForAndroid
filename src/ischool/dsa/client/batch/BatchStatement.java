package ischool.dsa.client.batch;

import ischool.dsa.client.ContractConnection;
import ischool.dsa.utility.DSRequest;
import ischool.dsa.utility.XmlUtil;
import ischool.dsa.utility.http.Cancelable;

import java.util.UUID;

import org.w3c.dom.Element;

public class BatchStatement {
	public static final String COMMIT_SERVICE = "DS.Base.Commit";
	public static final String CANCEL_BATCH_SERVICE = "DS.Base.CancelBatch";
	
	private ContractConnection _connection;
	private String _batchID;
	private int _sequence = 0;

	public BatchStatement(ContractConnection connection) {
		_connection = connection;
		_batchID = UUID.randomUUID().toString();
	}

	public void addBatch(String serviceName, DSRequest request) {
		request.setParameter("BatchID", _batchID);
		request.setParameter("Sequence", String.valueOf(_sequence));
		_sequence++;
		
		_connection.sendRequest(serviceName, request, new Cancelable());
	}

	public void commit() {
		DSRequest req = new DSRequest();
		Element content = XmlUtil.createElement("BatchID");
		content.setTextContent(_batchID);
		req.setContent(content);
		
		_connection.sendRequest(COMMIT_SERVICE, req, new Cancelable());
	}

	public void cancelBatch() {
		DSRequest req = new DSRequest();
		Element content = XmlUtil.createElement("BatchID");
		content.setTextContent(_batchID);
		req.setContent(content);
		
		_connection.sendRequest(CANCEL_BATCH_SERVICE, req, new Cancelable());
	}
}
