package toolService.jumei.updateJm;

import toolService.jumei.updateJm.task.*;

/**
 * Created by simin on 2017/10/25.
 */
public class RunJmTask {

    //以下只能执行一个，其余的需要注释掉，屏蔽。
    public static void main(String[] args) throws Exception {
        //产品库存属性相关
        {
//            UpdateProductJmTask updateProductJm = new UpdateProductJmTask();
            //更新产品库属性
//            updateProductJm.updateProductTask();
            //添加spu
//            updateProductJm.addSpuTask();
            //修改spu
//            updateProductJm.updateJmSpuTask();
        }

        //Deal详情相关
        {
//            UpdateDealJmTask updateDealJmTask = new UpdateDealJmTask();
            //更新Deal的相关属性
//            updateDealJmTask.updateDealTask();
            //更新Deal的sku
//            updateDealJmTask.updateDealSkuTask();
            //更新Deal的价格
//            updateDealJmTask.updateDealPriceTask();
            //Deal 添加sku
//            updateDealJmTask.addDealSkuTask();
            //Deal 再售
//            updateDealJmTask.copyDealTask("2017-11-24 10:00:00","2017-11-27 09:59:59");
            //Deal 延期
//            updateDealJmTask.updateDealEndTimeTask("2017-11-11 23:59:59");
            //Deal sku的上下架
//            updateDealJmTask.skuIsOnSaleTask();
        }

        //Mall详情相关
        {
//            UpdateMallJmTask updateMallJm = new UpdateMallJmTask();
            //更新Mall相关属性
//            updateMallJm.updateMallTask();
            //更新Mall 价格
//            updateMallJm.updateMallPriceTask();
            //更新 Mall sku
//            updateMallJm.updateMallSkuTask();
            //Mall 上下架
//            updateMallJm.updateMallIsOnSaleTask();
            //Mall添加sku
//            updateMallJm.addMallSkuTask();
        }

        //商品详情获取
        {
            GetJmInfoTask getJmInfoTask=new GetJmInfoTask();
            //ProductId 获取产品信息
            getJmInfoTask.getProductInfoByProductIdTask();
            //JmHashId 获取Deal信息
//            getJmInfoTask.getDealInfoByDealIdTask();
            //JmOrderId 获取订单信息
//            getJmInfoTask.getOrderInfoByOrderId();
        }

        //库存相关
        {
//            UpdateStockJmTask updateStockJmTask=new UpdateStockJmTask();
            //单例库存同步
//            updateStockJmTask.updateStockTask();
            //批量库存同步
//            updateStockJmTask.updateStockBatchTask();
        }
    }
}
