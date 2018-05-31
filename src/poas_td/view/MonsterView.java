package poas_td.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import poas_td.model.CellObject;
import poas_td.model.monsters.Monster;

import static java.lang.Math.max;

public class MonsterView extends ObjectView {

    private Monster _monster;

    private Image _sprite;
    
    public MonsterView(Image i, CellObject o){
        _sprite = i;
        _monster = (Monster) o;
    }

    @Override
    public void setSprite(Image sprite) {
        _sprite = sprite;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(_sprite, (_monster.get_position().x()-1)*16, (_monster.get_position().y()-1)*16);

        double health = _monster.get_health() / (double)_monster.get_max_health();
        double armor = _monster.get_armor() / (double)max(1, _monster.get_max_armor());

        gc.setFill(Color.RED);
        gc.fillRect((_monster.get_position().x()-1)*16, (_monster.get_position().y()-1)*16 - 2, 16*health, 2);
        gc.setFill(Color.GRAY);
        gc.fillRect((_monster.get_position().x()-1)*16, (_monster.get_position().y()-1)*16 - 4, 16*armor, 2);
    }

    @Override
    public CellObject ancestor() {
        return _monster;
    }
}
