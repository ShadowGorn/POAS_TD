package poas_td.view;

import java.sql.Time;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import poas_td.event.AmmoEvent;
import poas_td.event.AmmoListener;
import poas_td.model.Ammo;
import poas_td.model.CellObject;

public class AmmoView extends ObjectView {
    
    private long _spawn_time;

    private Ammo _ammo;

    private Image _sprite;
    
    public AmmoView(Image s, CellObject o){
        _spawn_time = System.currentTimeMillis();
        _ammo = (Ammo) o;
        _sprite = s;
    }

    @Override
    public void draw(GraphicsContext gc) {
        int _start_pos_x = _ammo.get_position().x()*16;
        int _start_pos_y = _ammo.get_position().y()*16;
        int _target_pos_x = _ammo.get_aim_position().x()*16;
        int _target_pos_y = _ammo.get_aim_position().y()*16;

        // Railgun
        if (_ammo.get_type() == "RAIL") {
            gc.setFill(Color.LIME);
            gc.setStroke(Color.LIME);
            gc.setLineWidth(3);
            gc.strokeLine(_start_pos_x, _start_pos_y, _target_pos_x, _target_pos_y);
        } // Machinegun
        else {
            long life_time = (System.currentTimeMillis() - _spawn_time)/50;      // Период жизни с момента создания
            // Текущая координата
            int cur_x = _start_pos_x;
            int cur_y = _start_pos_y;

            // Вычисление проекции скорости на оси X и Y
            double current_distance;
            double target_distance = Math.sqrt(Math.pow(_start_pos_x - _target_pos_x, 2) + Math.pow(_start_pos_y - _target_pos_y, 2));
            double y_g = (double) (_start_pos_y - _target_pos_y) / target_distance;
            double x_g = (double) (_start_pos_x - _target_pos_x) / target_distance;
            float speed_x = (float) (_ammo.get_speed() * x_g);
            float speed_y = (float) (_ammo.get_speed() * y_g);

            // Нахождение текущей позиции снаряда
            cur_x += -speed_x * life_time;
            cur_y += -speed_y * life_time;

            current_distance = Math.sqrt(Math.pow(_start_pos_x - cur_x, 2) + Math.pow(_start_pos_y - cur_y, 2));

            if (current_distance < target_distance) {
                if (_ammo.get_type() == "AUTO") {
                    gc.setFill(Color.YELLOW);
                } else if (_ammo.get_type() == "GRNDE") {
                    gc.setFill(Color.GREEN);
                } else if (_ammo.get_type() == "RCKT") {
                    gc.setFill(Color.RED);
                } else {
                    gc.setFill(Color.ORANGE);
                }
                gc.fillOval(cur_x, cur_y, 5, 5);
                gc.setStroke(Color.BLACK);
                gc.strokeOval(cur_x, cur_y, 5, 5);
            }
        }
    }

    @Override
    public void setSprite(Image sprite) {
        _sprite = sprite;
    }

    @Override
    public CellObject ancestor() {
        return _ammo;
    }
}
