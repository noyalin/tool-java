package toolService.calGroupSkuInvntory;

import bean.SkuGroupInvBean;
import dBSQLUtils.ConnectJDBC;
import dBSQLUtils.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simin on 2017/10/16.
 */
public class GetGroupSkuInvInfo {
    public static List<SkuGroupInvBean> getGroupSkuInvInfo(String groupSku){
        List<SkuGroupInvBean> groupSkuInvList = new ArrayList<SkuGroupInvBean>() ;
        PreparedStatement ps = null;
        Connection conn = ConnectJDBC.connectDB();
        ResultSet rs = null;
        String sql = SQLUtils.getMSSQL("");
        sql = SQLUtils.setParam(sql, "groupSku", groupSku);
//        List result = DBUtils.query(conn, sql);
//        DBUtils.closeDBConn(conn);
        if (null == sql || "".equals(sql)) {
            return new ArrayList<SkuGroupInvBean>();
        }
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (null != rs) {
                ResultSetMetaData rsm = rs.getMetaData();
                int count = rsm.getColumnCount();
                SkuGroupInvBean skuGroupInvBean=new SkuGroupInvBean();
                if (count > 0) {
                    while (rs.next()) {
                        skuGroupInvBean.setChannel(rs.getString("channel_id"));
                        skuGroupInvBean.setGroupSku(rs.getString("group_sku"));
                    }
                }

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return groupSkuInvList;
    }
}
