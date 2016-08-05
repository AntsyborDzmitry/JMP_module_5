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
        System.out.println("choose option -> ");

        String line = sc.nextLine();


        return  line;
    }

    private static boolean loadClass(String option){

        switch (option){
            case "1":
                loadFirstClass();
                return true;
            case "2":
                loadSecondClass();
                return true;
            default:
                System.out.println("wrong option data, try again ... ");
                return false;
        }
    }

    private static void loadFirstClass(){
        try {

            URLClassLoader loader = URLClassLoader.newInstance(new URL[]{new URL("file:/c://test_classLoading_module_1.jar")});
            Class cls = loader.loadClass("TestClass_1");
            callMain(cls);

        } catch (MalformedURLException | ClassNotFoundException  e) {
           e.printStackTrace();
        }
    }
    private static void loadSecondClass(){
            try {

                URLClassLoader loader = URLClassLoader.newInstance(new URL[]{new URL("file:/c://test_classLoading_module_2.jar")});
                Class cls = loader.loadClass("TestClass_2");
                callMain(cls);

            } catch (MalformedURLException | ClassNotFoundException  e) {
               e.printStackTrace();
            }
        }
    private static void callMain (Class cls){
        try{
            Method method = cls.getMethod("main", String[].class);
            String[]params = new String[2];
            method.invoke(null, (Object) params);
        }catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
           e.printStackTrace();
        }
    }
}