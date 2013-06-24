import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * This program is used to connect to the XML gateway API.
 * Each request can be altered by you to test the API and
 * validate the responses you receive back.
 */
public class XMLGatewayTest {
    public static void main(String[] args) {
        HttpURLConnection connection = null;
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;

        URL serverAddress = null;

        String postdata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" +
                "<TRANSACTION>\n" +
                "<FIELDS>\n" +
                "<FIELD KEY=\"transaction_center_id\">1264</FIELD>\n" +
                "<FIELD KEY=\"gateway_id\">a91c38c3-7d7f-4d29-acc7-927b4dca0dbe</FIELD> \n" +
                "<FIELD KEY=\"operation_type\">sale</FIELD>\n" +
                "<FIELD KEY=\"order_id\">YOURID_NUMBER</FIELD>\n" +
                "<FIELD KEY=\"total\">5.00</FIELD>\n" +
                "<FIELD KEY=\"card_name\">Visa</FIELD>\n" +
                "<FIELD KEY=\"card_number\">4111111111111111</FIELD>\n" +
                "<FIELD KEY=\"card_exp\">1113</FIELD>\n" +
                "<FIELD KEY=\"cvv2\">123</FIELD>\n" +
                "<FIELD KEY=\"owner_name\">Bob Auth</FIELD>\n" +
                "<FIELD KEY=\"owner_street\">123 Test St</FIELD>\n" +
                "<FIELD KEY=\"owner_city\">city</FIELD>\n" +
                "<FIELD KEY=\"owner_state\">PA</FIELD>\n" +
                "<FIELD KEY=\"owner_zip\">12345-6789</FIELD>\n" +
                "<FIELD KEY=\"owner_country\">US</FIELD>\n" +
                "<FIELD KEY=\"recurring\">0</FIELD>\n" +
                "<FIELD KEY=\"recurring_type\">annually</FIELD>\n" +
                "</FIELDS> \n" +
                "</TRANSACTION>";

        try {
            serverAddress = new URL("https://secure.1stpaygateway.net/secure/gateway/xmlgateway.aspx");

            //set up the initial connection
            connection = (HttpURLConnection)serverAddress.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postdata.getBytes().length));
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            connection.connect();

            //get the output stream writer and write the output to the server
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(postdata);
            wr.flush();

            //read the result from the server
            rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }

            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            //close the connection, set all objects to null
            connection.disconnect();
            rd = null;
            sb = null;
            wr = null;
            connection = null;
        }
    }
}