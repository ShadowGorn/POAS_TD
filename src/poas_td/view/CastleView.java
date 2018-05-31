package poas_td.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poas_td.model.Castle;
import poas_td.model.CellObject;

public class CastleView extends ObjectView {

    private Castle _castle;

    private Image _sprite;

    public CastleView(Image i, CellObject o) {
        _sprite = i;
        _castle = (Castle) o;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(_sprite, (_castle.get_position().x() - 3)*16, (_castle.get_position().y() - 1)*16);
    }

    @Override
    public void setSprite(Image sprite) {
        _sprite = sprite;
    }

    @Override
    public CellObject ancestor() {
        return _castle;
    }
}
