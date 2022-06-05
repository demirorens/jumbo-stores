package com.jumbo.store;

import com.jumbo.store.controller.v1.JumboStoreController;
import com.jumbo.store.controller.v1.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StoreApplicationTests {

    @Autowired
    LoginController loginController;
    @Autowired
    JumboStoreController jumboStoreController;

    @Test
    void contextLoads() {
        assertNotNull(loginController);
        assertNotNull(jumboStoreController);
    }

}
