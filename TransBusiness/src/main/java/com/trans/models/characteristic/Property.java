package com.trans.models.characteristic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Property {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@Setter
	@Column(nullable = false)
	protected String details;

	@Getter
	@Setter
	@Column(nullable = false)
	protected Long numberOfChar;

	public Property(String details, Long numberOfChar) {
		super();
		this.details = details;
		this.numberOfChar = numberOfChar;
	}
	
	public Property() {
		super();
	}
	
}
