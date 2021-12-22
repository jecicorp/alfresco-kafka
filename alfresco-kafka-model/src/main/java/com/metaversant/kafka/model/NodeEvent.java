package com.metaversant.kafka.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by jpotts, Metaversant on 6/9/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeEvent {

    /**
     * The Enum EventType.
     */
    public enum EventType {

	/** The create. */
	CREATE,

	/** The update. */
	UPDATE,

	/** The delete. */
	DELETE,

	/** The ping. */
	PING,

	/** The grant. */
	GRANT,

	/** The revoke. */
	REVOKE,

	/** The enable inherit. */
	ENABLE_INHERIT,

	/** The disable inherit. */
	DISABLE_INHERIT
    }

    /** The node ref. */
    private String nodeRef;
    /** The event type. */
    private NodeEvent.EventType eventType;

    /** The path. */
    private String path;

    /** The created. */
    private Date created;

    /** The modified. */
    private Date modified;

    /** The creator. */
    private String creator;

    /** The modifier. */
    private String modifier;

    /** The mimetype. */
    private String mimetype;

    /** The content type. */
    private String contentType;

    /** The site id. */
    private String siteId;

    /** The size. */
    private Long size;

    /** The parent. */
    private String parent;

    /** The authority. */
    private String authority;

    /** The permission. */
    private String permission;

    /** The permissions. */
    private NodePermissions permissions;

    /** The tags. */
    private List<String> tags;

    public String getNodeRef() {
	return nodeRef;
    }

    public void setNodeRef(String nodeRef) {
	this.nodeRef = nodeRef;
    }

    public NodeEvent.EventType getEventType() {
	return eventType;
    }

    public void setEventType(NodeEvent.EventType eventType) {
	this.eventType = eventType;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getModified() {
	return modified;
    }

    public void setModified(Date modified) {
	this.modified = modified;
    }

    public String getCreator() {
	return creator;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    public String getModifier() {
	return modifier;
    }

    public void setModifier(String modifier) {
	this.modifier = modifier;
    }

    public String getMimetype() {
	return mimetype;
    }

    public void setMimetype(String mimetype) {
	this.mimetype = mimetype;
    }

    public String getContentType() {
	return contentType;
    }

    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    public String getSiteId() {
	return siteId;
    }

    public void setSiteId(String siteId) {
	this.siteId = siteId;
    }

    public Long getSize() {
	return size;
    }

    public void setSize(Long size) {
	this.size = size;
    }

    public String getParent() {
	return parent;
    }

    public void setParent(String parent) {
	this.parent = parent;
    }

    public String getAuthority() {
	return authority;
    }

    public void setAuthority(String authority) {
	this.authority = authority;
    }

    public String getPermission() {
	return permission;
    }

    public void setPermission(String permission) {
	this.permission = permission;
    }

    public NodePermissions getPermissions() {
	return permissions;
    }

    public void setPermissions(NodePermissions permissions) {
	this.permissions = permissions;
    }

    public List<String> getTags() {
	return tags;
    }

    public void setTags(List<String> tags) {
	this.tags = tags;
    }
}
