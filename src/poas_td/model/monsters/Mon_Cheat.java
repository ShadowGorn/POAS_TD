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
public class Mon_Cheat extends Monster {

    public Mon_Cheat(boolean rare) {
        super((short)(rare ? 45 : 30),
              (short)(rare ? 60 : 30),
              (float)(rare ? 2 : 1),
              (short)(rare ? 10 : 25),
              (short)(rare ? 10 : 7));     
    }
}