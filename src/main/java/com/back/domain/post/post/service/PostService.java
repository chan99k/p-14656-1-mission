package com.back.domain.post.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.back.domain.post.post.document.Post;
import com.back.domain.post.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public long count() {
		return postRepository.count();
	}

	public Post create(String title, String content, String author) {
		Post post = new Post(title, content, author);
		return postRepository.save(post);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}
}
