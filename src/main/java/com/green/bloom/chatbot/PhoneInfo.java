package com.green.bloom.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PhoneInfo {

	private String deptName;
	private String memberName;
	private String phone;
	private String empRole;
}