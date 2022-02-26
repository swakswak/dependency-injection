# dependency-injection-toy
의존성 주입을 순수 자바 코드로 구현중

다음과 같은 조건을 가진다.

- `@Component` Annotation이 명시된 클래스의 생성자에 의존성 주입을 한다.
- `@Component` Annotation이 명시된 클래스의 생성자의 파라미터로 들어오는 타입은 전부 `@Component` Annotation이 명시된 클래스 타입이어야 한다.  
- 순환 참조를 할 경우 해당 Java Application이 실행이 되지 않아야 한다.