package com.green.bloom.domain.dto;

import com.green.bloom.domain.entity.DriveEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveDTO {
	
	private String driveTitle;
	private String driveContent;
	private String driveName;
	private String bucketKey;
	private String orgName;
	private String newName;
	private long no;
	
	public DriveEntity toEntity() {
		return DriveEntity.builder()
				.driveTitle(driveTitle)
				.driveContent(driveContent)
				.driveName(driveName)
				.bucketKey(bucketKey)
				.orgName(orgName)
				.newName(newName)
				.build();
	}

	public DriveDTO updateBucketKey(String destinationKey) {
		bucketKey=destinationKey;
		return this;
	}

}
