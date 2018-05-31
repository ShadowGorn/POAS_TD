/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poas_td.model.weapons;

import poas_td.model.Tower;

/**
 * Гранатомет
 *
 * @author nik95_000
 */
public class Wpn_Grenade extends Weapon {

    public Wpn_Grenade(Tower tower) {
        super(tower);

        _level = 1;
        _rate = (float) 0.15;
        _range = 5;
        _accuracy = (float) 0.8;
        _tower = tower;
        _ammo_damage = 4;
        _ammo_speed = 1;
        _ammo_radius = 2;
        _ammo_delay = 3;
        _ammo_type = "GRNDE";
        _ammo_name = "Grenade launcher";
    }

    public String get_name() {
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
     *
     * @return Стоимость создания
     */
    public static short create_cost() {
        return 30;
    }

    /**
     * Узнать стоимость улучшения
     *
     * @return Стоимость улучшения
     */
    @Override
    public short upgrade_cost() {
        if (get_level() < get_max_level()) {
            return (short) (create_cost() + Math.pow(get_level(), 1.2) * 20);
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
            _ammo_damage += 2;
            _accuracy -= 0.01;
            _range += 0.5;
            _ammo_radius += 0.4;
        }

        return upgrade_cost();
    }

    @Override
    public short get_max_level() {
        return 10;
    }
}
