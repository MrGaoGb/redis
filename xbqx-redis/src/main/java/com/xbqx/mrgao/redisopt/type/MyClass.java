package com.xbqx.mrgao.redisopt.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mr.Gao
 * @date 2024/8/16 9:38
 * @apiNote:
 */
public class MyClass<T> {


    public void printType() {
        // 获取父类的泛型类型
        Type type = getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            // 真实参数类型
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type t : actualTypeArguments) {
                System.out.println(t.getTypeName());
            }

            //
            Type rawType = parameterizedType.getRawType();
            System.out.println(rawType.getTypeName());
        }
    }
}
