import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author FRED
 */
public class TrackInfo {
  public static void main(String[] args) {
    if (args.length != 1){
      System.out.println("incorrect number of arguments");
      System.exit(0);
    }
    try{
      String filename = args[0];
      Track data = new Track (filename);
      System.out.println(data.size() + " points in track");
      System.out.println("lowest point is " + data.lowestPoint().toString());
      System.out.println("highest point is " + data.highestPoint().toString());
      System.out.println("total distance = " + String.format("%.3f",data.totalDistance()/1000 )+ " km");
      System.out.println ("average speed = " + String.format("%.3f", data.averageSpeed()) + " m/s");


    }
    catch (IOException e){
      System.out.println("Error reading track data from file ");
      System.exit(1);

    }
  }
}
