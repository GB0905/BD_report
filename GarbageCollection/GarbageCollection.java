import java.util.ArrayList;
import java.util.List;

public class GarbageCollection {
    public static void main(String[] args) {
        List<StringBuilder> stringBuilderList = new ArrayList<>();

        // 10만 개의 StringBuilder 객체를 생성하여 리스트에 추가
        for (int i = 0; i < 100000; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Object ").append(i);
            stringBuilderList.add(stringBuilder);
        }

        // 더 이상 필요하지 않은 리스트 자체를 null로 설정하여 모든 레퍼런스를 해제
        stringBuilderList = null;

        // 가비지 컬렉션을 실행하여 더 이상 참조되지 않는 StringBuilder 객체들이 차지하던 메모리를 회수
        System.gc();
    }
}