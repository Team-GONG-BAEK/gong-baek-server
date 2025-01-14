package com.ggang.be.domain.userEveryGroup.application;

import static org.mockito.Mockito.when;

import com.ggang.be.domain.group.dto.ReadEveryGroupMember;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupFixture;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userEveryGroup.fixture.UserEveryGroupFixture;
import com.ggang.be.domain.userEveryGroup.infra.UserEveryGroupRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class UserEveryGroupServiceImplTest {

    @InjectMocks
    private UserEveryGroupServiceImpl userEveryGroupService;

    @Mock
    private UserEveryGroupRepository userEveryGroupRepository;


    @Test
    @DisplayName("유저가 속한 주차별 모임  가져온다. - 호스트가 아닌 경우")
    void getEveryGroupUsersInfoNotHost() {
        // given
        EveryGroupEntity testEveryGroup = EveryGroupFixture.getTestEveryGroup();

        ReadEveryGroupMember dto = new ReadEveryGroupMember(testEveryGroup);

        when(userEveryGroupRepository.findUserEveryGroupEntityByEveryGroupEntity(testEveryGroup))
            .thenReturn(List.of(UserEveryGroupFixture.createByNickname("test", "test2"), UserEveryGroupFixture.createByNickname("test3", "test4"),
                UserEveryGroupFixture.createByNickname("test5", "test6")));
        // when
        List<FillMember> everyGroupUsersInfo = userEveryGroupService.getEveryGroupUsersInfo(dto);

        // then
        Assertions.assertThat(everyGroupUsersInfo).hasSize(3)
            .containsExactlyInAnyOrder(FillMember.of(UserEveryGroupFixture.createByNickname("test", "test2").getUserEntity(),
                    false),
                FillMember.of(UserEveryGroupFixture.createByNickname("test3", "test4").getUserEntity(), false),
                FillMember.of(UserEveryGroupFixture.createByNickname("test5", "test6").getUserEntity(), false));
    }


    @Test
    @DisplayName("유저가 속한 주차별 모임  가져온다.")
    void getEveryGroupUsersInfoIsHost() {
        // given
        EveryGroupEntity testEveryGroup = EveryGroupFixture.getTestEveryGroup();

        ReadEveryGroupMember dto = new ReadEveryGroupMember(testEveryGroup);

        when(userEveryGroupRepository.findUserEveryGroupEntityByEveryGroupEntity(testEveryGroup))
            .thenReturn(List.of(UserEveryGroupFixture.createByNickname("test", "test2"),
                UserEveryGroupFixture.createByNickname("nickname", "nickname"),
                UserEveryGroupFixture.createByNickname("test", "test3")));
        // when
        List<FillMember> everyGroupUsersInfo = userEveryGroupService.getEveryGroupUsersInfo(dto);

        // then
        Assertions.assertThat(everyGroupUsersInfo).hasSize(3)
            .containsExactlyInAnyOrder(FillMember.of(
                    UserEveryGroupFixture.createByNickname("test", "test2").getUserEntity(),
                    false),
                FillMember.of(
                    UserEveryGroupFixture.createByNickname("nickname", "nickname").getUserEntity(),
                    true),
                FillMember.of(
                    UserEveryGroupFixture.createByNickname("test", "test3").getUserEntity(),
                    false));
    }

}