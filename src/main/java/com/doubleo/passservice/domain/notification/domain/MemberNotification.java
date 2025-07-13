package com.doubleo.passservice.domain.notification.domain;

import com.doubleo.passservice.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNotification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_notification_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberNotification(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public static MemberNotification createMemberNotification(
            Long memberId, String title, String content) {
        return MemberNotification.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .build();
    }
}
