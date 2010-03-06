/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fgt.ws.client;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author Fabricio Tuosto
 */
public class IntegrationTest {

    @Test
    public void should_create_spring_context(){
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

       SaluteService service = (SaluteService) context.getBean("saluteService");

       service.sayHello("Fabricio");
       System.out.println("should not seen Item not found");
       service.sayHello("Fabricio");
       service.sayHello("Lorena");
    }
}
