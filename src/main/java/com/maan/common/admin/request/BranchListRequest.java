package com.maan.common.admin.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BranchListRequest {

	
	@JsonProperty("AttachedBranchInfo")
	private List<AttachedBranchReq> attachedBranchInfo;

}
