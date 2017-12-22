package util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import com.mongodb.*;
//import com.mongodb.client.MongoDatabase;
import bean.ShopBean;
import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;


public class AllUtils {

	public static String TM_SCHEMA_PATH = "D:\\TmSchema\\";

	public static final String url = "http://gw.api.taobao.com/router/rest";

	public static final String url_jd = "https://api.jd.com/routerjson";


	public static String[] getAppKey(String target) {
        String[] appKey = new String[4];
        if ("001_23".equals(target)) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "62013001ZZ9c83cb4fe59499440ef154430b7679c3866f21792368114";
			appKey[3] = "isNotTongGou";
        }else if("001_20".equals(target)){
			appKey[0] = "21008948";
			appKey[1] = "0a16bd08019790b269322e000e52a19f";
			appKey[2] = "6200b179d1debabf87e9621a5ZZd59f8ff44c9fc96bbc8c907029661";
			appKey[3] = "isNotTongGou";
		}else if("001_26".equals(target)){
			appKey[0] = "BFA3102EFD4B981E9EEC2BE32DF1E44E";
			appKey[1] = "90742900899f49a5acfaf3ec1040a35c";
			appKey[2] = "614a5873-f72e-4efc-9208-c0c5db4e07ac";
			appKey[3] = "isNotTongGou";
		} else if ("4".equals(target)) {
            appKey[0] = "BFA3102EFD4B981E9EEC2BE32DF1E44E";
            appKey[1] = "90742900899f49a5acfaf3ec1040a35c";
            appKey[2] = "b8a15129-971d-4428-a9bb-f806d9817724";
			appKey[3] = "isNotTongGou";
        } else if (target.equals("14")) {
            appKey[0] = "BFA3102EFD4B981E9EEC2BE32DF1E44E";
            appKey[1] = "90742900899f49a5acfaf3ec1040a35c";
            appKey[2] = "8a408467-cba2-49be-9678-c4fa1f9167af";
			appKey[3] = "isTongGou";
        } else if (target.equals("7")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6200f0845a29c133ZZe49458c85368465f420a41fae61052260121769";
			appKey[3] = "isNotTongGou";
        } else if (target.equals("16")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6201618b0e82569a52f5a7216fhj3988ec6dcc53580cca52533968112";
			appKey[3] = "isNotTongGou";
        } else if (target.equals("030")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6201f10deedd3c24cbdf464ef0a53abad5eb71a742558302155082130";
			appKey[3] = "isNotTongGou";
        } else if (target.equals("928j")) {
            appKey[0] = "BFA3102EFD4B981E9EEC2BE32DF1E44E";
            appKey[1] = "90742900899f49a5acfaf3ec1040a35c";
            appKey[2] = "4326ace5-57d7-4b9e-b24a-3ac2471eabe3";
			appKey[3] = "isTongGou";
        } else if (target.equals("024")) {
			appKey[0] = "23470212";
			appKey[1] = "f181e11992c7642cdc377fc834244c50";
			appKey[2] = "6200d22aa3691151534ae61400d917ZZ6c0cc8f442edd442939402618";
			appKey[3] = "isTongGou";
		} else if (target.equals("928_31")) {
			appKey[0] = "23583061";
			appKey[1] = "796955b61f045769345d47e18b29cf22";
			appKey[2] = "6201c0788f407f9ZZfb038a14a52618b42fa10991ace5313081276392";
			appKey[3] = "isTongGou";
		} else if (target.equals("018_23")) {
			appKey[0] = "21008948";
			appKey[1] = "0a16bd08019790b269322e000e52a19f";
			appKey[2] = "620230429acceg4103a72932e22e4d53856b145a192140b2854639042";
			appKey[3] = "isNotTongGou";
		} else if (target.equals("017_30")) {
			appKey[0] = "23522315";
			appKey[1] = "9fe0dfa4076032c69f0401e388856f13";
			appKey[2] = "6200722ed346f8ab969ee3d41a4466ZZ463ad8dc502b37b3031513024";
			appKey[3] = "isTongGou";
		} else if (target.equals("033")) {
			appKey[0] = "23647092";
			appKey[1] = "02c5e8b733f7b6856af5dcbc5598fb2d";
			appKey[2] = "6201b3071ddf87fbc3aaf68525f4c5e755436bdf6f742573247884386";
			appKey[3] = "isNotTongGou";
		}else if (target.equals("008_23")) {
			appKey[0] = "21008948";
			appKey[1] = "0a16bd08019790b269322e000e52a19f";
			appKey[2] = "6201b0399b7ZZ545f850aaf076b674ebe39bcc2160ea1d62587754791";
			appKey[3] = "isNotTongGou";
		}else if (target.equals("005_20")) {
			appKey[0] = "21008948";
			appKey[1] = "0a16bd08019790b269322e000e52a19f";
			appKey[2] = "62004103b9968bb4ccegba24b66ef3034cf43cfc7f5c409394694533";
			appKey[3] = "isNotTongGou";
		}
        return appKey;
    }


	public static ShopBean getShopBean(String shopCart){
		ShopBean shopBean = new ShopBean();
		if(shopCart.equals("all_27")) {
			shopBean.setAppKey("131");
			shopBean.setSessionKey("7e059a48c30c67d2693be14275c2d3be");
			shopBean.setAppSecret("0f9e3437ca010f63f2c4f3a216b7f4bc9698f071");
			shopBean.setApp_url("http://openapi.ext.jumei.com/");
		}else if(shopCart.equals("001_20")){
			shopBean.setAppKey("21008948");
			shopBean.setSessionKey("0a16bd08019790b269322e000e52a19f");
			shopBean.setAppSecret("6200b179d1debabf87e9621a5ZZd59f8ff44c9fc96bbc8c907029661");
			shopBean.setApp_url("http://gw.api.taobao.com/router/rest");
		}else if(shopCart.equals("001_23")){
			shopBean.setAppKey("21008948");
			shopBean.setSessionKey("0a16bd08019790b269322e000e52a19f");
			shopBean.setAppSecret("62013001ZZ9c83cb4fe59499440ef154430b7679c3866f21792368114");
			shopBean.setApp_url("http://gw.api.taobao.com/router/rest");
		}else if(shopCart.equals("001_24")){

		}else if(shopCart.equals("001_26")){
			shopBean.setAppKey("BFA3102EFD4B981E9EEC2BE32DF1E44E");
			shopBean.setSessionKey("90742900899f49a5acfaf3ec1040a35c");
			shopBean.setAppSecret("614a5873-f72e-4efc-9208-c0c5db4e07ac");
			shopBean.setApp_url("https://api.jd.com/routerjson");
		}else if(shopCart.equals("001_34")){

		}else if(shopCart.equals("005_20")){

		}else if(shopCart.equals("008_23")){

		}else if(shopCart.equals("010_23")){

		}else if(shopCart.equals("017_30")){

		}else if(shopCart.equals("033_30")){

		}else if(shopCart.equals("034_23")){

		}else if(shopCart.equals("034_41")){

		}else if(shopCart.equals("044_23")){

		}else if(shopCart.equals("928_31")){
			shopBean.setAppKey("23583061");
			shopBean.setSessionKey("796955b61f045769345d47e18b29cf22");
			shopBean.setAppSecret("6201c0788f407f9ZZfb038a14a52618b42fa10991ace5313081276392");
			shopBean.setApp_url("http://gw.api.taobao.com/router/rest");
		}else if(shopCart.equals("928_41")){

		}
		return shopBean;
	}


	public static void writeToDisk(String str,String id) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("E:\\AAA\\" + id +".txt", true),"UTF-8");
			osw.write(str+ "\n");
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToDisk(String str, String id, String path) {
		try {
			createDir(path);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + id + ".txt", true), "UTF-8");
			osw.write(str+ "\n");
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readFromDisk(){
		String numid;
		ArrayList<String> numiidList = new ArrayList<String>();
		//读取文件
		File csv = new File("C:\\temp\\numiid.csv");
		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader(csv));
			for (String strs = buf.readLine(); strs != null; strs = buf.readLine()){
				
				if("".equalsIgnoreCase(strs)){
					continue;
				}
				numid = strs.trim();
				numiidList.add(numid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numiidList;
	}

	public static void writeSchemaToDisk(String str, String cid, boolean isproduct) {
		try {
			if (null == str) {
				str = "";
			}
			//
			Document ttdocument = DocumentHelper.parseText(str);
			//
			XMLWriter write = null;
			if (isproduct) {
				write = new XMLWriter(
						new FileOutputStream(TM_SCHEMA_PATH
								+ cid + "_product.xml"));
			} else {
				write = new XMLWriter(new FileOutputStream(
						TM_SCHEMA_PATH + cid + "_item.xml"));
			}
			write.write(ttdocument);
			write.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void downloadImage(String imageUrl, String path, String folderName, String name) {
		try {
			createDir(path + folderName);
			URL url = new URL(imageUrl);

			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(new File(path + folderName + "\\" + name + ".jpg"));
			byte[] buffer = new byte[8096];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			fileOutputStream.write(buffer);
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getUpdateProductRule(String productId, String target) {
		// 取得AppKey
		String[] appKey = AllUtils.getAppKey(target);
		TaobaoClient client = new AutoRetryTaobaoClient(AllUtils.url, appKey[0], appKey[1], "xml");

		TmallProductUpdateSchemaGetRequest req = new TmallProductUpdateSchemaGetRequest();
		req.setProductId(Long.parseLong(productId));
		TmallProductUpdateSchemaGetResponse rsp = null;
		try {
			rsp = client.execute(req, appKey[2]);

		} catch (ApiException e) {
			e.printStackTrace();
		}

		if (rsp.isSuccess()) {
			return rsp.getUpdateProductSchema();
		}
		return "";
	}
//	public static String getUpdateItemRule(String numIId, String target) {
//		// 取得AppKey
//		String[] appKey = AllUtils.getAppKey(target);
//		TaobaoClient client = new AutoRetryTaobaoClient(AllUtils.url, appKey[0], appKey[1], "xml");
//		// 返回发布商品的规则文档
//		String downLoadItemUpdateXml = "";
//		if ("isTongGou".equals(appKey[3])) {
//			TmallItemUpdateSimpleschemaGetRequest request = new TmallItemUpdateSimpleschemaGetRequest();
//			request.setItemId(Long.parseLong(numIId));
//			try {
//				TmallItemUpdateSimpleschemaGetResponse response = client.execute(request, appKey[2]);
//				if (response.isSuccess()) {
//					downLoadItemUpdateXml = response.getResult();
//					AllUtils.writeSchemaToDisk(downLoadItemUpdateXml, numIId + "_downLoadUpdateSimple", false);
//				} else {
//					if (null != response.getSubMsg()) {
//						System.out.println("商品编辑规则信息获取接口接口Err!---" + response.getSubMsg());
//					}
//				}
//			} catch (ApiException e) {
//				e.printStackTrace();
//			}
//		} else {
//			TmallItemUpdateSchemaGetRequest req = new TmallItemUpdateSchemaGetRequest();
//			try {
//				// 将XML放入本地
//				req.setItemId(Long.valueOf(numIId));
//
//				TmallItemUpdateSchemaGetResponse rsp = client.execute(req, appKey[2]);
//				if (rsp.isSuccess()) {
//					downLoadItemUpdateXml = rsp.getUpdateItemResult();
//					AllUtils.writeSchemaToDisk(downLoadItemUpdateXml, numIId + "_downLoadUpdate", false);
//				} else {
//					if (null != rsp.getSubMsg()) {
//						System.out.println("商品编辑规则信息获取接口接口Err!---" + rsp.getSubMsg());
//					}
//				}
//			} catch (ApiException e) {
//				System.out.println("商品编辑规则信息获取接口接口Err!---" + e.getMessage());
//			}
//		}
//		return downLoadItemUpdateXml;
//	}

//	public static String getAddItemRule(String cid, String pid, String target) {
//		// 取得AppKey
//		String[] appKey = AllUtils.getAppKey(target);
//		TaobaoClient client = new AutoRetryTaobaoClient(AllUtils.url, appKey[0], appKey[1], "xml");
//		// 返回发布商品的规则文档
//		String downLoadItemAddXml = "";
//		if ("isTongGou".equals(appKey[3])) {
//			TmallItemAddSimpleschemaGetRequest request = new TmallItemAddSimpleschemaGetRequest ();
//			try {
//				TmallItemAddSimpleschemaGetResponse response = client.execute(request, appKey[2]);
//				if (response.isSuccess()) {
//					downLoadItemAddXml = response.getResult();
//					AllUtils.writeSchemaToDisk(downLoadItemAddXml, "DownLoadAddSimple", false);
//				} else {
//					if (null != response.getSubMsg()) {
//						System.out.println("商品编辑规则信息获取接口接口Err!---" + response.getSubMsg());
//					}
//				}
//			} catch (ApiException e) {
//				e.printStackTrace();
//			}
//		} else {
//			TmallItemAddSchemaGetRequest req = new TmallItemAddSchemaGetRequest();
//			try {
//				// 将XML放入本地
//				req.setCategoryId(Long.parseLong(cid));
//				req.setProductId(Long.parseLong(pid));
//				TmallItemAddSchemaGetResponse rsp = client.execute(req, appKey[2]);
//				if (rsp.isSuccess()) {
//					downLoadItemAddXml = rsp.getAddItemResult();
//					AllUtils.writeSchemaToDisk(downLoadItemAddXml, cid + "_" + pid + "_downLoadUpdate", false);
//				} else {
//					if (null != rsp.getSubMsg()) {
//						System.out.println("商品编辑规则信息获取接口接口Err!---" + rsp.getSubMsg());
//					}
//				}
//			} catch (ApiException e) {
//				System.out.println("商品编辑规则信息获取接口接口Err!---" + e.getMessage());
//			}
//		}
//		return downLoadItemAddXml;
//	}


	// 创建目录
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + destDirName);
			return true;
		} else {
			System.out.println("创建目录失败！");
			return false;
		}
	}

//	public static MongoDatabase getMongo(String databaseName) {
//
//		MongoDatabase mongoDatabase = null;
//		try {
//			ServerAddress serverAddress = new ServerAddress("10.0.0.59", 8787);
//
//			MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("app_user", "cms", "BmdJ23".toCharArray());
//			List<MongoCredential> credentials = new ArrayList<>();
//			credentials.add(mongoCredential);
//
//			MongoClient mongoClient = new MongoClient(serverAddress, credentials);
//			mongoDatabase = mongoClient.getDatabase(databaseName);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mongoDatabase;
//	}

//	private static MongoClientOptions getConfOptions() {
//		return new MongoClientOptions.Builder().socketKeepAlive(true) // 是否保持长链接
//				.connectTimeout(100000) // 链接超时时间
//				.socketTimeout(100000) // read数据超时时间
////				.readPreference(ReadPreference.primary()) // 最近优先策略
//				.connectionsPerHost(30) // 每个地址最大请求数
//				.maxWaitTime(150000) // 长链接的最大等待时间
//				.build();
//	}
//
//	public static String getAddProductXml(String cid, String brandId, String target) {
//		// 取得AppKey
//		String[] appKey = AllUtils.getAppKey(target);
//		TaobaoClient client = new AutoRetryTaobaoClient(AllUtils.url, appKey[0], appKey[1], "xml");
//		String xml = "";
//
//		TmallProductAddSchemaGetRequest req = new TmallProductAddSchemaGetRequest();
//		req.setCategoryId(Long.parseLong(cid));
//		req.setBrandId(Long.parseLong(brandId));
//		TmallProductAddSchemaGetResponse rsp = null;
//		try {
//			rsp = client.execute(req, appKey[2]);
//
//			if (rsp.isSuccess()) {
//				xml = rsp.getAddProductRule();
//			}
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
//		return xml;
//	}


}
