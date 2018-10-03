package com.enrico.dg.home.security.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ObjectHelper {

    private ObjectHelper() {
        throw new UnsupportedOperationException("must not be instantiated");
    }

    public static boolean equals(Object lhs, Object rhs) {
        return EqualsBuilder.reflectionEquals(lhs, rhs, false);
    }

    public static boolean equals(Object lhs, Object rhs, String... excludeFields) {
        return EqualsBuilder.reflectionEquals(lhs, rhs, excludeFields);
    }

    public static int hashCode(Object object) {
        return HashCodeBuilder.reflectionHashCode(object, false);
    }

    public static int hashCode(Object object, String... excludeFields) {
        return HashCodeBuilder.reflectionHashCode(object, excludeFields);
    }

    public static String toString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }
}