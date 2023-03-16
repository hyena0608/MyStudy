package generic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 최범균님 - 프로그래밍 초식 - 지네릭 변성(java)
 */
public class GenericVarianceTest {

    /**
     * 타입에 안정한 코드 작성 가능
     */
    @Test
    void 컴파일_시점_예방() {
        List<String> codes = new ArrayList<>();
        codes.add("1");
//        codes.add(1);
    }

    /**
     * Required type: GenericVarianceTest.Cage <Animal>
     * Provided: GenericVarianceTest.Cage <Tiger>
     */
    @Test
    void 제네릭과_하위_타입_컴파일_에러() {
        Animal a = new Tiger();
        // Cage<Animal> ca = new Cage<Tiger>();
    }


    /**
     * Cage<Tiger>가 Cage<Animal>의 하위 타입이라면 Cage<Tiger>에 Lion이 들어올 수 있어야 한다.
     * -> Cage<Tiger> 에서 Tiger 리스트를 가져오려 했지만 실제로 Lion 리스트를 가져오게 된다.
     */
    @Test
    void cage_tiger가_cage_animal의_하위_타입이_아니다() {
        Cage<Tiger> ct = new Cage<Tiger>();
        // Cage<Animal> ca = ct; // 이게 가능하다면
        // ca.push(new Lion()); // Lion은 Animal이니 가능하다.
        // List<Tiger> tigers = ct.getAll(); // Lion 리스트를 반환하게 된다.
    }


    /**
     * 무변성 (invariant)
     * -> A가 B의 상위 타입 일 때
     * -> GenericType<A>가 GenericType<B>의 상위 타입이 아니면 변성이 없다.
     */
    @Test
    void 무변성_invariant() {
        Animal animal = new Tiger(); // OK, Animal은 Tiger의 상위 타입이다.
        // Cage<Animal> ca = new Cage<Tiger>(); // ERROR, Cage<Animal>이 Cage<Tiger>의 상위 타입이 아니다.
    }


    /**
     * 무변성일 때 문제
     * 육식동물 우리에 고기 먹이를 주는 사육사
     */
    @Test
    void 무변성_문제() {
        ZookeeperV1 zk = new ZookeeperV1();
        Cage<Tiger> ct = new Cage<>();
        // zk.giveMeat(ct, new Meat()); // ERROR, Cage<Carnivore>가 Cage<Tiger>의 상위 타입이 아니다.
    }

    /**
     * 공변
     * -> A가 B의 상위 타입이고 T<A>가 T<B>의 상위 타입이면 공변
     * -> extends 사용해서 공변 처리 가능
     * Cage<? extends Carnivore> 타입에
     * Cage<Tiger>, Cage<Lion> 할당 가능
     */
    @Test
    void 공변() {
        ZookeeperV2 zk = new ZookeeperV2();
        Cage<Tiger> ct = new Cage<>();
        Cage<Lion> cl = new Cage<>();
        zk.giveMeat(ct, new Meat());
        zk.giveMeat(cl, new Meat());
    }

    /**
     * cage의 실제 타입이 Cage<Tiger>인지 Cage<Lion>인지 알 수 없다.
     * 제네릭은 컴파일 시점에 사라지기 때문이다.
     */
    @Test
    void 공변에서_값_사용() {
        Cage<Tiger> ct = new Cage<Tiger>();
//        ct.push(new Tiger());

        Cage<? extends Carnivore> cage = ct;
        // cage.push(new Tiger()); // ERROR push(? extends Carnivore)
    }

    /**
     * A가 B의 상위 타입이고 T<A>가 T<B>의 하위 타입이면 반공변
     * -> super 사용해서 반공변 처리 가능
     */
    @Test
    void 반공변_contravariant() {
        Cage<Tiger> ct = new Cage<>();
        Cage<? super Tiger> ctt = ct; // Cage<Tiger> 할당 가능
        ctt.push(new Tiger());        // OK, ctt는 최소 Cage<Tiger>나 그 상위 타입
        // ctt.push(new Lion());      // ERROR
        // ctt.push(new Carnivore()); // ERROR

        Cage<Carnivore> ct2 = new Cage<>();
        Cage<? super Tiger> ctt2 = ct2;
        ctt2.push(new Tiger());
        // ctt2.push(new Lion());      // ERROR
        // ctt2.push(new Carnivore()); // ERROR
    }

    /**
     * PECS
     * -> producer-extends, consumer-super
     * -> 값을 제공하면 extends
     * -> 값을 사용하면 super
     */
    @Test
    void pecs() {
        Cage<Tiger> ct = new Cage<>();

        Cage<? extends Tiger> ctt2 = ct;
//         ctt2.push(new Tiger()); // ERROR

        Tiger tiger = ctt2.getAll().get(0);

        Cage<? super Tiger> ctt = ct;
        ctt.push(new Tiger());
        Object object = ctt.getAll().get(0);
    }

    private static class Animal {
    }

    private static class Carnivore extends Animal {
    }

    private static class Tiger extends Carnivore {
    }

    private static class Lion extends Carnivore {
    }

    private static class Meat {
    }

    private static class ZookeeperV1 {
        public void giveMeat(Cage<Carnivore> cage, Meat m) {
        }
    }

    private static class ZookeeperV2 {
        public void giveMeat(Cage<? extends Carnivore> cage, Meat m) {
        }
    }

    private static class Cage<T> {

        private List<T> animals = new ArrayList<>();

        public void push(T animal) {
            this.animals.add(animal);
        }

        public List<T> getAll() {
            return animals;
        }
    }
}
