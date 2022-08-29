package stduy.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
@Getter
@MappedSuperclass // 이 속성들을 상속받은 Repository 에게 속성들을 전달해줌(없을시 전달안됨 -why?)
public class JpaBaseEntity {

    @Column(updatable = false) // 생성일이 바뀌지 않도록 설정
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist // em.persist 이전에 실행
    public void prPersist(){
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
    }
    @PreUpdate
    public void preUpdate(){
        updateDate = LocalDateTime.now();
    }
}
