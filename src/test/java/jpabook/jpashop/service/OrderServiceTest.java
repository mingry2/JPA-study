package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 상품주문() {
        //given
        Member member = createMember();
        em.persist(member);

        Book book = createBook("책1", 10000, 10);
        em.persist(book);

        int orderCount = 2;

        //when
        Order order = orderRepository.findOne(orderService.order(member.getId(), book.getId(), orderCount));

        //then
        assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        em.persist(member);

        Book book = createBook("책1", 10000, 10);
        em.persist(book);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderRepository.findOne(orderService.order(member.getId(), book.getId(), orderCount));
        });
    }

    @Test
    void 상품취소() {
        //given
        Member member = createMember();
        em.persist(member);
        Book book = createBook("BookA", 10000, 10);
        em.persist(book);
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 1);
        orderService.cancel(orderId);
        //then
        assertThat(orderRepository.findOne(orderId).getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    private static Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "아파트", "123-123"));
        return member;
    }

    private static Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }
}