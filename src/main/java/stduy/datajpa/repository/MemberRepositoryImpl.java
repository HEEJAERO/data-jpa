package stduy.datajpa.repository;

import lombok.RequiredArgsConstructor;
import stduy.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    // 해당 repository 뒤에 Impl 을 붙여줘야 자동으로 완성됨

    private final EntityManager em;


    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
