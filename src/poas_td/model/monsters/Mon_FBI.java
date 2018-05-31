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
public class Mon_FBI extends Monster {

   public Mon_FBI(boolean rare) {
        super((short)(rare ? 35 : 21),
              (short)(rare ? 2 : 0),
              (float)0.5,
              (short)(rare ? 20 : 10),
              (short)(rare ? 4 : 2));     
    }
}
