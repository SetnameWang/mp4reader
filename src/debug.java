import java.text.SimpleDateFormat;
import java.util.Date;

public class debug {
    private boolean debug = true;

    public debug(){}

    public debug(boolean debug){
        if(!debug) this.debug = false;
    }

    public void debug(String info){
        if (debug){
            String output = "[%s]:%s";

            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            System.out.println(String.format(output, ft.format(date), info));
        }
    }
}
