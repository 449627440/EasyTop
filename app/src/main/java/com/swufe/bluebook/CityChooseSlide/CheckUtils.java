package com.swufe.bluebook.CityChooseSlide;

import java.util.List;

/**
 * 校验类
 */
public class CheckUtils {

    /**
     * 判断是否为空
     *
     * @param obj
     * @return true:为空 false：不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return obj == null || ((String) obj).length() == 0
                    || "null".equals(((String) obj));
        } else if (obj instanceof Object[]) {
            Object[] temp = (Object[]) obj;
            for (int i = 0; i < temp.length; i++) {
                if (!isEmpty(temp[i])) {
                    return false;
                }
            }
            return true;
        } else if (obj instanceof List) {
            return obj == null || ((List) obj).isEmpty();
        }

        return obj == null;
    }
}
