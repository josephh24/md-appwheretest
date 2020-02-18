package com.example.appwhere.validaciones;

public class ResponseOperation {

    Boolean status;
    String texResponse;

    public ResponseOperation(Boolean status, String texResponse) {
        super();
        this.status = status;
        this.texResponse = texResponse;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTexResponse() {
        return texResponse;
    }

    public void setTexResponse(String texResponse) {
        this.texResponse = texResponse;
    }
}
