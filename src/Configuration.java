/**
 * Created by Rafał on 21.05.2017.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Configuration {
    /**
     * Zmienna przechowująca liczbę żyć
     */
    private int lifeNumber;
    /**
     * Zmienna przechowująca rozmiar mapy
     */
    private int mapSize;
    /**
     * Zmienna przechowująca czas rundy
     */
    private double roundTime;
    /**
     * Zmienna przechowująca nicki najlepszych graczy
     */
    private String[] highScoreNames = new String[10];
    /**
     * Zmienna przechowująca wyniki najlepszych graczy
     */
    private int[] points = new int[10];
    /**
     * Zmienna przechowująca poziom trudności
     */
    private int diffLevel = -1;
    /**
     * Tablica przechowująca dane o rozłożeniu obiektów na mapie
     */
    private int [][] map1;

    /**
     * Konstruktor obiektu klasy Configuration
     */
    public Configuration() {
        this.loadConfiguration();
    }

    /**
     * Metoda wczytująca level
     */



    public static String[] getHighScores(){
        File file = new File("src/high.txt");
        Scanner in=null;
        try {
            in = new Scanner(file);
        }catch(FileNotFoundException e){
            Gui.gui.addMessage("File not found", 3, true);
            return null;
        }

        String[] tmp = new String[10];
        //int[] tmpInt = new int[10];
        for(int i=0; i<10; i++) {
            //this.highScoreNames[i] = in.nextLine();
            tmp[i]=in.nextLine();
            //String[] splited = number[i].split(" ");
            //number[i]=splited[0];
            //tmpInt[i]= Integer.parseInt(splited[1]);
            //System.out.println( getPoints()[i]);

            //this.points[i] = in.nextInt();

        }
        return tmp;
    }

    public static int getMapSize(String number){

        File file = new File("src/" + "level" + number + ".txt");
        Scanner in = null;

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            Gui.gui.addMessage("File not found", 3, true);
            return 0;
        }
        int mapsize=in.nextInt();

        return mapsize;
    }
    public static int[][] getLevel(String number){
        File file = new File("src/" + "level" + number + ".txt");
        Scanner in = null;

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            Gui.gui.addMessage("File not found", 3, true);
            return null;
        }
        int tmpInt=in.nextInt();
        int[][]map=new int[tmpInt][tmpInt];

        try{
            for(int j = 0; j< tmpInt; j++)
            {
                for(int k = 0; k< tmpInt; k++)
                    map[j][k]=in.nextInt();


            }

        } catch (NoSuchElementException | IllegalStateException e) {
            Gui.gui.addMessage("No level error", 3, true);
            try {
                in.close();
            } catch (IllegalStateException ise) {
            }
            return null;
        }
        try {
            in.close();
        } catch (IllegalStateException ise) {return null;
        }
        return map;
    }



    public void loadConfiguration() {
        String tmp;
        int tmpInt;
        try {


            File file = new File("src/conf.txt");
            File file2 = new File("src/HighScores");
            Scanner in = new Scanner(file);
            Scanner in2 = new Scanner(file2);
            this.setLifeNumber(in.nextInt());
            tmpInt=in.nextInt();
            map1 = new int [tmpInt][tmpInt];
            this.setMapSize(tmpInt);
            this.setRoundTime(in.nextDouble());

            tmp=in.nextLine();

        /**    for(int i=0; i<10; i++) {
                //this.highScoreNames[i] = in.nextLine();
                number=in2.nextLine();
                String[] splited = number.split(" ");
                this.getHighScoreNames()[i]=splited[0];
                this.getPoints()[i]= Integer.parseInt(splited[1]);
                //System.out.println( getPoints()[i]);

                //this.points[i] = in.nextInt();

            }*/
            //       in.nextInt();

            for(int j = 0; j< getMapSize(); j++)
            {
                for(int k = 0; k< getMapSize(); k++)
                    this.getMap1()[j][k]=in.nextInt();


            }


            // System.out.println(getLifeNumber() + " " + getMapSize() + " " + getRoundTime());
            //String zdanie = in.nextLine();
            //System.out.println(zdanie);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.szolmap();
    }

    /**
     * Metoda służąca do wyświetlenia w konsoli układu mapy
     */
    void szolmap(){
        for (int i = 0; i < this.getMapSize(); i++){
            for (int j = 0; j < this.getMapSize(); j++){
                //System.out.print(this.getMap1()[i][j]+ " ");

            }
            //System.out.println();
        }
    }

    /**
     * Getter liczby żyć
     *
     */
    public int getLifeNumber() {
        return lifeNumber;
    }
    /**
     * Setter liczby żyć
     *
     */
    public void setLifeNumber(int lifeNumber) {
        this.lifeNumber = lifeNumber;
    }
    /**
     * Getter rozmiaru mapy
     *
     */
    public int getMapSize() {
        return mapSize;
    }
    /**
     * Setter rozmiaru mapy
     *
     */
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }
    /**
     * Getter czasu rundy
     *
     */
    public double getRoundTime() {
        return roundTime;
    }
    /**
     * Setter czasu rundy
     *
     */
    public void setRoundTime(double roundTime) {
        this.roundTime = roundTime;
    }
    /**
     * Getter liczby żyć
     *
     */
    public String[] getHighScoreNames() {
        return highScoreNames;
    }
    /**
     * Setter najlepszych wyników
     *
     */
    public void setHighScoreNames(String[] highScoreNames) {
        this.highScoreNames = highScoreNames;
    }
    /**
     * Getter punktów
     *
     */
    public int[] getPoints() {
        return points;
    }
    /**
     * Setter punktów
     *
     */
    public void setPoints(int[] points) {
        this.points = points;
    }

    public int getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(int diffLevel) {
        this.diffLevel = diffLevel;
    }
    /**
     * Getter tablicy prezentującej układ obiektów na mapie
     *
     */
    public int[][] getMap1() {
        return map1;
    }
    /**
     * Setter tablicy prezentującej układ obiektów na mapie
     *
     */
    public void setMap1(int[][] map1) {
        this.map1 = map1;
    }
}

