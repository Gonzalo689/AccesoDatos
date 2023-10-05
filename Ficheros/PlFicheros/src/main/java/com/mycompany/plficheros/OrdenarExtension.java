
package com.mycompany.plficheros;

import java.util.Comparator;

/**
 *
 * @author Gonzalo
 */
public class OrdenarExtension implements Comparator<Fichero>{

    @Override
    public int compare(Fichero f1, Fichero f2) {
        return f1.getExtension().compareToIgnoreCase(f2.getExtension());
    }

    
    
}
