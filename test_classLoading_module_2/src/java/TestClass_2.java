public class TestClass_2 implements Test {

    static {
           System.out.println("");
           System.out.println("TestClass_2 -> loaded");
           System.out.println("");
       }

       @Override
       public void sayHello() {
           System.out.println("");
           System.out.println("Hello from TestClass_2");
           System.out.println("");
       }
}
