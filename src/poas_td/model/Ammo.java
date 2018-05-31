package poas_td.model;

import java.util.ArrayList;
import java.util.Random;

import poas_td.model.monsters.Monster;
import poas_td.event.AmmoEvent;
import poas_td.event.AmmoListener;

import static java.lang.Math.ceil;

public class Ammo extends CellObject {
    
    protected final short _damage;

    protected final float _accuracy;

    protected final float _speed;

    protected final short _radius;

    protected final float _delay;

    protected final Position _aim_position;

    protected int _tics;
    
     protected String _type;

    public boolean is_missed() {
        return _is_missed;
    }

    protected boolean _is_missed;

    /**
     * Взрывается, если время вышло
     */
    public void explode() {
        if (--_tics <= 0) {
            
            if (!_is_missed) {
                ArrayList<CellObject> monsters = super._field.get_objects(Monster.class, _aim_position, (short) (_radius+1));

                monsters.stream().forEach((monster) -> {
                    ((Monster) monster).hit(_damage);
                });
            }

            exploded();
        }
    }

    /**
     *
     * @param from - start position of ammo
     * @param to - final position of ammo
     * @param damage - damage of ammo
     * @param accuracy - accuracy of ammo
     * @param speed - speed of ammo
     * @param radius - damage raius of ammo
     * @param delay - time before ammo explodes
     */
    public Ammo(Position from, Position to, short damage, 
            float accuracy, float speed, short radius, 
            float delay, String type) {
        super.set_position(from);
        _aim_position = to;
        _damage = damage;
        _accuracy = accuracy;
        _speed = speed;
        _radius = radius;
        _delay = delay;
        _tics = (int) ceil((from.distance_to(to) * (GameModel.game_model().sec_to_tics()/speed)));
        _type = type;
        _is_missed = new Random().nextDouble() > _accuracy;

        super._field.add_object(get_position(), this);
    }

    public short get_damage() {
        return _damage;
    }

    public float get_speed() {
        return _speed;
    }

    public short get_radius() {
        return _radius;
    }

    public float get_delay() {
        return _delay;
    }

    public Position get_aim_position() {
        return _aim_position;
    }

    @Override
    public Position get_position() {
        return super.get_position();
    }

    protected ArrayList<AmmoListener> _listener_list = new ArrayList<>();

    public void add_ammo_listener(AmmoListener l) {
        _listener_list.add(l);
    }

    public void remove_ammo_listener(AmmoListener l) {
        _listener_list.remove(l);
    }

    protected void exploded() {
        AmmoEvent e = new AmmoEvent(this);
        e.set_ammo(this);

        for (AmmoListener listener : _listener_list) {
            listener.exploded(e);
        }
    }
    
    public String get_type() {
        return _type;
    }
}
