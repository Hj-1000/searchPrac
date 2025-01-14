package com.example.search.Config;
//5. 사용자 메소드를 빈에 등록
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    //mopdelmapper 를 사용할 때 private ModelmMapper modelmapper = new ModelmMapper()로 사용
    //클래스를 사용해서 이용해야 하는데, 이것을 자동 주입으로 변경
    //private final ModelMapper modelmapper;
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}

//6. 작업할 클래스와 인터페이스를 생성한다.
//테이블명(여러테이블) 또는 프로젝트명(단일테이블)을 참고해서
//테이블명 + 패키지명

//테이블 설계가 완료된 후 작업(필드명은 데이터베이스 명령어(예약어)와 동일한 이름은 사용X)
//테이블명 : store
//idx : 일련번호 - 기본키, 생략불가능, 자동증가
//storename : 매장총판명, 문자(45), 생략가능
//storeid : 총판id, 문자(45), 생략가능
//storechifid : 총판장Id, 문자(45), 생략가능
//storechif : 총판장명(대표), 문자(45), 생략가능
//storetel : 총판연락처, 문자(25), 생략가능
//storedel : 삭제여부, 문자(1), todfirrksmd, Y-삭제, N-미삭제(현재 이용중)
//regdate : 생성일자
//moddate : 수정일자
