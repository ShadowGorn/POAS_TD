package poas_td.model;

import java.io.FileNotFoundException;
import poas_td.model.weapons.Weapon;
import poas_td.model.monsters.Monster;
import java.util.ArrayList;
import poas_td.view.GUI;

public class GameModel {

    private Field _field;

    private Treasury _treasury;

    private boolean _waves_depleted;

    private boolean _monsters_depleted;

    private boolean _castle_destroyed;

    private GameData _game_data;

    private static GameModel _game_model;

    private GameModel() {
        try {
            _game_data = new GameData();
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        _treasury = new Treasury();
        _field = new Field(_game_data.load_road_positions(),
                           _game_data.load_waves(),
                           _game_data.load_wave_times(),
                           _game_data.load_castle_position(),
                           _game_data.load_monster_spawn_positon());

        _waves_depleted = false;
        _monsters_depleted = false;
        _castle_destroyed = false;
    }
    
    public void place_towers() {
        _field.castle().set_position(_game_data.load_castle_position());
        _field.monster_spawn().set_position(_game_data.load_monster_spawn_positon());
        
        for (Position pos : _game_data.load_tower_positions()) {
            _field.add_object(pos, new Tower());
        }
    }

    private static final float tic = (float) 0.05; // How long is 1 tic

    public static GameModel game_model() {
        if (_game_model == null) {
            _game_model = new GameModel();
        }
        return _game_model;
    }

    public Treasury treasury() {
        return _treasury;
    }

    public Field field() {
        return _field;
    }

    public GameData game_data() {
        return _game_data;
    }

    public int sec_to_tics() {
        return (int) (1 / tic );
    }
    
    public float tic_to_sec() {
        return tic;
    }

    /**
     * Начало игры
     */
    public void start() {
        short start_health = game_data().load_start_castle_health();
        field().castle().set_health(start_health);

        treasury().set_gold(game_data().load_start_gold());

        ArrayList<CellObject> towers = field().get_objects(Tower.class);

        for (CellObject tower : towers) {
            ((Tower) tower).set_weapon(new Weapon((Tower) tower));
        }
    }

    public boolean step() {
        field().monster_spawn().create_monster();

        ArrayList<CellObject> monsters = field().get_objects(Monster.class);

        _monsters_depleted = monsters.isEmpty();

        for (CellObject monster : monsters) {
            ((Monster) monster).move();
        }

        ArrayList<CellObject> towers = field().get_objects(Tower.class);

        for (CellObject tower : towers) {
            ((Tower) tower).shoot();
        }

        ArrayList<CellObject> ammos = field().get_objects(Ammo.class);

        for (CellObject ammo : ammos) {
            ((Ammo) ammo).explode();
        }
        
        return !(_castle_destroyed || (_waves_depleted && _monsters_depleted));
    }
    
    public boolean get_result() {
        return !_castle_destroyed;
    }

    public void monsters_depleted() {
        _monsters_depleted = true;
    }

    public void waves_depleted() {
        _waves_depleted = true;
    }

    public void castle_destroyed() {
        _castle_destroyed = true;
    }
}
