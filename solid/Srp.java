package solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/** 단일 책임 원칙(SRP: Single Responsibility Principle)
 * 클래스를 변경할 이유는 단 하나여야 한다.
 * 여러 책임을 가지는 대신 하나의 핵심 책임만 가져야한다.
 * 만약 여러 책임을 가진 클래스를 만들게 되면 "GOD OBJECT" 라는 안티패턴이 되어버린다.
 * 아래의 코드는 1. 항목에 대한 책임, 2. 객체의 영속성에 대한 책임, 총 두가지의 책임을 가지고 있다.
 * 만약, 프로그램에 여러 객체가 존재하고, 저장 로직에 변경이 필요할경우 모든 객체의 저장로직을 바꿔줘야하는 문제가 있다.
 */
class Journal {
    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    // 1. 항목 추가
    public void addEntry(String text) {
        entries.add("" + (++count) + ": " + text);
    }

    // 2. 항목 제거
    public void removeEntry(int index) {
        entries.remove(index);
    }

    //
    // --- SRP 위배 ---
    //

    // 3. 일지 저장
//    public void save(String filename) {
        // ...데이터베이스에 일지를 저장합니다.
//    }

    // 4. 일지 조회
//    public Jounal load(String filename) {
        // ...데이터베이스에서 일지를 조회합니다.
//    }

    //
    // --- end: SRP 위배 ---
    //

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }
}

/** Separation of Concern
 * 관심사(책임 분리)
 * Persistence 클래스는 객체의 영속성에 대한 책임을 가지게 된다.
 * Journal 클래스는 이제 항목에 관한 책임만 가지게 된다.
 */
class Persistence {

    public void save(Journal journal, String filename, boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(filename).exists()) {
            try (PrintStream out = new PrintStream(filename)) {
                out.println(journal.toString());
            }
        }
    }
}

public class Srp {
    public static void main(String[] args) throws Exception {
        Journal j = new Journal();
        j.addEntry("오후 9시 블로그 작성한다.");
        j.addEntry("오후 11시 취침한다.");

        Persistence p = new Persistence();
        String filename = "210206_일기.txt";
        p.save(j, filename, true);

        Runtime.getRuntime().exec("notepad.exe" + filename);
    }
}

