package cn.swallow.platform.core.beetl;

import cn.swallow.platform.core.beetl.tag.DictSelectorTag;
import cn.swallow.platform.core.beetl.util.BeetlUtil;
import cn.swallow.platform.core.beetl.util.ShiroExt;
import cn.swallow.platform.core.util.CommonUtil;
import cn.swallow.platform.core.util.KaptchaUtil;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * beetl页面工具注册
 * @author shenyu
 * @create 2019/3/15
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {
    @Autowired
    private Environment env;

    @Autowired
    private DictSelectorTag dictSelectorTag;

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new BeetlUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
        groupTemplate.registerTagFactory("dictSelector", () -> dictSelectorTag);

        groupTemplate.registerFunction("env", new Function() {
            @Override
            public String call(Object[] paras, Context ctx) {
                String key = (String) paras[0];
                String value = env.getProperty(key);
                if (value != null) {
                    return getStr(value);
                }
                if (paras.length == 2) {
                    return (String) paras[1];
                }
                return null;
            }

            String getStr(String str) {
                try {
                    return new String(str.getBytes("iso8859-1"), StandardCharsets.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
