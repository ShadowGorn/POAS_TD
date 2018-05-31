/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.event;

import java.util.EventObject;
import poas_td.model.monsters.Monster;

/**
 * Событие, связанное с монстром
 * @author nik95_000
 */
public class MonsterEvent extends EventObject {

    Monster _monster;
    
    public void set_monster(Monster m) {
        _monster = m;
    }
    
    public Monster get_monster() {
        return _monster;
    }
    
    public MonsterEvent(Object o) {
        super(o);
    }   
}
