package SWEA.D2_D3;

import java.io.*;
import java.util.*;

public class SWEA24420 {
    static int t, a, b;
    static Set<Integer> setA, setB;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            setA = new HashSet<>();
            setB = new HashSet<>();

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < a; i++){
                setA.add(Integer.parseInt(st.nextToken()));
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0 ; i < b; i++){
                setB.add(Integer.parseInt(st.nextToken()));
            }

            char res = '=';

            loop:
            if (a == b){
                for (int aa : setA){
                    if (!setB.contains(aa)){
                        res = '?';
                        break loop;
                    }
                }
            } else if (a > b){
                // a가 더 크니까, b의 모든 원소가 a의 원소에 포함되어 있어야 부분집합
                for (int bb : setB){
                    if (!setA.contains(bb)){
                        res = '?';
                        break loop;
                    }
                }
                res = '>';
            } else {
                for (int aa : setA){
                    if (!setB.contains(aa)){
                        res = '?';
                        break loop;
                    }
                }
                res = '<';
            }
            System.out.println(res);
        }
    }
}
