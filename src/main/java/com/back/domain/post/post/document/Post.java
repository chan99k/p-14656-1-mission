package com.back.domain.post.post.document;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * <h3>Persistable 인터페이스 구현이 필요한 이유</h3>
 * <p>
 *   Spring Data Elasticsearch에서 엔티티의 "신규 상태(New State)"를 정확히 제어하기 위해
 *   <code>Persistable</code> 인터페이스를 구현합니다.
 * </p>
 *
 * <h4>1. JPA와 Elasticsearch의 상태 관리 차이</h4>
 * <ul>
 *     <li>
 *         <strong>JPA:</strong> 영속성 컨텍스트(Persistence Context)가 엔티티의 상태
 *         (<code>transient</code>, <code>managed</code>, <code>detached</code> 등)를 직접 추적하고 관리합니다.
 *     </li>
 *     <li>
 *         <strong>Elasticsearch:</strong> 별도의 영속성 컨텍스트가 존재하지 않습니다.
 *         따라서 프레임워크는 엔티티 저장 시 <code>isNew()</code> 메서드의 반환값을 기준으로
 *         새 문서 생성(Index)과 기존 문서 수정(Update)을 구분합니다.
 *     </li>
 * </ul>
 *
 * <h4>2. 데이터 감사(Auditing)와 @CreatedDate</h4>
 * <p>
 *   <code>@CreatedDate</code> 어노테이션이 올바르게 동작하려면 프레임워크가 해당 엔티티를
 *   신규 상태로 명확히 인식해야 합니다. <code>isNew()</code>가 적절히 구현되지 않으면
 *   생성 날짜가 채워지지 않는 등 Auditing 기능이 누락될 수 있습니다.
 * </p>
 *
 * <h4>3. 사용자 정의 ID(Custom ID) 지원</h4>
 * <p>
 *   기본 <code>isNew()</code> 로직은 ID 필드의 <code>null</code> 여부만 확인합니다.
 *   하지만 애플리케이션에서 <strong>직접 ID를 할당하여 저장하는 경우</strong>, ID가 존재함에도 불구하고
 *   새 문서인 경우가 발생합니다. 이를 위해 생성일(<code>createdAt</code>) 필드의 <code>null</code>
 *   여부를 함께 확인하여 신규 문서임을 증명해야 합니다.
 * </p>
 */
@Data
@Document(indexName = "posts")
public class Post implements Persistable<String> {
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
	@CreatedDate
	private OffsetDateTime createdAt;

	@Field(
		type = FieldType.Date,
		format = DateFormat.date_time
	)
	@LastModifiedDate
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

	/**
	 * ID가 null이거나 날짜 필드가 모두 null이면 새 엔티티로 판단
	 */
	@Override
	public boolean isNew() {
		return id == null || (createdAt == null && lastModifiedAt == null);

	}
}
