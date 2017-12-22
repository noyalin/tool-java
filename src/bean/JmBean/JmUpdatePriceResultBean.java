package bean.JmBean;

import java.util.List;

/**
 * Created by simin on 2017/10/25.
 */
public class JmUpdatePriceResultBean {
    String successCount;
    List<ErrorList> errorList;

    public String getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }

    public  List<ErrorList>  getErrorList() {
        return errorList;
    }

    public void setErrorList( List<ErrorList> errorList) {
        this.errorList = errorList;
    }

    public static class  ErrorList{
        String jumei_sku_no;
        String error_code;
        String error_message;

        public String getErrcode() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getJumei_sku_no() {
            return jumei_sku_no;
        }

        public void setJumei_sku_no(String jumei_sku_no) {
            this.jumei_sku_no = jumei_sku_no;
        }

        public String getError_message() {
            return error_message;
        }

        public void setError_message(String error_message) {
            this.error_message = error_message;
        }
    }
}
