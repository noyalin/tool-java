package dBSQLUtils;

/**
 * Created by simin on 2017/10/16.
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * SQL 脚本执行类
 *
 */
public final class SQLUtils {

//    private static Logger log = Logger.getLogger(SQLUtils.class.getName());

    public static HashMap<String, String> sqllist;
    public static HashMap<String, String> mssqllist;

    public static void init(ClassLoader cl, String sqlConfig) {
        try {
            sqllist = loadSql(cl, sqlConfig);
//            log.info("size:" + sqllist.size());
        } catch (Exception e) {
//            log.info("SQL LOAD FAILURE.");
        }
    }

    public static void initms(ClassLoader cl, String sqlConfig) {
        try {
            mssqllist = loadSql(cl, sqlConfig);
//            log.info("mssql size:" + mssqllist.size());
        } catch (Exception e) {
//            log.info("MSSQL LOAD FAILURE.");
        }
    }
    /**
     * 读取 SQL 文件，获取 SQL 语句
     * @param sqlFile
     *            SQL 脚本文件
     * @return List<sql> 返回所有 SQL 语句的 List
     * @throws Exception
     */
    private static HashMap<String, String> loadSql(ClassLoader cl, String sqlFile) throws Exception {
        HashMap<String, String> sqlList = new HashMap<String, String>();
        try {
            InputStream sqlFileIn = cl.getResourceAsStream(sqlFile);
            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead));
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder  =  factory.newDocumentBuilder();
            String xml = sqlSb.toString();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            Element root = doc.getDocumentElement();
            NodeList cats = root.getElementsByTagName("sql");
            for(int i = 0; i < cats.getLength(); i++){
                Node sqlNode = cats.item(i);
                NamedNodeMap attr = sqlNode.getAttributes();
                Node value = attr.getNamedItem("id");
                sqlList.put(value.getTextContent().trim(), sqlNode.getTextContent().trim());
            }

            return sqlList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static String getSQL(String sqlId){
        String newSql = sqllist.get(sqlId).replaceAll("\\r\\n", " ").replaceAll("\\n", " ");
        //log.info("sql:" + newSql);
        return newSql;
    }
    public static String getMSSQL(String sqlId){
        String newSql = mssqllist.get(sqlId).replaceAll("\\r\\n", " ").replaceAll("\\n", " ");
        //log.info("sql:" + newSql);
        return newSql;
    }

    public static String setParam(String sql, String name, String value){
        String strName = "#" + name + "#";
        String strValue = "'" + value + "'";
        String strSql = sql.replace(strName, strValue);
        //log.info("excute sql:" + strSql);
        return strSql;
    }
    public static String setParamNoQuo(String sql, String name, String value){
        String strName = "#" + name + "#";
        String strValue = value;
        String strSql = sql.replace(strName, strValue);
        //log.info("excute sql:" + strSql);
        return strSql;
    }
    public static String setParam(String sql, String name, int value){
        String strName = "#" + name + "#";
        String strValue = String.valueOf(value);
        String strSql = sql.replace(strName, strValue);
        //log.info("excute sql:" + strSql);
        return strSql;
    }
    public static String setParam(String sql, String name,Timestamp value){
        String strName = "#" + name + "#";
        String strValue = "'" + value + "'";
        String strSql = sql.replace(strName, strValue);
        //log.info("excute sql:" + strSql);
        return strSql;
    }
}
