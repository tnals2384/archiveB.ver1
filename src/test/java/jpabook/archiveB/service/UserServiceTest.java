package jpabook.archiveB.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {
/*
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        User user= new User();
        user.setName("kim");

        //when
        Long savedId= userService.join(user);
        //then
        em.flush();
        Assertions.assertEquals(user, userRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        User user1= new User();
        user1.setName("kim");
        User user2= new User();
        user2.setName("kim");

        userService.join(user1);
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class,
                () ->userService.join(user2));
        assertEquals("이미 존재하는 회원입니다.",thrown.getMessage());
    }
*/


}