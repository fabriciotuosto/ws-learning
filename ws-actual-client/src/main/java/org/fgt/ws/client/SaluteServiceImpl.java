/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fgt.ws.client;

/**
 *
 * @author Fabricio Tuosto
 */
public class SaluteServiceImpl implements SaluteService{

    @Override
    public String sayHello(String name) {
        return String.format("Hello %s , How are you ?",name);
    }

}
