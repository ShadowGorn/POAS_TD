package poas_td.model;

public class CellObject {

    protected Position _position;

    protected Field _field;

    public Position get_position() {
        return _position;
    }

    public void set_position(Position pos) {
        _field = GameModel.game_model().field();
        _position = pos;
    }
}
