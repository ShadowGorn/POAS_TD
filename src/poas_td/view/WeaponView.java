package poas_td.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poas_td.model.CellObject;
import poas_td.model.Tower;
import poas_td.model.weapons.Weapon;

public class WeaponView extends ObjectView {

    private Weapon _weapon;
    
    private Image _sprite;
    
    public WeaponView(Image i, CellObject o){
        _sprite = i;
        _weapon = ((Tower)o).get_weapon();
    }

    @Override
    public void setSprite(Image sprite) {
        _sprite = sprite;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(_sprite, (_weapon.tower().get_position().x()-1)*16, 
                              (_weapon.tower().get_position().y()-1)*16);
    }

    @Override
    public CellObject ancestor() {
        return _weapon.tower();
    }
}
