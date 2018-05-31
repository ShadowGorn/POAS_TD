package poas_td.model;

public class Position implements Comparable {
    
    private int _x;
    private int _y;
    
    public Position()
    {
        _x = 0;
        _y = 0;
    }
    
    public Position(int x, int y)
    {
        _x = x;
        _y = y;
    }
    
    public int x()
    {
        return _x;
    }
    
    public int y()
    {
        return _y;
    }
    
    @Override
    public boolean equals(Object other)
    {
        boolean res = other instanceof Position && 
                _x == ((Position)other).x() && _y == ((Position)other).y();
        return res;
    }
    
    public int distance_to(Position other)
    {
        return (int)Math.sqrt(Math.pow(_x - other.x(),2.0)+Math.pow(_y - other.y(),2.0) + 0.5);
    }

    @Override
    public int compareTo(Object t) { 
        if(_x == ((Position)t).x()) {
            if(_y == ((Position)t).y()) {
                return 0;
            } else if( _y < ((Position)t).y() ){
                return -1;
            } else {
                return 1;
            }
        } else if ( _x < ((Position)t).x()) {
            return -1;
        } else {
            return 1;
        }
    }
}
