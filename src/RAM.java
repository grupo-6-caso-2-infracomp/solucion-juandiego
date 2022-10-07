public class RAM {
    // every process has a certain amount of pages, that we delimit here
    private int pageFrameSize;
    private int[] pageFrame;

    public RAM(int pageFrameSize) {
        this.pageFrameSize = pageFrameSize;
        this.pageFrame = new int[pageFrameSize];
    }
}
