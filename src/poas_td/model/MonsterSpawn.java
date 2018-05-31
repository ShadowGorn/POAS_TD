package poas_td.model;

import java.util.ArrayList;
import javafx.util.Pair;
import poas_td.model.monsters.*;

public class MonsterSpawn extends CellObject {

	private short _wave;
        
        private short _monster_number;
        
        private short _waves_count;

	private int _tics;
        
        private int _tics_to_end_wave;
        
        private ArrayList<ArrayList<Pair<Class,Boolean>>> _waves;
        
        private ArrayList<Integer> _wave_times;
        
        public MonsterSpawn(ArrayList<ArrayList<Pair<Class,Boolean>>> waves , ArrayList<Integer> wave_times) {
            _waves_count = (short) waves.size();
            _waves = waves;
            _wave_times = wave_times;
            _wave = 0;
            _monster_number = 0;
            _tics = 10;
            _tics_to_end_wave = 0;
        }

	public Monster create_monster() {
            if(_wave >= _waves.size()) {
                return null;
            }
                   
            --_tics_to_end_wave;
            if(_tics_to_end_wave < 0) {
                _tics_to_end_wave = GameModel.game_model().sec_to_tics()*_wave_times.get(_wave) + _waves.get(_wave).size()*tics_for_monster();
                _tics = 0;
            }
            
            if(--_tics <= 0) {
                
                if(_monster_number <_waves.get(_wave).size()) {
                Pair<Class,Boolean> next = _waves.get(_wave).get(_monster_number);
                
                Monster next_m;
                
                if(next.getKey() == Mon_Batman.class)
                    next_m = new Mon_Batman(next.getValue());
                else if(next.getKey() == Mon_Cheat.class)
                    next_m = new Mon_Cheat(next.getValue());
                else if(next.getKey() == Mon_Creepy.class)
                    next_m = new Mon_Creepy(next.getValue());
                else if(next.getKey() == Mon_FBI.class)
                    next_m = new Mon_FBI(next.getValue());
                else
                    next_m = new Mon_Pony(next.getValue());
                
                _field.add_object(_position, next_m);
                
                _tics = tics_for_monster();
                _monster_number++;
                return next_m;  
                } else if (_tics_to_end_wave <= 0){
                    _wave++;
                    _monster_number = 0;
                    
                }
            }
            return null;
	}
        
        private short tics_for_monster() {
            return (short) GameModel.game_model().sec_to_tics();
        }

	public void waves_depleted() {
            GameModel.game_model().waves_depleted();
	}
        
        public int time_to_end_of_wave() {
            return (int) (GameModel.game_model().tic_to_sec()*_tics_to_end_wave - 0.5) ;
        }

}
