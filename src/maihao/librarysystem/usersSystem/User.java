package maihao.librarysystem.usersSystem;

import maihao.librarysystem.book.Book;
import maihao.librarysystem.bookshelf.BookShelfUser;
import maihao.librarysystem.library.Library;

public class User {

    private  int privilegeLevel;
    private BookShelfUser backPack = new BookShelfUser();

    public User(){
        this.privilegeLevel = 1;
    }

    public void addBackPackBook(Book book) {
        backPack.addBook(book);
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public void scanBackPack() {
        backPack.scanAllBooks();
    }

    public void scanBackPackByType(String type) {
        backPack.scanBooksByType(type);
    }

    public void returnBookToLibrary(int tag) {
        Book book = backPack.getBookByTag(tag - 1);
        backPack.removeBook(book);
//        System.out.println(backPack.getTotalNum());
        Library.baseBookShelfLibrary.addBook(book);
    }

}
