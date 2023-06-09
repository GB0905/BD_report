# 정규 표현식 (Regular Expression)

### 정규 표현식이란?
특정한 규칙을 가진 문자열의 집합을 표현하는 데 사용하는 형식 언어이다.<p>
문자열의 검색과 치환을 위한 용도로 쓰이고 있다.
"정규식"이라고도 말한다.

### 정규 표현식이 필요한 이유
- 간편하고 직관적인 코드를 작성할 수 있다.
- 정규식을 사용하면 텍스트 또는 데이터 내에서 특정 패턴을 가진 이메일 주소, 전화번호, URL 등의 정보를 찾는 작업이 간편해진다.
- 간편해진 패턴 인식으로 텍스트의 특정 부분을 교체, 추출하거나 데이터를 원하는 형식으로 쉽게 변환할 수 있다.
- 이메일 주소, 암호 등이 특정 패턴과 일치하는지 유효성 검사를 확인할 수 있고 이를 통해 데이터 정확성과 무결성 보장에 도움이 된다.

---

### 정규 표현식의 기초, 메타 문자
메타 문자 : 원래 그 문자가 가진 뜻이 아닌 특별한 용도로 사용하는 문자<p>
<code>. ^ $ * + ? { } [ ] \ | ( )</code>


### 문자 클래스 [ ]
문자 클래스로 만들어진 정규식은 <code>"[ ] 사이의 문자들과 매치"</code>라는 의미를 갖는다.
>문자 클래스를 만드는 메타 문자인 [ ] 사이에는 어떤 문자도 들어갈 수 있다.<p>

### 정규 표현식 [abc]의 의미는 -> "a, b, c 중 한 개의 문자와 매치"
- "a"는 정규식과 일치하는 문자인 "a"가 있으므로 매치
- "before"는 정규식과 일치하는 문자인 "b"가 있으므로 매치
- "dude"는 정규식과 일치하는 문자인 a, b, c 중 어느 하나도 포함하고 있지 않으므로 매치되지 않음<p>

[ ] 안의 두 문자 사이에 하이픈(-)을 사용하면 두 문자 사이의 범위(From - To)를 의미한다. <p>
문자 클래스[ ] 안에 ( ^ ) 메타 문자를 사용할 경우에는 반대(not)라는 의미를 갖는다.<p>
ex) 정규 표현식 [a-c]은 [abc]와 동일하고, [0-5]는 [012345]와 동일하다.
- [a-zA-Z] : 알파벳 모두 매치
- [0-9] : 숫자 매치
- [^0-9] : 숫자가 아닌 문자만 매치<p>

정규식 별도의 표기법
- \d - 숫자와 매치, [0-9]
- \D - 숫자가 아닌 것과 매치, [^0-9]
- \s - whitespace 문자와 매치, [ \t\n\r\f\v] 
- \S - whitespace 문자가 아닌 것과 매치, [^ \t\n\r\f\v]
- \w - 문자+숫자와 매치, [a-zA-Z0-9_]
- \W - 문자+숫자가 아닌 문자와 매치, [^a-zA-Z0-9_]


### Dot(.)
Dot(.) 메타 문자는 줄바꿈 문자인 <code>\n</code>을 제외한 모든 문자와 매치됨을 의미한다.<p>
<code>정규식 a.b의 의미는 -> "a + 모든문자 + b"</code><p>
즉, a와 b라는 문자 사이에 어떤 문자가 들어가도 모두 매치된다는 의미이다.<p>
ex) 문자열 "aab", "a0b", "abc"가 정규식 <code>a.b</code>의 매치
- "aab"는 가운데 문자 "a"가 모든 문자를 의미하는 .과 일치하므로 정규식과 매치
- "a0b"는 가운데 문자 "0"가 모든 문자를 의미하는 .과 일치하므로 정규식과 매치
- "abc"는 "a"문자와 "b"문자 사이에 어떤 문자라도 하나는있어야 하는 이 정규식과 일치하지 않으므로 매치X<p>

<code>정규식a[.]b의 의미는 -> "a + Dot(.)문자 + b"</code><p>
따라서, 정규식 <code<a[.]b</code>는 "a.b" 문자열과 매치되고, "a0b" 문자열과는 매치되지 않는다<p>


### 반복(*)
<code>ca*t</code><p>
 반복을 의미하는 <code> * </code>메타 문자가 사용되었다.<p>
 <code> * </code>은 <code> * </code>바로 앞에 있는 문자 a가 0부터 무한대로 반복될 수 있다는 의미이다.<p>
-> 사실은 메모리 제한으로 2억 개 정도만 가능<p>

|정규식|문자열|Match 여부|설명|
|--|--|--|--|
|ca*t|ct|yes|"a"가 0번 반복되어 매치|
|ca*t|cat|yes|"a"가 0번 이상 반복되어 매치 (1번 반복)|
|ca*t|caaat|yes|"a"가 0번 이상 반복되어 매치 (3번 반복)|


### 반복(+)
<CODE> + </CODE>는 최소 1번 이상 반복될 때 사용한다<p>
-> 즉 <code> * </code>가 반복 횟수 0부터라면 <CODE> + </CODE>는 반복 횟수 1부터인 것<p>
<code>정규식 ca+t의 의미는 -> "c + a(1번 이상 반복) + t"</code><p>

|정규식|문자열|Match 여부|설명|
|--|--|--|--|
|ca+t|ct|No|"a"가 0번 반복되어 매치X|
|ca+t|cat|yes|"a"가 1번 이상 반복되어 매치 (1번 반복)|
|ca+t|caaat|yes|"a"가 1번 이상 반복되어 매치 (3번 반복)|


### 반복({m,n}, ?)
{ } 메타 문자를 사용하면 반복 횟수를 고정할 수 있다.<p>
정규식{m, n}을 사용하면 반복 횟수가 m부터 n까지 매치할 수 있다<p>
- {3,} : 반복 횟수 3 이상
- {,3} : 반복 횟수 3 이하
- {1,} : <CODE> + </CODE>와 동일
- {0,} : <CODE> * </CODE>와 동일

#### 1. {m}
<code>정규식 ca{2}t의 의미는 -> "c + a(반드시 2번 반복) + t"</code><p>
|정규식|문자열|Match 여부|설명|
|--|--|--|--|
|ca{2}t|cat|No|"a"가 1번만 반복되어 매치X|
|ca{2}t|caat|Yes|"a"가 2번 반복되어 매치|

#### 2. {m, n}
<code>정규식 ca{2,5}t의 의미는 -> "c + a(2~5회 반복) + t"</code><p>
|정규식|문자열|Match 여부|설명|
|--|--|--|--|
|ca{2,5}t|cat|No|"a"가 1번만 반복되어 매치X|
|ca{2,5}t|caat|yes|"a"가 2번 반복되어 매치|
|ca{2,5}t|caaaaat|yes|"a"가 5번 반복되어 매치|

#### 3. ?
반복은 아니지만 이와 비슷한 개념으로<code> ? </code>이 있다.<p>
<code> ? </code>메타문자가 의미하는 것은 <code>{0,1}</code>이다.<p><p>

<code>정규식 ab?c의 의미는 -> "a + b(있어도 되고 없어도 된다) + c"</code><p>
|정규식|문자열|Match 여부|설명|
|--|--|--|--|
|ab?c|abc|Yes|"b"가 1번 사용되어 매치|
|ab?c|ac|Yes|"b"가 0번 사용되어 매치| 
<p>
-> 즉, b 문자가 있거나 없거나 둘 다 매치되는 경우이다.<p>
<code>*, +, ? </code>메타 문자는 모두 {m, n} 형태로 고쳐 쓰는 것이 가능하지만 가급적 <code>*, +, ? </code>메타 문자를 사용하는 것이 좋다

### 메타문자 ( | )
<code>|</code> 메타 문자는 or과 동일한 의미로 사용된다. <p>
A|B라는 정규식이 있다면 A 또는 B라는 의미가 된다.

### 메타문자 ( ^ )
<code>^</code> 메타 문자는 문자열의 맨 처음과 일치함을 의미한다. <p>
컴파일 옵션 <code>re.MULTILINE</code>을 사용할 경우에는 여러 줄의 문자열일 때 각 줄의 처음과 일치하게 된다.

### 메타문자 ( $ )
<code>$</code>  문자열의 끝과 매치함을 의미한다. <p>
> <code>^</code> 또는 <code>$</code> 문자를 메타 문자가 아닌 문자 그 자체로 매치하고 싶은 경우에는 <code>\^, \$ </code>로 사용하면 된다.

### 메타문자 ( $ )
<code>$</code>  문자열의 끝과 매치함을 의미한다. <p>
> <code>^</code> 또는 <code>$</code> 문자를 메타 문자가 아닌 문자 그 자체로 매치하고 싶은 경우에는 <code>\^, \$ </code>로 사용하면 된다.

### 메타문자 ( \A )
<code>\A</code>는 문자열의 처음과 매치됨을 의미한다. <p>
<code>re.MULTILINE</code> re.MULTILINE 옵션을 사용할 경우 <code>^</code>은 각 줄의 문자열의 처음과 매치되지만 <code>\A</code>는 줄과 상관없이 전체 문자열의 처음하고만 매치된다.

### 메타문자 ( \Z )
<code>\Z</code>는 문자열의 끝과 매치됨을 의미한다. <p>
<code>re.MULTILINE</code>옵션을 사용할 경우 <code>$</code>메타 문자와는 달리 전체 문자열의 끝과 매치된다.

### 메타문자 ( \b )
<code>\b</code>는 단어 구분자(Word boundary)이다. 보통 단어는 whitespace에 의해 구분된다.

***

### 정규 표현식 모듈 함수
1. re.compile()
    - 정규식 패턴을 정규식 개체로 컴파일
2. re.match() 
    - 문자열의 처음부터 정규식과 매치되는지 조사
3. re.search()
    - 문자열 전체를 검색하여 정규식과 매치되는지 조사
4. re.findall()	
    - 정규식과 매치되는 모든 문자열을 리스트로 리턴
5. re.finditer()
    - 정규식과 매치되는 모든 문자열을 반복 가능한 객체로 리턴
6. re.split()
    - 정규식을 기준으로 문자열을 분리하여 리스트로 리턴
7. re.sub()
    - 문자열에서 정규식과 일치하는 모든 항목을 지정된 대체 항목으로 변경