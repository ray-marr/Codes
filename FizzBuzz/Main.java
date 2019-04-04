import java.util.HashMap;
import java.util.Map;

class Main {
  public static void main(String[] args) {
    outputFizzBuzz();
}

public static void outputFizzBuzz(){

    Map<Integer,String> hm = new HashMap<Integer, String>();
    hm.put(3, "Fizz");  
    hm.put(4, "Buzz");  
    hm.put(5, "Wizz");

    for(int i = 1; i<=100; i++){
      boolean match = false;

      for(int number : hm.keySet()){
        if(i%number==0){
          System.out.print(hm.get(number));
          match = true;
        }
    }
    if(match==false){
      System.out.print(i);
    }    
  System.out.println();
  }
}
}
