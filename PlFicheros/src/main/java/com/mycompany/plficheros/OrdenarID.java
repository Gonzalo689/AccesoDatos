
package com.mycompany.plficheros;

import java.util.Comparator;

/**
 *
 * @author Gonzalo
 */
public class OrdenarID implements Comparator<Fichero>{

    @Override
    public int compare(Fichero f1, Fichero f2) {
        if (f1.getId() == f2.getId()) 
            return 0;
        else if(f1.getId()>f2.getId())
            return 1;
        else
            return -1;
    }
    
}
