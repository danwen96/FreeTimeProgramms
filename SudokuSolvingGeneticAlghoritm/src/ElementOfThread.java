import java.util.Random;
import java.util.concurrent.Callable;

public class ElementOfThread implements Callable<int[]> {

    int liczbaCzynnikow;
    int[] czynniki;
    Sudoku sudoku;

//rozwiazywanie sudoku dla testu
    ElementOfThread(int[] czynniki,int liczbaCzynnikow,Sudoku sudoku) {
        this.liczbaCzynnikow = liczbaCzynnikow;
        this.czynniki = czynniki;
        this.sudoku = sudoku;
    }

    @Override
    public int[] call() throws Exception {

        Random random = new Random();
        int czyDojdzieDoMutacji = Math.abs(random.nextInt()%10);

        if(czyDojdzieDoMutacji == 1) {
            int zmiana = Math.abs(random.nextInt() % 9) + 1;
            int adresZmiany = Math.abs(random.nextInt() % liczbaCzynnikow);

            czynniki[adresZmiany] = zmiana;
        }

        return czynniki;
    }
}
