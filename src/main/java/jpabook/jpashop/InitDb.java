package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Address address = new Address("서울", "1", "11111");
            Member member = createMember("userA", address);

            Book book1 = createItem("book1", 10000, 10, "author2", "isbn2");
            Book book2 = createItem("book2", 20000, 20, "author3", "isbn3");

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        public void dbInit2() {
            Address address1 = new Address("부산", "2", "22222");
            Member member = createMember("userB", address1);

            Book book1 = createItem("book1", 10000, 10, "author2", "isbn2");
            Book book2 = createItem("book2", 20000, 20, "author3", "isbn3");

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private Book createItem(String itemName, int price, int stockQuantity, String author, String isbn) {
            Book book1 = new Book();
            book1.setName(itemName);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            book1.setAuthor(author);
            book1.setIsbn(isbn);
            em.persist(book1);
            return book1;
        }

        private Member createMember(String memberName, Address address) {
            Member member = new Member();
            member.setName(memberName);
            member.setAddress(address);
            em.persist(member);
            return member;
        }
    }
}
