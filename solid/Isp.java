package solid;

class Document {
}

interface Machine {
    void print(Document d);
    void scan(Document d);

    void fax(Document d);
}

interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

class JustPrinter implements Printer {

    @Override
    public void print(Document d) {
        // 프린트 작업
    }
}

class Photocopier implements Printer, Scanner {

    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

interface MultiFunctionDevice extends Printer, Scanner {}

class MultiFunctionMachine implements  MultiFunctionDevice {

    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}


class OldFashionedPrinter implements Machine {

    @Override
    public void print(Document d) {
        // 프린트 로직
    }

    @Override
    public void scan(Document d) {
        // 불필요
    }

    @Override
    public void fax(Document d) {
        // 불필요
    }
}

class MultiFunctionPrinter implements Machine {

    @Override
    public void print(Document d) {
        // 프린트 로직
    }

    @Override
    public void scan(Document d) {
        // 스캔 로직
    }

    @Override
    public void fax(Document d) {
        // 팩스 로직
    }
}

public class Isp {


}
