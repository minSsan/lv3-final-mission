package finalmission.member.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Role;
import finalmission.member.repository.MemberRepository;
import finalmission.member.service.dto.request.CreateMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @DisplayName("중복되는 이메일의 계정은 생성할 수 없다.")
    @Test
    void cannotCreateDuplicatedEmailUser() {
        // given
        String name = "testUser";
        String email = "test@test.com";
        String password = "12341234";

        Member member = new Member(Role.CUSTOMER, name, email, password);
        memberRepository.save(member);

        CreateMemberRequest request = new CreateMemberRequest(name, email, password, Role.CUSTOMER.name());

        // when & then
        assertThatThrownBy(() -> {
            memberService.create(request);
        }).isInstanceOf(IllegalArgumentException.class);
     }
}
