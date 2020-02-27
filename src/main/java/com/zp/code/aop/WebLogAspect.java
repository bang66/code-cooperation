package com.zp.code.aop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhangpeng
 * @since 1.0.0
 */

@Aspect
@Component
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    /** 换行符 */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private ThreadLocal<String> url = new ThreadLocal<>();

    private ThreadLocal<String> description = new ThreadLocal<>();

    private ThreadLocal<String> method = new ThreadLocal<>();

    private ThreadLocal<String> className = new ThreadLocal<>();

    private ThreadLocal<String> methodName = new ThreadLocal<>();

    private ThreadLocal<String> ip = new ThreadLocal<>();

    private ThreadLocal<String> args = new ThreadLocal<>();

    private ThreadLocal<Long> beforeTime = new ThreadLocal<>();

    private ThreadLocal<String> responseArgs = new ThreadLocal<>();




    /** 以自定义 @WebLog 注解为切点 */
    @Pointcut("@annotation(com.zp.code.aop.WebLog)")
    public void webLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取 @WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);
        url.set(request.getRequestURL().toString());
        description.set(methodDescription);
        method.set(request.getMethod());
        className.set(joinPoint.getSignature().getDeclaringTypeName());
        methodName.set(joinPoint.getSignature().getName());
        ip.set(request.getRemoteAddr());
        args.set(new Gson().toJson(joinPoint.getArgs()));
        beforeTime.set(System.currentTimeMillis());

    }

    /**
     * 在切点之后织入
     */
    @After("webLog()")
    public void doAfter() {
        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");
        // 打印请求 url
        logger.info("URL            : {}", url.get());
        // 打印描述信息
        logger.info("Description    : {}", description.get());
        // 打印 Http method
        logger.info("HTTP Method    : {}", method.get());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method   : {} . {}", className.get(), methodName.get());
        // 打印请求的 IP
        logger.info("IP             : {}", ip.get());
        // 打印请求入参
        logger.info("Request Args   : {}", args.get());
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - beforeTime.get());
        // 打印出参
        logger.info("Response Args  : {}", responseArgs.get());
        logger.info("=========================================== End ===========================================" + LINE_SEPARATOR);
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        responseArgs.set(new Gson().toJson(result));
        return result;
    }


    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }

}
