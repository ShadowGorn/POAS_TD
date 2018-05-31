package poas_td.model;

import poas_td.model.monsters.Monster;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.util.Pair;
import poas_td.event.AmmoEvent;
import poas_td.event.AmmoListener;
import poas_td.event.CellObjectEvent;
import poas_td.event.CellObjectListener;
import poas_td.event.MonsterEvent;
import poas_td.event.MonsterListener;

public class Field {

    private TreeMap<Position, ArrayList<CellObject>> _objects;

    private MonsterSpawn _monster_spawn;

    private Castle _castle;
    
    private Road _road;
    
    public Field(ArrayList<Position> road,
                 ArrayList<ArrayList<Pair<Class,Boolean>>> waves,
                 ArrayList<Integer> wave_time,
                 Position castle_position, Position monster_spawn_position) {
        _objects = new TreeMap<>();
        
        _castle = new Castle();
        
        _monster_spawn = new MonsterSpawn(waves,wave_time);
               
        _road = new Road(road);       
    }

    public Road road() {
        return _road;
    }

    public MonsterSpawn monster_spawn() {
        return _monster_spawn;
    }

    public Castle castle() {
        return _castle;
    }

    /**
     *
     * @param position
     * @param object
     */
    public void add_object(Position position, CellObject object) {
        
        clear_add_object(position,object);
        
        if(object instanceof Monster)
            ((Monster)object).add_monster_listener(new FieldMonsterListener());
        else if(object instanceof Ammo)
            ((Ammo)object).add_ammo_listener(new FieldAmmoListener());
        
        new_object_on_field(object);
    }
    
    private void clear_add_object(Position position, CellObject object) {
        
        object.set_position(position);
        
        ArrayList<CellObject> pos_objects = _objects.get(position);
        if(pos_objects == null)
            pos_objects = new ArrayList<>();
        pos_objects.add(object);
            
        _objects.put(position, pos_objects);
    }

    public void delete_object(Position position, CellObject cell_object) {
        //if (_objects.containsKey(position) && _objects.get(position).contains(cell_object)) {   
            clear_delete_object(position,cell_object);
            object_removed_from_field(cell_object);
        //}
    }
    
    private void clear_delete_object(Position position, CellObject cell_object) { 
            
        ArrayList<CellObject> pos_objects = _objects.get(position);

        pos_objects.remove(cell_object);
        
        _objects.put(position, pos_objects);
    }

    public ArrayList<CellObject> get_objects(Class type) {
        ArrayList<CellObject> result = new ArrayList<>();
        
        for (Position pos : _objects.keySet()) {
            for (CellObject object : _objects.get(pos)) {
                if (type.isInstance(object)) {
                    result.add(object);
                }
            }
        }
        
        return result;
    }

    public ArrayList<CellObject> get_objects(Class type, Position pos, short radius) {
        ArrayList<CellObject> result = new ArrayList<>();
        
        for (Position posit : _objects.keySet()) {
            for (CellObject object : _objects.get(posit)) {
                if (type.isInstance(object) && 
                        object.get_position().distance_to(pos) <= radius) {
                    result.add(object);
                }
            }
        }
 
        return result;
    }

    public void monsters_depleted() {
        GameModel.game_model().monsters_depleted();
    }

    private ArrayList<CellObjectListener> _listener_list = new ArrayList<>();

    public void add_cellobject_listener(CellObjectListener l) {
        _listener_list.add(l);
    }

    public void remove_cellobject_listener(CellObjectListener l) {
        _listener_list.remove(l);
    }
    
    public void new_object_on_field (CellObject object) {
        CellObjectEvent e = new CellObjectEvent(this);
        e.set_object(object);

        for (CellObjectListener listener : _listener_list) {
            listener.new_object_on_field(e);
        }
    }
    
    public void object_removed_from_field (CellObject object) {
        CellObjectEvent e = new CellObjectEvent(this);
        e.set_object(object);

        for (CellObjectListener listener : _listener_list) {
            listener.object_removed_from_field(e);
        }
    }

    private class FieldAmmoListener implements AmmoListener {

        @Override
        public void exploded(AmmoEvent e) {
            delete_object(e.get_ammo().get_position(),e.get_ammo());
        }
    }
    
    private class FieldMonsterListener implements MonsterListener {

        @Override
        public void monster_moved(MonsterEvent e) {
            Monster monster = e.get_monster();
            Position castle_pos = castle().get_position();
            Position monster_pos = monster.get_position();

            clear_delete_object(road().prev(monster.get_position()),monster);
            
            if (castle_pos.equals(monster_pos)) {
                monster.impact(_castle);
                monster.die("impact with castle");

                if (get_objects(Monster.class).isEmpty()) {
                    monsters_depleted();
                }
            } else {
                clear_add_object(monster.get_position(),monster);
            }
        }

        @Override
        public void monster_died(MonsterEvent e) {
            object_removed_from_field(e.get_monster());
        }    
    }
}
