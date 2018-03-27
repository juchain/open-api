package com.blockshine.common.constant;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 返回编码映射表
 *
 *  @author maxiaodong
 */
public class CodeConstant {

    private static ConcurrentHashMap<String, String> codeMap = new ConcurrentHashMap<String, String>();

    static {
        codeMap.put("success", "success");
        codeMap.put("PARAM_LOST", "PARAM_LOST");

    }


    public static final int SUCCESS = 0;
    /**
     * 参数丢失
     */
    public static final int PARAM_LOST = 1000101;
    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 1000201;

    /**
     * token 不存在
     */
    public static final int NOT_TOKEN = 1000301;


    /**
     * token不正确
     */
    public static final int EXCEPTION_TOKEN = 1000302;

    /**
     * token终止
     */
    public static final int EXPIRED_TOKEN = 1000303;


    /**
     * 服务器内部错误
     */
    public static final int INTERAL_ERROR = 2000101;

    /**
     * 第三方调用错误
     */
    public static final int THIRD_PARTY_ERROR = 3000101;

    /**
     * 拒绝服务
     */
    public static final int SERVICE_REFUSED = 4000101;
    
    /**
     * 数据服务异常
     */
    public static final int DATA_SERVICE_ERROR = 4000102;



    /**
     * 底层链错误
     */
    public static final int CHAIN_ERROR = 5000001;

    /**
     * 底层链无数据返回
     */
    public static final int CHAIN_NODATA = 5000002;


    /**
     * address error code
     */
    public static final int NOT_EXIST_ADDRESS_ERROR = 6000001;

    /**
     *上传数据不可以大于1M
     */
    public static final int NOT_GT_ONEM_ERROR = 7000001;



    public static final String TOKEN = "token:";

    public static final String REFRESH_TOKEN = "token:refresh:";

    public  interface DATA_CHAIN_STATUS{
        //状态 1:penging 2:sucess 3:falied
         int PENDING = 1;
         int SUCESS = 2;
         int FALID = 3;

    }
	

}
