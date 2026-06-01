package Questions.Patterns.UsingRecursion;

public class LeftAlignedAscending {
    public static void main(String[] args) {
        make(4,0);

    }
    static void make(int row , int col){

//        base case :
        if( row == 0 )
            return;
        if(col < row){
            make(row , col +1);
            System.out.print("*");
        }
        else{
            make(row-1 , 0);
            System.out.println();
        }
    }
}
