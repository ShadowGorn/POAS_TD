/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.event;

import java.util.EventObject;
import poas_td.model.Ammo;

/**
 *
 * @author nik95_000
 */
public class AmmoEvent extends EventObject {
    
    Ammo _ammo;

    public AmmoEvent(Object o) {
        super(o);
    }
    
    public void set_ammo(Ammo ammo) {
        _ammo = ammo;
    }
    
    public Ammo get_ammo() {
        return _ammo;
    }  
}
