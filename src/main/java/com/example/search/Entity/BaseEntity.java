//7. 공통적으로 사용할 필드를 지정
// 생성일자, 수정일자, 회원ID
package com.example.search.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
//객체지향 프로그램에서는 클래스내의 변수를 직접 접근하는 방식을 추천하지 않는다.
//클래스내의 변수를 접근하는 방법은 메소드를 통해서 관리
// set변수명() : 해당변수에 값을 저장하는 메소드 ==> set 변수명() 메소드를 한번에 setter
// get변수명() : 해당변수에 값을 읽어오는 메소드 ==> get 변수명() 메소드를 한번에 getter
// toString() : 클래스내에 있는 모든 변수를(일부분도 가능) 한번에 읽어온다.
// builder() : 클래스내에 있는 모든 변수를(일부분도 가능) 한번에 저장한다.

@Getter@Setter
//혼자서는 사용 못하고 상속을 받아서 처리할 수 있는 클래스로 선언
@MappedSuperclass
//감사를 통한 이벤트 처리
public abstract class BaseEntity {
    @Column(name = "regdate", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime regdate;

    @Column(name = "moddate")
    @LastModifiedDate
    private LocalDateTime moddate;
    // 변수에 처리하는 함수를 사용자가 작성
    // 변수에 변화가 생길 때 수정시간이 오래 걸린다. 실수로 오류 발생
    // 자동으로 함수를 관리해주는 라이브러리 lombok
//    public LocalDateTime getModdate() {
//        return moddate;
//    }
//
//    public void setModdate(LocalDateTime moddate) {
//        this.moddate = moddate;
//    }
//
//    @Override
//    public String toString() {
//        return "BaseEntity{" +
//                "regdate=" + regdate +
//                ", moddate=" + moddate +
//                '}';
//    }



}
