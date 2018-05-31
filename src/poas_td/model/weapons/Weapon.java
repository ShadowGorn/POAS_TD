package poas_td.model.weapons;

import poas_td.model.Ammo;
import poas_td.model.GameModel;
import poas_td.model.Position;
import poas_td.model.Tower;

public class Weapon {

    protected short _level;

    protected float _rate;

    protected float _range;

    protected float _accuracy;

    protected String _ammo_type;

    protected String _ammo_name;

    // Башня, на которой находится оружие
    protected Tower _tower;

    // Параметры снарядов, испусаемых оружием
    protected short _ammo_damage;
    protected float _ammo_speed;
    protected short _ammo_radius;
    protected float _ammo_delay;

    public Weapon(Tower tower) {
        _level = 0;
        _rate = 1;
        _range = 3;
        _accuracy = (float) 0.7;
        _tower = tower;
        _ammo_damage = 1;
        _ammo_speed = 2;
        _ammo_radius = 0;
        _ammo_delay = 0;
        _ammo_type = "BASE";
        _ammo_name = "Base weapon";
    }
    
    public String get_name(){
        return _ammo_name;
    }

    public void shoot(Position pos) {
        Ammo ammo = new Ammo(_tower.get_position(), pos,
                _ammo_damage, _accuracy, _ammo_speed, _ammo_radius,
                _ammo_delay, _ammo_type);
    }

    public short upgrade() {
        _level++;
        GameModel.game_model().treasury().decrease_gold(upgrade_cost());
        return 0;
    }
    
    public static short create_cost() {
        return 0;
    }

    public short upgrade_cost() {
        return 0;
    }

    protected short base_upgrade_cost() {
        return 0;
    }

    public short cell_cost() {
        short cost = create_cost();

        for (int level = 2; level <= get_level(); level++) {
            cost += create_cost();
            cost += (Math.pow(get_level(), 1.2) * base_upgrade_cost());
        }

        return cost;
    }

    public short sell() {
        _tower.set_weapon(new Weapon(_tower));
        GameModel.game_model().treasury().add_gold(cell_cost());
        return cell_cost();
    }


    public short get_level() {
        return _level;
    }

    public short get_max_level() {
        return 0;
    }

    public float get_rate() {
        return _rate;
    }

    public float get_range() {
        return _range;
    }

    public float get_accuracy() {
        return _accuracy;
    }

    public Tower tower() {
        return _tower;
    }

    public float get_ammo_speed() {
        return _ammo_speed;
    }

    public float get_damage() {
        return _ammo_damage;
    }
}
