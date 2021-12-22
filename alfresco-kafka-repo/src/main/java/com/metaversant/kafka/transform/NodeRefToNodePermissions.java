package com.metaversant.kafka.transform;

import java.util.HashSet;
import java.util.Set;

import org.alfresco.repo.security.permissions.AccessDeniedException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AccessPermission;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.log4j.Logger;

import com.metaversant.kafka.model.NodePermission;
import com.metaversant.kafka.model.NodePermissions;

/**
 * Created by jpotts, Metaversant on 8/28/19.
 */
public class NodeRefToNodePermissions {

    /** The LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NodeRefToNodePermissions.class);

    /** The permission service. */
    private PermissionService permissionService;

    /**
     * Transform.
     *
     * @param nodeRef the node ref
     * @return the node permissions
     */
    public NodePermissions transform(final NodeRef nodeRef) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("NodePermissions transform invoked for nodeRef: " + nodeRef);
	}
	// determine if the node inherits its ACL from the parent
	final boolean inherits = permissionService.getInheritParentPermissions(nodeRef);
	final NodePermissions perms = new NodePermissions();
	perms.setInheritanceEnabled(inherits);

	// convert the Alfresco object into our own
	try {
	    final Set<AccessPermission> permissionSet = permissionService.getAllSetPermissions(nodeRef);
	    final Set<NodePermission> set = new HashSet<>(permissionSet.size());
	    for (final AccessPermission perm : permissionSet) {
		final NodePermission nodePerm = new NodePermission();
		nodePerm.setAuthority(perm.getAuthority());
		nodePerm.setAuthorityType(perm.getAuthorityType().name());
		nodePerm.setPermission(perm.getPermission());
		nodePerm.setInherited(perm.isInherited());
		set.add(nodePerm);
	    }
	    perms.setPermissions(set);
	} catch (AccessDeniedException e) {
	    LOGGER.warn("Do not have permission to read getAllSetPermissions on " + nodeRef);
	}
	return perms;
    }

    /**
     * Sets the permission service.
     *
     * @param permissionService the new permission service
     */
    public void setPermissionService(final PermissionService permissionService) {
	this.permissionService = permissionService;
    }
}
