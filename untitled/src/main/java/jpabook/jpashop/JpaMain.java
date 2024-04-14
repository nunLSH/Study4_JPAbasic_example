package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); // tx 얻기
        tx.begin();

        try {

            Order order = new Order();
//            order.addOrderItem(new OrderItem()); // 양방향 연관관계 - 편의메서드 사용
            em.persist(order);

            // 단방향 연관관계로 작성
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            em.persist(orderItem);

            tx.commit();
        } catch (Exception e) {
            tx.rollback(); // 문제가 생기면 철회
        } finally {
            em.close(); // 작업이 끝나면 EntityManager를 닫아줌.
        }
        // 전체 애플리케이션이 완전히 끝나면 EntityManagerFactory를 닫아주어야 함.
        emf.close();
    }
}