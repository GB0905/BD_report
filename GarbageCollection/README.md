# 가비지 컬렉션 (Garbage Collection)

### 가비지 컬렉션이란?
 프로그래밍 언어에서 메모리 관리를 위해 사용되는 기술이다. </br>

 자바의 메모리 관리 방법 중의 하나로 JVM(자바 가상 머신)의 Heap 영역에서 동적으로 할당했던 메모리 중 필요 없게 된 메모리 객체(garbage)를 모아 주기적으로 제거하는 프로세스이다.</br>
 C / C++ 언어에서는 이러한 가비지 컬렉션이 없어 프로그래머가 수동으로 메모리 할당과 해제를 일일이 해줘야 했다.</br>

 반면 Java에서는 가비지 컬렉터가 메모리 관리를 대행해주기 때문에 Java 프로세스가 한정된 메모리를 효율적으로 사용할수 있게 하고, 개발자 입장에서 메모리 관리, 메모리 누수(Memory Leak) 문제에서 대해 관리하지 않아도 되어 프로그래머의 번거로움과 실수를 줄여주는데 도움을 준다.


### 가비지 컬렉션이 필요한 이유
- 동적 메모리 할당: 많은 프로그래밍 언어에서는 동적으로 메모리를 할당하는 기능을 제공 </br>
가비지 컬렉션은 동적으로 할당된 메모리를 추적하고 해제함으로써 프로그래머가 메모리 관리에 신경 쓰지 않고도 유연한 데이터 구조를 만들 수 있다.</br>

- 자동 메모리 관리: 가비지 컬렉션은 프로그래머가 명시적으로 메모리를 해제하는 작업을 수행 X </br>
이는 메모리 누수(memory leaks)와 같은 일반적인 버그를 방지하고 프로그래밍 생산성을 향상시킨다.</br>
 
프로그램의 실행 동안 동적으로 메모리가 할당 되는데, 프로그래머가 직접 할당한 메모리가 아니면 관리하기 어렵고, 메모리 사용량 또한 파악하기 힘들다.</br>
동적으로 메모리가 할당된 것처럼 사용하지 않는 메모리를 또한 동적으로 추적하고 해제하는 작업을 수행해야 할 필요가 있다. </br>
따라서 가비지 컬렉션이 필요하다.

### 가비지 컬렉션 대상
- 가비지 컬렉션은 특정 객체가 garbage인지 아닌지 판단하기 위해서 도달성, 도달능력(Reachability) 이라는 개념을 적용한다.
- 객체에 레퍼런스가 있다면 Reachable로 구분되고, 객체에 유효한 레퍼런스가 없다면 Unreachable로 구분해버리고 수거한다. 
    - Reachable : 객체가 참조되고 있는 상태
    - Unreachable  : 객체가 참조되고 있지 않은 상태 (GC의 대상) 

### 가비지 컬렉션 청소 방식
**STW(Stop The World)**
*가비지 컬렉션이 자동으로 처리해준다 해도 메모리가 언제 해제되는지 정확하게 알 수 없어 제어하기 힘들다.</br>
가비지 컬렉션이 동작하는 동안 다른 동작을 멈추기 때문에 오버헤드가 발생되는 문제점이 있다.</br>
이를 Stop-The-World라 한다.*</br>
STW는 GC를 수행하기 위해 JVM이 프로그램 실행을 멈추는 현상을 의미한다.</br>
GC가 작동하는 동안 GC 관련 Thread를 제외한 모든 Thread는 멈추게 되어 서비스 이용에 차질이 생길 수 있다.</br>

**Mark And Sweep**
- 다양한 GC에서 사용되는 객체를 솎아내는 내부 알고리즘이다. 
    [가비지 컬렉션이 동작하는 아주 기초적인 청소 과정]</br>

가비지 컬렉션이 될 대상 객체를 식별(Mark)하고 제거(Sweep)하며 객체가 제거되어 파편화된 메모리 영역을 앞에서부터 채워나가는 작업(Compaction)을 수행하게 된다.</br>
- Mark 과정 : 먼저 Root Space로부터 그래프 순회를 통해 연결된 객체들을 찾아내어 각각 어떤 객체를 참조하고 있는지 찾아서 마킹한다.
- Sweep 과정 : 참조하고 있지 않은 객체 즉 Unreachable 객체들을 Heap에서 제거한다.
- Compact 과정 : Sweep 후에 분산된 객체들을 Heap의 시작 주소로 모아 메모리가 할당된 부분과 그렇지 않은 부분으로 압축한다.(가비지 컬렉터 종류에 따라 하지 않는 경우도 있음)
Mark And Sweep 방식을 사용하면 루트로부터 연결이 끊긴 순환 참조되는 객체들을 모두 지울수 있다.</br>
>[ GC의 Root Space ]
>>Mark And Sweep 방식은 루트로 부터 해당 객체에 접근이 가능한지가 해제의 기준이 된다.</br>
    JVM GC에서의 Root Space는 Heap 메모리 영역을 참조하는 method area, static 변수, stack, native method stack이 되게 된다.

### heap 메모리의 구조
JVM의 힙(heap) 영역은 동적으로 레퍼런스 데이터가 저장되는 공간으로서, 가비지 컬렉션에 대상이 되는 공간이다.</br>
>[Heap영역은 처음 설계될 때 다음의 2가지를 전제 (Weak Generational Hypothesis)로 설계]
> - 대부분의 객체는 금방 접근 불가능한 상태(Unreachable)가 된다.
> - 오래된 객체에서 새로운 객체로의 참조는 아주 적게 존재한다.</br>

JVM 개발자들은 효율적인 메모리 관리를 위해, 객체의 생존 기간에 따라 물리적인 Heap 영역을 나누었다.</br>
-  Heap 영역
   - 동적으로 할당된 메모리 영역으로 모든 Object 타입 및 new를 사용하여 객체를 생성하면 힙 영역에 저장한다.
   - Heap 영역은 Young과 Old 영역으로 설계되었다.</br>

- Old 영역(Old Generation)
    - Young영역에서 Reachable 상태를 유지하여 살아남은 객체가 복사되는 영역
    - Young 영역보다 크게 할당되며, 영역의 크기가 큰 만큼 가비지는 적게 발생한다.
    - Old 영역에 대한 가비지 컬렉션(Garbage Collection)을 Major GC 또는 Full GC라고 부른다.</br>


- Young 영역(Young Generation)
    - 새롭게 생성된 객체가 할당(Allocation)되는 영역
    - 대부분의 객체가 금방 Unreachable 상태가 되기 때문에, 많은 객체가 Young 영역에 생성되었다가 사라진다.
    - Young 영역에 대한 가비지 컬렉션(Garbage Collection)을 Minor GC라고 부른다.</br>

- Young 영역의 3가지 영역
    - Eden 
        - new를 통해 새로 생성된 객체가 위치. 
        - 정기적인 쓰레기 수집 후 살아남은 객체들은 Survivor 영역으로 보냄
    - Survivor 0 / Survivor 1
        - 최소 1번의 GC 이상 살아남은 객체가 존재하는 영역
        - Survivor 영역에는 특별한 규칙이 있는데, Survivor 0 또는 Survivor 1 둘 중 하나에는 꼭 비어 있어야 한다.


### 가비지 컬렉션 동작 과정
#### Minor GC 과정
- Young Generation 영역은 짧게 살아남는 메모리들이 존재하는 공간이다.
- 모든 객체는 처음에는 Young Generation에 생성되게 된다.
- Young Generation의 공간은 Old Generation에 비해 상대적으로 작기 때문에 메모리 상의 객체를 찾아 제거하는데 적은 시간이 걸린다. (작은 공간에서 데이터를 찾아서)
- 이 때문에 Young Generation 영역에서 발생되는 GC를 Minor GC라 불린다.

    1. 처음 생성된 객체는 Young Generation 영역의 일부인 Eden 영역에 위치
    2. 객체가 계속 생성되어 Eden 영역이 꽉차게 되고 Minor GC가 실행
    3. Mark 동작을 통해 reachable 객체를 탐색
    4. Eden 영역에서 살아남은 객체는 1개의 Survivor 영역으로 이동
    5. Eden 영역에서 사용되지 않는 객체(unreachable)의 메모리를 해제(sweep)
    6. 살아남은 모든 객체들은 age값이 1씩 증가
    7. 또다시 Eden 영역에 신규 객체들로 가득 차게 되면 다시한번 minor GC 발생하고 mark 한다
    8. marking 한 객체들을 비어있는 Survival 1으로 이동하고 sweep</br>
    10. 다시 살아남은 모든 객체들은 age가 1씩 증가
    11. 이러한 과정을 반복

#### Major GC (Full GC) 과정
- Old Generation은 길게 살아남는 메모리들이 존재하는 공간이다.
- Old Generation의 객체들은 거슬러 올라가면 처음에는 Young Generation에 의해 시작되었으나, GC 과정 중에 제거되지 않은 경우 age 임계값이 차게되어 이동된 녀석들이다.
- Major GC는 객체들이 계속 Promotion되어 Old 영역의 메모리가 부족해지면 발생하게 된다.</br>

    1. 객체의 age가 임계값(여기선 8로 설정)에 도달하게 되면,
    2. 이 객체들은 Old Generation 으로 이동된다. 이를 promotion 이라 부른다.
    3. 위의 과정이 반복되어 Old Generation 영역의 공간(메모리)가 부족하게 되면 Major GC가 발생되게 된다.</br>
Major GC는 Old 영역은 데이터가 가득 차면 GC를 실행하는 단순한 방식이다. 

##### Minor GC와 Major GC 차이점
|GC 종류|Minor GC|Major GC|
|--|--|--|
|대상|Young Generation|Old Generation|
|실행 시점|Eden 영역이 꽉 찬 경우|Old 영역이 꽉 찬 경우|
|실행 속도|빠르다|느리다|

***

#### 메모리 누수(Leak)
- 더 이상 사용되지 않는 객체들이 가비지 컬렉터에 의해 회수되지 않고 계속 누적이 되는 현상이다
- 가비지 컬렉션을 통해 소멸 대상이 되는 객체가 되기 위해서는 어떠한 reference 변수에서 가르키지 않아야 한다.

#### 가비지 컬렉션의 성능을 높이는 코딩 방법
1. Collection의 크기를 예측하여 설정
2. Stream을 사용
3. String의 사용을 최적화
4. 불변성(Immutability)을 활용
5. 불필요한 Collection의 생성을 피하기

####