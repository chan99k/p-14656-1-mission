package com.back.global;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.back.domain.post.post.document.Post;
import com.back.domain.post.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration // @Configuration 어노테이션으로 설정 클래스 지정
@RequiredArgsConstructor
public class BaseInitData {

	private final PostService postService;

	@Bean
	public ApplicationRunner baseInitDataRunner() { // ApplicationRunner Bean을 통해 애플리케이션 시작 시 초기화 로직 실행
		return args -> {
			// log.debug("ApplicationRunner 빈은 스프링에 등록되면 자동으로 실행됩니다");
			work1();
			work2();
			work3();
			work4();
			work5();
		};
	}

	private void work1() {
		log.debug("Post entity 개수: {}", postService.count());
		log.debug("샘플 Post 데이터 생성");
		if (postService.count() == 0) {
			for (int i = 1; i <= 10; i++) {
				String title = "Sample Post Title " + i;
				String content = "This is the content of sample post number " + i + ".";
				String author = "Author" + i;
				Post post = postService.create(title, content, author);
				log.debug("Created Post: {}", post);
			}
		}
	}

	private void work2() {
		log.debug("기존 Post 전체 조회");
		for (Post post : postService.findAll()) {
			log.debug("Existing Post: {}", post);
		}
	}

	private void work3() {
		log.debug("Post 단건 조회");
		for (Post post : postService.findAll()) {
			Post fetchedPost = postService.findById(post.getId());
			log.debug("조회된 Post: {}", fetchedPost);
		}
	}

	private void work4() {
		log.debug("Post 단건 수정");
		for (Post post : postService.findAll()) {
			String newTitle = post.getTitle() + " [Updated]";
			String newContent = post.getContent() + " This content has been updated.";
			Post updatedPost = postService.update(post.getId(), newTitle, newContent);
			log.debug("Updated Post: {}", updatedPost);
		}
	}

	private void work5() {
		log.debug("Post 삭제");
		for (Post post : postService.findAll()) {
			postService.delete(post.getId());
			log.debug("Deleted Post: {}", post.getId());
		}
		log.debug("삭제 후 Post 개수: {}", postService.count());
	}
}
