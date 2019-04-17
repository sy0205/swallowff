package cn.swallow.platform.core.common.log.factory;

import cn.swallow.platform.core.constant.state.LogSucceed;
import cn.swallow.platform.core.constant.state.LogType;
import cn.swallow.platform.modular.system.entity.LoginLog;
import cn.swallow.platform.modular.system.entity.OperationLog;

import java.util.Date;

/**
 * @author shenyu
 * @create 2019/3/15
 */
public class LogFactory {
    /**
     * 创建操作日志
     */
    public static OperationLog createOperationLog(LogType logType, Integer userId, String bussinessName, String clazzName, String methodName, String msg, LogSucceed succeed) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogType(logType.getMessage());
        operationLog.setLogname(bussinessName);
        operationLog.setUserId(userId);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateTime(new Date());
        operationLog.setSucceed(true);
        operationLog.setMessage(msg);
        return operationLog;
    }

    /**
     * 创建登录日志
     */
    public static LoginLog createLoginLog(LogType logType, Integer userId, String msg, String ip) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLogName(logType.getMessage());
        loginLog.setUserId(userId);
        loginLog.setCreateTime(new Date());
        loginLog.setSucceed(true);
        loginLog.setIp(ip);
        loginLog.setMessage(msg);
        return loginLog;
    }
}
