public class TestClass_1 implements Test {

    static {
        System.out.println("TestClass_1 -> loaded");
    }

    @Override
    public void sayHello() {
        System.out.println("Hello from TestClass_1");
    }
}
