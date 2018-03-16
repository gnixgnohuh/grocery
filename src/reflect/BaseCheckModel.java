package reflect;

import reflect.helper.NotBlank;
import reflect.helper.NotEmpty;
import reflect.helper.NotNull;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author: gnixgnohuh
 * @date : 18-3-16
 * @time : 下午3:59
 * @desc :　这是一个提供校验功能的类
 */
public class BaseCheckModel implements Checkable, Serializable {
    @Override
    public void check() {
        try {
            NotNull notnull;
            NotBlank notblank;
            NotEmpty notempty;
            Object value;
            Class<?> clazz;

            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                value = f.get(this);
                notnull = f.getAnnotation(NotNull.class);
                notblank = f.getAnnotation(NotBlank.class);
                notempty = f.getAnnotation(NotEmpty.class);
                if (value == null) {
                    if (notnull != null || notblank != null || notempty != null) {
                        throw new IllegalArgumentException(f.getName() + "字段不允许为null");
                    }
                    continue;
                }

                if (value instanceof String) {
                    if ("".equals(String.valueOf(value).trim()) && notblank != null) {
                        throw new IllegalArgumentException(f.getName() + "字段不允许为blank");
                    }
                    continue;
                }

                clazz = value.getClass();

                if (clazz.isArray()) {
                    if (((Object[]) value).length == 0 && notempty != null) {
                        throw new IllegalArgumentException(f.getName() + "字段不允许为empty");
                    }

                    f1:
                    for (Object object : ((Object[]) value)) {
                        if (!Checkable.class.isAssignableFrom(object.getClass())) {
                            break f1;
                        }
                        ((Checkable) object).check();
                    }
                    continue;
                }

                if (Iterable.class.isAssignableFrom(clazz)) {
                    if (!((Iterable<?>) value).iterator().hasNext() && notempty != null) {
                        throw new IllegalArgumentException(f.getName() + "字段不允许为empty");
                    }
                    f1:
                    for (Object object : ((Iterable<?>) value)) {
                        if (!Checkable.class.isAssignableFrom(object.getClass())) {
                            break f1;
                        }
                        ((Checkable) object).check();
                    }
                    continue;
                }

                if (Checkable.class.isAssignableFrom(clazz)) {
                    ((Checkable) value).check();
                }
            }
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }
}
