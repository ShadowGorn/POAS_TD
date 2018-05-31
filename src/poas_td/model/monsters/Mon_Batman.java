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
public class Mon_Batman extends Monster {

    public Mon_Batman(boolean rare) {
        super((short)(rare ? 27 : 15),
              (short)(rare ? 29 : 15),
              (float)(rare ? 1.25 : 0.75),
              (short)(rare ? 300 : 150),
              (short)(rare ? 4 : 3));     
    }
}