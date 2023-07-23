package shoppingproject.shop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingproject.shop.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        if(member.getId() == null){
            em.persist(member);
        }else{
            em.merge(member);
        }

    }


    // 조회
    public Member findOne(long id){
        return em.find(Member.class, id);
    }

    // 전체 조회
    public List<Member> findAll() throws Exception{ //qlsting : 객체를 기준으로 조회함.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //이름으로 조회
    public Optional<Member> findByLoginIds(String id) throws Exception{
        List<Member>  members =  em.createQuery("select m from Member m where m.loginId = :id",Member.class)
                .setParameter("id",id)
                .getResultList();
        return members.stream().findAny();
    }
    public Member findByLoginId(String id) throws Exception{
        Member member =  em.createQuery("select m from Member m where m.loginId = :id",Member.class)
                .setParameter("id",id)
                .getSingleResult();
        return member;
    }


    public Optional<Member> chkLogin(String id,String pw) throws Exception{
        Member member =  em.createQuery("select m from Member m where m.loginId = :id and m.password = :pw",Member.class)
                .setParameter("id",id)
                .setParameter("pw",pw)
                .getSingleResult();
        return Optional.ofNullable(member);
    }

    public void remove(Member member){
        em.remove(member);
    }
}
