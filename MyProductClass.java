// Public class representing a customized product class that extends from ProductClass
public class MyProductClass extends ProductClass {
    // Instance variables
    protected String description = "";
    protected int quantity = 0;
    protected  String colour = "";
    protected String origin = "";
    protected int jitTrigger = 0;

    protected int purchaseQuant = 0;


    // Constructor that initializes the MyProductClass object using CSV line data
    public MyProductClass(String csvLine) {
        String [] data = csvLine.split("\\|");
        this.name = data[0];
        this.price = Double.parseDouble(data[1]);
        this.colour = data[2];
        this.quantity = Integer.parseInt(data[3]);
        this.description = data[4];
        this.origin = data[5];
        this.jitTrigger = Integer.parseInt(data[6]);




    }//MyProductClass()


    // Setter and getter for purchase quantity
    public void setpurchaseQuant(int purchaseQuant){
        this.purchaseQuant = purchaseQuant;
    }

    public int getpurchaseQuant(){
        return this.purchaseQuant;
    }



    // Setter and getter for color
    public void setColour (String colour){
        this.colour = colour;
    } // void setColour (String)

    public String getColour () {
        return this.colour;
    } // String getColour ()


    // Setter and getter for quantity
    public void setQuantity (int quantity) {
        this.quantity = quantity;
    } // void setQuantity (int)

    public int getQuantity () {
        return this.quantity;
    } // int getQuantity ()


    // Setter and getter for description
    public void setDescription (String description) {
        this.description = description;
    } // void setDescription (String)

    public String getDescription () {
        return this.description;
    } // int getDescription ()


    // Setter and getter for origin
    public void setOrigin (String origin) {
        this.origin = origin;
    } // void setDescription (String)

    public String getOrigin () {
        return this.origin;
    } // String getOrigin ()


    // Setter and getter for JIT trigger
    public void setJitTrigger (int jitTrigger) {
        this.jitTrigger = jitTrigger;
    } // void setDescription (int)

    public int getJitTrigger () {
        return this.jitTrigger;
    } // int getQuantity ()






    // Method to convert MyProductClass object to CSV format
    public String toCSV(){
        return name+"|"+price+"|" +colour+"|"+quantity+"|"+description+"|"+origin+"|"+jitTrigger+"|";
    }


    // Method to convert MyProductClass object to a string representation
    public String toString(){
        return "type="+ this.name + "; price="+this.price+"; quant="+this.quantity+"; description="+this.description+"; origin = "+ this.origin+"; JitTrig = "+ this.jitTrigger;
    }


    // Method to check if the MyProductClass object contains a specific item (case-insensitive)
    public boolean contains(String item){
        boolean found = false;

        if (this.name.toLowerCase().indexOf(item.toLowerCase())>-1){
            found = true;
        }//end if
        return found;
    }//boolean contains (String item)
}//public class MyProductClass extends ProductClass
