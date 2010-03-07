/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fgt.ws.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:/context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringIntegrationTest {

    @Autowired
    private SaluteService saluteService;

    @Test
    public void should_create_spring_context() {
        saluteService.sayHello("Mipo");
    }
}
