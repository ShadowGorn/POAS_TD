/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.model.weapons;

import poas_td.model.Tower;

/**
 * Рельсовое орудие
 * @author nik95_000
 */
public class Wpn_Rail extends Weapon {

    public Wpn_Rail (Tower tower) {
        super(tower);
        
        _level = 1;
        _rate = (float) 0.1;
        _range = (float) 32;
        _accuracy = (float) 0.3;
        _tower = tower;
        _ammo_damage = 100;
        _ammo_speed = 46;
        _ammo_radius = 0;
        _ammo_delay = 0;
        _ammo_type = "RAIL";
        _ammo_name = "Railgun";
    }
    
    public String get_name(){
        return _ammo_name;
    }
    
    public short cell_cost() {
        short cost = create_cost();

        for (int level = 2; level <= get_level(); level++) {
            cost += create_cost();
            cost += (Math.pow(get_level(), 1.2) * base_upgrade_cost());
        }

        return cost;
    }
    
    /**
     * Возвращает стоимость создания оружия
     * @return Стоимость создания
     */
    public static short create_cost() {
        return 100;
    }

    /**
     * Узнать стоимость улучшения
     *
     * @return Стоимость улучшения
     */
    @Override
    public short upgrade_cost() {
        if(get_level() < get_max_level()) {
            return (short)(create_cost() + Math.pow(get_level(),1.2)* 80);
        } else {
            return 0;
        }
    }

    /**
     * Улучшение оружия на основе текущих характеристик
     *
     * @return Стоимость улучшения
     */
    @Override
    public short upgrade() {
        super.upgrade();
        if (get_level() < get_max_level()) {
            _rate += 0.14;
            _ammo_damage -= 2;
            _accuracy += 0.08; 
        }
        
        return upgrade_cost();
    }
    
    @Override
    public short get_max_level() {
        return 5;
    }
}
