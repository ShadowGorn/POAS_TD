package poas_td.model;

import java.util.ArrayList;

public class Road {

    private ArrayList<Position> _positions;
    
    public Road (ArrayList<Position> positions) {
        _positions = positions;
    }

    public Position next(Position pos) {
        if (_positions.contains(pos)) {
            int index = _positions.indexOf(pos);
            if( ++index >= _positions.size())
                return new Position(0, 0);
            return _positions.get(index);
        } else {
            return new Position(0, 0);
        }
    }

    public Position prev(Position pos) {
        if (_positions.contains(pos)) {
            int index = _positions.indexOf(pos);
            if (--index == -1) {
                index = _positions.size() - 1;
            }
            return _positions.get(index);
        } else {
            return new Position(0, 0);
        }
    }

    public Position after(Position from, int number) {
        if (_positions.contains(from)) {
            int index = _positions.indexOf(from);
            if (index + number >= _positions.size() || index + number < 0) {
                return new Position(0, 0);
            }
            return _positions.get(index);
        } else {
            return new Position(0, 0);
        }
    }

    public Position closest_to_end(ArrayList<Position> positions) {
        int maxPos = 0;
        for (Position pos : positions) {
            if (_positions.indexOf(pos) > _positions.indexOf(positions.get(maxPos))) {
                maxPos = positions.indexOf(pos);
            }
        }
        return positions.get(maxPos);
    }

}
