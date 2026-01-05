package com.back.domain.post.post.document;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "posts")
public class Post {
	@Id
	private String id;
	@Field(type = FieldType.Text)
	private String title;
	@Field(type = FieldType.Text)
	private String content;
	/**
	 * <h3>Elasticsearch Keyword 타입 매핑 개요</h3>
	 * <p>
	 * 해당 필드를 Elasticsearch의 <code>Keyword</code> 타입으로 지정합니다.
	 * 분석기를 거치지 않는 정적 텍스트 데이터를 처리하는 데 최적화되어 있습니다.
	 * </p>
	 *
	 * <h4>1. 주요 특징</h4>
	 * <ul>
	 *     <li>
	 *         <strong>비분석 (Not Analyzed):</strong>
	 *         데이터 저장 시 형태소 분석(Tokenizing)을 거치지 않습니다.
	 *         예를 들어, <code>"Hello World"</code>는 분리되지 않고 전체 문자열 그대로 인덱싱됩니다.
	 *     </li>
	 *     <li>
	 *         <strong>완전 일치 (Exact Match):</strong>
	 *         검색 시 대소문자를 포함하여 값이 정확히 일치해야만 검색 결과에 포함됩니다.
	 *     </li>
	 *     <li>
	 *         <strong>성능 최적화:</strong>
	 *         구조상 정렬(Sorting), 집계(Aggregation), 필터링 작업에 매우 효율적입니다.
	 *     </li>
	 * </ul>
	 *
	 * <h4>2. 주요 사용 사례</h4>
	 * <p>전체 텍스트 검색보다는 데이터의 <strong>분류</strong>나 <strong>식별</strong>이 목적인 경우에 사용합니다.</p>
	 * <ul>
	 *     <li>고유 식별자: <code>ID</code>, 이메일 주소, 호스트명</li>
	 *     <li>상태 코드: <code>OPEN</code>, <code>CLOSED</code>, <code>PENDING</code></li>
	 *     <li>분류 체계: 태그(Tag), 카테고리 이름</li>
	 *     <li>정확한 명칭: 사용자 이름, 상품 코드 등 (정확히 일치하는 값을 찾을 때)</li>
	 * </ul>
	 */
	@Field(type = FieldType.Keyword)
	private String author;

	@Field(
		type = FieldType.Date,
		format = DateFormat.date_time
	)
	private OffsetDateTime createdAt;

	@Field(
		type = FieldType.Date,
		format = DateFormat.date_time
	)
	private OffsetDateTime lastModifiedAt;

	public Post(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = OffsetDateTime.now();
		this.lastModifiedAt = OffsetDateTime.now();
	}

	@Override
	public String toString() {
		return "Post{" +
			"id='" + id + '\'' +
			", title='" + title + '\'' +
			", content='" + content + '\'' +
			", author='" + author + '\'' +
			", createdAt=" + createdAt +
			", lastModifiedAt=" + lastModifiedAt +
			'}';
	}
}
