package com.metaversant.kafka.behavior;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.security.permissions.PermissionServicePolicies;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.log4j.Logger;

import com.metaversant.kafka.model.NodeEvent;
import com.metaversant.kafka.service.MessageService;
import com.metaversant.kafka.transform.NodeRefToNodePermissions;

/**
 * Created by jpotts, Metaversant on 8/28/19.
 */
public class GenerateNodePermissionEvent
	implements PermissionServicePolicies.OnGrantLocalPermission, PermissionServicePolicies.OnRevokeLocalPermission,
	PermissionServicePolicies.OnInheritPermissionsDisabled, PermissionServicePolicies.OnInheritPermissionsEnabled {

    /** The LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GenerateNodePermissionEvent.class);

    /** The policy component. */
    private PolicyComponent policyComponent;

    /** The message service. */
    private MessageService messageService;

    /** The node permissions transformer. */
    private NodeRefToNodePermissions nodePermissionsTransformer;

    /**
     * Inits the.
     */
    public void init() {

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Initializing GenerateNodePermissionEvent behaviors");
	}

	/** The on grant local permission. */
	Behaviour onGrantLocalPermission = new JavaBehaviour(this, "onGrantLocalPermission",
		Behaviour.NotificationFrequency.EVERY_EVENT);

	/** The on revoke local permission. */
	Behaviour onRevokeLocalPermission = new JavaBehaviour(this, "onRevokeLocalPermission",
		Behaviour.NotificationFrequency.EVERY_EVENT);

	/** The on inherit permissions enabled. */
	Behaviour onInheritPermissionsEnabled = new JavaBehaviour(this, "onInheritPermissionsEnabled",
		Behaviour.NotificationFrequency.EVERY_EVENT);

	/** The on inherit permissions disabled. */
	Behaviour onInheritPermissionsDisabled = new JavaBehaviour(this, "onInheritPermissionsDisabled",
		Behaviour.NotificationFrequency.EVERY_EVENT);

	// Bind behaviours to node policies
	this.policyComponent.bindClassBehaviour(PermissionServicePolicies.OnGrantLocalPermission.QNAME,
		ContentModel.TYPE_CMOBJECT, onGrantLocalPermission);

	this.policyComponent.bindClassBehaviour(PermissionServicePolicies.OnRevokeLocalPermission.QNAME,
		ContentModel.TYPE_CMOBJECT, onRevokeLocalPermission);

	this.policyComponent.bindClassBehaviour(PermissionServicePolicies.OnInheritPermissionsEnabled.QNAME,
		ContentModel.TYPE_BASE, onInheritPermissionsEnabled);

	this.policyComponent.bindClassBehaviour(PermissionServicePolicies.OnInheritPermissionsDisabled.QNAME,
		ContentModel.TYPE_BASE, onInheritPermissionsDisabled);

    }

    /**
     * On grant local permission.
     *
     * @param nodeRef    the node ref
     * @param authority  the authority
     * @param permission the permission
     */
    @Override
    public void onGrantLocalPermission(final NodeRef nodeRef, final String authority, final String permission) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("inside onGrantLocalPermission");
	}
	final NodeEvent nodeEvent = new NodeEvent();
	nodeEvent.setEventType(NodeEvent.EventType.GRANT);
	nodeEvent.setNodeRef(nodeRef.getId());
	nodeEvent.setAuthority(authority);
	nodeEvent.setPermission(permission);
	nodeEvent.setPermissions(nodePermissionsTransformer.transform(nodeRef));
	messageService.publish(nodeEvent);
    }

    /**
     * On revoke local permission.
     *
     * @param nodeRef    the node ref
     * @param authority  the authority
     * @param permission the permission
     */
    @Override
    public void onRevokeLocalPermission(final NodeRef nodeRef, final String authority, final String permission) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("inside onRevokeLocalPermission");
	}
	final NodeEvent nodeEvent = new NodeEvent();
	nodeEvent.setEventType(NodeEvent.EventType.REVOKE);
	nodeEvent.setNodeRef(nodeRef.getId());
	nodeEvent.setAuthority(authority);
	nodeEvent.setPermission(permission);
	nodeEvent.setPermissions(nodePermissionsTransformer.transform(nodeRef));
	messageService.publish(nodeEvent);
    }

    /**
     * On inherit permissions disabled.
     *
     * @param nodeRef the node ref
     * @param async   the async
     */
    @Override
    public void onInheritPermissionsDisabled(final NodeRef nodeRef, final boolean async) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("inside onInheritPermissionsDisabled");
	}
	final NodeEvent nodeEvent = new NodeEvent();
	nodeEvent.setEventType(NodeEvent.EventType.DISABLE_INHERIT);
	nodeEvent.setNodeRef(nodeRef.getId());
	nodeEvent.setPermissions(nodePermissionsTransformer.transform(nodeRef));
	messageService.publish(nodeEvent);
    }

    /**
     * On inherit permissions enabled.
     *
     * @param nodeRef the node ref
     */
    @Override
    public void onInheritPermissionsEnabled(final NodeRef nodeRef) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("inside onInheritPermissionsEnabled");
	}
	final NodeEvent nodeEvent = new NodeEvent();
	nodeEvent.setEventType(NodeEvent.EventType.ENABLE_INHERIT);
	nodeEvent.setNodeRef(nodeRef.getId());
	nodeEvent.setPermissions(nodePermissionsTransformer.transform(nodeRef));
	messageService.publish(nodeEvent);
    }

    /**
     * Sets the policy component.
     *
     * @param policyComponent the new policy component
     */
    public void setPolicyComponent(final PolicyComponent policyComponent) {
	this.policyComponent = policyComponent;
    }

    /**
     * Sets the message service.
     *
     * @param messageService the new message service
     */
    public void setMessageService(final MessageService messageService) {
	this.messageService = messageService;
    }

    /**
     * Sets the node permissions transformer.
     *
     * @param nodePermissionsTransformer the new node permissions transformer
     */
    public void setNodePermissionsTransformer(final NodeRefToNodePermissions nodePermissionsTransformer) {
	this.nodePermissionsTransformer = nodePermissionsTransformer;
    }
}
