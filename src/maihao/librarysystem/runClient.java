package maihao.librarysystem;

import maihao.librarysystem.library.BlackListHandler;
import maihao.librarysystem.library.Library;
import maihao.librarysystem.library.LoaderShelf;
import maihao.librarysystem.usersSystem.RegistryHandler;
import java.util.Scanner;

public class runClient {
    public static void main(String[] args) {

        RegistryHandler.onRegistryAdm();//固定注册管理员
        System.out.println("LibrarySystem: " + Config.SYSTEM_VERSION);
        System.out.println(Config.INITIAL_INTERFACE);

        System.out.println(Library.baseBookShelfLibrary.getTotalNum());
        Scanner scanner = new Scanner(System.in);

        boolean isLogin = false;
        while (!isLogin) {
            System.out.println(Config.REGISTER);
            int choice0 = scanner.nextInt();

            if (choice0 == 1) {
                System.out.println(Config.LOGIN_NAME);
                String registryName = scanner.next();//注册用户名
                System.out.println(Config.LOGIN_PASSWORD);
                String registryPassword = scanner.next();//注册用户密码
                if (RegistryHandler.userNameSearch(registryName)) {
                    System.out.println(Config.HAS_REGISTERED);
                } else {
                    RegistryHandler.onRegistry(registryName, registryPassword);//写入注册表
                    System.out.println(Config.REGISTRY_SUCCESSFULLY);
                    System.out.println("恭喜你成为第 " + RegistryHandler.userHashMap.size() + " 名用户！");
                }
            } else if (choice0 == 2) {
                isLogin = true;
            } else if (choice0 == 3) {
                System.out.println(Config.ADMINISTRATOR_LOGIN);//管理员登录
                String administratorName = scanner.next();
                String administratorPassword = scanner.next();
                if (RegistryHandler.administratorSearch(administratorName, administratorPassword)) {//匹配注册表
                    System.out.println(Config.ADMINISTRATOR_LOGIN_SUCCESSFULLY);//登陆成功

                    boolean adminIsFinished = false;
                    while(!adminIsFinished) {
                        System.out.println(Config.ADMINISTRATOR_CHOICE);
                        System.out.println(Config.ADMINISTRATOR_1);
                        System.out.println(Config.ADMINISTRATOR_2);
                        System.out.println(Config.ADMINISTRATOR_3);
                        System.out.println(Config.ADMINISTRATOR_4);
                        int choice1 = scanner.nextInt();
                        if (choice1 == 1) {
                            System.out.println(Config.ADMINISTRATOR_1_CHOICE);
                            int choice2 = scanner.nextInt();
                            if (choice2 == 1) {//上架
                                LoaderShelf.shelfLoad();
                            } else if (choice2 == 2) {//下架
                                LoaderShelf.shelfDownload();
                            }
                        } else if (choice1 == 2) {
                            System.out.println(Config.ADMINISTRATOR_2_CHOICE);
                            int choice2 = scanner.nextInt();
                            if (choice2 == 1) {//新建
                                LoaderShelf.newBook();
                            } else if (choice2 == 2) {//删除
                                LoaderShelf.deleteBook();
                            }
                        } else if (choice1 == 3) {//设置黑名单
                            System.out.println(Config.ADMINISTRATOR_3_CHOICE);
                            int choice2 = scanner.nextInt();
                            if (choice2 == 1) {//添加黑名单
                                BlackListHandler.addBlackList();
                            } else if (choice2 == 2) {//排除黑名单
                                BlackListHandler.removeBlackList();
                            }
                        } else if (choice1 == 4) {
                            adminIsFinished = true;
                        }
                    }
                } else {
                    System.out.println(Config.LOGIN_FAILED);//管理员登录失败
                }
            }

        }


        System.out.println(Config.LOGIN_NAME);
        String loginName = scanner.next();//输入登录名
        System.out.println(Config.LOGIN_PASSWORD);
        String loginPassword = scanner.next();//输入登录密码

        if (RegistryHandler.userSearch(loginName, loginPassword) && RegistryHandler.getUser(loginName, loginPassword).getPrivilegeLevel() == 1) {//匹配注册表
            System.out.println(Config.LOGIN_SUCCESSFULLY);//登录成功
            boolean isEnd = false;
            while (!isEnd) {

                System.out.println(Config.USER_CHOICE);

                int choice1 = scanner.nextInt();
                if (choice1 == 1) {//还书

                    System.out.println(Config.USER_ALL_BOOKS);
                    RegistryHandler.getUser(loginName, loginPassword).scanBackPack();//查询已借书
                    System.out.println(Config.SEARCH_BY_TYPE);
                    int choice2 = scanner.nextInt();
                    if (choice2 == 1) {
                        System.out.println(Config.SEARCH_BY_STRING_TYPE);
                        System.out.println(Config.BOOK_TYPE_COMIC);
                        System.out.println(Config.BOOK_TYPE_NOVEL);
                        System.out.println(Config.BOOK_TYPE_PROGRAMMING);
                        String type = scanner.next();
                        RegistryHandler.getUser(loginName, loginPassword).scanBackPackByType(type);//分类查询
                    }

                    System.out.println(Config.RETURN_BOOK_TAG);
                    int choice2Tag = scanner.nextInt();
                    RegistryHandler.getUser(loginName, loginPassword).returnBookToLibrary(choice2Tag);//还书操作

                    System.out.println(Config.ASK_USER1);//是否需要继续操作
                    int choice3 = scanner.nextInt();
                    if (choice3 == 1) {
                        isEnd = false;
                    } else if (choice3 == 2) {
                        isEnd = true;
                    }
                } else if (choice1 == 2) {
                    //图书馆查询
                    System.out.println(Config.LIBRARY_ALL_BOOKS);
                    Library.scanAllBookOnAllShelf();
                    System.out.println(Config.LIBRARY_SEARCH_BY_TYPE);//分类查询

                    int choice2 = scanner.nextInt();
                    if (choice2 == 1) {
                        System.out.println(Config.SEARCH_BY_STRING_TYPE);
                        System.out.println(Config.BOOK_TYPE_COMIC);
                        System.out.println(Config.BOOK_TYPE_NOVEL);
                        System.out.println(Config.BOOK_TYPE_PROGRAMMING);
                        String type = scanner.next();
                        Library.scanBookWithType(type);
                    }
                    System.out.println(Config.LIBRARY_SEARCH_BY_SHELF);//指定书架

                    int choice3 = scanner.nextInt();
                    if (choice3 == 1) {
                        System.out.println(Config.LIBRARY_SHELF_TAG);
                        int shelfTag = scanner.nextInt();
                        Library.scanBookByShelfTag(shelfTag);
                    }
                    //借书
                    System.out.println(Config.BORROW_BOOK);
                    System.out.println(Config.LIBRARY_SHELF_TAG);
                    int libraryShelfTag = scanner.nextInt();
                    System.out.println(Config.LIBRARY_BOOK_TAG);
                    int libraryBookTag = scanner.nextInt();
                    RegistryHandler.getUser(loginName, loginPassword).addBackPackBook(Library.getBookFromLibrary(libraryShelfTag, libraryShelfTag));
                    Library.removeBookFromLibrary(libraryShelfTag, libraryBookTag);
                    System.out.println(Config.BORROW_SUCCESSFULLY);//借书成功

                    System.out.println(Config.USER_ALL_BOOKS);
                    RegistryHandler.getUser(loginName, loginPassword).scanBackPack();

                    System.out.println(Config.ASK_USER1);
                    int choice4 = scanner.nextInt();
                    if (choice4 == 1) {
                        isEnd = false;
                    } else if (choice4 == 2) {
                        isEnd = true;
                    }
                }
            }
        } else if (RegistryHandler.userSearch(loginName, loginPassword) && RegistryHandler.getUser(loginName, loginPassword).getPrivilegeLevel() == 0) {
            System.out.println(Config.IN_BLACKLIST);//黑名单用户
        } else {
            System.out.println(Config.LOGIN_FAILED);//登录失败
        }
    }
}
