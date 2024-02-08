package com.green.bloom.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.green.bloom.domain.dto.DriveDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Drive")
@Entity
public class DriveEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	@Column(nullable = false)
	private String driveTitle;
	@Column(nullable = false)
	private String driveContent;
	@Column(nullable = false)
	private String driveName;
	private String bucketKey;
	private String orgName;
	private String newName;
	@CreationTimestamp
	@Column(columnDefinition = "timestamp(6) null")
	private LocalDateTime createdDate;
	
	public DriveDTO toDriveDTO() {
		return DriveDTO.builder()
				.no(no)
				.driveTitle(driveTitle)
				.driveContent(driveContent)
				.orgName(orgName)
				.bucketKey(bucketKey)
				.newName(newName)
				.driveName(driveName)
				.build();
	}

}
