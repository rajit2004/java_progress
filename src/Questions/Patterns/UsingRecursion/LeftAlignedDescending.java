package Questions.Patterns.UsingRecursion;

public class LeftAlignedDescending {
    public static void main(String[] args) {
        make(4,0);

    }
    static void make(int row , int col){

//        base case :
        if( row == 0 )
            return;
        if(col < row){
            System.out.print("*");
            make(row , col +1);
        }
        else{
            System.out.println();
            make(row-1 , 0);
        }
    }
}
