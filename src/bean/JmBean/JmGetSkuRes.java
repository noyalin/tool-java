package bean.JmBean;

/**
 * Created by simin on 2017/10/26.
 */
public class JmGetSkuRes {
    private String sku_no;
    private String customs_product_number;
    private String customers_price;
    private String market_price;
    private String discount;
    private String stocks;
    private String is_enable;

    public String getSku_no() {
        return sku_no;
    }

    public void setSku_no(String sku_no) {
        this.sku_no = sku_no;
    }

    public String getCustoms_product_number() {
        return customs_product_number;
    }

    public void setCustoms_product_number(String customs_product_number) {
        this.customs_product_number = customs_product_number;
    }

    public String getCustomers_price() {
        return customers_price;
    }

    public void setCustomers_price(String customers_price) {
        this.customers_price = customers_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getIs_enable() {
        if("1".equals(is_enable)) {
            return  this.is_enable="Y";
        }else {
            return  this.is_enable="N";
        }
    }

    public void setIs_enable(String is_enable) {
        this.is_enable=is_enable;
    }
}
