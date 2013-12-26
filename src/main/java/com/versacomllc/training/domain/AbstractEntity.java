package com.versacomllc.training.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass for all Entity-classes, containing ID, creation date and
 * last modification date.
 * 
 * @author Shamim Ahmmed
 * 
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "record_created")
	private Date recordCreated;

	@Column(name = "last_modified")
	private Date lastModified;

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @return the recordCreated
	 */
	public final Date getRecordCreated() {
		return recordCreated;
	}

	/**
	 * @return the lastModified
	 */
	public final Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @param recordCreated
	 *            the recordCreated to set
	 */
	public final void setRecordCreated(final Date recordCreated) {
		this.recordCreated = recordCreated;
	}

	/**
	 * @param lastModified
	 *            the lastModified to set
	 */
	public final void setLastModified(final Date lastModified) {
		this.lastModified = lastModified;
	}

}
