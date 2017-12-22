package bean.JmBean;

import java.util.List;

/**
 * Created by simin on 2017/10/26.
 */
public class JmGetProductInfo_Spus {
    private String spu_no;

    private String upc_code;

    private String propery;

    private String size;

    private String attributes;

    private Double abroad_price;

    private String area_code;

    private String abroad_url;

    private List<JmGetProductInfo_Spus_Sku> sku_list;

    public String getSpu_no() {
        return spu_no;
    }

    public void setSpu_no(String spu_no) {
        this.spu_no = spu_no;
    }

    public String getUpc_code() {
        return upc_code;
    }

    public void setUpc_code(String upc_code) {
        this.upc_code = upc_code;
    }

    public String getPropery() {
        return propery;
    }

    public void setPropery(String propery) {
        this.propery = propery;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Double getAbroad_price() {
        return abroad_price;
    }

    public void setAbroad_price(Double abroad_price) {
        this.abroad_price = abroad_price;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getAbroad_url() {
        return abroad_url;
    }

    public void setAbroad_url(String abroad_url) {
        this.abroad_url = abroad_url;
    }

    public List<JmGetProductInfo_Spus_Sku> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<JmGetProductInfo_Spus_Sku> sku_list) {
        this.sku_list = sku_list;
    }
}
