import java.util.*;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner= new Scanner(System.in);
//        String ip=scanner.nextLine();
//        boolean res=true;
//        if(!(ip.charAt(0)>='0')&(ip.charAt(0) <='9'))
//        res=false;
//        if(!(ip.charAt(ip.length()-1)>='0')&(ip.charAt(ip.length()-1) <='9'))
//            res=false;
//        String[] ipParts=ip.split("\\.");
//        int [] arr = new int [ipParts.length];
//        for(int i=0; i<ipParts.length; i++) {
//            arr[i] = Integer.parseInt(ipParts[i]);
//        }
//        for (int i=0; i< arr.length; i++){
//            if(arr[i]>255||arr[i]<0)
//                res=false;
//            if(arr.length!=4)
//                res=false;
//        }
//        if(res)
//            System.out.println("YES");
//        else System.out.println("NO");


        Scanner scanner= new Scanner(System.in);
        String ip=scanner.nextLine();
        String regex="((([0-1]?[0-9]?[0-9])|([2]?[0-5]?[0-5]))\\.){3}((([0-1]?[0-9]?[0-9])|([2]?[0-5]?[0-5])))";
        if(ip.matches(regex))
            System.out.println("YES");
        else System.out.println("NO");





    }
}