package cn.zybcn.idempotent;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-21 20:51
 */
public class Constant {

    public static final SpelExpressionParser PARSER = new SpelExpressionParser();

    public static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();


    public static final String KEY = "key";

    public static final String DEL_KEY = "delKey";
}
