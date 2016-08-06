import org.apache.log4j.Logger;

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

    private static final Logger log = Logger.getLogger(MyCustomClassLoader.class);

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

    public synchronized Class loadClass(String name) {

        //check local cache
        Class result = (Class<?>)LoadedClasses.get(name);

        if (result != null) {
            log.info("Class " + name + " found in cache");
            return result;
        }


        if(result == null){

            //if cache is empty - ask parent
            try {
                result = super.loadClass(name, false);
                log.info("Class " + name + " was returned from parent class loader " + result.getClassLoader().getClass().getName());
            } catch (ClassNotFoundException e) {

                if(result == null){

                    //if still is empty - try to load manually
                    URLClassLoader loader = null;
                    try {

                        loader = URLClassLoader.newInstance(new URL[]{new URL(PROTOCOL + PATH + packageName)});
                        result = loader.loadClass(name);

                        if(result != null){
                           saveToCache (result);
                           log.info("Class "+name+" was loaded  manually  by " + result.getClassLoader().getClass().getName());
                        }
                    } catch (MalformedURLException | ClassNotFoundException e1) {

                        log.error("Class [" + name + "] and package [" + packageName + "] wasn't found");
                    }
                }
            }
        }
        if(result == null){
            log.error("Class [" + name + "] and package [" + packageName + "] wasn't found");
        }

        return result;
    }

    private void saveToCache(Class result){
        LoadedClasses.put(result.getName(), result);
    }
}
