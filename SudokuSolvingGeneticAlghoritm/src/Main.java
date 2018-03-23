import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    static int nrOfChange = 30;
    static int[] adresyZNajwiekszymiWartosciami = new int[nrOfChange];
    static int[] najwiekszeWartosci = new int[nrOfChange];

    static int[] adresyZNajmniejszymiWartosciami = new int[nrOfChange];
    static int[] najmniejszeWartosci = new int[nrOfChange];

    public static void main(String[] args) throws InterruptedException {
        //projekt ulepszania poprzez rywalizacje pomiedzy watkami realizujacymi powierzone im zadania

        int nrOfTasks = 1000;

        Sudoku[] sudoku = new Sudoku[nrOfTasks];
        for(int i=0;i<nrOfTasks;i++)
            sudoku[i] = new Sudoku();

        int rozmiarCzynnikow = sudoku[0].rozmiarDanychDoWypelnienia();

        ExecutorService executor = Executors.newFixedThreadPool(nrOfTasks);
        List<Future<int[]>> list = new ArrayList<Future<int[]>>();
        int[] wynikWatkow = new int[nrOfTasks];

        Random random = new Random();

        int[][] tab = new int[nrOfTasks][rozmiarCzynnikow];//tworzenie tablic i przypisywanie im poczatkowych losowych wartosci
        for(int i=0;i<nrOfTasks;i++)//tablice zawieraja losowe wartosci wpisane do sudoku dla kazdego watku
            for(int j=0;j<rozmiarCzynnikow;j++)
                tab[i][j] = Math.abs(random.nextInt()%9)+1;
        int calkowitaSuma = 0;
        //int idWyniku

        for(int k=0;k<10000000;k++) {
            calkowitaSuma = 0;
            for(int i=0;i<nrOfChange;i++) {
                adresyZNajwiekszymiWartosciami[i] = 0;
                adresyZNajmniejszymiWartosciami[i] = 0;
                najmniejszeWartosci[i] = 400;
                najwiekszeWartosci[i] = 0;
            }

//            System.out.println("Rozpoczynam tworzenie wątków");

            for (int i = 0; i < nrOfTasks; i++) {
                Callable<int[]> callable = new ElementOfThread(tab[i], rozmiarCzynnikow, sudoku[i]);
                Future<int[]> future = executor.submit(callable);
                list.add(future);
            }

////        for(Future<int[]> future_string : list){
            for (int i = 0; i < list.size(); i++) {
                try {
                    wynikWatkow[i] = sudoku[i].sprawdzPoprawnosc(list.get(i).get(), rozmiarCzynnikow);
                    if(wynikWatkow[i] == 0)
                    {
                        System.out.println("Znaleziono wynik po "+k+" iteracjach !!!\n\n\n");
                        sudoku[i].wyswietlUzupelnione(tab[i]);
                        try { System.in.read(); } catch (IOException ex) { }
                    }
//                    if(k%1000 == 0)
//                        System.out.println(wynikWatkow[i]);
                    calkowitaSuma += wynikWatkow[i];
                    przetestujWartosc(wynikWatkow[i],i);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            list.clear();
            if(k%1000 == 0)
                System.out.println("Calkowita suma: " + calkowitaSuma);

            for(int i=0;i<nrOfChange;i++)
            {
//                skopiujWatkiZTychNajlepszych(tab[adresyZNajwiekszymiWartosciami[i]],tab[adresyZNajmniejszymiWartosciami[i]],rozmiarCzynnikow);
                skombinujWatkiZTychNajlepszych(tab[adresyZNajwiekszymiWartosciami[i]],tab,rozmiarCzynnikow);
            }

//            System.out.println("\n");
        }
        executor.shutdown();
        for (int i = 0; i < nrOfTasks; i++) {
            //System.out.println(wynikWatkow[i]);
            sudoku[i].wyswietlUzupelnione(tab[i]);
        }
    }

    static void przetestujWartosc(int wartosc,int id)
    {
        if(wartosc> najwiekszeWartosci[0])//najmniejsze wartosci na poczatku tablicy
        {
            for(int i=1;i<nrOfChange;i++) {
                if (wartosc < najwiekszeWartosci[i]) {
                    przesunTablice(najwiekszeWartosci, i + 1);
                    najwiekszeWartosci[i - 1] = wartosc;
                    adresyZNajwiekszymiWartosciami[i - 1] = id;
                    break;
                }
                if (i == nrOfChange-1) {
                    przesunTablice(najwiekszeWartosci, i + 1);
                    najwiekszeWartosci[i] = wartosc;
                    adresyZNajwiekszymiWartosciami[i] = id;
                }
            }
        }

        if(wartosc< najmniejszeWartosci[0])//najwieksze warosci na poczatku tablicy
        {
            for(int i=1;i<nrOfChange;i++) {
                if (wartosc > najmniejszeWartosci[i]) {
                    przesunTablice(najmniejszeWartosci,i+1);
                    najmniejszeWartosci[i - 1] = wartosc;
                    adresyZNajmniejszymiWartosciami[i - 1] = id;
                    break;
                }
                if (i == nrOfChange-1) {
                    przesunTablice(najmniejszeWartosci, i + 1);
                    najmniejszeWartosci[i] = wartosc;
                    adresyZNajmniejszymiWartosciami[i] = id;
                }
            }
        }
    }

    static void losujWartosciDlaNowegoWatku(int tab[], int rozmiar)
    {
        for(int i=0;i<rozmiar;i++)
        {
            Random random = new Random();
            tab[i] = Math.abs(random.nextInt()%9)+1;
        }
    }

    static void skopiujWatkiZTychNajlepszych(int tab[],int tab2[], int rozmiar)
    {
        for(int i=0;i<rozmiar;i++)
        {
            tab[i] = tab2[i];
        }
    }

    static void przesunTablice(int[] tab,int id)
    {
        for(int i=0;i<(id-1);i++)
        {
            tab[i] = tab[i+1];
        }
    }

    static void skombinujWatkiZTychNajlepszych(int tab[],int calaTab[][],int rozmiar)
    {
        Random random = new Random();
        int adrTab1 = Math.abs(random.nextInt()%nrOfChange);
        int adrTab2 = Math.abs(random.nextInt()%nrOfChange);
        int tmpLosowanie;
        for(int i=0;i<rozmiar;i++)
        {
            tmpLosowanie = random.nextInt()%2;
            if(tmpLosowanie == 0)
                tab[i] = calaTab[adresyZNajmniejszymiWartosciami[adrTab1]][i];
            else
                tab[i] = calaTab[adresyZNajmniejszymiWartosciami[adrTab2]][i];
        }

    }

}
