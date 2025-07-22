package com.ggang.be.api.block;

import com.ggang.be.api.group.ActiveCombinedGroupVoPreparer;
import com.ggang.be.api.group.dto.CombinedGroupVos;
import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import com.ggang.be.domain.constant.Category;
import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.group.everyGroup.EveryGroupFixture;
import com.ggang.be.domain.group.everyGroup.dto.EveryGroupVo;
import com.ggang.be.domain.group.everyGroup.dto.ReadEveryGroup;
import com.ggang.be.domain.group.onceGroup.OnceGroupEntity;
import com.ggang.be.domain.group.onceGroup.OnceGroupFixture;
import com.ggang.be.domain.group.onceGroup.dto.OnceGroupVo;
import com.ggang.be.domain.group.onceGroup.dto.ReadOnceGroup;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlockingGroupTest {

    @Mock
    private OnceGroupService onceGroupService;

    @Mock
    private EveryGroupService everyGroupService;

    @InjectMocks
    private ActiveCombinedGroupVoPreparer activeCombinedGroupVoPreparer;

    private UserEntity testUser;
    private Category testCategory;
    private List<Long> blockedUsers;

    private List<EveryGroupVo> everyGroupVos;
    private List<OnceGroupVo> onceGroupVos;

    @BeforeEach
    void setUp() {
        testUser = UserEntityFixture.createByNickname("hello1");
        testCategory = Category.EXERCISE;
        blockedUsers = List.of(2L);

        everyGroupVos = createEveryGroupTestData();
        onceGroupVos = createOnceGroupTestData();

        setupCommonMocks();
    }

    @Test
    @DisplayName("차단된 사용자의 그룹이 필터링되어야 한다")
    void shouldFilterBlockedUserGroups() {
        // When
        CombinedGroupVos result = activeCombinedGroupVoPreparer.prepareGroupVos(
                testUser, testCategory, blockedUsers
        );

        // Then
        assertThat(result.everyGroupVos()).hasSize(2);
        assertThat(result.onceGroupVos()).hasSize(2);

        assertThat(result.everyGroupVos())
                .extracting(EveryGroupVo::nickname)
                .containsExactlyInAnyOrder("hello1", "hello3");

        assertThat(result.onceGroupVos())
                .extracting(OnceGroupVo::nickname)
                .containsExactlyInAnyOrder("hello1", "hello3");
    }

    @Test
    @DisplayName("차단 리스트가 비어있으면 모든 그룹이 반환되어야 한다")
    void shouldReturnAllGroupsWhenNoBlockedUsers() {
        // Given
        List<Long> emptyBlockList = List.of();

        // When
        CombinedGroupVos result = activeCombinedGroupVoPreparer.prepareGroupVos(
                testUser, testCategory, emptyBlockList
        );

        // Then
        assertThat(result.everyGroupVos()).hasSize(3);
        assertThat(result.onceGroupVos()).hasSize(3);

        assertThat(result.everyGroupVos())
                .extracting(EveryGroupVo::nickname)
                .containsExactlyInAnyOrder("hello1", "hello2", "hello3");

        assertThat(result.onceGroupVos())
                .extracting(OnceGroupVo::nickname)
                .containsExactlyInAnyOrder("hello1", "hello2", "hello3");
    }

    private List<EveryGroupVo> createEveryGroupTestData() {
        return List.of(
                toEveryGroupVo("hello1", 1L),
                toEveryGroupVo("hello2", 2L),
                toEveryGroupVo("hello3", 3L)
        );
    }

    private List<OnceGroupVo> createOnceGroupTestData() {
        return List.of(
                toOnceGroupVo("hello1", 1L),
                toOnceGroupVo("hello2", 2L),
                toOnceGroupVo("hello3", 3L)
        );
    }

    private EveryGroupVo toEveryGroupVo(String nickname, Long id) {
        UserEntity user = UserEntityFixture.createByNickname(nickname);
        ReflectionTestUtils.setField(user, "id", id);
        EveryGroupEntity entity = EveryGroupFixture.createByUser(user);
        ReflectionTestUtils.setField(entity, "id", id);
        return EveryGroupVo.of(entity);
    }

    private OnceGroupVo toOnceGroupVo(String nickname, Long id) {
        UserEntity user = UserEntityFixture.createByNickname(nickname);
        ReflectionTestUtils.setField(user, "id", id);
        OnceGroupEntity entity = OnceGroupFixture.createByUser(user);
        ReflectionTestUtils.setField(entity, "id", id);
        return OnceGroupVo.of(entity);
    }


    private void setupCommonMocks() {
        when(everyGroupService.isSameSchoolEveryGroup(any(UserEntity.class), any(EveryGroupVo.class)))
                .thenReturn(true);
        when(onceGroupService.isSameSchoolOnceGroup(any(UserEntity.class), any(OnceGroupVo.class)))
                .thenReturn(true);

        when(everyGroupService.getActiveEveryGroups(testUser, testCategory))
                .thenReturn(ReadEveryGroup.of(everyGroupVos));
        when(onceGroupService.getActiveOnceGroups(testUser, testCategory))
                .thenReturn(ReadOnceGroup.of(onceGroupVos));
    }
}
