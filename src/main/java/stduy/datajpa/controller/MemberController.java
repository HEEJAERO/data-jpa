package stduy.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stduy.datajpa.dto.MemberDto;
import stduy.datajpa.entity.Member;
import stduy.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        //1 처럼 id 를 조회 후 넣는게 아닌 Member 로 받아도 스프링 부츠가 도메인 클래스 컨버터라는 기능이 id 를 찾아서 반환
        // 그러나 이 방법은 권장되는 방법은 아님
        // 또한 이 방법은 조회용으로만 사용해야한다 .
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable) {
        return memberRepository.findAll(pageable).map(member -> new MemberDto(member));
    }
    //@PostConstruct
    public void init(){
        for(int i=0;i<100;i++){
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
