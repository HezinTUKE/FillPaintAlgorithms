/*

If you can improve the code write on e-mail : vova.gezin@gmail.com , write to SUBJECT "FILL IMAGE PAINT !!!"

Author : Vova Hezin

*/


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedImage image = ImageIO.read(new File("C://Users//Vova//Desktop//bg.jpg"));

        //Position of chosen pixel
        Point p = new Point();
        p.x = 1; p.y = 1;

        long startTime = System.nanoTime();

        //color that we will change
        int oldColor = image.getRGB(p.x,p.y);
        fillImageFast(image, p, oldColor);

        long stopTime = System.nanoTime();

        System.out.println("Speed of fillImage = " + TimeUnit.SECONDS.convert((stopTime - startTime),  TimeUnit.NANOSECONDS) + " seconds");

    }

    //recursive algorithm (S, N, W, E)
    public static void fillImageR(BufferedImage image, Point p, int oldColor){
        int color = Color.GREEN.getRGB();

        if(oldColor == color) return;

        if(p.x < image.getWidth() && p.x >= 0 && p.y < image.getHeight() && p.y >= 0 && image.getRGB(p.x, p.y) == oldColor ){
            try {
                image.setRGB(p.x, p.y, color);
                fillImageR(image, new Point(p.x - 1, p.y), oldColor);
                fillImageR(image, new Point(p.x + 1, p.y), oldColor);
                fillImageR(image, new Point(p.x, p.y - 1), oldColor);
                fillImageR(image, new Point(p.x, p.y + 1), oldColor);
            }catch (Exception e){}
        }

        //Sava image on computer
        try {
            ImageIO.write(image, "jpg",  new File("C://Users//Vova//Desktop//bg123.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //not recursive algorithm (S, W, E, N)
    public static void fillImageWR(BufferedImage image, Point p, int oldColor){
        int color = Color.RED.getRGB();
        ArrayList<Integer> stack = new ArrayList<>();

        if(oldColor == color) return;

        stack.add(p.x); stack.add(p.y);

        while (stack.size() != 0){
            int[] n = {stack.get(0), stack.get(1)};

            for(int i = 0; i < 2; i++) stack.remove(0);

            if(image.getRGB(n[0], n[1]) == oldColor){
                image.setRGB(n[0], n[1], color);
                try {
                    if (0 >= n[0] - 1 && image.getRGB(n[0] - 1, n[1]) == oldColor) {
                        stack.add(n[0] - 1);
                        stack.add(n[1]);
                    }
                    if (image.getWidth() > n[0] + 1 && image.getRGB(n[0] + 1, n[1]) == oldColor) {
                        stack.add(n[0] + 1);
                        stack.add(n[1]);
                    }
                    if (0 >= n[1] - 1 && image.getRGB(n[0], n[1] - 1) == oldColor) {
                        stack.add(n[0]);
                        stack.add(n[1] - 1);
                    }
                    if (image.getHeight() > n[1] + 1 && image.getRGB(n[0], n[1] + 1) == oldColor) {
                        stack.add(n[0]);
                        stack.add(n[1] + 1);
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                   // e.printStackTrace();
                }
            }
        }

        //Sava image on computer
        try {
            ImageIO.write(image, "jpg",  new File("C://Users//Vova//Desktop//bg123.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Fast alghorithm
    public static void fillImageFast(BufferedImage image, Point p, int oldColor){
        int color = Color.GREEN.getRGB();
        ArrayList<Point> stack = new ArrayList<>();
        Point point;

        stack.add(p);

        while(stack.size() != 0){
            point = stack.get(0);
            stack.remove(0);
            int y = point.y;

            while(y > 0 && image.getRGB(point.x, y) == oldColor) y--;
            //y+=1;
            int west = 0, east = 0;

            while(y < image.getHeight() && image.getRGB(point.x, y) == oldColor){
                image.setRGB(point.x, y, color);

                if(west == 0 && point.x > 0 && image.getRGB(point.x-1, y) == oldColor){
                    stack.add(new Point(point.x-1, y));
                    west = 1;
                }else if(west == 1 && point.x > 0 && image.getRGB(point.x - 1, y) != oldColor)
                    west = 0;

                if(east == 0 && point.x < image.getHeight() && image.getRGB(point.x + 1, y) == oldColor){
                    stack.add(new Point(point.x+1, y));
                    east = 1;
                }else if(east == 1 && point.x < image.getWidth() && image.getRGB(point.x + 1, y) != oldColor)
                    east = 0;

                y++;
            }
        }

        //Sava image on computer
        try {
            ImageIO.write(image, "jpg",  new File("C://Users//Vova//Desktop//bg123.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
