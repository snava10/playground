import org.apache.spark.SparkConf;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.apache.spark.sql.functions.*;

public class PropertyData {

    public static void main(String[] args) throws UnknownHostException {

        final SparkConf conf = new SparkConf().setMaster("local").setAppName("UK Property Data");
        conf.set("es.index.auto.create", "true");

        final String filePath = "src/main/resources/pp-2018-part1.csv";

        // Create the spark session
        final SparkSession spark = SparkSession.builder().appName("UK Property Data").config(conf).getOrCreate();

        // Read csv
        final Dataset<Row> dataset = spark.read().format("csv")
                .option("inferSchema", "true")
                .option("header", "true")
                .load(filePath)
                .persist(StorageLevel.MEMORY_AND_DISK_SER());

        // Count. Head. Print schema.
        final long lines = dataset.count();
        System.out.println(lines);
        System.out.println(dataset.head(1));
        dataset.printSchema();

        // Select individual columns
        dataset.select("Id").show(10);

        // Select multiple columns. Group by. Sort.
        final Dataset<Row> dfPostcode = dataset.select(col("postcode"), col("price")).groupBy("postcode").avg()
                .sort(desc("avg(price)"))
                .cache();
        dfPostcode.printSchema();
        dfPostcode.show(50);

        final List<Column> catColumns = Arrays.asList(col("county"), col("district"),
                col("locality"), col("street"), col("propertyType"), col("postcode"));

        final Dataset<Row> groupByCounty = groupBy(dataset, catColumns, "price").cache();
        groupByCounty.printSchema();
        groupByCounty.show(50);

        // Writing to parket
        Dataset<Row> groupByPostcode = groupBy(dataset, Collections.singletonList(col("postcode")), "price").cache();
//        groupByPostcode.write().mode(SaveMode.Overwrite).format("parquet").save("src/main/resources/gbPostcode.parquet");

        // Reading from parket
        Dataset<Row> readGroupByPostcode = spark.read().load("src/main/resources/gbPostcode.parquet");
        readGroupByPostcode.show(50);

        // Save to ES
//        JavaEsSparkSQL.saveToEs(groupByPostcode, "gp_postcode/docs");
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        final DeleteIndexRequest request = new DeleteIndexRequest("gp_postcode");
        final Settings settings = Settings.builder().put("cluster.name", "docker-cluster").build();
        final TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        final Map<String, String> s = new HashMap<String, String>() {{
            put("cluster.name", "docker-cluster");
        }};
        client.admin().indices().delete(request, new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                JavaEsSparkSQL.saveToEs(groupByPostcode, "gp_postcode/docs", s);
            }

            @Override
            public void onFailure(Exception e) {
                JavaEsSparkSQL.saveToEs(groupByPostcode, "gp_postcode/docs", s);
            }
        });
    }

    /**
     * Group by the cat columns and calculate aggregations on the metricColumn
     * @param dataset Dataset.
     * @param catColumns Categorical columns to group by.
     * @param metricColumn Column to aggregate on.
     * @return Dataset resulting from the group by.
     */
    private static Dataset<Row> groupBy(Dataset<Row> dataset, List<Column> catColumns, String metricColumn) {
        final List<Column> selColumns = new ArrayList<>(catColumns);
        selColumns.add(col(metricColumn));

        Column[] categoriesColumns = new Column[catColumns.size()];
        for (int i = 0; i < categoriesColumns.length; i++) {
            categoriesColumns[i] = catColumns.get(i);
        }
        Column[] selectColumns = new Column[selColumns.size()];
        for (int i = 0; i < selColumns.size(); i++) {
            selectColumns[i] = selColumns.get(i);
        }

        return dataset.select(selectColumns)
                .groupBy(categoriesColumns)
                .agg(avg(metricColumn).alias("avg"), sum(metricColumn).alias("sum"), count(metricColumn).alias("count"))
                .sort(desc("avg"));
    }
}
