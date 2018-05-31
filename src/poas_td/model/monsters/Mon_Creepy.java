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
public class Mon_Creepy extends Monster {

    public Mon_Creepy(boolean rare) {
        super((short)(rare ? 60 : 7),
              (short)(rare ? 60 : 0),
              (float)(2),
              (short)(rare ? 100 : 75),
              (short)(rare ? 20 : 8));     
    }
}
