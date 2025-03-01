package goorm.saerojinro.common.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	@LastModifiedDate
	protected LocalDateTime updatedAt;

	@Column
	protected LocalDateTime deletedAt;

	public void delete(){
		deletedAt = LocalDateTime.now();
	}
}
