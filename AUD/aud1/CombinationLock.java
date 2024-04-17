package Napredno_Programiranje.AUD.aud1;

public class CombinationLock {

    private int combination;
    private boolean isOpen;
    private static int DEFAULT_COMBINATION = 100;

    public CombinationLock(int combination) {
        this.combination = combination;
        if(isCombinationValid(combination)){
            this.combination = combination;
        }else {
            this.combination=DEFAULT_COMBINATION;
        }
        isOpen = false;
    }

    private boolean isCombinationValid(int combination){
        return combination>=100&&combination<1000;
    }

    public boolean open(int combination){
        this.isOpen = (this.combination == combination);
        return this.isOpen;
    }
    public boolean changeCombination(int oldCombination,int newCombination){
        if(open(oldCombination)&&isCombinationValid(newCombination)){
            this.combination = newCombination;
            return true;
        }return false;
    }
    private boolean isOpen() {
        return isOpen;
    }
    public void lock(){
        this.isOpen = false;
    }

    public static void main(String[] args) {
        CombinationLock validLock = new CombinationLock(234);
        System.out.println(validLock.isOpen());
        System.out.println(validLock.open(123));
        System.out.println(validLock.open(333));
        System.out.println(validLock.open(234));
        validLock.lock();
//        itnitn

    }



}
