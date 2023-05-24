import java.util.ArrayList;
import java.util.List;

public class MemoryLeakExample {
    public static void main(String[] args) {
        List<int[]> intArrayList = new ArrayList<>();

        while (true) {
            int[] array = new int[1000000];
            intArrayList.add(array);

            // 이전에 추가된 배열을 리스트에서 제거하지 않아 메모리 누수가 발생
            // 가비지 컬렉션은 사용되지 않는 객체를 회수하지만, 
            // 이 경우 리스트에서 제거되지 않은 배열이 계속 메모리에 남아있음
            // 리스트는 계속 커지며 메모리 사용량이 계속 증가하여 결국 메모리 누수가 발생
        }
    }
}