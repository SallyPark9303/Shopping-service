package shoppingproject.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional// 모든 데이터변경,로직은 트랜잭션안에서 실행되어야하는 편이 좋음.
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long join(Member member) throws Exception{

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return  member.getId();

    }

    public void update(Long id, Member param){
        Member mem = memberRepository.findOne(id);
        mem.setLoginId(param.getLoginId());
        mem.setName(param.getName());
        mem.setEmail(param.getEmail());
        mem.setContact(param.getContact());
        mem.setGender(param.getGender());
        mem.setUserType(param.getUserType());

    }

    private void validateDuplicateMember(Member member) throws Exception {
        Optional<Member> findMembers = memberRepository.findByLoginIds(member.getLoginId());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원아이디 입니다.");
        }
    }

    public List<Member> findMembers() throws Exception{
        return memberRepository.findAll();
    }
    public Member findOne(long id) throws Exception{
        return memberRepository.findOne(id);
    }
    public Member getLogin(String id, String pw) throws Exception {
        // 로그인 정보 체크
        chkLogin(id,pw);
       Member loginUser =  memberRepository.findByLoginId(id);
        return  loginUser;

    }
    private void chkLogin(String loginId, String password) throws Exception {

      Optional<Member> loginUser = memberRepository.chkLogin(loginId,password);
      if(loginUser == null){
          throw new IllegalStateException("로그인 정보가 일치 하지 않습니다.");
      }
    }

    public void remove(Member member){
        memberRepository.remove(member);

    }



}

