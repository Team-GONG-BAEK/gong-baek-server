package com.ggang.be.domain.block.application;

import com.ggang.be.domain.block.BlockEntity;
import com.ggang.be.domain.block.infra.BlockRepository;
import com.ggang.be.domain.report.ReportEntity;
import com.ggang.be.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockServiceImpl {

    private final BlockRepository blockRepository;

    @Transactional
    public void blockUser(ReportEntity reportEntity, UserEntity userEntity) {
        blockRepository.save(buildBlockEntity(reportEntity, userEntity));
    }

    public List<String> findUserBlocks(long userId) {
        return blockRepository.findUserId(userId)
                .stream()
                .map(UserEntity::getNickname)
                .toList();
    }

    private BlockEntity buildBlockEntity(ReportEntity reportEntity, UserEntity userEntity) {
        return BlockEntity.builder()
                .report(reportEntity)
                .user(userEntity)
                .build();
    }

    public List<String> findByReports(List<ReportEntity> reports) {
        return reports.stream()
                .map(blockRepository::findByReport)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(blockEntity -> blockEntity.getUser().getNickname())
                .toList();
    }

    @Transactional
    public void deleteBlocksByUser(UserEntity user) {
        blockRepository.deleteAllByUser(user);
    }

    @Transactional
    public void deleteBlocksByBlockedUserId(Long userId) {
        blockRepository.deleteAllByBlockedUserId(userId);
    }
}
