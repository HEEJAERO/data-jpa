package stduy.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import stduy.datajpa.dto.MemberDto;
import stduy.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
        //해당 어노테이션이 없어도 동작함 (엔티티.메소드 로 namedquery 를 먼저 찾고
        //이후 메서드 이름으로 쿼리를 자동으로 생성되는 기준으로 생성된다.
    List<Member> findByUsername(@Param("username") String username);
    //named 쿼리에 :username 변수에 넣을 값을 넘길때는 @Param 으로 매칭을 시켜줘야한다.
    // namedQuery 는 거의 사용안함?
    // 어플리케이션 로딩시점에 오류를 찾을 수 있다는 것이 가장 큰 장점점

    @Query("select m from Member m where m.username = :username and m.age = :age")
        // 이것또한 어플리케이션 로딩시점에 오류를 찾을 수 있다는 것이 가장 큰 장점점 > 이름없는 네임드쿼리같은 느낌? -> 이것을 실무에서 많이 사용
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new stduy.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);

    Member findMemberByUsername(String username);

    Optional<Member> findOptionalByUsername(String username);

    @Query(value = "select m from Member m left join m.team t"
            , countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
    // 자동으로 countQuery 가 생성되기는 하지만 count 쿼리에서는 필요가 없는 조인도 같이 실행이 된다
    // 성능을 올리기 위해서 countQuery 를 따로 분리하여 작성 하는것이 가능하다.


    //jpa 의 executeUpdate 를 실행하기 위해서 Modifying 어노테이션을 넣어줘야함
    @Modifying(clearAutomatically = true) // 영속성 컨텍스트를 초기화 해주지 않으면  과거의 값이 남아 있어 (같은트랜잭션안의) 이후 연산에 문제가 생길수도있다.
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
        //fetch join -> 멤버를 조회할때 연관된 팀을 같이 불러옴
        // -> 사용하지않으면 team 을 조회할때까지 프록시객체(가짜값) 을 넣어둠
    List<Member> findMemberFetchJoin();
    // jpql 없이 패치조인을 사용하기 위해 jpa 데이터에서 entityGraph 라는 기능을 제공

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //@EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findEntityByUsername(@Param("username") String username);


    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);
}

