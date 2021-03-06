/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.model.weapons;

import poas_td.model.Tower;

/**
 * Ракетомет
 * @author nik95_000
 */
public class Wpn_Rocket extends Weapon {

    public Wpn_Rocket (Tower tower) {
        super(tower);
        
        _level = 1;
        _rate = (float)0.5;
        _range = (float)8;
        _accuracy = (float) 0.9;
        _tower = tower;
        _ammo_damage = 2;
        _ammo_speed = 10;
        _ammo_radius = 0;
        _ammo_delay = 0;
        _ammo_type = "RCKT";
        _ammo_name = "Rocket launcher";
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
    
    /*
     * Возвращает стоимость создания оружия
     * @return Стоимость создания
     */
    public static short create_cost() {
        return 35;
    }

    /**
     * Узнать стоимость улучшения
     *
     * @return Стоимость улучшения
     */
    @Override
    public short upgrade_cost() {
        if(get_level() < get_max_level()) {
            return (short)(create_cost() + Math.pow(get_level(),1.2)* 25);
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
            _rate -= 0.01;
            _ammo_damage += 3;
            _accuracy -= 0.02;
            _ammo_radius += 0.3; 
        }
        
        return upgrade_cost();
    }
    
    @Override
    public short get_max_level() {
        return 10;
    }
}
