package poas_td.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import poas_td.event.CellObjectEvent;
import poas_td.event.CellObjectListener;
import poas_td.event.WeaponEvent;
import poas_td.event.WeaponListener;
import poas_td.model.Ammo;
import poas_td.model.CellObject;
import poas_td.model.GameModel;
import poas_td.model.Position;
import poas_td.model.Tower;
import poas_td.model.monsters.Monster;
import poas_td.model.weapons.Weapon;
import poas_td.model.weapons.Wpn_Auto;
import poas_td.model.weapons.Wpn_Grenade;
import poas_td.model.weapons.Wpn_Rocket;
import poas_td.model.weapons.Wpn_Rail;

public class GUI {

    private final int _FPS = 20;

    private ArrayList<ObjectView> _objects;

    private GameModel _model;

    private boolean weapon_changes;

    private FieldView _field_view;
    private CastleView _castle_view;
    private MonsterSpawnView _monster_spawn_view;

    private Canvas _backgroundCanvas;
    private GraphicsContext _backgroundGC;
    private ImageView _wheel_btn;

    private Image _health;
    private Image _coin;

    private Canvas _monstersCanvas;
    GraphicsContext _monstersGC;

    private Canvas _towersCanvas;
    GraphicsContext _towersGC;

    private Canvas _ammoCanvas;
    GraphicsContext _ammoGC;

    private Canvas _GUICanvas;
    GraphicsContext _GUIGC;

    StackPane _root;
    Timeline _timeline;

    public GUI(Stage primaryStage) {
        this.weapon_changes = true;
        _objects = new ArrayList<>();

        _model = GameModel.game_model();
        _model.field().add_cellobject_listener(new GUI_listener());

        _model.place_towers();
        _model.start();

        _field_view = new FieldView(_model.game_data().load_map());
        _castle_view = new CastleView(_model.game_data().load_good_castle(), _model.field().castle());
        _monster_spawn_view = new MonsterSpawnView(_model.game_data().load_bad_castle(),
                _model.field().monster_spawn());

        _backgroundCanvas = new Canvas(512, 576);
        _backgroundGC = _backgroundCanvas.getGraphicsContext2D();

        draw_map();

        _coin = new Image(new File("resources/coin.gif").toURI().toString());
        _health = new Image(new File("resources/health.gif").toURI().toString());
        // То самое колёсико настроек и прикручивание к нему события на нажатие кнопки мыши
        Image wheel = new Image(new File("resources/wheel.gif").toURI().toString());
        _wheel_btn = new ImageView();
        _wheel_btn.setImage(wheel);
        _wheel_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            System.out.println("Hello World!");
        });
        _monstersCanvas = new Canvas(512, 512);
        _monstersGC = _monstersCanvas.getGraphicsContext2D();

        _towersCanvas = new Canvas(512, 512);
        _towersGC = _towersCanvas.getGraphicsContext2D();

        _ammoCanvas = new Canvas(512, 512);
        _ammoGC = _ammoCanvas.getGraphicsContext2D();

        _GUICanvas = new Canvas(512, 512);
        _GUIGC = _GUICanvas.getGraphicsContext2D();

        _root = new StackPane();
        Scene scene = new Scene(_root, 512, 576);

        _root.getChildren().add(_backgroundCanvas);
        _root.getChildren().add(_monstersCanvas);
        _root.setAlignment(_monstersCanvas, Pos.BOTTOM_CENTER);

        _root.getChildren().add(_towersCanvas);
        _root.setAlignment(_towersCanvas, Pos.BOTTOM_CENTER);

        _root.getChildren().add(_ammoCanvas);
        _root.setAlignment(_ammoCanvas, Pos.BOTTOM_CENTER);

        _root.getChildren().add(_wheel_btn);

        _root.getChildren().add(_GUICanvas);
        _root.setAlignment(_GUICanvas, Pos.TOP_CENTER);

        _GUICanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            System.out.printf("Click at: %f %f\n", event.getX() / 16 + 1, event.getY() / 16 - 3);

            ArrayList res = _model.field().get_objects(Tower.class, new Position((int) event.getX() / 16 + 1, (int) event.getY() / 16 - 3), (short) 1);
            boolean isNotEmpty = !res.isEmpty();

            if (isNotEmpty) {
                Weapon wpn = ((Tower) res.get(0)).get_weapon();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(wpn.get_name());
                alert.setHeaderText(wpn.get_name() + "(lvl." + wpn.get_level()
                        + ").\nRate:" + wpn.get_rate()
                        + "; Dmg: " + wpn.get_damage()
                        + "; Accr: " + wpn.get_accuracy() + "%");
                alert.setContentText("Choose your option.");
                
                ButtonType btn_cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                
                if (wpn.get_level() == 0) {
                    ButtonType btn_auto = new ButtonType("Buy machinegun($" + Wpn_Auto.create_cost() + ")");
                    ButtonType btn_grnde = new ButtonType("Buy grenade launcher($" + Wpn_Grenade.create_cost() + ")");
                    ButtonType btn_rckt = new ButtonType("Buy rocket launcher($" + Wpn_Rocket.create_cost() + ")");
                    ButtonType btn_rail = new ButtonType("Buy railgun($" + Wpn_Rail.create_cost() + ")");
                    alert.getButtonTypes().setAll(btn_auto, btn_grnde, btn_rckt, btn_rail, btn_cancel);
                    
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == btn_auto) {
                        wpn.tower().set_weapon(new Wpn_Auto(wpn.tower()));
                        System.out.printf("You set machinegun\n");
                    } else if (result.get() == btn_grnde) {
                        wpn.tower().set_weapon(new Wpn_Grenade(wpn.tower()));
                        System.out.printf("You set grenade launcher\n");
                    } else if (result.get() == btn_rckt) {
                        wpn.tower().set_weapon(new Wpn_Rocket(wpn.tower()));
                        System.out.printf("You set rocket launcher\n");
                    } else if (result.get() == btn_rail) {
                        wpn.tower().set_weapon(new Wpn_Rail(wpn.tower()));
                        System.out.printf("You set railgun\n");
                    } else {
                        System.out.printf("NU LADN))))\n");
                    }
                } else {
                    ButtonType btn_upgr = new ButtonType("Upgrade ($" + wpn.upgrade_cost() + ")");
                    ButtonType btn_sell = new ButtonType("Sell ($" + wpn.cell_cost() + ")");

                    alert.getButtonTypes().setAll(btn_upgr, btn_sell, btn_cancel);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == btn_upgr && wpn.upgrade_cost() <= _model.treasury().get_gold()) {
                        wpn.upgrade();
                        System.out.printf("You have updgraded your tower\n");
                    } else if (result.get() == btn_sell) {
                        wpn.sell();
                        System.out.printf("You have sold your tower\n");
                    } else {
                        System.out.printf("NU LADN))))\n");
                    }
                }
            }
        });

        _root.setAlignment(_wheel_btn, Pos.TOP_RIGHT);
        _root.setMargin(_wheel_btn, new Insets(21, 21, 21, 21));

        primaryStage.setTitle("Tower Defence!");
        primaryStage.setScene(scene);
        primaryStage.show();

        final Duration oneFrameAmt = Duration.millis(1000 / _FPS);
        final KeyFrame oneFrame; // oneFrame
        oneFrame = new KeyFrame(oneFrameAmt,
                (EventHandler) event -> {
                    //for (int i = 0; i < 5; ++i) {
                    if (!_model.step()) {
                        _timeline.stop();
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Game over");
                        alert.setHeaderText("Game finished");
                        alert.setContentText("You lose.");

                        ButtonType btn_ok = new ButtonType("Ok", ButtonData.OK_DONE);
                        alert.getButtonTypes().setAll(btn_ok);
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    draw_scene();
                    //}
                });

        _timeline = new Timeline(oneFrame);
        _timeline.setCycleCount(Timeline.INDEFINITE);
        _timeline.play();
    }

    public void draw_map() {
        _field_view.draw(_backgroundGC);
        _castle_view.draw(_backgroundGC);
        _monster_spawn_view.draw(_backgroundGC);
    }

    public void draw_scene() {
        drawMonsters(_monstersGC);
        if (weapon_changes) {
            drawTowers(_towersGC);
            weapon_changes = false;
        }
        drawAmmo(_ammoGC);
        drawGUI(_GUIGC);
    }

    private void drawMonsters(GraphicsContext gc) {
        gc.clearRect(0, 0, 512, 512);
        for (Object o : _objects) {
            if (o instanceof MonsterView) {
                ((MonsterView) o).draw(gc);
            }
        }
    }

    private void drawTowers(GraphicsContext gc) {
        gc.clearRect(0, 0, 512, 512);
        for (Object o : _objects) {
            if (o instanceof WeaponView) {
                ((WeaponView) o).draw(gc);
            }
        }
    }

    private void drawAmmo(GraphicsContext gc) {
        gc.clearRect(0, 0, 512, 512);
        for (Object o : _objects) {
            if (o instanceof AmmoView) {
                ((AmmoView) o).draw(gc);
            }
        }
    }

    private void drawGUI(GraphicsContext gc) {
        gc.clearRect(0, 0, 512, 100);

        // Draw castle health
        gc.drawImage(_health, 7, 17);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("System", 12));
        gc.fillText(Integer.toString(_model.field().castle().get_health()), 25, 28);

        // Draw coin count
        gc.drawImage(_coin, 7, 32);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("System", 12));
        gc.fillText(Integer.toString(_model.treasury().get_gold()), 25, 45);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("System", 12));
        gc.fillText("Next wave in: " + Integer.toString(_model.field().monster_spawn().time_to_end_of_wave()), 100, 28);
    }

    private class GUI_listener implements CellObjectListener {

        @Override
        public void new_object_on_field(CellObjectEvent e) {
            CellObject o = e.get_object();

            if (o.getClass() == Tower.class) {
                ((Tower) o).add_weapon_listener(new GUI_weapon_listener());
            } else if (o instanceof Monster) {
                String type = o.getClass().toString();
                type = type.substring(type.lastIndexOf(".") + 1);
                _objects.add(new MonsterView(_model.game_data().load_monster(type), o));
            } else if (o instanceof Ammo) {
                String type = ((Ammo) o).get_type();
                _objects.add(new AmmoView(_model.game_data().load_weapon("Wpn_Rocket"), o));
            }
        }

        @Override
        public void object_removed_from_field(CellObjectEvent e) {
            CellObject o = e.get_object();
            for (int i = 0; i < _objects.size(); i++) {
                if (((ObjectView) _objects.get(i)).ancestor() == o) {
                    _objects.remove(i);
                    break;
                }
            }
        }

    }

    private class GUI_weapon_listener implements WeaponListener {

        @Override
        public void weapon_changed(WeaponEvent e) {
            CellObject o = e.get_weapon().tower();
            for (int i = 0; i < _objects.size(); i++) {
                if (((ObjectView) _objects.get(i)).ancestor() == o) {
                    _objects.remove(i);
                    break;
                }
            }
            String type = e.get_weapon().getClass().toString();
            type = type.substring(type.lastIndexOf(".") + 1);
            _objects.add(new WeaponView(_model.game_data().load_weapon(type), o));

            weapon_changes = true;
        }

    }
}
