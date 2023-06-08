package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    //Jpa 시작
    //Persistance.xml부터 시작해서 파일을 읽고 엔티티매니저팩토리를 만듬듬
   public static void main(String[] args) {

       //엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
       //엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
       // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행

       //DB당 한개만 생성
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");




       //DB커넥션을 얻어서 쿼리를 날리고 종료되는 행위를 할때마다 EntityManager를 만들어야함
       EntityManager em = emf.createEntityManager();

       //jpa에서는 트랜잭션 안에서 모든 작업이 이루어진다(중요)
       EntityTransaction tx = em.getTransaction();
       tx.begin();
       //code

       try {

           //저장
//           Member member = new Member();
//           member.setId(2L);
//           member.setName("HelloB");
//           em.persist(member);
           //조회
//           Member findMember = em.find(Member.class, 1L);
//           System.out.println("findMember.Id() = " + findMember.getId());
//           System.out.println("findMember.Name() = " + findMember.getName());
           //나이가 18살 이상인 회원을 모두 검색하고 싶다면? -> JPQL을 사용해야함
//           List<Member> result = em.createQuery("select m from Member as m",Member.class)
//                   .getResultList();
//
//           for(Member member : result){
//               System.out.println("member.getName() = " + member.getName());
//           }
           //삭제//           Member findMember = em.remove(Member.class, 1L);

           //수정
//           Member findMember = em.find(Member.class, 1L);
//           findMember.setName("HelloJPA");
           //영속성컨텍스트 개념
           //비영속
//           Member member = new Member();
//           member.setId(101L);
//           member.setName("HelloJPA");

           //영속 (영속상태가 된다고 바로 DB에 쿼리가 날라가지 않는다.)
//           System.out.println("====BEFORE====");
//           em.persist(member);
//           System.out.println("====AFTER====");
//
//           Member findMember =  em.find(Member.class, 101L);
//
//           System.out.println("findMember.getId() = " + findMember.getId());
//           System.out.println("findMember.getName() = " + findMember.getName());

           //준영속상태
//           Member member = em.find(Member.class, 150L);
//           member.setName("AAAAA");

           //영속 상태였던 member(엔티티)를 영속상태에서 제외시킴 -> 고로 ZZZZZ였던 데이터가 AAAAA로 DB에서 수정되지 않음
//          em.detach(member);
           //해당 엔티티뿐만아니라 모든 영속성 컨텍스트를 초기화시킴
//           em.clear();
//           Member member2 = em.find(Member.class,150L);

           //연관관계 매핑 기초

           //저장

//           Team team =new Team();
//           team.setName(("TeamA"));
//           em.persist(team);
//
//           Member member =new Member();
//           member.setUsername("member1");
//           //순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자1
//           member.setTeam(team); //**
//           em.persist(member);

           //순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자2
           //team.getMembers().add(member); //**
           //여기서 지우고 setTeam 메서드 안에서 설정 해주자.

           //객체를 테이블에 맟추어  데이터 중심으로 모델링하면, 협력 관계를만들 수 없다.
           //테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다.
           //객체는 참조를 사용해서 연관된 객체를 찾는다.
           //테이블과 객체 사이에는 이런 큰 간격이 있다.

//           Team team = new Team();
//           team.setName("teamA");
//           em.persist(team);
//
//           Member member1 = new Member();
//           member1.setUsername("member1");
//           member1.setTeam(team);
//           em.persist(member1);
//
//           em.flush();
//           em.clear();
//
//           Member m = em.find(Member.class, member1.getId());
//
//           System.out.println("m = " + m.getTeam().getClass());
//
//
//           System.out.println(" ===================== " );
//           m.getTeam().getName();
//           System.out.println(" ===================== " );


           List<Member> result = em.createQuery(
                   "select m From Member m where m.username like '%kim%'",
                   Member.class
           ).getResultList();






           //System.out.println("refMember = " + refMember.getUsername());
           //프록시는 호출할때 영속성 컨텍스트의 도움을 받아서 가짜 객체의 target을 진짜 객체에서 받아서 가져온다.
           //그런데, 영속성 컨텍스트를 detach해서 관리 안해버리거나 close(또는 clear)해서 닫아버리게 되면, 기존 영속성 컨텍스트의 정보가 날아가고, 프록시를 호출하면 에러가 발생한다.

            //강제초기화 및 프록시 초기화 확인
//           Hibernate.initialize(refMember);
//           System.out.println("isLoaded= " + emf.getPersistenceUnitUtil().isLoaded(refMember));

           //커밋하는 순간 DB에 쿼리가 날라간다.
           tx.commit();

           //e플러쉬는 commit하거나 jpql쿼리를 날릴때 자동으로 플러쉬 되므로 em.flush는 거의 쓸일이 없다.
           //플러시는 영속성 컨텍스트의 변경내용을 DB에 동기화 하는 작업이다.



       } catch(Exception e){
//           System.out.println("e.getMessage() = " + e.getMessage());
           e.printStackTrace();
           tx.rollback();
       } finally {
           em.close();
       }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member.getUsername() = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
    
        String username = member.getUsername();
        System.out.println("username = " + username);
        
        Team team =member.getTeam();
        System.out.println("team.getName() = " + team.getName());
   }
}
