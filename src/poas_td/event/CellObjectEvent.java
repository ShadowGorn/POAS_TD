/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.event;

import java.util.EventObject;
import poas_td.model.CellObject;

/**
 *
 * @author nik95_000
 */
public class CellObjectEvent extends EventObject {
    
    CellObject _object;

    public CellObjectEvent(Object o) {
        super(o);
    }
    
    public void set_object(CellObject object) {
        _object = object;
    }
    
    public CellObject get_object () {
        return _object;
    }
}
