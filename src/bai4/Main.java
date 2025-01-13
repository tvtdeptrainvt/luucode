package bai4;

import java.util.Random;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

class TransactionSimulation implements Runnable {
    static final int INIT_BALANCE = 50;
    static final int NUM_TRANS = 1000000;

    // Sử dụng AtomicLong để tránh race condition và tràn số
    static AtomicLong balance = new AtomicLong(INIT_BALANCE);
    static AtomicLong credits = new AtomicLong(0);
    static AtomicLong debits = new AtomicLong(0);

    public void performTransaction() {
        Random random = new Random();
        for (int i = 0; i < NUM_TRANS; i++) {
            int v = random.nextInt(NUM_TRANS);
            if (random.nextInt(2) == 0) {
                // Credit transaction
                balance.addAndGet(v);  // Cập nhật balance an toàn
                credits.addAndGet(v);  // Cập nhật credits an toàn
            } else {
                // Debit transaction
                balance.addAndGet(-v); // Cập nhật balance an toàn
                debits.addAndGet(v);   // Cập nhật debits an toàn
            }
        }
    }

    @Override
    public void run() {
        performTransaction();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[10];

        // Tạo và bắt đầu 10 luồng
        for (int i = 0; i < 10; i++) {
            TransactionSimulation simulation = new TransactionSimulation();
            threads[i] = new Thread(simulation);
            threads[i].start();
        }

        // Đợi tất cả các luồng hoàn thành
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Final Balance: " + balance.get());
        System.out.println("Final Balance2: " + (TransactionSimulation.INIT_BALANCE + credits.get() - debits.get()));
        System.out.println("Total Credits: " + credits.get());
        System.out.println("Total Debits: " + debits.get());
        long elapsedTime = endTime - startTime;
        System.out.println("Execution Time: " + elapsedTime + " milliseconds");
    }
}
/* khi chạy nhiều luồng cùng một lúc có thể sảy ra vấn đề race conditison, nơi nhiều luồng cố gắng truy cập
và sửa đổi các biến chung đồng thời , điều này dẫn đến kết quả không chính xác
và bị sai lệck vì không có cơ chế bảo vệ sự truy cập đồng thời vào các tài nguyên chung.

để balance và balance2 bằng nhau , cần đảm bảo rằng quá trình tính toán cho balance và balance2 dều đồng nhất
và không có xung đột giữa các luồng. đầu tiên thử sử dụng synchronization để đảm bảo rằng chỉ có một luồng
có thể sửa đổi các giá trị tại 1 thời điểm nhưng bị tràn số dẫn đến sai lệch nên quay sang
sử dụng synchronized


**/
