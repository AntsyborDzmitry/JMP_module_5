public class TestClass_2 implements Test {

    static {
        System.out.println("TestClass_2 -> loaded");
    }

    @Override
    public void sayHello() {
        System.out.println("Hello from TestClass_2");
    }
}
