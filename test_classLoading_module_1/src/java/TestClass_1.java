public class TestClass_1 implements Test {

    static {
        System.out.println(" ");
        System.out.println("static block -> TestClass_1  loaded");
        System.out.println(" ");
    }

    @Override
    public void sayHello() {
        System.out.println(" ");
        System.out.println("Hello from instance of TestClass_1");
        System.out.println(" ");
    }
}
