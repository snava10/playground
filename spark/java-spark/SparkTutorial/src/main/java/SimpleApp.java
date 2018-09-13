/* SimpleApp.java */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class SimpleApp {
    public static void main(String[] args) {

        String logFile = "s3a://dataanalysis-dev/shakespeare.txt"; // Should be some file on your system
//        String logFile = "target/classes/shakespeare.txt";
        System.out.println("File " + logFile);

        //Create a SparkContext to initialize
        SparkConf conf = new SparkConf()
                .setMaster("local")
                .set("fs.s3a.access.key", "xxx")
                .set("fs.s3a.secret.key", "xxx")
                //.setMaster("local")
                .setAppName("Count a's and b's");
        SparkSession spark = SparkSession.builder().appName("SimpleApplication").config(conf).getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();

        long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();
    }
}
