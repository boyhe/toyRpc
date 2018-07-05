package rpc.common;

import java.io.Serializable;

public class RpcResponse implements Serializable {
    private static final long serialVersionUID = 214L;
    private String responseId;
    private Object result;
    private Exception exception;

    public RpcResponse() {
    }

    public RpcResponse(String responseId, Object result, Exception exception) {
        this.responseId = responseId;
        this.result = result;
        this.exception = exception;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "responseId='" + responseId + '\'' +
                ", result=" + result +
                ", exception=" + exception +
                '}';
    }
}
