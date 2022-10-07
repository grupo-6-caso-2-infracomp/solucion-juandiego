public class ReferenceUpdate extends Thread{

    private int reference;

    //get from TLB
    //cases:
    //can get from TLB (best)
    //gets from PT
    //gets from disk (worst) -> have to take into account aging algorithm and choose the biggest counter to delete
}
