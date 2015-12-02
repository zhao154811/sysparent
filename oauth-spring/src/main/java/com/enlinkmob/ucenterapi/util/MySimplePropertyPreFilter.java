package com.enlinkmob.ucenterapi.util;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.HashSet;
import java.util.Set;

public class MySimplePropertyPreFilter implements PropertyFilter {
    public enum JsonFitler {
        in, ex
    }

    private final Class<?> clazz;
    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();

    public MySimplePropertyPreFilter(JsonFitler jf, String... properties) {
        this(null, jf, properties);
    }

    public MySimplePropertyPreFilter(Class<?> clazz, JsonFitler includeOrExclude, String... properties) {
        super();
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
                if (includeOrExclude == JsonFitler.in) {
                    this.includes.add(item);
                } else {
                    this.excludes.add(item);
                }

            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    @Override
    public boolean apply(Object source, String name, Object value) {
        if (source == null) {
            return true;
        }

        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }

        if (this.excludes.contains(name)) {
            return false;
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        return false;
    }


}
