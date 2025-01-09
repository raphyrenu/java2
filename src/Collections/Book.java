package Collections;

import java.util.*;


/**
 * The class contains details of the Book id, name, author and price.
 * Books are sorted by implementing Comparable
 * interface and duplicates are removed by overriding
 * hashCode and equals methods from object class
 * @author RAPHAEL Born to code
 *
 * @see <a href='https://docs.oracle.com/cd/E11882_01/appdev.112/e11822/adobjint.htm'>Object</a>
 * @see Object#equals
 */

public class Book implements Comparable<Book> {
    /**
     * this be the ID of the book
     * @see Collections
     */


    private Integer id;
    /**
     * this be the name of the name of th book
     */
    private String name;
    /**
     * this be the author of the book
     */
    private String author;
    /**
     * this be the price of the book
     */
    private Integer price;

    public Book() {
    }


    /**
     *
     * @param id id of the book
     * @param name name of th book
     * @param author name of person write the book
     * @param price price of the book
     */
    public Book(int id, String name, String author, int price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return id of the book
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book { " +
                "id= " + this.id +
                ", name= '" + this.name + '\'' +
                ", author= '" + this.author + '\'' +
                ", price= " + this.price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && name.equals(book.name) && author.equals(book.author) && price.equals(book.price);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + name.hashCode() + author.hashCode() + price.hashCode();
    }

    @Override
    public int compareTo(Book o) {
        if (this.name.compareTo(o.name)!=0) {
            return this.name.compareTo(o.name);
        }
        if (this.author.compareTo(o.author)!=0) {
            return this.author.compareTo(o.author);
        }
        if (this.id.compareTo(o.id)!=0) {
            return this.id.compareTo(o.id);
        }
        return this.price.compareTo(o.price);
    }

    public static void main(String[] args) {
        Book book1 = new Book(1, "Phy", "Raphy", 500);
        Book book2 = new Book(2, "FastX", "Hatuma", 300);
        Book book3 = new Book(3, "All of us are dead", "Me", 700);
        Book book4 = new Book(1, "Phy", "Raphy", 500);
        Book book5 = new Book(1, "OOP with java", "Raphy", 500);

        Set<Book> books = new HashSet<Book>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.forEach(System.out::println);

//        List<Book> book = new LinkedList<Book>();
//        book.add(book1);
//        book.addFirst(book5);
//        book.addFirst(book2);
//        book.addLast(book3);
//        book.addLast(book4);
//
//        List<Book> book = new ArrayList<Book>();
//        book.add(book1);
//        book.addFirst(book5);
//        book.addFirst(book2);
//        book.addLast(book3);
//        book.addLast(book4);
// Collections.sort(book);
//
//        for(Book b:book){
//            System.out.println(b);
//        }
    }


}
