package stduy.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    @Value("#{target.username +' ' +target.age}")//username 과 age 를 가져와서 문자열로 반환해줌
    // openProjection - 쿼리로는 일단 모든 값을 가져온 뒤에 필요한 데이터만을 추출하는 방법
    // closeProjection - 필요한 데이터만 쿼리를 날려서 가져오는것
    String getUsername();
}
