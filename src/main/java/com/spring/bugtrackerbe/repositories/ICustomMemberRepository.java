package com.spring.bugtrackerbe.repositories;

import com.spring.bugtrackerbe.dto.MemberResponseDTO;

import java.util.List;

public interface ICustomMemberRepository {

    List<MemberResponseDTO> findMemberResponseDTOByProjectId(Integer projectId);
}
