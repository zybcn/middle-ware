package cn.zybcn.idempotent.expression;

import cn.zybcn.idempotent.Constant;
import cn.zybcn.idempotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-21 18:32
 */
public class ExpressionResolver implements KeyResolver {

    @Override
    public String resolver(Idempotent idempotent, JoinPoint point) {
        Object[] arguments = point.getArgs();
        String[] params = Constant.DISCOVERER.getParameterNames(getMethod(point));
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (params != null && params.length > 0) {
            for (int len = 0, size = params.length; len < size; len++) {
                context.setVariable(params[len], arguments[len]);
            }
        }
        Expression expression = Constant.PARSER.parseExpression(idempotent.key());
        return expression.getValue(context, String.class);
    }

    /**
     * 根据切点解析方法信息
     *
     * @param joinPoint 切点信息
     * @return Method 原信息
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
                        method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }


}
