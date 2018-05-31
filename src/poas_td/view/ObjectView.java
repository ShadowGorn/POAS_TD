package poas_td.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poas_td.model.CellObject;

public abstract class ObjectView {
        private Image _sprite;
        public abstract CellObject ancestor();
        public abstract void setSprite(Image sprite);
	public abstract void draw(GraphicsContext gc);
}
