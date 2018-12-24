package com.ligoo.framework.proxy;

import com.ligoo.framework.annotation.Transaction;
import com.ligoo.framework.helper.DataSourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2018/12/20 13:40:27
 * @Description: 事务代理类
 */
public class TransactionProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    /**
     * description: 代理处理方法
     * author: Administrator
     * date: 2018/12/20 13:53
     *
     * @param:
     * @return:
     */
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try {
                DataSourceHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DataSourceHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            }catch (Exception e){
                DataSourceHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
