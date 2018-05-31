package poas_td.model;

public class Castle extends CellObject {

    private short _health;
    
    public Castle() {
    }

    public void hit(short damage) {
        _health -= damage;
        if(_health <=0 )
            castle_destroyed();
    }

    public void set_health(short health) {
        _health = health;
    }

    public short get_health() {
        return _health;
    }
      
    public void castle_destroyed() {
           GameModel.game_model().castle_destroyed();
    }
}
