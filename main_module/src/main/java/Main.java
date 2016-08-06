import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

/**
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line = callMenu(sc);
        while(!loadClass(line)){
            line = callMenu(sc);
        };

        sc.close();
    }

    private static String callMenu (Scanner sc){

        System.out.println(" ----------------------------------------------------------- ");
        System.out.println("|   [1]    - load class from test_classLoading_module_1.jar |");
        System.out.println("|                                                           |");
        System.out.println("|   [2]    - load class from test_classLoading_module_2.jar |");
        System.out.println(" ----------------------------------------------------------- ");
        System.out.println();
        System.out.println("choose option ... ");
        System.out.println();

        String line = sc.nextLine();


        return  line;
    }

    private static boolean loadClass(String option){

        switch (option){
            case "1":
                loadMyClass("test_classLoading_module_1.jar","TestClass_1");
                return true;
            case "2":
                loadMyClass("test_classLoading_module_2.jar","TestClass_2");
                return true;
            default:
                System.out.println("wrong option data, try again ... ");
                return false;
        }
    }

    private static void loadMyClass(String path, String name){

        try {
            MyCustomClassLoader loader = new MyCustomClassLoader(path);

            //case1 -> load from  MyCustomClassLoader
            Class cls = loader.loadClass(name);

            Test test = (Test)cls.newInstance();
            test.sayHello();

            //case2 -> load from  MyCustomClassLoader cache (use unique principle)
            /*
                Class cls1 = loader.loadClass(name);
                Test test1 = (Test)cls.newInstance();
                test1.sayHello();
            */

            //case3 -> load native class (use delegation principle)
            /*
                Class cls2 = loader.loadClass("java.lang.String");
            */

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
           e.printStackTrace();
        }
    }
}