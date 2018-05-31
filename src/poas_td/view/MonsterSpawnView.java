package poas_td.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poas_td.model.CellObject;
import poas_td.model.MonsterSpawn;

public class MonsterSpawnView extends ObjectView {

    private MonsterSpawn _monster_spawn;
    
    private Image _sprite;
    
    public MonsterSpawnView(Image i, CellObject o){
        _sprite = i;
        _monster_spawn = (MonsterSpawn) o;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(_sprite, (_monster_spawn.get_position().x() - 3)*16, (_monster_spawn.get_position().y() + 3)*16);
    }

    @Override
    public void setSprite(Image sprite) {
        _sprite = sprite;
    }

    @Override
    public CellObject ancestor() {
        return _monster_spawn;
    }
}
