import java.io.IOException;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.util.Objects;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author YOUR NAME
 */
public class Track {

    private Point[] tracks;

  // TODO: Create a stub for the constructor
    public Track (){
        // track constructor
        this.tracks = new Point[0];
    }

    public Track(String filename) throws IOException {

        try{
            readFile(filename);
        } catch (IOException e){
            System.out.println("file cannot be opened");
        }

    }

  // TODO: Create a stub for readFile()
    public void readFile(String filename) throws IOException {
        Scanner scanner = new Scanner (Paths.get(filename));

        this.tracks = new Point[0];
        while (scanner.hasNextLine()){
            String field = scanner.nextLine();
            String[] rows = field.split(",");
            if (rows.length < 4){
                throw new GPSException("not enough values to make a point");
            }
            if (!Objects.equals(rows[0], "Time")){
                Point obj = new Point(ZonedDateTime.parse(rows[0]), Double.parseDouble(rows[1]), Double.parseDouble(rows[2]), Double.parseDouble(rows[3]));
                add (obj);
            }
        }
        scanner.close();
    }
  // TODO: Create a stub for add()

    public void add(Point point){
        this.tracks = Arrays.copyOf(this.tracks, this.tracks.length + 1);
        this.tracks[this.tracks.length -1] = point;
    }

  // TODO: Create a stub for get()

    public Point get(int index){
        if (index > (this.tracks.length -1) || index < 0){
            throw new GPSException ("out of range index");
        }
        return this.tracks[index];
    }


  // TODO: Create a stub for size()

    public int size(){
        return this.tracks.length;
    }


  // TODO: Create a stub for lowestPoint()

    public Point lowestPoint(){
        if (this.tracks.length == 0){
            throw new GPSException("unable to calculate highest point: no points available");
        }

        int index_lowest = 0;

        for (int i = 1; i < this.tracks.length; i ++){
            if (this.tracks[i].getElevation() < this.tracks[index_lowest].getElevation()){
                index_lowest = i;
            }
        }

        return this.tracks[index_lowest];
    }

  // TODO: Create a stub for highestPoint()

    public Point highestPoint(){


        if (this.tracks.length == 0){
            throw new GPSException("unable to calculate highest point: no points availabel");
        }

        int index_highest = 0;

        for (int i = 1; i < this.tracks.length; i ++){
            if (this.tracks[i].getElevation() > this.tracks[index_highest].getElevation()){
                index_highest = i;
            }
        }

        return this.tracks[index_highest];
    }


  // TODO: Create a stub for totalDistance()

    public double totalDistance(){
        if (this.tracks.length < 2){
            throw new GPSException ("cannot compute distance");
        }

        double distance = 0;
        for (int i =0; i < this.tracks.length -1; i++){
            distance += Point.greatCircleDistance(this.tracks[i],this.tracks[i+1]);
        }
        return distance;
    }

  // TODO: Create a stub for averageSpeed()

    public double averageSpeed(){
        double totalDist = totalDistance();
        // get times
        ZonedDateTime startTime = this.tracks[0].getTime();
        ZonedDateTime endTime = this.tracks[this.tracks.length - 1].getTime();

        // used duration library import

        Duration duration = Duration.between(startTime, endTime);

        // used chat gpt prompt to create variable seconds elapsed
        double secondsElapsed = duration.getSeconds() + duration.getNano() / 1e9;

        return totalDist / secondsElapsed;


    }
    }

