package poas_td.model.monsters;

import java.util.ArrayList;
import poas_td.event.MonsterEvent;
import poas_td.event.MonsterListener;
import poas_td.model.Castle;
import poas_td.model.CellObject;
import poas_td.model.GameModel;

public abstract class Monster extends CellObject {

    protected short _health;

    protected short _max_health;

    protected short _max_armor;

    protected short _armor;

    protected float _speed;

    protected short _gold;

    protected short _damage;

    protected int _tics;

    protected String _status;

    protected Monster(short health, short armor, float speed, short gold, short damage) {
        super();
        _health = health;
        _max_health = health;
        _armor = armor;
        _max_armor = armor;
        _speed = speed;
        _gold = gold;
        _damage = damage;
        _tics = (int) (GameModel.game_model().sec_to_tics()/speed);
        _status = "live";
    }

    public short get_health() {
        return _health;
    }

    public short get_max_health() {
        return _max_health;
    }

    public short get_max_armor() {
        return _max_armor;
    }

    public short get_armor() {
        return _armor;
    }

    public float get_speed() {
        return _speed;
    }

    public short cost() {
        return _gold;
    }

    public void hit(short damage) {
        _armor -= damage;
        if (_armor < 0) {
            _health += _armor;
            _armor = 0;
        }
        if (_health <= 0) {
            die("explode");
        }
    }

    /**
     * Шаг монстра
     */
    public void move() {
        if (--_tics <= 0) {
            set_position(super._field.road().next(_position));

            monster_moved();
            
            _tics = (int) (GameModel.game_model().sec_to_tics()/_speed);
        }
    }

    /**
     * Смерть монстра
     *
     * @param reason Причина смерти
     */
    public void die(String reason) {

        _status = "die from".concat(reason);

        monster_died();
        
        GameModel.game_model().treasury().add_gold(cost());

        super._field.delete_object(_position, this);
    }

    /**
     * Монстр столкнулся с замком
     *
     * @param castle Замок
     */
    public void impact(Castle castle) {
        castle.hit(_damage);
    }

    public String get_status() {
        return _status;
    }

    protected ArrayList<MonsterListener> _listener_list = new ArrayList<>();

    public void add_monster_listener(MonsterListener l) {
        _listener_list.add(l);
    }

    public void remove_monster_listener(MonsterListener l) {
        _listener_list.remove(l);
    }

    protected void monster_moved() {
        MonsterEvent e = new MonsterEvent(this);
        e.set_monster(this);

        for (MonsterListener listener : _listener_list) {
            listener.monster_moved(e);
        }
    }

    protected void monster_died() {
        MonsterEvent e = new MonsterEvent(this);
        e.set_monster(this);

        for (MonsterListener listener : _listener_list) {
            listener.monster_died(e);
        }
    }
}
