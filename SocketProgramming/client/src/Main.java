import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder stringBuilder = new StringBuilder();

        int caseCount = Integer.parseInt(br.readLine());

        for (int i = 1; i <= caseCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int D = Integer.parseInt(st.nextToken());

            LinkedList<Long> groundList = new LinkedList<>();
            LinkedList<Long> groundRecentCutList = new LinkedList<>();
//            Queue<Grass> grassQ = new LinkedList<>();

            for (int y = 0; y < N; y++) {
                StringTokenizer stGround = new StringTokenizer(br.readLine());
                for (int x = 0; x < M; x++) {
                    groundList.add(Long.parseLong(stGround.nextToken()));
                    groundRecentCutList.add(0L);
                }
            }

            Collections.sort(groundList);

            Long[] oils = new Long[D];
            StringTokenizer stOil = new StringTokenizer(br.readLine());
            for (int day = 0; day < D; day++) {
                oils[day] = Long.parseLong(stOil.nextToken());
            }

            Long answer = 0L;
            for (long day = 0; day < D; day++) {
                while (oils[(int) day] --> 0) {
                    // TODO : max + per day length ++
                    Long max = Collections.max(groundList);
                    int maxIdx = groundList.lastIndexOf(max);



                    answer += (max + day - groundRecentCutList.get(maxIdx)) * (day + 1);

                    groundList.set(maxIdx, 1L);
                    groundRecentCutList.set(maxIdx, day + 1);
                }
            }

            stringBuilder
                    .append("#")
                    .append(i)
                    .append(" ")
                    .append(answer)
                    .append("\n");

        }
        bw.write(stringBuilder.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
