/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.event;

import java.util.EventListener;

/**
 *
 * @author nik95_000
 */
public interface CellObjectListener extends EventListener {
    public void new_object_on_field (CellObjectEvent e);
    
    public void object_removed_from_field (CellObjectEvent e);
}
