/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.ejb.Stateless;

/**
 *
 * @author Kevin
 */
@Stateless
public class AddBean implements AddBeanRemote {

    @Override
    public Integer Add(int Int1, int Int2) {
        return Int1+Int2;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
