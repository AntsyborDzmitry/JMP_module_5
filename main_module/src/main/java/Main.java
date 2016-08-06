import org.apache.log4j.Logger;
import java.util.Scanner;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        log.info(" Start ");
        Scanner sc = new Scanner(System.in);
        String line = callMenu(sc);

        while(!loadClass(line)){
            line = callMenu(sc);
        };

        sc.close();
        log.info(" End ");
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

        return sc.nextLine();
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
            if (cls != null){
                Test test = (Test)cls.newInstance();
                test.sayHello();
            }


            //case2 -> load from  MyCustomClassLoader cache (use unique principle)

                /*Class cls1 = loader.loadClass(name);
                if (cls != null){
                    Test test1 = (Test)cls.newInstance();
                    test1.sayHello();
                }*/


            //case3 -> load native class (use delegation principle)
            /*
                Class cls2 = loader.loadClass("java.lang.String");
            */

        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }
}