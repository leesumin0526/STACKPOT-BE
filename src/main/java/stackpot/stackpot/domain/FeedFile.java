package stackpot.stackpot.domain;

import jakarta.persistence.*;
import lombok.*;
import stackpot.stackpot.domain.common.BaseEntity;
import stackpot.stackpot.domain.enums.Visibility;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FeedFile extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(length = 255, nullable = false)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;
}
