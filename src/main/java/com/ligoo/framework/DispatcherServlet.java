package com.ligoo.framework;

import com.ligoo.framework.bean.Data;
import com.ligoo.framework.bean.Handler;
import com.ligoo.framework.bean.Param;
import com.ligoo.framework.bean.View;
import com.ligoo.framework.helper.BeanHelper;
import com.ligoo.framework.helper.ConfigHelper;
import com.ligoo.framework.helper.ControllerHelper;
import com.ligoo.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/14 15:47:13
 * @Description: 请求转发器
 */
@WebServlet(value = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    /**
     * description: 初始化
     * author: Administrator
     * date: 2018/12/14 16:06
     *
     * @param:
     * @return:
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关Helper类
        HelperLoader.init();
        // 获取ServletContext对象(用户注册Servlet)
        ServletContext servletContext = config.getServletContext();
        // 注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        // 注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

    }

    /**
     * description: 调用服务
     * author: Administrator
     * date: 2018/12/14 16:06
     *
     * @param:
     * @return:
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求方法与路径
        String requestMethod = request.getMethod().toLowerCase();
        //String requestPath = request.getPathInfo();
        String requestPath = request.getServletPath();
        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler != null){
            // 获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerInstance = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            // 填充请求参数对象
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getStrign(request.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if(ArrayUtil.isNotEmpty(params)){
                    for (String param: params){
                        String[] array = StringUtil.splitString(param, "=");
                        if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            // 调用的Action方法
            Object result;
            Method actionMethod = handler.getActionMethod();
            if(param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerInstance, actionMethod);
            }else {
                result = ReflectionUtil.invokeMethod(controllerInstance, actionMethod, param);
            }
            // 处理Action方法返回值
            if(result instanceof View){
                View view = (View) result;
                String path = view.getPath();
                if(StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        response.sendRedirect(request.getContextPath() + path);
                    }else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String,Object> entry: model.entrySet()){
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                }
            }else if(result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if(model != null){
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
