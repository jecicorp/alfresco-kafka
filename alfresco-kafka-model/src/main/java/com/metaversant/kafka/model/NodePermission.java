package com.metaversant.kafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by jpotts, Metaversant on 6/9/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodePermission {

    /** The authority. */
    private String authority;

    /** The authority type. */
    private String authorityType;

    /** The permission. */
    private String permission;

    /** The is inherited. */
    private boolean isInherited;

    public String getAuthority() {
	return authority;
    }

    public void setAuthority(String authority) {
	this.authority = authority;
    }

    public String getAuthorityType() {
	return authorityType;
    }

    public void setAuthorityType(String authorityType) {
	this.authorityType = authorityType;
    }

    public String getPermission() {
	return permission;
    }

    public void setPermission(String permission) {
	this.permission = permission;
    }

    public boolean isInherited() {
	return isInherited;
    }

    public void setInherited(boolean isInherited) {
	this.isInherited = isInherited;
    }

}
