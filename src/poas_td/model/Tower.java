package poas_td.model;

import java.util.ArrayList;
import poas_td.event.WeaponEvent;
import poas_td.event.WeaponListener;
import poas_td.model.monsters.Monster;
import poas_td.model.weapons.Weapon;

public class Tower extends CellObject {

    private Weapon _weapon;

    private int _tics;

    public Monster find_target() {
        ArrayList<CellObject> monsters = super._field.get_objects(Monster.class);
        
        ArrayList<Monster> targets = new ArrayList<>();
        ArrayList<Position> targets_pos = new ArrayList<>();
        for(CellObject target : monsters) {
            if(calculate_target_position((Monster)target).distance_to(_position) <= _weapon.get_range()) {
                targets.add((Monster)target);
                targets_pos.add(((Monster)target).get_position());
            }
        }
        
        if(targets.isEmpty())
            return null;
        
        Position closest = super._field.road().closest_to_end(targets_pos);
        int ind = targets_pos.indexOf(closest);
        
        return targets.get(ind);
    }

    /**
     * Улучшить оружие на башне
     */
    public void upgrade_weapon() {
        _weapon.upgrade();
    }

    /**
     * Сделать выстрел, если пришло время
     */
    public void shoot() {
        if (--_tics <= 0) {
            Monster target = find_target();

            if (target instanceof Monster && !GameModel.game_model().field().road()
                  .next(calculate_target_position(target)).equals(new Position(0,0))) {
                _weapon.shoot(calculate_target_position(target));
                
                _tics = (int) (GameModel.game_model().sec_to_tics()/_weapon.get_rate());
            }
        }
    }

    public Weapon get_weapon() {
        return _weapon;
    }

    public void set_weapon(Weapon weapon) {
        _weapon = weapon;
        _tics = (int) (1.0/_weapon.get_rate()*GameModel.game_model().sec_to_tics());
        weapon_changed();
    }

    private Position calculate_target_position(Monster monster) {
        Position m = monster.get_position();
        int monster_path_length = 0;
        
        while(!check_position(monster_path_length,monster.get_speed(),m) && 
                !super._field.road().next(m).equals(new Position(0,0))) {
            monster_path_length++;
            m = super._field.road().next(m);
        }
        check_position(monster_path_length,monster.get_speed(),m);
        return m;
    }

    private int tics_to_position(Position target, float ammo_speed) {
        return get_position().distance_to(target) * (int) (GameModel.game_model().sec_to_tics()/ammo_speed);
    }
    
    private boolean check_position(int monster_path_length, float monster_speed, Position m) { 
        int tics_speed = (int) (GameModel.game_model().sec_to_tics()/monster_speed);
        int min_tics_count = monster_path_length * tics_speed;
        int max_tics_count = (monster_path_length + 1) * tics_speed;
        
        int ammo_tics = tics_to_position(m,_weapon.get_ammo_speed());
        
        return ammo_tics >= min_tics_count && ammo_tics <= max_tics_count;
    }
    
    protected ArrayList<WeaponListener> _listener_list = new ArrayList<>();

    public void add_weapon_listener(WeaponListener l) {
        _listener_list.add(l);
    }

    public void remove_weapon_listener(WeaponListener l) {
        _listener_list.remove(l);
    }
    
    protected void weapon_changed() {
        WeaponEvent e = new WeaponEvent(this);
        e.set_weapon(_weapon);

        for (WeaponListener listener : _listener_list) {
            listener.weapon_changed(e);
        }
    }
}
