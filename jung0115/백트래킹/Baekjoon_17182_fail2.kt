// 2차시 2024.07.20.토 : 백준 - 우주탐사선(17182) - 시간 초과
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import java.util.LinkedList
import java.util.Queue
import kotlin.math.min

data class Path(
  val position: Int, // 현재 있는 행성 위치
  val time: Int, // 지금까지 이동에 소요된 시간
  val visitedCnt: Int, // 지금까지 방문한 행성의 수
  val visitedMask: Int, // 행성들 방문 체크 - ⭐️ 비트마스킹
)

fun main() {
  // ✅ Input
  val br = BufferedReader(InputStreamReader(System.`in`))
  var st = StringTokenizer(br.readLine())

  var N: Int = st.nextToken().toInt() // 행성의 개수
  var K: Int = st.nextToken().toInt() // 발사되는 행성의 위치 = 시작점

  // 각 행성간 이동거리
  var planet = Array(N, {Array(N, {0})})
  for(i: Int in 0..N-1) {
    st = StringTokenizer(br.readLine())
    for(j: Int in 0..N-1) {
      planet[i][j] = st.nextToken().toInt()
    }
  }

  // ✅ Solve
  var planetPath = LinkedList<Path>() as Queue<Path>
  var visitedMask = 1 shl K // ⭐️ ex) 0001이면 첫번째 행성만 방문한 것
  planetPath.offer(Path(K, 0, 1, visitedMask))

  var answer = 1000 * 10

  while(!planetPath.isEmpty()) {
    val current = planetPath.poll()

    // 모든 행성을 탐색했다면
    if(current.visitedCnt == N) {
      answer = min(answer, current.time)
      continue
    }
    
    for(i: Int in 0..N-1) {
      // 현재 위치랑 같거나 이미 현재의 최소 시간을 넘어섰다면 
      if(current.position == i || current.time >= answer) continue

      var newVisitedCnt = current.visitedCnt
      if((current.visitedMask and (1 shl i)) == 0) newVisitedCnt++

      val newVisitedMask = current.visitedMask or (1 shl i)

      planetPath.offer(
        Path(
          position = i,
          time = current.time + planet[current.position][i],
          newVisitedCnt,
          visitedMask = newVisitedMask
        )
      )
    }
  }

  // ✅ Output
  print(answer)
}