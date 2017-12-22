package bean;

/**
 * Created by simin on 2017/10/16.
 */
public class SkuGroupInvBean {
    private String channel;
    private String groupSku;
    private String sku;
    private String groupInv;
    private String singleInv;
    private String sepSingleInv;
    private String reqSepGroupInv;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGroupSku() {
        return groupSku;
    }

    public void setGroupSku(String groupSku) {
        this.groupSku = groupSku;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getGroupInv() {
        return groupInv;
    }

    public void setGroupInv(String groupInv) {
        this.groupInv = groupInv;
    }

    public String getSingleInv() {
        return singleInv;
    }

    public void setSingleInv(String singleInv) {
        this.singleInv = singleInv;
    }

    public String getSepSingleInv() {
        return sepSingleInv;
    }

    public void setSepSingleInv(String sepSingleInv) {
        this.sepSingleInv = sepSingleInv;
    }

    public String getReqSepGroupInv() {
        return reqSepGroupInv;
    }

    public void setReqSepGroupInv(String reqSepGroupInv) {
        this.reqSepGroupInv = reqSepGroupInv;
    }
}
