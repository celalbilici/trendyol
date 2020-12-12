package steps;



import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.io.IOException;



public class Hooks extends MyMethods{

private final Dictionary dictionary;




    public Hooks(Dictionary dictionary) {
        this.dictionary = dictionary;
    }


    @Before()
    public void setProp(Scenario scenario){

        this.dictionary.setData("os",System.getProperty("os.name"));
        String currentBrowser=scenario.getSourceTagNames().toString().toLowerCase();

        if(currentBrowser.contains("@chrome")){
            this.dictionary.setData("driver","chrome");
        }else if(currentBrowser.contains("@safari")){
            this.dictionary.setData("driver","safari");
        }else if(currentBrowser.contains("@firefox")){
            this.dictionary.setData("driver","firefox");
        }

    }


    @Before()
    public void saveElements() throws IOException {
       this.dictionary.setData(convertObject( readFile(System.getProperty("user.dir")+"/src/test/resources/pageElements/pages.json")));
    }




}
