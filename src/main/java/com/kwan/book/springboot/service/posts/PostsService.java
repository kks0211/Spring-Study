package com.kwan.book.springboot.service.posts;

import com.kwan.book.springboot.web.domain.posts.Posts;
import com.kwan.book.springboot.web.domain.posts.PostsRepository;
import com.kwan.book.springboot.web.dto.PostsReponseDto;
import com.kwan.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id =" +id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsReponseDto findById (Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당사용자가 없습니다 id= " + id));
        return new PostsReponseDto(entity);
    }
}
