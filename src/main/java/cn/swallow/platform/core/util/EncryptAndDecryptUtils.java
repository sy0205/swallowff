package cn.swallow.platform.core.util;

/**
 * @author Administrator
 * @description
 * @create 2019/5/7
 */
public class EncryptAndDecryptUtils {
    /**
     * MD5 加密
     *
     * @author : chenssy
     * @date : 2016年5月20日 下午4:54:23
     *
     * @param value 待加密字符
     * @return
     */
    public static String md5Encrypt(String value){
        String result = null;
        if(value != null && !"".equals(value.trim())){
            result =  MD5Util.encrypt(value,MD5Util.MD5_KEY);
        }
        return result;
    }

    /**
     * SHA加密
     *
     * @author : chenssy
     * @date : 2016年5月20日 下午4:59:42
     *
     * @param value 待加密字符
     * @return	密文
     */
    public static String shaEncrypt(String value){
        String result = null;
        if(value != null && !"".equals(value.trim())){
            result =  MD5Util.encrypt(value,MD5Util.SHA_KEY);
        }
        return result;
    }

    /**
     * BASE64 加密
     *
     * @author : chenssy
     * @date : 2016年5月20日 下午5:16:12
     *
     * @param value 待加密字符串
     * @return
     */
    public static String base64Encrypt(String value){
        String result = null;
        if(value != null && !"".equals(value.trim())){
            result =  Base64Util.encrypt(value.getBytes());
        }
        return result;

    }

    /**
     * BASE64 解密
     *
     * @author : chenssy
     * @date : 2016年5月20日 下午5:16:34
     *
     * @param value 待解密字符串
     * @return
     */
    public static String base64Decrypt(String value){
        String result = null;
        try {
            if(value != null && !"".equals(value.trim())){
                byte[] bytes = Base64Util.decrypt(value);
                result = new String(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * DES加密<br>
     *
     * @author : chenssy
     * @date : 2016年5月20日 下午5:39:46
     *
     * @param value 加密字符
     * @param key 若key为空，则使用默认key
     * @return 加密成功返回密文，否则返回null
     */
    public static String desEncrypt(String value,String key){
        key = key == null ? DESUtil.KEY : key;
        String result = null;

        try {
            if(value != null && !"".equals(value.trim())){
                result = DESUtil.encrypt(value, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * DES解密
     *
     * @author : chenssy
     * @date : 2016年5月20日 下午5:55:56
     *
     * @param value 待解密字符
     * @param key 若key为空，则使用默认key
     * @return
     */
    public static String desDecrypt(String value,String key){
        key = key == null ? DESUtil.KEY : key;
        String result = null;

        try {
            if(value != null && !"".equals(value.trim())){
                result =  DESUtil.decrypt(value, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * AES加密
     *
     * @author:chenssy
     * @date : 2016年5月21日 上午9:58:58
     *
     * @param value
     * 					待加密内容
     * @param key
     * 					秘钥
     * @return
     */
    public static String aesEncrypt(String value,String key ){
        key = key == null ? AESUtil.KEY : key;
        String result = null;
        try {
            if(value != null && !"".equals(value.trim())){		//value is not null
                result = AESUtil.encrypt(value,key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * AES解密
     *
     * @author:chenssy
     * @date : 2016年5月21日 上午10:02:07
     *
     * @param value
     * 				待解密内容
     * @param key
     * 				秘钥
     * @return
     */
    public static String aesDecrypt(String value , String key){
        key = key == null ? AESUtil.KEY : key;
        String result = null;
        try {
            if(value != null && !"".equals(value.trim())){		//value is not null
                result = AESUtil.decrypt(value,key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
