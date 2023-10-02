package com.moim.backend.domain.space.request;

import com.moim.backend.domain.space.entity.TransportationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

public class GroupRequest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Participate {
        @NotNull(message = "스페이스 아이디를 입력하지 않았습니다.")
        private Long groupId;

        @NotBlank(message = "닉네임을 입력하지 않았습니다.")
        private String userName;

        @NotBlank(message = "출발 위치가 입력하지 않았습니다.")
        private String locationName;

        @NotNull(message = "위도를 입력하지 않았습니다.")
        private Double latitude;

        @NotNull(message = "경도를 입력하지 않았습니다.")
        private Double longitude;

        @Enumerated(STRING)
        @NotNull(message = "이동 수단을 입력하지 않았습니다.")
        private TransportationType transportationType;

        private String password;

        public GroupServiceRequest.Participate toServiceRequest() {
            return GroupServiceRequest.Participate.builder()
                    .groupId(groupId)
                    .userName(userName)
                    .locationName(locationName)
                    .latitude(latitude)
                    .longitude(longitude)
                    .transportationType(transportationType)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParticipateUpdate {

        @NotNull
        private Long participateId;

        @NotBlank(message = "닉네임을 입력하지 않았습니다.")
        private String userName;

        @NotBlank(message = "출발 위치가 입력되지 않았습니다.")
        private String locationName;

        @NotNull
        private Double latitude;

        @NotNull
        private Double longitude;

        @Enumerated(value = STRING)
        @NotNull
        private TransportationType transportationType;

        public GroupServiceRequest.ParticipateUpdate toServiceRequest() {
            return GroupServiceRequest.ParticipateUpdate.builder()
                    .participateId(participateId)
                    .userName(userName)
                    .locationName(locationName)
                    .latitude(latitude)
                    .longitude(longitude)
                    .transportationType(transportationType)
                    .build();
        }
    }
}
