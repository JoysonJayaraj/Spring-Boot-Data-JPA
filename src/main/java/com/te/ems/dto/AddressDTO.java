package com.te.ems.dto;

import com.te.ems.entity.AddressType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressDTO {

	private String addressCity;
	private String addressState;
	private AddressType addressType;

}
