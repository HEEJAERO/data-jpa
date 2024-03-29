package stduy.datajpa.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import stduy.datajpa.entity.Member;

import javax.persistence.criteria.*;

public class MemberSpec {

    public static Specification<Member> teamName(final String teamName){
        return (root, query, builder) -> {

            if (StringUtils.isEmpty(teamName)) {
                return null;
            }
            Join<Object, Object> t = root.join("team", JoinType.INNER);
            return builder.equal(t.get("name"),teamName);
        };
    }
    public static Specification<Member> username(final String username){
        return (root, query, builder) -> builder.equal(root.get("username"),username);
    }
}
