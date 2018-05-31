/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poas_td.model.monsters;

/**
 *
 * @author nik95_000
 */
public class Mon_Pony extends Monster {

    public Mon_Pony(boolean rare) {
        super((short)(rare ? 19 : 12),
              (short)(0),
              (float)(rare ? 2 : 1.5),
              (short)(rare ? 35 : 15),
              (short)(rare ? 4 : 2));     
    }
}