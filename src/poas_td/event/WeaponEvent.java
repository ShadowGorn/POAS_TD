/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.event;

import java.util.EventObject;
import poas_td.model.weapons.Weapon;

/**
 *
 * @author nik95_000
 */
public class WeaponEvent extends EventObject {
    
    Weapon _weapon;

    public WeaponEvent(Object o) {
        super(o);
    }
    
    public void set_weapon(Weapon weapon) {
        _weapon = weapon;
    }
    
    public Weapon get_weapon() {
        return _weapon;
    }  
}
