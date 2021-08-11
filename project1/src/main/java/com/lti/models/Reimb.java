package com.lti.models;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ers_reimbursement")
public class Reimb {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false, name = "reimb_id")
	private int reimbId;
	@Column(name = "reimb_amount", nullable = false)
	private float amount;
	@Column(name = "reimb_submitted", nullable = false)
	private Timestamp submitted;
	@Column(name = "reimb_resolved")
	private Timestamp resolved;
	@Column(name = "reimb_description")
	private String description;
	@Column(name = "reimb_receipt")
	private byte[] receipt;
	@ManyToOne @JoinColumn(name = "reimb_author", nullable = false)
	private User author;
	@ManyToOne @JoinColumn(name = "reimb_resolver")
	private User resolver;
	@ManyToOne @JoinColumn(name = "reimb_status_id", nullable = false)
	private ReimbStatus status;
	@ManyToOne @JoinColumn(name = "reimb_type_id", nullable = false)
	private ReimbType type;
	
	
	
	
	public Reimb(int reimbId, float amount, Timestamp submitted, Timestamp resolved, String description, byte[] receipt,
			User author, User resolver, ReimbStatus status, ReimbType type) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
	public Reimb(int reimbId) {
		super();
		this.reimbId = reimbId;
	}
	public Reimb(float amount, Timestamp submitted, User author, ReimbStatus status, ReimbType type) {
		super();
		this.amount = amount;
		this.submitted = submitted;
		this.author = author;
		this.status = status;
		this.type = type;
	}
	public Reimb() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getReimbId() {
		return reimbId;
	}
	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Timestamp getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}
	public Timestamp getResolved() {
		return resolved;
	}
	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getReceipt() {
		return receipt;
	}
	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getResolver() {
		return resolver;
	}
	public void setResolver(User resolver) {
		this.resolver = resolver;
	}
	public ReimbStatus getStatus() {
		return status;
	}
	public void setStatus(ReimbStatus status) {
		this.status = status;
	}
	public ReimbType getType() {
		return type;
	}
	public void setType(ReimbType type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + reimbId;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimb other = (Reimb) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver == null) {
			if (other.resolver != null)
				return false;
		} else if (!resolver.equals(other.resolver))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Reimb [reimbId=" + reimbId + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", receipt=" + Arrays.toString(receipt) + ", author="
				+ author + ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
	}
	

	
	
}

