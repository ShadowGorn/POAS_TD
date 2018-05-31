package poas_td.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import javafx.scene.image.Image;
import javafx.util.Pair;
import poas_td.model.monsters.*;

public class GameData {

    private Position _castlePosition;
    private Position _monsterSpawnPosition;
    
    private ArrayList<Position> _road;
    private ArrayList<Position> _towerPositions;
    private ArrayList<ArrayList<Pair<Class,Boolean>>> _waves;
    private ArrayList<Integer> _wave_times;
    private HashMap<String,Image> _monsters;
    private HashMap<String,Image> _weapons;
    private Image _good_castle,_bad_castle,_map;

    public GameData() throws FileNotFoundException {
        _road = new ArrayList<>();
        _towerPositions = new ArrayList<>();
        _waves = new ArrayList<>();
        _wave_times = new ArrayList<>();
        _monsters = new HashMap<>();
        _weapons = new HashMap<>();
        
        read();
    }
    
    private void read() throws FileNotFoundException {
        
        File file = new File("resources/gamedata.txt");
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }

        Scanner sc = new Scanner(file);
        sc.useDelimiter(Pattern.compile("(\\p{Space})+"));

            while (sc.hasNext()) {
                String s = sc.next();
                
                if("castle".equals(s)) {
                    _castlePosition = read_position(sc);
                } else if("monsterspawn".equals(s)) {
                    _monsterSpawnPosition = read_position(sc);
                } else if("road".equals(s)) {
                    int count = sc.nextInt();
                    for(int i=0; i<count; ++i) {
                        _road.add(read_position(sc));
                    }
                } else if("towers".equals(s)) {
                    int count = sc.nextInt();
                    for(int i=0; i<count; ++i) {
                        _towerPositions.add(read_position(sc));
                    }
                } else if("wave_times".equals(s)) {
                    int count = sc.nextInt();
                    for(int i=0; i<count; ++i) {
                        _wave_times.add(sc.nextInt());
                    }
                } else if("waves".equals(s)) {
                    int count = sc.nextInt();
                    for(int i=0; i<count; ++i) {
                        _waves.add(new ArrayList<>());
                        int amount = sc.nextInt();
                        for(int j=0; j<amount; ++j) {
                            
                            String monster = sc.next();
                            
                            Class m_c = Monster.class;
                            if(null != monster)
                               switch (monster) {
                                case "Mon_Batman":
                                    m_c = Mon_Batman.class;
                                    break;
                                case "Mon_Cheat":
                                    m_c = Mon_Cheat.class;
                                    break;
                                case "Mon_Creepy":
                                    m_c = Mon_Creepy.class;
                                    break;
                                case "Mon_FBI":
                                    m_c = Mon_FBI.class;
                                    break;
                                default:
                                    m_c = Mon_Pony.class;
                                    break;
                            }
                                    
                            boolean strong = sc.nextInt() != 0;
                           
                            _waves.get(i).add(new Pair(m_c,strong));
                        }
                    }
                } else if("monsters".equals(s)) {
                    int count = sc.nextInt();
                    for(int i=0; i<count; ++i) {
                        String monster = sc.next();
                        String filename = sc.next();
                        
                        Image img = new Image(new File(filename).toURI().toString());
                        
                        _monsters.put(monster, img);
                    }
                } else if("weapons".equals(s)) {
                    int count = sc.nextInt();
                    for(int i=0; i<count; ++i) {
                        String weapon = sc.next();
                        String filename = sc.next();
                        
                        Image img = new Image(new File(filename).toURI().toString());
                        _weapons.put(weapon, img);
                    }
                } else if("good_castle".equals(s)) {
                    String filename = sc.next();                       
                    _good_castle = new Image(new File(filename).toURI().toString());
                } else if("bad_castle".equals(s)) {
                    String filename = sc.next();                       
                    _bad_castle = new Image(new File(filename).toURI().toString());
                } else if("map".equals(s)) {
                    String filename = sc.next();                       
                    _map = new Image(new File(filename).toURI().toString());
                }
            }

    }
    
    private Position read_position(Scanner sc) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        return new Position(x,y);
    }

    public Position load_castle_position() {
        return _castlePosition;
    }

    public Position load_monster_spawn_positon() {
        return _monsterSpawnPosition;
    }

    public ArrayList<Position> load_road_positions() {
        return _road;
    }

    public ArrayList<Position> load_tower_positions() {
        return _towerPositions;
    }
    
    ArrayList<Integer> load_wave_times() {
        return _wave_times;
    }
    
    ArrayList<ArrayList<Pair<Class,Boolean>>> load_waves() {
        return _waves;
    }

    public int load_start_gold() {
        return 100;
    }

    public short load_start_castle_health() {
        return 100;
    }
    
    public Image load_monster(String type) {
        return _monsters.get(type);
    }
    
     public Image load_weapon(String type) {
        return _weapons.get(type);
    }
     
    public Image load_good_castle() {
        return _good_castle;
    }
    
    public Image load_bad_castle() {
        return _bad_castle;
    }
    
    public Image load_map() {
        return _map;
    }
}
