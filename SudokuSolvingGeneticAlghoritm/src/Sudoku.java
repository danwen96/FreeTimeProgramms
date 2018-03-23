public class Sudoku {
    int[][] tablicaSudoku;
    int rozmiarTablicy = 9;
    int liczby[] = {1,2,3,4,5,6,7,8,9};
    int czyWykorzystane[] = {0,0,0,0,0,0,0,0,0};

    Sudoku()
    {
        tablicaSudoku = new int[rozmiarTablicy][rozmiarTablicy];
        wypelnijPierwotnymiWartosciami();
    }

    void wyswietl()
    {
        for(int i = 0;i<rozmiarTablicy;i++) {
            for (int j = 0; j < rozmiarTablicy; j++) {
                if(j%3 == 0)System.out.print(" ");
                System.out.print(tablicaSudoku[i][j] + " ");
            }
            if(i%3 == 2)System.out.println();
            System.out.println();
        }
    }

    void wyswietlUzupelnione(int tab[])
    {
        int licznik = 0;
        for(int i = 0;i<rozmiarTablicy;i++) {
            for (int j = 0; j < rozmiarTablicy; j++) {
                if(j%3 == 0)System.out.print(" ");
                if(tablicaSudoku[i][j] != 0)
                    System.out.print(tablicaSudoku[i][j] + " ");
                else {
                    System.out.print(tab[licznik]+ " ");
                    licznik++;
                }

            }
            if(i%3 == 2)System.out.println();
            System.out.println();
        }
    }

    int rozmiarDanychDoWypelnienia()
    {
        int doZwrocenia = 0;
        for(int i=0;i<rozmiarTablicy;i++)
            for(int j=0;j<rozmiarTablicy;j++)
                if(tablicaSudoku[i][j] == 0) doZwrocenia++;
        return doZwrocenia;
    }

    int sprawdzPoprawnosc(int[] czynniki, int n) {
        int[][] tab = new int[9][9];

        int k = 0;
        for (int i = 0; i < 9; i++) {//wypelniam puste pola dostarczonymi czynnikami
            for (int j = 0; j < 9; j++) {
                if (tablicaSudoku[i][j] == 0) {
                    tab[i][j] = czynniki[k];
                    k++;
                } else
                    tab[i][j] = tablicaSudoku[i][j];
            }
        }

        int roznica = 0;
//        int[][] sumy = new int[3][9]; // 1 dla wierszowego, 2 dla kolumnowego, 3 dla okienek
//
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 9; j++)
//                sumy[i][j] = 0;
//
//        for (int j = 0; j < 9; j++)
//            for (int i = 0; i < 9; i++) {//sprawdzenie poprawnosci, wypelnienia sudoku, wierszowo i kolumnami
//                sumy[0][j] += tab[j][i];
//                sumy[1][j] += tab[i][j];
//            }
//
//        for (int i = 0; i < 9; i++)
//            for(int a = 0;a<3;a++)
//                for(int b=0;b<3;b++) {//tu sprawdzenie w kazdym rogu i posrodku
//                    sumy[2][i] += tab[a+(i/3)*3][b+(i%3)*3];
//                }
//
//
//        for(int i=0;i<3;i++)
//            for(int j=0;j<9;j++)//zsumowanie lacznej roznicy od prawidlowego wyniku( w postaci kwadratu)
//                roznica += Math.abs(sumy[i][j]-45);


        int sumaRoznosci = 0;
        for(int i=0;i<9;i++)
        {

            for(int j=0;j<9;j++)
            {
                for(int l=0;l<9;l++) {
                    if (tab[i][j] == liczby[l])
                    {
                        czyWykorzystane[l]=1;
                    }
                }
            }
            sumaRoznosci = 0;
            for(int l=0;l<9;l++) {
                sumaRoznosci += czyWykorzystane[l];
                czyWykorzystane[l] = 0;
            }
            roznica += (9 - sumaRoznosci)*1;
        }

        sumaRoznosci = 0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                for(int l=0;l<9;l++) {
                    if (tab[j][i] == liczby[l])
                    {
                        czyWykorzystane[l]=1;
                    }
                }
            }
            sumaRoznosci = 0;
            for(int l=0;l<9;l++) {
                sumaRoznosci += czyWykorzystane[l];
                czyWykorzystane[l] = 0;
            }
            roznica += (9 - sumaRoznosci)*1;
        }

        sumaRoznosci = 0;
        for(int r=0;r<3;r++)
            for(int p=0;p<3;p++) {

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        for(int l=0;l<9;l++) {
                            if (tab[r*3 + j][p*3 + i] == liczby[l])
                            {
                                czyWykorzystane[l]=1;
                            }

                        }
                    }

                }
                sumaRoznosci = 0;
                for(int l=0;l<9;l++) {
                    sumaRoznosci += czyWykorzystane[l];
                    czyWykorzystane[l] = 0;
                }
                roznica += (9 - sumaRoznosci)*1;
            }
       return roznica;
    }

    void wypelnijPierwotnymiWartosciami()
    {
        for(int i = 0;i<rozmiarTablicy;i++)
            for(int j=0;j<rozmiarTablicy;j++)
                tablicaSudoku[i][j] = 0;

        tablicaSudoku[0][0] = 2;
        tablicaSudoku[0][1] = 3;
        tablicaSudoku[0][4] = 5;
        tablicaSudoku[0][7] = 6;
        tablicaSudoku[1][1] = 6;
        tablicaSudoku[1][2] = 7;
        tablicaSudoku[1][6] = 3;
        tablicaSudoku[1][8] = 9;
        tablicaSudoku[2][1] = 1;
        tablicaSudoku[2][3] = 9;
        tablicaSudoku[2][6] = 2;
        tablicaSudoku[2][8] = 5;

        tablicaSudoku[3][0] = 3;
        tablicaSudoku[3][2] = 6;
        tablicaSudoku[3][4] = 2;
        tablicaSudoku[3][6] = 8;
        tablicaSudoku[3][7] = 4;
        tablicaSudoku[4][3] = 3;
        tablicaSudoku[4][5] = 4;
        tablicaSudoku[5][1] = 9;
        tablicaSudoku[5][2] = 4;
        tablicaSudoku[5][4] = 1;
        tablicaSudoku[5][6] = 7;
        tablicaSudoku[5][8] = 2;

        tablicaSudoku[6][0] = 7;
        tablicaSudoku[6][2] = 3;
        tablicaSudoku[6][5] = 1;
        tablicaSudoku[6][7] = 9;
        tablicaSudoku[7][0] = 6;
        tablicaSudoku[7][2] = 1;
        tablicaSudoku[7][6] = 5;
        tablicaSudoku[7][7] = 2;
        tablicaSudoku[8][1] = 4;
        tablicaSudoku[8][4] = 7;
        tablicaSudoku[8][7] = 1;
        tablicaSudoku[8][8] = 3;
    }
}
