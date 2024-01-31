import java.util.LinkedList;
import java.util.List;

public class Library {
    
    private int id;
    private String address;
    private List<Book> books;

    
    public Library(){
        books = new LinkedList<>();
    }
    
    public Library(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int sizebooks(){
        return books.size();
    }
    public Book getbook(int i){
        return books.get(i);
    }
    public void addbook(Book b){
        books.add(b);
    }

    @Override
    public String toString() {
        return "Library [id=" + id + ", address=" + address + ", books=" + books + "]";
    }

    
    
}
