/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.event;

import java.util.EventListener;

/**
 *
 * @author nik95_000
 */
public interface MonsterListener extends EventListener {
    void monster_moved(MonsterEvent e);
    
    void monster_died(MonsterEvent e);
}
