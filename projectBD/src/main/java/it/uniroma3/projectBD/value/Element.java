package it.uniroma3.projectBD.value;

public class Element {

    private String label, data;

    public Element(String label, String data){
        this.label = label;
        this.data = data;
    }

    public String getLabel(){
        return this.label;
    }

    public String getData(){
        return this.data;
    }


}
