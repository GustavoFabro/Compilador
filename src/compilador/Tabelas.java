package compilador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 *
 * @author gustavofabro
 */
public class Tabelas {

    public int[][] tabParsing = new int[100][100];

    private final int tabRegras[][] = {
            {7, 35, 59, 33},
            {60, 61, 62, 63},
            {19, 40, 64, 200, 27, 65, 44, 66},
            {10},
            {37},
            {41},
            {36},
            {40, 200, 64, 27, 65, 44, 66},
            {10},
            {18, 68, 67, 44, 69},
            {10},
            {40, 200, 202, 64},
            {49, 40, 200, 202, 64},
            {10, 203},
            {68, 67, 44, 69},
            {10},
            {22, 43, 37, 48, 37, 42, 4, 70},
            {6},
            {20, 71,},
            {43, 37, 42},
            {10},
            {12},
            {6},
            {20, 71},
            {12},
            {72, 40, 206, 73, 35, 62, 63, 74, 33, 44, 60}, //26
            {10},
            {2},
            {12},
            {6},
            {52, 68, 40, 200, 204, 75, 51}, //31
            {10},
            {49, 68, 40, 200, 204, 75},
            {10},
            {3, 94, 44},
            {10},
            {40, 201},
            {23},
            {14},
            {8, 76, 77, 13},
            {44, 76, 77},
            {10},
            {9, 52, 95, 51, 35, 76, 77, 33, 79},
            {1, 52, 95, 96, 51, 35, 76, 77, 33},
            {17, 35, 76, 33, 1, 52, 95, 96, 51},
            {5, 52, 40, 201, 80, 51},
            {21, 40, 201, 81},
            {15, 52, 82, 83, 51},
            {11, 52, 97, 44, 95, 44, 84, 51, 35, 76, 33},
            {40, 201, 45, 85}, //50
            {10},
            {40, 201, 98, 99},
            {25},
            {30},
            {24},
            {29},
            {26},
            {28},
            {53, 95},
            {34, 95},
            {55, 95},
            {10},
            {40, 201, 27, 99},
            {40, 201},
            {38},
            {37},
            {49, 40, 201, 80},
            {10},
            {40, 87},
            {31},
            {56},
            {32, 99},
            {57, 99},
            {50, 99},
            {47, 99},
            {52, 67, 51},
            {10},
            {39},
            {85},
            {49, 82, 83},
            {10},
            {88, 89, 90},
            {91, 92},
            {37},
            {40, 201},
            {36},
            {38},
            {52, 85, 51},
            {26, 93},
            {30, 93},
            {25, 93},
            {24, 93},
            {29, 93},
            {28, 93},
            {10},
            {32, 88, 89},
            {57, 88, 89},
            {88, 89,},
            {32, 88, 89},
            {57, 88, 89},
            {34, 88, 89},
            {10},
            {50, 91, 92},
            {47, 91, 92},
            {53, 91, 92},
            {10},
            {16, 35, 76, 77, 33},
            {10}
    };


    public Tabelas() {
        //Tabela de Parsing
        tabParsing[58][7] = 1;
        tabParsing[59][8] = 2;
        tabParsing[59][6] = 2;
        tabParsing[59][12] = 2;
        tabParsing[59][18] = 2;
        tabParsing[59][19] = 2;
        tabParsing[59][33] = 2;
        tabParsing[59][2] = 2;
        tabParsing[60][2] = 26;
        tabParsing[60][6] = 26;
        tabParsing[60][8] = 27;
        tabParsing[60][12] = 26;
        tabParsing[60][18] = 27;
        tabParsing[60][19] = 27;
        tabParsing[60][33] = 27;
        tabParsing[61][18] = 4;
        tabParsing[61][19] = 3;
        tabParsing[61][33] = 4;
        tabParsing[61][8] = 4;
        tabParsing[62][8] = 11;
        tabParsing[62][18] = 10;
        tabParsing[63][8] = 40;
        tabParsing[64][27] = 14;
        tabParsing[64][44] = 14;
        tabParsing[64][49] = 13;
        tabParsing[64][51] = 14;
        tabParsing[65][36] = 7;
        tabParsing[65][37] = 5;
        tabParsing[65][41] = 6;
        tabParsing[66][8] = 9;
        tabParsing[66][18] = 9;
        tabParsing[66][33] = 9;
        tabParsing[66][40] = 8;
        tabParsing[67][40] = 12;
        tabParsing[68][6] = 18;
        tabParsing[68][12] = 22;
        tabParsing[68][20] = 19;
        tabParsing[68][22] = 17;
        tabParsing[69][6] = 15;
        tabParsing[69][8] = 16;
        tabParsing[69][12] = 15;
        tabParsing[69][20] = 15;
        tabParsing[69][22] = 15;
        tabParsing[70][6] = 23;
        tabParsing[70][12] = 25;
        tabParsing[70][20] = 24;
        tabParsing[71][40] = 21;
        tabParsing[71][43] = 20;
        tabParsing[72][2] = 28;
        tabParsing[72][6] = 30;
        tabParsing[72][12] = 29;
        tabParsing[73][35] = 32;
        tabParsing[73][52] = 31;
        tabParsing[74][3] = 35;
        tabParsing[74][33] = 36;
        tabParsing[75][49] = 33;
        tabParsing[75][51] = 34;
        tabParsing[76][1] = 44;
        tabParsing[76][3] = 51;
        tabParsing[76][5] = 46;
        tabParsing[76][9] = 43;
        tabParsing[76][11] = 49;
        tabParsing[76][13] = 51;
        tabParsing[76][15] = 48;
        tabParsing[76][17] = 45;
        tabParsing[76][19] = 51;
        tabParsing[76][21] = 47;
        tabParsing[76][33] = 51;
        tabParsing[76][40] = 50;
        tabParsing[76][44] = 51;
        tabParsing[77][13] = 42;
        tabParsing[77][33] = 42;
        tabParsing[77][44] = 41;
        tabParsing[79][3] = 108;
        tabParsing[79][13] = 108;
        tabParsing[79][16] = 107;
        tabParsing[79][19] = 108;
        tabParsing[79][33] = 108;
        tabParsing[79][44] = 108;
        tabParsing[80][49] = 67;
        tabParsing[80][51] = 68;
        tabParsing[81][3] = 77;
        tabParsing[81][13] = 77;
        tabParsing[81][19] = 77;
        tabParsing[81][33] = 77;
        tabParsing[81][44] = 77;
        tabParsing[81][52] = 76;
        tabParsing[82][36] = 79;
        tabParsing[82][37] = 79;
        tabParsing[82][38] = 79;
        tabParsing[82][39] = 78;
        tabParsing[82][40] = 79;
        tabParsing[82][52] = 79;
        tabParsing[83][49] = 80;
        tabParsing[83][51] = 81;
        tabParsing[84][40] = 69;
        tabParsing[85][36] = 82;
        tabParsing[85][37] = 82;
        tabParsing[85][38] = 82;
        tabParsing[85][40] = 82;
        tabParsing[85][52] = 82;
        tabParsing[87][31] = 70;
        tabParsing[87][32] = 72;
        tabParsing[87][47] = 75;
        tabParsing[87][50] = 74;
        tabParsing[87][56] = 71;
        tabParsing[87][57] = 73;
        tabParsing[88][36] = 83;
        tabParsing[88][37] = 83;
        tabParsing[88][38] = 83;
        tabParsing[88][40] = 83;
        tabParsing[88][52] = 83;
        tabParsing[89][3] = 102;
        tabParsing[89][13] = 102;
        tabParsing[89][19] = 102;
        tabParsing[89][24] = 102;
        tabParsing[89][25] = 102;
        tabParsing[89][26] = 102;
        tabParsing[89][28] = 102;
        tabParsing[89][29] = 102;
        tabParsing[89][30] = 102;
        tabParsing[89][32] = 99;
        tabParsing[89][33] = 102;
        tabParsing[89][34] = 101;
        tabParsing[89][44] = 102;
        tabParsing[89][49] = 102;
        tabParsing[89][51] = 102;
        tabParsing[89][57] = 100;
        tabParsing[90][3] = 95;
        tabParsing[90][13] = 95;
        tabParsing[90][19] = 95;
        tabParsing[90][24] = 92;
        tabParsing[90][25] = 91;
        tabParsing[90][26] = 89;
        tabParsing[90][28] = 94;
        tabParsing[90][29] = 93;
        tabParsing[90][30] = 90;
        tabParsing[90][33] = 95;
        tabParsing[90][44] = 95;
        tabParsing[90][49] = 95;
        tabParsing[90][51] = 95;
        tabParsing[91][36] = 86;
        tabParsing[91][37] = 84;
        tabParsing[91][38] = 87;
        tabParsing[91][40] = 85;
        tabParsing[91][52] = 88;
        tabParsing[92][3] = 106;
        tabParsing[92][13] = 106;
        tabParsing[92][19] = 106;
        tabParsing[92][24] = 106;
        tabParsing[92][25] = 106;
        tabParsing[92][26] = 106;
        tabParsing[92][28] = 106;
        tabParsing[92][29] = 106;
        tabParsing[92][30] = 106;
        tabParsing[92][32] = 106;
        tabParsing[92][33] = 106;
        tabParsing[92][34] = 106;
        tabParsing[92][44] = 106;
        tabParsing[92][47] = 104;
        tabParsing[92][49] = 106;
        tabParsing[92][50] = 103;
        tabParsing[92][51] = 106;
        tabParsing[92][53] = 105;
        tabParsing[92][57] = 106;
        tabParsing[93][32] = 96;
        tabParsing[93][36] = 98;
        tabParsing[93][37] = 98;
        tabParsing[93][38] = 98;
        tabParsing[93][40] = 98;
        tabParsing[93][52] = 98;
        tabParsing[93][57] = 97;
        tabParsing[94][14] = 39;
        tabParsing[94][23] = 38;
        tabParsing[94][40] = 37;
        tabParsing[95][40] = 52;
        tabParsing[96][34] = 60;
        tabParsing[96][51] = 62;
        tabParsing[96][53] = 59;
        tabParsing[96][55] = 61;
        tabParsing[97][40] = 63;
        tabParsing[98][24] = 55;
        tabParsing[98][25] = 53;
        tabParsing[98][26] = 57;
        tabParsing[98][28] = 58;
        tabParsing[98][29] = 56;
        tabParsing[98][30] = 54;
        tabParsing[99][37] = 66;
        tabParsing[99][38] = 65;
        tabParsing[99][40] = 64;
    }

    public ArrayList<Integer> getRegras(int row){
        int regras[] = new int[tabRegras[row].length];

        System.arraycopy(tabRegras[row], 0, regras, 0, tabRegras[row].length);

        List<Integer> list = IntStream.of(regras).boxed().collect(Collectors.toList());

        Collections.reverse(list);

        return new ArrayList<>(list);
    }
}