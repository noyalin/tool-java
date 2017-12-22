package bean.JmBean;

import java.util.List;

/**
 * Created by simin on 2017/10/26.
 */
public class JmGetDealInfoRes {
    private String deal_status;
    private String product_id;
    private String product_short_name;
    private List<JmGetSkuRes> sku_list;
    private String long_name;
    private String medium_name;
    private String short_name;
    private String brand_name;
    private String start_time;
    private String end_time;
    private String shipping_system_id;
    private String user_purchase_limit;

    public String getDeal_status() {
        if("1".equals(deal_status)){
            this.deal_status="Y";
        }else {
            this.deal_status = "N";
        }
        return deal_status;
    }

    public void setDeal_status(String deal_status) {
        this.deal_status=deal_status;
    }

    public String getUser_purchase_limit() {
        return user_purchase_limit;
    }

    public void setUser_purchase_limit(String user_purchase_limit) {
        this.user_purchase_limit = user_purchase_limit;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public List<JmGetSkuRes> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<JmGetSkuRes> sku_list) {
        this.sku_list = sku_list;
    }

    public String getProduct_short_name() {
        return product_short_name;
    }

    public void setProduct_short_name(String product_short_name) {
        this.product_short_name = product_short_name;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getMedium_name() {
        return medium_name;
    }

    public void setMedium_name(String medium_name) {
        this.medium_name = medium_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getEnd_time() {
//        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Long time=new Long(end_time);
//        String endTime = format.format(time);
//        return endTime;
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(Long.parseLong(end_time) * 1000));
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_time() {
//        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(Long.parseLong(start_time) * 1000));
//        Long time=new Long(start_time);
//        String startTime = format.format(time);
//        return startTime;
    }

    public void setStart_time(String start_time) {

        this.start_time = start_time;
    }

    public String getShipping_system_id() {
        if("3151".equals(shipping_system_id)){
            return  this.shipping_system_id ="洛杉矶001仓库";
        }else {
            return shipping_system_id;
        }
    }

    public void setShipping_system_id(String shipping_system_id) {

        this.shipping_system_id = shipping_system_id;

    }
}
