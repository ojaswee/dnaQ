package dnaQ.Connections;

import java.awt.Desktop;
import java.net.URI;

public class WebConnections {

    private static void browseToURL(String url) throws Exception{
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        }else{
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("xdg-open " + url);
        }
    }

    public static void searchGene(String term) throws Exception{
        browseToURL("https://www.genecards.org/cgi-bin/carddisp.pl?gene=" + term );
    }
}
