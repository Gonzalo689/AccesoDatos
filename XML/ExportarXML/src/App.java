import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;

public class App {
    public LinkedList<Proveedor> proveedores = new LinkedList<Proveedor>();
    public LinkedList<Producto> productos = new LinkedList<Producto>();
    public void cargarDatos(){
        Producto p1 = new Producto(1, "Cementos", 12, "EUR");
        Producto p2 = new Producto(2, "Ladrillos", 15, "EUR"); 
        Producto p3 = new Producto(3, "Tejas", 20, "EUR"); 
        Producto p4 = new Producto(7, "Cigalas", 12, "JPY"); 
        Producto p5 = new Producto(9, "Langostinos", 1, "EUR");  
        Producto p6 = new Producto(10, "Rape", 2, "GBR"); 
        Producto p7 = new Producto(11, "Almejas", 3, "JPY"); 
        Producto p8 = new Producto(12, "Bote de Gritos", 50, "EUR");
        productos.add(p1);productos.add(p2);productos.add(p3);productos.add(p4);
        productos.add(p5);productos.add(p6);productos.add(p7);productos.add(p8);

        Proveedor pr1 = new Proveedor(1, "Copinsa", "España");
        pr1.aniadirProducto(p1);
        pr1.aniadirProducto(p2);
        pr1.aniadirProducto(p3);
        Proveedor pr2 = new Proveedor(2, "Mariscos Recio", "España");
        pr2.aniadirProducto(p4);
        pr2.aniadirProducto(p5);
        pr2.aniadirProducto(p6);
        pr2.aniadirProducto(p7);
        Proveedor pr3 = new Proveedor(1, "Copinsa", "España");
        pr3.aniadirProducto(p8);
        proveedores.add(pr3); proveedores.add(pr1);proveedores.add(pr2);

    }

    public void crearStringXML() {
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n<examen>\n";
        xml += "\t<productos>\n"; 
        for (Producto producto : productos) {
            xml+= "\t\t<producto id='"+producto.getId()+"'>\n";
            xml+= "\t\t\t<nombre>"+producto.getNombre()+"</nombre>\n";
            xml+= "\t\t\t<precio moneda='"+producto.getMoneda()+"'>"+producto.getPrecio()+"</precio>\n";
            xml+= "\t\t</producto>\n";
        }
        xml += "\t</productos>\n"; 
        xml += "\t<proveedores>\n"; 
        for (Proveedor prov : proveedores) {
            xml+= "\t\t<proveedor id='"+prov.getId()+"'>\n";
            xml+= "\t\t\t<nombre>"+prov.getNombre()+"</nombre>\n";
            xml+= "\t\t\t<pais>"+prov.getPais()+"</pais>\n";
            xml+= "\t\t\t<productos>\n";
            for (Producto product : prov.getProductos()) {
                xml+= "\t\t\t\t<producto>"+product.getId()+"</producto>\n";
            }
            xml+= "\t\t\t</productos>\n";
            xml += "\t\t</proveedor>\n";
        }
        xml += "\t</proveedores>\n"; 
        xml += "</examen>";
        crearXML(xml);
    }
    public void crearXML(String xml) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("examen.xml"));
            bw.write(xml);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        App a = new App();
        a.cargarDatos();
        a.crearStringXML();
        
    }
}
