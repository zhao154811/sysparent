package com.enlinkmob.ucenterapi.model;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
public class MySecurityConfig implements ConfigAttribute {


    // ~ Instance fields
    // ================================================================================================

    private String attrib;

    // ~ Constructors
    // ===================================================================================================

    public MySecurityConfig(String config) {
        Assert.hasText(config, "You must provide a configuration attribute");
        this.attrib = config;
    }

    public MySecurityConfig() {
    }

    // ~ Methods
    // ========================================================================================================

    public boolean equals(Object obj) {
        if (obj instanceof ConfigAttribute) {
            ConfigAttribute attr = (ConfigAttribute) obj;

            return this.attrib.equals(attr.getAttribute());
        }

        return false;
    }

    public String getAttribute() {
        return this.attrib;
    }

    public int hashCode() {
        return this.attrib.hashCode();
    }

    public String toString() {
        return this.attrib;
    }

    public static List<ConfigAttribute> createListFromCommaDelimitedString(String access) {
        return createList(StringUtils.commaDelimitedListToStringArray(access));
    }

    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(
                attributeNames.length);

        for (String attribute : attributeNames) {
            attributes.add(new SecurityConfig(attribute.trim()));
        }

        return attributes;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }
}
