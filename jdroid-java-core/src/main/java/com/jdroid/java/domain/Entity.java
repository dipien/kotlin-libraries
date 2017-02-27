package com.jdroid.java.domain;

import java.io.Serializable;

/**
 * Domain Entity
 */
public abstract class Entity implements Serializable, Identifiable {

	private static final long serialVersionUID = 907671509045298947L;

	private String id;
	private String parentId;

	public Entity(String id) {
		this.id = id;
	}

	public Entity() {
	}

	/**
	 * Gets the identification for this {@link Entity}
	 *
	 * @return the id of this {@link Entity}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entity{id='");
		builder.append(id);
		builder.append("\', parentId='");
		builder.append(parentId);
		builder.append("\'}");
		return builder.toString();
	}

	/**
	 * Since equality has been redefined, so must be the hashCode function.
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		return (id != null) ? prime * id.hashCode() : super.hashCode();
	}

	/**
	 * Redefines equality depending on the id of the entities being compared.
	 *
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		Entity other = (Entity)obj;

		if (id != null) {
			return id.equals(other.getId());
		}

		if (other.getId() != null) {
			return false;
		}

		return super.equals(other);
	}
}
