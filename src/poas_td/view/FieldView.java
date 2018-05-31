package poas_td.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poas_td.model.CellObject;
import poas_td.model.monsters.Monster;

public class FieldView extends ObjectView {
    
    private Image _sprite;
    
    public FieldView(Image i){
        _sprite = i;
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(_sprite, 0, 0);
    }

    @Override
    public void setSprite(Image sprite) {
        _sprite = sprite;
    }

    @Override
    public CellObject ancestor() {
        return new CellObject();
    }
}
