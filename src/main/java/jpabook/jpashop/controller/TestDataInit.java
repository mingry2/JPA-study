package jpabook.jpashop.controller;

import jakarta.annotation.PostConstruct;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final ItemService itemService;

    @PostConstruct
    public void init() {
        Member member1 = new Member();
        member1.setName("John Doe");
        member1.setAddress(new Address("London", "London", "Korea"));
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("민경");
        member2.setAddress(new Address("Korea", "Korea", "Korea"));
        memberService.join(member2);

        Book book1 = new Book();
        book1.setName("book1");
        book1.setPrice(10000);
        book1.setStockQuantity(10);
        book1.setAuthor("author1");
        book1.setIsbn("isbn");
        itemService.saveItem(book1);

        Book book2 = new Book();
        book2.setName("book2");
        book2.setPrice(20000);
        book2.setStockQuantity(20);
        book2.setAuthor("author2");
        book2.setIsbn("isbn");
        itemService.saveItem(book2);

    }
}
