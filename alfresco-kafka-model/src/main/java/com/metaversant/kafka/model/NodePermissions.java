package com.metaversant.kafka.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by jpotts, Metaversant on 6/9/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodePermissions {

    /** The permissions. */
    private Set<NodePermission> permissions;

    /** The is inheritance enabled. */
    private boolean isInheritanceEnabled;

    public Set<NodePermission> getPermissions() {
	return permissions;
    }

    public void setPermissions(Set<NodePermission> permissions) {
	this.permissions = permissions;
    }

    public boolean isInheritanceEnabled() {
	return isInheritanceEnabled;
    }

    public void setInheritanceEnabled(boolean isInheritanceEnabled) {
	this.isInheritanceEnabled = isInheritanceEnabled;
    }
}
