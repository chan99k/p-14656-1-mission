package com.back.domain.post.post.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.back.domain.post.post.document.Post;

public interface PostRepository extends ElasticsearchRepository<Post,String> {
}
