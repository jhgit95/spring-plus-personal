플러스 심화 주차 개인 과제


7. N+1
- `CommentController` 클래스의 `getComments()` API를 호출할 때 N+1 문제가 발생하고 있어요. N+1 문제란, 데이터베이스 쿼리 성능 저하를 일으키는 대표적인 문제 중 하나로, 특히 연관된 엔티티를 조회할 때 발생해요.
- 해당 문제가 발생하지 않도록 코드를 수정해주세요.

![n+1](https://github.com/user-attachments/assets/1e6c9703-d377-493a-a244-72e1011839eb)

----

8. QueryDSL
- JPQL로 작성된 `findByIdWithUser` 를 QueryDSL로 변경합니다.
- 7번과 마찬가지로 N+1 문제가 발생하지 않도록 유의해 주세요!

----

9. Spring  Security
 - 기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 Spring Security로 변경해주세요.
    - 접근 권한 및 유저 권한 기능은 그대로 유지해주세요.
    - 권한은 Spring Security의 기능을 사용해주세요.
- 토큰 기반 인증 방식은 유지할 거예요. JWT는 그대로 사용해주세요.  
![security1](https://github.com/user-attachments/assets/a99fc7dc-bfcd-4a4f-8c01-03f7e833df1f)
![security2](https://github.com/user-attachments/assets/a89608c6-5863-4565-abb9-47095ac6eaed)
![security3](https://github.com/user-attachments/assets/cf573de7-2121-4c53-8125-f80c0d3dfa1f)

----

10. QueryDSL 을 사용하여 검색 기능 만들기
- 새로운 API로 만들어주세요.
- 검색 조건은 다음과 같아요.
    - 검색 키워드로 일정의 제목을 검색할 수 있어요.
        - 제목은 부분적으로 일치해도 검색이 가능해요.
    - 일정의 생성일 범위로 검색할 수 있어요.
        - 일정을 생성일 최신순으로 정렬해주세요.
    - 담당자의 닉네임으로도 검색이 가능해요.
        - 닉네임은 부분적으로 일치해도 검색이 가능해요.
- 다음의 내용을 포함해서 검색 결과를 반환해주세요.
    - 일정에 대한 모든 정보가 아닌, 제목만 넣어주세요.
    - 해당 일정의 담당자 수를 넣어주세요.
    - 해당 일정의 총 댓글 개수를 넣어주세요.
- 검색 결과는 페이징 처리되어 반환되도록 합니다.

   aaaaaaa

----

11. Transaction 심화
- 매니저 등록 요청을 기록하는 로그 테이블을 만들어주세요.
    - DB 테이블명: `log`
- 매니저 등록과는 별개로 로그 테이블에는 항상 요청 로그가 남아야 해요.
    - 매니저 등록은 실패할 수 있지만, 로그는 반드시 저장되어야 합니다.
    - 로그 생성 시간은 반드시 필요합니다.
    - 그 외 로그에 들어가는 내용은 원하는 정보를 자유롭게 넣어주세요.
