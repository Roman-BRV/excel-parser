package ua.pp.helperzit.excelparser.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.pp.helperzit.excelparser.ExcelParser;
import ua.pp.helperzit.excelparser.service.models.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ExcelParser.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private User invalidUser;

    @BeforeEach
    void setUp(){
        testUser = new User("userName", "email@email.com");
        invalidUser = new User("invalidUser","");
    }

    @Test
    public void givenUserRepository_whenSaveAndRetrieveEntity_thenOK(){
        User excepted = userRepository.save(testUser);
        User result = userRepository.findById(excepted.getId()).orElse(invalidUser);

        assertNotEquals(0,result.getId());
        assertEquals(excepted, result);
    }
}
