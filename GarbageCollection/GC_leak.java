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

//무한 루프가 생성되고 루프의 각 반복 내에서 새로운 정수 배열이 생성되어 'intArrayList'에 추가
//이전에 추가된 어레이는 목록에서 제거 X

//가비지 컬렉션은 더 이상 접근할 수 없거나 프로그램에서 사용하지 않는 개체가 차지하는 메모리를 자동으로 회수함
//하지만 이 경우 배열은 여전히 ​​intArrayList에 의해 참조되므로 가비지 수집되지 않음
//루프가 계속 실행됨에 따라 점점 더 많은 어레이가 목록에 추가
//더 이상 필요하지 않지만 여전히 참조되기 때문에 가비지 수집할 수 없는 개체가 메모리를 차지 -> 메모리 누수가 발생

// 메모리 누수를 방지하려면 더 이상 필요하지 않은 배열을 목록에서 제거해야 함