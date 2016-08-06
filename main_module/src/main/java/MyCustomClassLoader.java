import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MyCustomClassLoader extends ClassLoader {

    private  static final String PATH = "c://";
    private  static final String PROTOCOL = "file:/";
    private  String packageName = "";
    private Map LoadedClasses = new HashMap<String, Class<?>>();

    public MyCustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    public MyCustomClassLoader() {
        this(MyCustomClassLoader.class.getClassLoader());
    }

    public MyCustomClassLoader(String packageName) {
        this(MyCustomClassLoader.class.getClassLoader());
        this.packageName = packageName;
    }

    public synchronized Class loadClass(String name) throws ClassNotFoundException {

        //check local cache
        Class result = (Class<?>)LoadedClasses.get(name);

        if (result != null) {
            System.out.println("% Class "+name+" found in cache");
            return result;
        }

        //if cache is empty - ask parent
        if(result == null){
            try {
                result = super.loadClass(name, false);
            } catch (ClassNotFoundException e) {

                //if still is empty - try to load manually
                if(result == null){
                    URLClassLoader loader = null;

                    try {

                        loader = URLClassLoader.newInstance(new URL[]{new URL(PROTOCOL + PATH + packageName)});

                    } catch (MalformedURLException e1) {

                        e1.printStackTrace();
                        throw new ClassNotFoundException();
                    }

                    result = loader.loadClass(name);
                    saveToCache (result);
                }
            }
        }

        return result;
    }

    private void saveToCache(Class result){
        LoadedClasses.put(result.getName(), result);
    }
}
