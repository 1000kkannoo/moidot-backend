package com.moim.backend.domain.space.service;

import com.moim.backend.domain.space.Repository.GroupRepository;
import com.moim.backend.domain.space.Repository.ParticipationRepository;
import com.moim.backend.domain.space.entity.Groups;
import com.moim.backend.domain.space.entity.Participation;
import com.moim.backend.domain.space.request.GroupServiceRequest;
import com.moim.backend.domain.space.response.GroupResponse;
import com.moim.backend.domain.user.entity.Users;
import com.moim.backend.global.common.Result;
import com.moim.backend.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final ParticipationRepository participationRepository;

    public GroupResponse.Create createGroup(GroupServiceRequest.Create request, Users user) {
        Groups group = groupRepository.save(request.toGroupEntity(user));

        return GroupResponse.Create.response(group);
    }

    public GroupResponse.Participate participateGroup(GroupServiceRequest.Participate request, Users user) {
        if (!request.getTransportation().equals("BUS") && !request.getTransportation().equals("SUBWAY")) {
            throw new CustomException(Result.INVALID_TRANSPORTATION);
        }
        Groups group = groupRepository.findById(request.getGroupId())
                .orElseThrow(
                        () -> new CustomException(Result.NOT_FOUND_GROUP)
                );
        String encryptedPassword = (request.getPassword() != null) ?
                encrypt(request.getPassword()) : null;

        Participation participation = participationRepository.save(
                request.toParticipationEntity(group, user, encryptedPassword)
        );

        return GroupResponse.Participate.response(participation);
    }

    // method
    public static String encrypt(String password) {
        try {
            StringBuilder sb = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(Result.FAIL);
        }
    }

}
