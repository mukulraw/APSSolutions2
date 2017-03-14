package com.aps.user.apssolutions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class SignatureBean{

    @SerializedName("signature_detail")
    @Expose
    private List<SignatureDetail> signatureDetail = null;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<SignatureDetail> getSignatureDetail() {
        return signatureDetail;
    }

    public void setSignatureDetail(List<SignatureDetail> signatureDetail) {
        this.signatureDetail = signatureDetail;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}