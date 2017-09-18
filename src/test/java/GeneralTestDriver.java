public class GeneralTestDriver {

    public static void main(String[] args) {

        String sample1 = "This is a very long string, it should be longer than the maximum, just to test what happens, it should be exactly 129 chars long.";
        String sample2 = "This is a very short string, it should be 56 chars long.";
        String sample3 = "This is a medium string, it is about a month old, and in characters its 75.";
        String sample4 = "This is a very short string.";

        drawMenuMessage(sample4);
    }

    private static void drawMenuMessage(String msg) {
        int maxLineSize = 60;
        int x = 80;
        int y = 260;
        int yLineInc = 35;
        int yBigInc = 70;

        if (msg.length() > maxLineSize) {
            String line = msg.substring(0, maxLineSize);

            int index = line.length();
            while (checkIfEndIsAChar(line, index)) index--;

            line = msg.substring(0, index);
            msg = msg.substring(line.length(), msg.length());

            System.out.println(line);

            /* Recursive call */
            drawMenuMessage(msg);
        } else {
            System.out.println(msg);
        }
    }

    private static boolean checkIfEndIsAChar(String line, int length) {
        char lastChar = line.charAt(length - 1);
        return (lastChar != ' ') && (lastChar != '\t') && (lastChar != '\n');
    }

}