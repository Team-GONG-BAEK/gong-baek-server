package com.ggang.be.domain.userOnceGroup.application;

import static org.mockito.Mockito.when;

import com.ggang.be.domain.group.dto.ReadOnceGroupMember;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupFixture;
import com.ggang.be.domain.userEveryGroup.dto.FillMember;
import com.ggang.be.domain.userOnceGroup.fixture.UserOnceGroupFixture;
import com.ggang.be.domain.userOnceGroup.infra.UserOnceGroupRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserOnceGroupServiceImplTest {


    @Mock
    private UserOnceGroupRepository userOnceGroupRepository;


    @InjectMocks
    private UserOnceGroupServiceImpl userOnceGroupService;


    @Test
    @DisplayName("유저가 속한 모든 그룹의 정보를 가져온다. - host 아닌 경우")
    void getOnceGroupUsersInfoNotHost() {
        // given

        OnceGroupEntity testOnceGroupEntity = OnceGroupFixture.getTestOnceGroupEntity();
        ReadOnceGroupMember dto = new ReadOnceGroupMember(testOnceGroupEntity);
        when(userOnceGroupRepository.findUserOnceGroupEntityByOnceGroupEntity(testOnceGroupEntity))
            .thenReturn(List.of(UserOnceGroupFixture.createByNickname("test", "test2"), UserOnceGroupFixture.createByNickname("test3", "test4"),
                UserOnceGroupFixture.createByNickname("test5", "test6")));

        // when
        List<FillMember> onceGroupUsersInfo = userOnceGroupService.getOnceGroupUsersInfo(dto);

        // then
        Assertions.assertThat(onceGroupUsersInfo).hasSize(3)
            .containsExactlyInAnyOrder(FillMember.of(UserOnceGroupFixture.createByNickname("test", "test2").getUserEntity(),
                    false),
                FillMember.of(UserOnceGroupFixture.createByNickname("test3", "test4").getUserEntity(), false),
                FillMember.of(UserOnceGroupFixture.createByNickname("test5", "test6").getUserEntity(), false));
    }

    @Test
    @DisplayName("유저가 속한 모든 그룹의 정보를 가져온다. - host가 있는 경우")
    void getOnceGroupUsersInfoIsHost() {
        // given
        // host 이름 nickname
        OnceGroupEntity testOnceGroupEntity = OnceGroupFixture.createByUserNickname("nickname");
        ReadOnceGroupMember dto = new ReadOnceGroupMember(testOnceGroupEntity);
        when(userOnceGroupRepository.findUserOnceGroupEntityByOnceGroupEntity(testOnceGroupEntity))
            .thenReturn(List.of(UserOnceGroupFixture.createByNickname("test2", "qwer"),
                UserOnceGroupFixture.createByNickname("nickname", "nickname"),
                UserOnceGroupFixture.createByNickname("test", "test3")));

        // when
        List<FillMember> onceGroupUsersInfo = userOnceGroupService.getOnceGroupUsersInfo(dto);

        // then
        Assertions.assertThat(onceGroupUsersInfo).hasSize(3)
            .containsExactlyInAnyOrder(
                FillMember.of(UserOnceGroupFixture.createByNickname("test2", "qwer").getUserEntity(),
                    false),
                FillMember.of(UserOnceGroupFixture.createByNickname("nickname", "nickname").getUserEntity(),
                    true),
                FillMember.of(UserOnceGroupFixture.createByNickname("test", "test3").getUserEntity(),
                    false));
    }


}