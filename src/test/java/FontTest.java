import java.awt.*;

/**
 * Created by phenotype on 27-06-2017.
 */
public class FontTest {


    public static void main(String[] args) {
        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }
    }

}

