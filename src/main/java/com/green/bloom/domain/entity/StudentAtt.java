package com.green.bloom.domain.entity;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@Table(name="StudentAtt")
@Entity

public class StudentAtt {


	@Id //출결번호
	private String SANumber;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stuNo")
	private StudentEntity StudentEntity;
	
	
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isPresent;
}
