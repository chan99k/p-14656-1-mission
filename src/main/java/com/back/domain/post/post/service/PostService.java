package com.back.domain.post.post.service;

import org.springframework.stereotype.Service;

import com.back.domain.post.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public long count() {
		return postRepository.count();
	}
}
