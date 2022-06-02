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
public class TempBean implements TempBeanRemote {

    @Override
    public float ConvertToCelcius(float Ferin) {
        float Celcius = (Ferin-32)*5/9.0f;
        return Celcius;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
