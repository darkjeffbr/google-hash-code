import java.util.Scanner;
import java.io.*;
import java.util.*;
/*
6 2 7
1 2 3 6 5 4
5 2 2
0 1 2 3 4
4 3 1
0 2 3 5
*/
public class Main {
    public static void main(String[] args) throws Exception {

        //String[] files = {"a_example.txt","b_read_on.txt","c_incunabula.txt","d_tough_choices.txt","e_so_many_books.txt","f_libraries_of_the_world.txt"};
        String[] files = {"c_incunabula.txt","d_tough_choices.txt","e_so_many_books.txt","f_libraries_of_the_world.txt"};

        for(String file: files){
        Scanner scanner = new Scanner(new File(file));
        
        String firstLine = scanner.nextLine();
        String[] line = firstLine.split(" ");
        int booksAmount = Integer.valueOf(line[0]);
        int libreriesAmount = Integer.valueOf(line[1]);
        int daysTotal = Integer.valueOf(line[2]);

        //Leer valor de libros
        String linesOfBooks = scanner.nextLine();
        String[] bookLine = linesOfBooks.split(" ");
        int[] booksCollection = new int[booksAmount];
        for(int i = 0; i < booksAmount; i++){
            booksCollection[i] = Integer.valueOf(bookLine[i]);
        }

        BookStore[] bookStores = new BookStore[libreriesAmount];

        for(int i = 0; i < libreriesAmount; i++){

            String bookStoreFirstLine = scanner.nextLine();
            String bookStoreSecondLine = scanner.nextLine();

            String[] splitedBookStoreFirstLine = bookStoreFirstLine.split(" ");
            String[] splitedBookStoreSecondLine = bookStoreSecondLine.split(" ");

            int[] books = new int[Integer.valueOf(splitedBookStoreFirstLine[0])];
            int delay = Integer.valueOf(splitedBookStoreFirstLine[1]);
            int booksPerDay = Integer.valueOf(splitedBookStoreFirstLine[2]);

            for(int j = 0; j < books.length; j++){
                books[j] = Integer.valueOf(splitedBookStoreSecondLine[j]);
            }

            bookStores[i] = new BookStore(i,books,delay,booksPerDay,booksCollection);
        }

        // Odenar librerias por valorTotal
        Arrays.sort(bookStores, new Comparator<BookStore>(){
            public int compare(BookStore b1, BookStore b2){
                int valueBooks1 = b1.totalValue(booksCollection);
                int valueBooks2 = b2.totalValue(booksCollection);
                if(valueBooks1 == valueBooks2){
                    return 0;
                }else if(valueBooks1 < valueBooks2){
                    return 1;
                }else{
                    return -1;
                }
            }
        });

        for(int i = 0; i < bookStores.length; i++){
            System.out.println("Bookstore with id:" +  bookStores[i].id + " has value: " + bookStores[i].totalValue);
        }

        PrintStream ps = new PrintStream(new FileOutputStream(new File(file + ".out")));
        //calcular numero de librerias
        int bookStoresAmount = 0;
        int accDelay = 0;
        for(int i = 0; i < bookStores.length; i++){
            accDelay += bookStores[i].delay;
            if(accDelay > daysTotal){
                break;
            }else{
                bookStoresAmount++;
            }
        }
        ps.println(bookStoresAmount);
        accDelay = 0;
        for(int i = 0; i < bookStoresAmount; i++){
            accDelay += bookStores[i].delay;
            int remainingDays = daysTotal - accDelay;
                 int shippedBooks = remainingDays * bookStores[i].booksPerDay;
                 if(shippedBooks > bookStores[i].books.length){
                    shippedBooks = bookStores[i].books.length;
                 }
                 ps.println(bookStores[i].id + " " + shippedBooks);
                 for(int j = 0; j  < shippedBooks - 1; j++){
                     ps.print(bookStores[i].books[j] + " ");
                 }
                 ps.println(bookStores[i].books[shippedBooks - 1]);
        }
        
        }
    }
}


class BookStore{

    int id;
    int[] books;
    int delay;
    int booksPerDay;
    int totalValue;

    public BookStore(int id, int[] books, int delay, int booksPerDay, int[] booksCollection){
        this.id = id;
        this.books = books;
        this.delay = delay;
        this.booksPerDay = booksPerDay;
        Arrays.sort(this.books);
        this.books = this.reverseArray(this.books);
        this.totalValue = this.totalValue(booksCollection);

    }

    int totalValue(int[] booksCollection){
        int value = 0;
        //System.out.println("Bookstore with id:" +  this.id +" :" );
        for(int i = 0; i < this.books.length; i++){
            value += booksCollection[this.books[i]];
        }
        //System.out.println("Calculated value " +  value);
        //System.out.println("Books per day: " +  booksPerDay);
        return value / this.booksPerDay;
    }


    int[] reverseArray(int[] validData){
    for(int i = 0; i < validData.length / 2; i++){
        int temp = validData[i];
        validData[i] = validData[validData.length - i - 1];
        validData[validData.length - i - 1] = temp;
    }
    return validData;
    }
}


