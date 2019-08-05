package com.wms.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class AccessorUtil {
    private static final Map classPropertyDescriptorCache = Collections.synchronizedMap(new WeakHashMap(128));
    private static final Map excludeNameIgnoredPropertiesListCache = Collections.synchronizedMap(new WeakHashMap(64));

    public static void main(String[] args) {
    }

    public static void copyClass(Object source, Object target, String[] copiedProperties) {
        Class sourceClazz = source.getClass();
        Class targetClazz = target.getClass();

        PropertyDescriptor[] targetPropertyDescriptors = getPropertyDescriptorsByClass(targetClazz);
        List copyProperties = prepareIgnoredPropertiesList(copiedProperties);
        //
        if (targetPropertyDescriptors == null || targetPropertyDescriptors.length <= 0) {
            return;
        }

        for (int i = 0; i < targetPropertyDescriptors.length; ++i) {
            PropertyDescriptor targetPd = targetPropertyDescriptors[i];
            if (targetPd.getWriteMethod() != null && (copyProperties != null && copyProperties.contains(targetPd.getName()))) {
                //get value from source class
                PropertyDescriptor sourcePd = getPropertyDescriptor(sourceClazz, targetPd.getName());
                Method readMethod = sourcePd.getReadMethod();
                setMethodAccessible(readMethod);

                Object sourceValue = null;
                try {
                    sourceValue = readMethod.invoke(source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //set value for target class
                Method writeMethod = targetPd.getWriteMethod();
                setMethodAccessible(writeMethod);
                try {
                    writeMethod.invoke(target, sourceValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setMethodAccessible(Method method) {
        if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            method.setAccessible(true);
        }
    }

    private static List prepareIgnoredPropertiesList(String[] excludes) {
        if (excludes != null) {
            List ignoredPropertiesList = new ArrayList();
            IgnoredPropertiesCacheKey key = new IgnoredPropertiesCacheKey(excludes);
            Reference ref = (Reference) excludeNameIgnoredPropertiesListCache.get(key);
            if (ref != null) {
                ignoredPropertiesList = (List) ref.get();
                if (ignoredPropertiesList != null) {
                    return ignoredPropertiesList;
                }
            }

            for (int i = 0; i < excludes.length; ++i) {
                String exclude = excludes[i];
                if (exclude != null) {
                    if (exclude.startsWith("get")) {
                        ignoredPropertiesList.add(Introspector.decapitalize(exclude.substring(3)));
                    } else if (exclude.startsWith("is")) {
                        ignoredPropertiesList.add(Introspector.decapitalize(exclude.substring(2)));
                    } else if (Character.isUpperCase(exclude.charAt(0))) {
                        ignoredPropertiesList.add(Introspector.decapitalize(exclude));
                    } else {
                        ignoredPropertiesList.add(exclude);
                    }
                }
            }

            excludeNameIgnoredPropertiesListCache.put(key, new WeakReference(ignoredPropertiesList));
            return ignoredPropertiesList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private static class IgnoredPropertiesCacheKey {
        private String[] excludes;

        public IgnoredPropertiesCacheKey(String[] excludes) {
            this.excludes = excludes;
        }

        public int hashCode() {
            int result = 1;

            for (int index = 0; index < this.excludes.length; ++index) {
                result = 31 * result + (this.excludes[index] == null ? 0 : this.excludes[index].hashCode());
            }

            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (this.getClass() != obj.getClass()) {
                return false;
            } else {
                IgnoredPropertiesCacheKey other = (IgnoredPropertiesCacheKey) obj;
                return Arrays.equals(this.excludes, other.excludes);
            }
        }
    }

    public static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName) {
        PropertyDescriptor[] propertyDescriptor = getPropertyDescriptorsByClass(clazz);
        if (propertyDescriptor != null) {
            for (int i = 0; i < propertyDescriptor.length; ++i) {
                if (propertyDescriptor[i].getName().equals(propertyName)) {
                    return propertyDescriptor[i];
                }
            }
        }

        return null;
    }

    public static PropertyDescriptor[] getPropertyDescriptorsByClass(Class clazz) {
        PropertyDescriptor[] descriptors;
        Reference ref = (Reference) classPropertyDescriptorCache.get(clazz);
        if (ref != null) {
            descriptors = (PropertyDescriptor[]) ref.get();
            if (descriptors != null) {
                return descriptors;
            }
        }

        BeanInfo info;

        try {
            info = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException var5) {
            throw new IllegalStateException("not able to introspect the class [" + clazz + "], nested exception is " + var5);
        }

        descriptors = info.getPropertyDescriptors();
        classPropertyDescriptorCache.put(clazz, new WeakReference(descriptors));
        return descriptors;
    }
}
