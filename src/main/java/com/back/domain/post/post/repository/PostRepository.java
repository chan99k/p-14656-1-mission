package com.back.domain.post.post.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.back.domain.post.post.document.Post;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
	/**
	 * 왜 findAll()을 재선언하나요?
	 * <ul>
	 *     <li>
	 *          ElasticsearchRepository의 기본 findAll()은 Iterable<Post>를 반환합니다.
	 *     </li>
	 *     <li>
	 *         List<Post> 반환 타입으로 재선언하면 Spring Data가 자동으로 List로 변환해줍니다.
	 *     </li>
	 *     <li>
	 *         JPA의 JpaRepository는 기본으로 List<T>를 반환하므로 재선언이 필요 없습니다.
	 *     </li>
	 * </ul>
	 */
	List<Post> findAll();
}
