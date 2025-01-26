package stackpot.stackpot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackpot.stackpot.converter.FeedConverter;
import stackpot.stackpot.converter.PotConverter;
import stackpot.stackpot.domain.Feed;
import stackpot.stackpot.domain.Pot;
import stackpot.stackpot.repository.FeedRepository.FeedRepository;
import stackpot.stackpot.repository.PotRepository.PotRepository;
import stackpot.stackpot.web.dto.FeedSearchResponseDto;
import stackpot.stackpot.web.dto.PotSearchResponseDto;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PotRepository potRepository;
    private final FeedRepository feedRepository;
    private final PotConverter potConverter;
    private final FeedConverter feedConverter;

    @Override
    @Transactional(readOnly = true)
    public Page<PotSearchResponseDto> searchPots(String keyword, Pageable pageable) {
        Page<Pot> pots = potRepository.searchByKeyword(keyword, pageable);
        return pots.map(potConverter::toSearchDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedSearchResponseDto> searchFeeds(String keyword, Pageable pageable) {
        Page<Feed> feeds = feedRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword, keyword, pageable);

        // FeedConverter를 사용해 DTO 변환 및 좋아요 개수 포함
        return feeds.map(feedConverter::toSearchDto);
    }

}

